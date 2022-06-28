package com.fnproject.wrstore.DTO;


import com.fnproject.wrstore.models.Cart;
import com.fnproject.wrstore.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@Setter
@Getter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CartItemDto {
    int id;
    @NotNull int quantity;
    @NotNull Product product;


    public CartItemDto(Cart cart) {
        this.setId(cart.getCartId());
        this.setQuantity(cart.getQuantity());
        this.setProduct(cart.getProduct());
    }
}
