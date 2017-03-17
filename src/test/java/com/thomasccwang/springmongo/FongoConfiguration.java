package com.thomasccwang.springmongo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.github.fakemongo.Fongo;
import com.mongodb.Mongo;
import com.thomasccwang.springmongo.event.EncryptionMongoEventListener;

@Configuration
@EnableMongoRepositories("com.thomasccwang.springmongo.repository")
public class FongoConfiguration extends AbstractMongoConfiguration {

    @Override
    protected String getDatabaseName() {
        return "foo";
    }

    @Override
    @Bean
    public Mongo mongo() throws Exception {
        return new Fongo("foo-test").getMongo();
    }

    @Override
    protected String getMappingBasePackage() {
        return "com.thomasccwang.springmongo.model";
    }

    @Bean
    public EncryptionMongoEventListener encryptionEventListener() {
        return new EncryptionMongoEventListener(new TestEncryptionImpl());
    }

}
