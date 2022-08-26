package me.potato.orderservice;

import me.potato.orderservice.client.ProductClient;
import me.potato.orderservice.client.UserClient;
import me.potato.orderservice.dto.ProductDto;
import me.potato.orderservice.dto.PurchaseOrderRequestDto;
import me.potato.orderservice.dto.UserDto;
import me.potato.orderservice.service.OrderFulfillmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private UserClient userClient;

    @Autowired
    private ProductClient productClient;

    @Autowired
    private OrderFulfillmentService orderFulfillmentService;

    @Test
    void contextLoads() {
        var dtoFlux = Flux.zip(userClient.getAllUsers(), productClient.getAllProducts())
                .map(t -> buildDto(t.getT1(), t.getT2()))
                .flatMap(dto -> this.orderFulfillmentService.processOrder(Mono.just(dto)))
                .doOnNext(System.out::println);

        StepVerifier.create(dtoFlux)
                .expectNextCount(4)
                .verifyComplete();

    }

    private PurchaseOrderRequestDto buildDto(UserDto userDto, ProductDto productDto) {
        var dto = new PurchaseOrderRequestDto();
        dto.setProductId(productDto.getId());
        dto.setUserId(userDto.getId());
        return dto;
    }

}
