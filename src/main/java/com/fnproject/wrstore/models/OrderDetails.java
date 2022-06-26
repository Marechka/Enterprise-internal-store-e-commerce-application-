package com.fnproject.wrstore.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_details")


@Entity
public class OrderDetails {

    @EmbeddedId
    OrderDetailsID orderDetailsID;

    @NonNull
    int quantity;

    @NonNull
    double itemTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDetails)) return false;
        OrderDetails that = (OrderDetails) o;
        return quantity == that.quantity && Double.compare(that.itemTotal, itemTotal) == 0 && orderDetailsID.equals(that.orderDetailsID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDetailsID, quantity, itemTotal);
    }
}
