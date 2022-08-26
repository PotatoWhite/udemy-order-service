package me.potato.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TransactionResponseDto {
    private Long              userId;
    private Integer           amount;
    private TransactionStatus status;
}
