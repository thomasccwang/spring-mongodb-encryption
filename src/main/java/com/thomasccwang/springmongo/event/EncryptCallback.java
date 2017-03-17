package com.thomasccwang.springmongo.event;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;

import com.mongodb.DBObject;
import com.thomasccwang.springmongo.annotation.PersistEncrypted;
import com.thomasccwang.springmongo.encryption.Encryption;

class EncryptCallback implements FieldCallback {

    private final Object source;
    private final DBObject dbObject;
    private final Encryption encryption;

    public EncryptCallback(Object source, DBObject dbObject, Encryption encryption) {
        this.source = source;
        this.dbObject = dbObject;
        this.encryption = encryption;
    }

    @Override
    public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
        if (!field.isAnnotationPresent(PersistEncrypted.class)) {
            return;
        }
        ReflectionUtils.makeAccessible(field);
        if (field.getType() == String.class) {
            String plainText = (String) ReflectionUtils.getField(field, source);
            if (StringUtils.isBlank(plainText)) {
                return;
            }

            String encryptedValue = null;
            try {
                encryptedValue = encryption.encrypt(plainText);
            } catch (Exception e) {

            }
            if (encryptedValue != null) {
                dbObject.put(field.getName(), encryptedValue);
            }
        } else {
            Object fieldObject = ReflectionUtils.getField(field, source);
            if (fieldObject != null) {
                DBObject fieldDbObject = (DBObject) dbObject.get(field.getName());
                ReflectionUtils.doWithFields(fieldObject.getClass(),
                        new EncryptCallback(fieldObject, fieldDbObject, encryption),
                        ReflectionUtils.COPYABLE_FIELDS);
            }
        }
    }
}
