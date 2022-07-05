package com.fnproject.wrstore.models;


import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
//@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int prodId;

    @NotNull
    String name;
    @NotNull
    String description;

    @NotNull
    double price;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "product")
    private List<OrderDetails> orderDetails = new java.util.ArrayList<>();


    public Product(String name, String description, double price ) {
        this.name = name;
        this.price = price;
        this.description = description;
    }

}