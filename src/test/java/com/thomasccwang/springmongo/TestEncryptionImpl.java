package com.thomasccwang.springmongo;

import com.thomasccwang.encryption.Encryption;

public class TestEncryptionImpl implements Encryption {

    @Override
    public String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        return "foo" + plainText;
    }

    @Override
    public String decrypt(String cipher) {
        if (cipher == null) {
            return null;
        }
        if (!cipher.startsWith("foo")) {
            // incorrect "encryption"
            return cipher;
        }
        return cipher.substring(3);
    }

}
