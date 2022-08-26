package me.potato.orderservice.client;

import me.potato.orderservice.dto.TransactionRequestDto;
import me.potato.orderservice.dto.TransactionStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import reactor.test.StepVerifier;

@RestClientTest(UserClient.class)
class UserClientTest {
    @Autowired
    private UserClient userClient;

    @Test
    void getUserById() {
        StepVerifier.create(userClient.getUserById(1L))
                .expectNextMatches(user -> user.getName().equals("sam"))
                .expectComplete()
                .verify();
    }

    @Test
    void getAllUsers() {
        StepVerifier.create(userClient.getAllUsers())
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }


    @Test
    void authorizeTransaction() {
        StepVerifier.create(userClient.authorizeTransaction(new TransactionRequestDto(1L, 100000)))
                .expectNextMatches(transaction -> transaction.getStatus().equals(TransactionStatus.DECLINED))
                .expectComplete()
                .verify();
    }


}