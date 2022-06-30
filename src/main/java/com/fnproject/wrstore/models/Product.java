package com.fnproject.wrstore.models;

import com.fnproject.wrstore.DTO.ProductDto;
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
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "product")

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotNull
    String name;

//    @NotNull
//    String imageURL;

    @NotNull
    double price;

    @NotNull
    String description;


//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "category_id", nullable = false)
//    Category category;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
//    private List<WishList> wishListList;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;


    public Product(ProductDto productDto) {
        this.name = productDto.getName();
//        this.imageURL = productDto.getImageURL();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
//        this.category = category;
    }

    public Product(String name /*String imageURL*/, double price, String description /*Category category*/) {
        super();
        this.name = name;
        //this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        // this.category = category;
    }

}