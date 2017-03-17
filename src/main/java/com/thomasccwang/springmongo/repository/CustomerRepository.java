package com.thomasccwang.springmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.thomasccwang.springmongo.model.Customer;

public interface CustomerRepository extends MongoRepository<Customer, String> {

}
