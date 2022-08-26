package me.potato.orderservice.util;

import me.potato.orderservice.dto.*;
import me.potato.orderservice.entity.PurchaseOrder;

public class EntityDtoUtil {
    public static void setTransactionRequestDto(RequestContext context) {
        var transactionRequestDto = new TransactionRequestDto();
        transactionRequestDto.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        transactionRequestDto.setAmount(context.getProductDto().getPrice());
        context.setTransactionRequestDto(transactionRequestDto);
    }

    public static PurchaseOrder getOPurchaseOrder(RequestContext context) {
        var purchaseOrder = new PurchaseOrder();
        purchaseOrder.setUserId(context.getPurchaseOrderRequestDto().getUserId());
        purchaseOrder.setProductId(context.getPurchaseOrderRequestDto().getProductId());
        purchaseOrder.setAmount(context.getProductDto().getPrice());

        var orderStatus = context.getTransactionResponseDto().getStatus();
        purchaseOrder.setStatus(TransactionStatus.APPROVED.equals(orderStatus) ? OrderStatus.COMPLETED : OrderStatus.FAILED);

        return purchaseOrder;
    }

    public static PurchaseOrderResponseDto getPurchaseOrderResponseDto(PurchaseOrder purchaseOrder) {
        var purchaseOrderResponseDto = new PurchaseOrderResponseDto();
        purchaseOrderResponseDto.setOrderId(purchaseOrder.getId());
        purchaseOrderResponseDto.setUserId(purchaseOrder.getUserId());
        purchaseOrderResponseDto.setProductId(purchaseOrder.getProductId());
        purchaseOrderResponseDto.setAmount(purchaseOrder.getAmount());
        purchaseOrderResponseDto.setStatus(purchaseOrder.getStatus());
        return purchaseOrderResponseDto;
    }
}
