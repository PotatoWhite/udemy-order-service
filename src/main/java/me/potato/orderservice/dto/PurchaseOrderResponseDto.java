package me.potato.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderResponseDto {
    private Long        orderId;
    private Long        userId;
    private String      productId;
    private Integer     amount;
    private OrderStatus status;
}
