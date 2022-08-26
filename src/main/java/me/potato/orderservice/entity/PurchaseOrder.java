package me.potato.orderservice.entity;

import lombok.Data;
import lombok.ToString;
import me.potato.orderservice.dto.OrderStatus;

import javax.persistence.*;

@Data
@ToString
@Entity
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private Long    id;
    private Long    userId;
    private String  productId;
    private Integer amount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

}
