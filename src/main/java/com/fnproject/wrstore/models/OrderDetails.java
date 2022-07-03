package com.fnproject.wrstore.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
//@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "order_details")


@Entity
public class OrderDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotNull
    int qty;

//    @NotNull
//    @Column(name = "price")
//    double price;

//    @Column(name = "date")
//    Date date;

    @ToString.Exclude
    @ManyToOne()
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    Order order;

    @OneToOne( orphanRemoval = true)
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    Product product;

    public OrderDetails(int quantity, Order order, Product product) {
        this.qty = quantity;
        this.order = order;
        this.product = product;
    }

}