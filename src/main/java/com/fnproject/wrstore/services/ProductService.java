package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.ProductRepository;
import com.fnproject.wrstore.models.Product;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class ProductService {
    ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }
    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Product findById(int id) throws NoSuchElementException{
        return productRepository.findById(id).orElseThrow();
    }

    public void saveOrUpdate(Product product){
        productRepository.save(product);
        log.info(String.format("Product ID created: %d Order Employee Name: %s",product.getId(),product.getName()));
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

}
