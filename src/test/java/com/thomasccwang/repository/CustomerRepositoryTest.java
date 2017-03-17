package com.thomasccwang.repository;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.thomasccwang.configuration.FongoConfiguration;
import com.thomasccwang.model.CreditCard;
import com.thomasccwang.model.Customer;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(FongoConfiguration.class)
@ContextConfiguration(classes = {CustomerRepositoryTest.class})
public class CustomerRepositoryTest {

    @Inject
    private CustomerRepository repository;

    @Inject
    private Mongo mongo;

    @Test
    public void testQueryMongo() {
        CreditCard card = new CreditCard("John Doe", "1111222233334444");
        Customer john = Customer.builder().firstName("John").lastName("Doe")
                .email("johndoe@email.com").creditCard(card).build();
        repository.save(john);

        // query mongo directly and inspect the DBObject
        DBCollection dbCollection = mongo.getDB("foo").getCollection("customer");
        DBObject query = new BasicDBObject("firstName", "John");
        DBObject dbCustomer = dbCollection.findOne(query);
        Assert.assertEquals(john.getFirstName(), dbCustomer.get("firstName"));
        Assert.assertEquals(john.getLastName(), dbCustomer.get("lastName"));
        String encryptedEmail = "foo" + john.getEmail();
        Assert.assertEquals(encryptedEmail, dbCustomer.get("email"));

        DBObject dbCard = (DBObject) dbCustomer.get("creditCard");
        Assert.assertEquals(card.getName(), dbCard.get("name"));
        String encryptedCardNumber = "foo" + card.getNumber();
        Assert.assertEquals(encryptedCardNumber, dbCard.get("number"));

    }
}
