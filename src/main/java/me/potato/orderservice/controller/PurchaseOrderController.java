package me.potato.orderservice.controller;

import lombok.RequiredArgsConstructor;
import me.potato.orderservice.dto.PurchaseOrderRequestDto;
import me.potato.orderservice.dto.PurchaseOrderResponseDto;
import me.potato.orderservice.service.OrderFulfillmentService;
import me.potato.orderservice.service.OrderQueryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("order")
public class PurchaseOrderController {
    private final OrderFulfillmentService orderFulfillmentService;

    private final OrderQueryService orderQueryService;

    @PostMapping
    public Mono<ResponseEntity<PurchaseOrderResponseDto>> purchaseOrder(@RequestBody Mono<PurchaseOrderRequestDto> purchaseOrderRequestDto) {
        return orderFulfillmentService.processOrder(purchaseOrderRequestDto)
                .map(ResponseEntity::ok)
                .onErrorReturn(WebClientResponseException.class, ResponseEntity.badRequest().build()) // WebClientResponseException comes from WebClient
                .onErrorReturn(WebClientRequestException.class, ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()); // WebClientRequestException comes from WebClient
    }

    @GetMapping("/{userId}")
    public Flux<PurchaseOrderResponseDto> getProductsByUserId(@PathVariable Long userId) {
        return orderQueryService.getProductsByUserId(userId);
    }
}
