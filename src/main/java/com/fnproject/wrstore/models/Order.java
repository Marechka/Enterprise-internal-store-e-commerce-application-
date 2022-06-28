package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "orderw")

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int orderId;

    @NonNull
    Date orderDate;


    double orderSubtotal;
    double tax;
    double total;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH})
    @JoinColumn(name = "employee_employee_id")
    private Employee employee;

    public Order( @NonNull Date orderDate, Employee employee) {
        //this.orderId = orderId;
        this.orderDate = orderDate;
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return orderId == order.orderId && Double.compare(order.orderSubtotal, orderSubtotal) == 0 && Double.compare(order.tax, tax) == 0 && Double.compare(order.total, total) == 0 && orderDate.equals(order.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderDate, orderSubtotal, tax, total);
    }
}
