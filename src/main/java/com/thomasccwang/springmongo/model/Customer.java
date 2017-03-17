package com.thomasccwang.springmongo.model;

import org.springframework.data.annotation.Id;

import com.thomasccwang.springmongo.annotation.PersistEncrypted;

public class Customer {

    @Id
    private String id;
    private String firstName;
    private String lastName;
    @PersistEncrypted
    private String email;
    @PersistEncrypted
    private CreditCard creditCard;

    public static Builder builder() {
        return new Builder();
    }

    public Customer() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public static class Builder {
        private String firstName;
        private String lastName;
        private String email;
        private CreditCard creditCard;

        private Builder() {
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder creditCard(CreditCard creditCard) {
            this.creditCard = creditCard;
            return this;
        }

        public Customer build() {
            Customer customer = new Customer();
            customer.setFirstName(firstName);
            customer.setLastName(lastName);
            customer.setEmail(email);
            customer.setCreditCard(creditCard);
            return customer;
        }
    }
}
