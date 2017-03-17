package com.thomasccwang.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thomasccwang.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
