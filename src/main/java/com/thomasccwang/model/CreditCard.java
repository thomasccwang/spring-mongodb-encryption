package com.thomasccwang.model;

import com.thomasccwang.annotation.PersistEncrypted;

public class CreditCard {

    private String name;
    @PersistEncrypted
    private String number;

    public CreditCard(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}
