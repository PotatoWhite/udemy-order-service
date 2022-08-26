package me.potato.orderservice.client;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import reactor.test.StepVerifier;


@RestClientTest(ProductClient.class)
class ProductClientTest {
    @Autowired
    private ProductClient productClient;

    @Test
    void getProductById() {
        StepVerifier.create(productClient.getProductById("6308da83b8353637ee7d080f"))
                .expectNextMatches(product -> product.getPrice() == 800)
                .expectComplete()
                .verify();
    }

    @Test
    void getAllProducts() {
        StepVerifier.create(productClient.getAllProducts())
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

}