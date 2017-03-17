package com.thomasccwang.springmongo.event;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.thomasccwang.springmongo.annotation.PersistEncrypted;
import com.thomasccwang.springmongo.encryption.Encryption;

class DecryptCallback implements FieldCallback {

    private final Object source;
    private final Encryption encryption;

    public DecryptCallback(Object source, Encryption encryption) {
        this.source = source;
        this.encryption = encryption;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(PersistEncrypted.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        if (field.getType() == String.class) {
            String fieldValue = (String) ReflectionUtils.getField(field, source);
            if (StringUtils.isBlank(fieldValue)) {
                return;
            }

            String decryptedValue = null;
            try {
                decryptedValue = encryption.decrypt(fieldValue);
            } catch (Exception e) {

            }
            if (decryptedValue != null) {
                ReflectionUtils.setField(field, source, decryptedValue);
            }
        } else {
            Object fieldObject = ReflectionUtils.getField(field, source);
            if (fieldObject != null) {
                ReflectionUtils.doWithFields(fieldObject.getClass(),
                        new DecryptCallback(fieldObject, encryption),
                        ReflectionUtils.COPYABLE_FIELDS);
            }
        }
    }
}
