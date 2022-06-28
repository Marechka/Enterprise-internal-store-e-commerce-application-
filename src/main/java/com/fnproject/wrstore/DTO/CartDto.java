package com.fnproject.wrstore.DTO;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartDto {
    List<CartItemDto> cartItems;
    double totalCost;
}


