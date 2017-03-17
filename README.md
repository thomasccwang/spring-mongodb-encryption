# spring-mongodb-encryption

This project was created for those working with Spring Data MongoDB (http://projects.spring.io/spring-data-mongodb/), and need a way to encrypt specific fields when documents are saved in MongoDB.

There is an open jira ticket (https://jira.spring.io/browse/DATAMONGO-874) to request such a feature, but as of now it has not been implemented.

By utilizing Spring's field reflections and Spring Data MongoDB's event listeners, it is possible to specify fields to be encrypted when persisted in MongoDB using annotations.

Note: this project does not include any specific implementation of encryption algorithm. Simply implement the Encryption interface using a desired encryption algorithm.

