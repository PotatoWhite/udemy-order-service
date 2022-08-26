package me.potato.orderservice.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class UserDto {
    private Long    id;
    private String  name;
    private Integer balance;
}
