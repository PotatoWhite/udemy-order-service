package me.potato.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class PurchaseOrderRequestDto {
    private Long   userId;
    private String productId;
}
