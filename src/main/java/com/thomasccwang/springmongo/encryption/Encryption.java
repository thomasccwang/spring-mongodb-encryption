package com.thomasccwang.springmongo.encryption;

public interface Encryption {

    String encrypt(String plainText);

    String decrypt(String cipher);
}
