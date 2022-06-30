package com.fnproject.wrstore.DTO;

import com.fnproject.wrstore.models.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductDto {

        int id;
        @NotNull String name;
        //@NotNull String imageURL;
        @NotNull double price;
        @NotNull String description;
       // @NotNull Integer categoryId;

        public ProductDto(Product product) {
            this.setId(product.getId());
            this.setName(product.getName());
            //this.setImageURL(product.getImageURL());
            this.setDescription(product.getDescription());
            this.setPrice(product.getPrice());
           // this.setCategoryId(product.getCategory().getId());
        }

        public ProductDto(@NotNull String name, /*@NotNull String imageURL*/ @NotNull double price, @NotNull String description /*@NotNull Integer categoryId*/) {
            this.name = name;
           // this.imageURL = imageURL;
            this.price = price;
            this.description = description;
           // this.categoryId = categoryId;
        }

    }
