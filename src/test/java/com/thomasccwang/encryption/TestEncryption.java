package com.thomasccwang.encryption;

public class TestEncryption implements Encryption {

    @Override
    public String encrypt(String plainText) {
        if (plainText == null) {
            return null;
        }
        return "foo" + plainText;
    }

    @Override
    public String decrypt(String encrypted) {
        if (encrypted == null) {
            return null;
        }
        if (!encrypted.startsWith("foo")) {
            // incorrect "encryption"
            return encrypted;
        }
        return encrypted.substring(3);
    }

}
