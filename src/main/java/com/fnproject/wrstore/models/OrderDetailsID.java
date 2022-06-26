package com.fnproject.wrstore.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
//@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_details_ID")

@Embeddable
public class OrderDetailsID implements Serializable {

    @NonNull
    @Column(name = "order_number")
    int orderNumber;

    @NonNull
    @Column(name = "product_id")
    int productId;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetailsID)) return false;
        OrderDetailsID that = (OrderDetailsID) o;
        return orderNumber == that.orderNumber && productId == that.productId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderNumber, productId);
    }
}
