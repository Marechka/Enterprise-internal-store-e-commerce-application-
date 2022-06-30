package com.fnproject.wrstore.services;

import com.fnproject.wrstore.DTO.ProductDto;
import com.fnproject.wrstore.data.ProductRepository;
import com.fnproject.wrstore.models.Product;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent())
            throw new NoSuchElementException("Product id is invalid or there is no record" + id);
        return optionalProduct.get();
    }

    public void saveOrUpdate(Product product){
        productRepository.save(product);
        log.info(String.format("Product ID created: %d Order Employee Name: %s",product.getId(),product.getName()));
    }

    public void delete(Product product){
        productRepository.delete(product);
    }

    public List<ProductDto> listProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for(Product product : products) {
            ProductDto productDto = getDtoFromProduct(product);
            productDtos.add(productDto);
        }
        return productDtos;
    }

    public static ProductDto getDtoFromProduct(Product product) {
        ProductDto productDto = new ProductDto(product);
        return productDto;
    }

    public static Product getProductFromDto(ProductDto productDto) {
        Product product = new Product(productDto);
        return product;
    }

    public void addProduct(ProductDto productDto) {
        Product product = getProductFromDto(productDto);
        productRepository.save(product);
    }

    public void updateProduct(int productID, ProductDto productDto) {
        Product product = getProductFromDto(productDto);
        product.setId(productID);
        productRepository.save(product);
    }


    public Product getProductById(Integer productId) throws NoSuchElementException{
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (!optionalProduct.isPresent())
            throw new NoSuchElementException("Product id is invalid " + productId);
        return optionalProduct.get();
    }





}
