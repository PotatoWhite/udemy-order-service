package me.potato.orderservice.service;

import lombok.RequiredArgsConstructor;
import me.potato.orderservice.dto.PurchaseOrderResponseDto;
import me.potato.orderservice.repository.PurchaseOrderRepository;
import me.potato.orderservice.util.EntityDtoUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@Service
public class OrderQueryService {
    private final PurchaseOrderRepository purchaseOrderRepository;

    public Flux<PurchaseOrderResponseDto> getProductsByUserId(Long userId) {
        return Flux.fromStream(() -> purchaseOrderRepository.findByUserId(userId).stream())
                .map(EntityDtoUtil::getPurchaseOrderResponseDto)
                .subscribeOn(Schedulers.boundedElastic());
    }

}
