package com.fnproject.wrstore.data;

import com.fnproject.wrstore.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "select * from product where name like :name",nativeQuery = true)
    Product findProductByProductName(String name);

}
