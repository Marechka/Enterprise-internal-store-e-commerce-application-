package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Objects;

@NoArgsConstructor
// @AllArgsConstructor
@RequiredArgsConstructor
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

    @NonNull
    String prodName;

    @NonNull
    double price;

    @NonNull
    int inStockQty;

    @NonNull
    String vendorName;

    @NonNull
    String vendorItemID;

    @NonNull
    double purchasePrice;

    public Product(int id, @NonNull String prodName, @NonNull double price, @NonNull int inStockQty, @NonNull String vendorName, @NonNull String vendorItemID, @NonNull double purchasePrice) {
        this.id = id;
        this.prodName = prodName;
        this.price = price;
        this.inStockQty = inStockQty;
        this.vendorName = vendorName;
        this.vendorItemID = vendorItemID;
        this.purchasePrice = purchasePrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return id == product.id && Double.compare(product.price, price) == 0 && inStockQty == product.inStockQty && Double.compare(product.purchasePrice, purchasePrice) == 0 && prodName.equals(product.prodName) && vendorName.equals(product.vendorName) && vendorItemID.equals(product.vendorItemID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, prodName, price, inStockQty, vendorName, vendorItemID, purchasePrice);
    }
}
