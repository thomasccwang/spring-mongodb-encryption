package com.thomasccwang.encryption;

public interface Encryption {

    String encrypt(String plainText);

    String decrypt(String cipher);
}
