package com.thomasccwang.event;

import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.util.ReflectionUtils;

import com.mongodb.DBObject;
import com.thomasccwang.encryption.Encryption;

/**
 * Register as a Bean with a Spring Data MongoDB configuration to enable encryption on fields.
 */
public class EncryptionMongoEventListener extends AbstractMongoEventListener<Object> {

    private final Encryption encryption;

    public EncryptionMongoEventListener(Encryption encryption) {
        this.encryption = encryption;
    }

    @Override
    public void onBeforeSave(BeforeSaveEvent<Object> event) {
        Object source = event.getSource();
        DBObject dbObject = event.getDBObject();
        ReflectionUtils.doWithFields(source.getClass(),
                new EncryptCallback(source, dbObject, encryption),
                ReflectionUtils.COPYABLE_FIELDS);
    }

    @Override
    public void onAfterConvert(AfterConvertEvent<Object> event) {
        Object source = event.getSource();
        ReflectionUtils.doWithFields(source.getClass(),
                new DecryptCallback(source, encryption),
                ReflectionUtils.COPYABLE_FIELDS);
    }
}
