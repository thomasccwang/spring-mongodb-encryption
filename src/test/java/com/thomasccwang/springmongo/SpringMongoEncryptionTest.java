package com.thomasccwang.springmongo;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.thomasccwang.springmongo.model.CreditCard;
import com.thomasccwang.springmongo.model.Customer;
import com.thomasccwang.springmongo.repository.CustomerRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@Import(FongoConfiguration.class)
@ContextConfiguration(classes = {SpringMongoEncryptionTest.class})
public class SpringMongoEncryptionTest {

    @Inject
    private CustomerRepository repository;

    @Inject
    private Mongo mongo;

    @Before
    public void init() {
        repository.deleteAll();
    }

    @Test
    public void testObjectPersisted() {
        Customer customer = createCustomer();
        Assert.assertNull(customer.getId());
        Customer persistedCustomer = repository.save(customer);
        Assert.assertEquals(1, repository.findAll().size());
        Assert.assertNotNull(persistedCustomer.getId());
    }

    @Test
    public void testObjectFieldsNotEncrypted() {
        Customer customer = repository.save(createCustomer());
        Assert.assertEquals("johndoe@email.com", customer.getEmail());
        CreditCard card = customer.getCreditCard();
        Assert.assertEquals("1111222233334444", card.getNumber());
    }

    @Test
    public void testFieldEncryptedInMongo() {
        Customer customer = repository.save(createCustomer());

        DBCollection dbCollection = getCustomerCollection();
        DBObject query = new BasicDBObject("firstName", "John");
        DBObject dbCustomer = dbCollection.findOne(query);
        String encryptedEmail = "foo" + customer.getEmail();
        Assert.assertEquals(encryptedEmail, dbCustomer.get("email"));
    }

    @Test
    public void testNestedFieldEncryptedInongo() {
        Customer customer = repository.save(createCustomer());
        CreditCard card = customer.getCreditCard();

        DBCollection dbCollection = getCustomerCollection();
        DBObject query = new BasicDBObject("firstName", "John");
        DBObject dbCustomer = dbCollection.findOne(query);
        DBObject dbCard = (DBObject) dbCustomer.get("creditCard");
        String encryptedCardNumber = "foo" + card.getNumber();
        Assert.assertEquals(encryptedCardNumber, dbCard.get("number"));
    }

    private Customer createCustomer() {
        CreditCard card = new CreditCard("John Doe", "1111222233334444");
        Customer customer = Customer.builder()
                .firstName("John")
                .lastName("Doe")
                .email("johndoe@email.com")
                .creditCard(card)
                .build();
        return customer;
    }

    @SuppressWarnings("deprecation")
    private DBCollection getCustomerCollection() {
        return mongo.getDB("foo").getCollection("customer");
    }
}
