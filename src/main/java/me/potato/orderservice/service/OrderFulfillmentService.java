package me.potato.orderservice.service;

import lombok.RequiredArgsConstructor;
import me.potato.orderservice.client.ProductClient;
import me.potato.orderservice.client.UserClient;
import me.potato.orderservice.dto.PurchaseOrderRequestDto;
import me.potato.orderservice.dto.PurchaseOrderResponseDto;
import me.potato.orderservice.dto.RequestContext;
import me.potato.orderservice.repository.PurchaseOrderRepository;
import me.potato.orderservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.retry.Retry;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class OrderFulfillmentService {

    private final PurchaseOrderRepository purchaseOrderRepository;
    private final ProductClient           productClient;
    private final UserClient              userClient;

    public Mono<PurchaseOrderResponseDto> processOrder(Mono<PurchaseOrderRequestDto> requestMono) {
        return requestMono.map(RequestContext::new)
                .flatMap(this::productRequestResponse)
                .doOnNext(EntityDtoUtil::setTransactionRequestDto)
                .flatMap(this::userTransactionRequestResponse)
                .map(EntityDtoUtil::getOPurchaseOrder)
                .publishOn(Schedulers.boundedElastic())
                .map(purchaseOrderRepository::save) //blocking
                .map(EntityDtoUtil::getPurchaseOrderResponseDto);
    }

    private Mono<RequestContext> productRequestResponse(RequestContext rc) {
        return this.productClient.getProductById(rc.getPurchaseOrderRequestDto().getProductId())
                .doOnNext(rc::setProductDto)
                .retryWhen(Retry.fixedDelay(5, Duration.ofSeconds(1)))
                .thenReturn(rc);
    }

    private Mono<RequestContext> userTransactionRequestResponse(RequestContext rc) {
        return this.userClient.authorizeTransaction(rc.getTransactionRequestDto())
                .doOnNext(rc::setTransactionResponseDto)
                .thenReturn(rc);
    }
}
