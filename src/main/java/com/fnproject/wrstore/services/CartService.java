package com.fnproject.wrstore.services;

import com.fnproject.wrstore.DTO.AddToCartDto;
import com.fnproject.wrstore.DTO.CartDto;
import com.fnproject.wrstore.DTO.CartItemDto;
import com.fnproject.wrstore.data.CartRepository;
import com.fnproject.wrstore.models.Cart;
import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
public class CartService {

    private  CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public void addToCart(AddToCartDto addToCartDto, Product product, Employee employee){
        Cart cart = new Cart(product, addToCartDto.getQuantity(), employee);
        cartRepository.save(cart);
    }


    public CartDto listCartItems(Employee employee) {
        List<Cart> cartList = cartRepository.findAllByEmployeeOrderByDateDesc(employee);
        List<CartItemDto> cartItems = new ArrayList<>();
        for (Cart cart:cartList){
            CartItemDto cartItemDto = getDtoFromCart(cart);
            cartItems.add(cartItemDto);
        }
        double totalCost = 0;
        for (CartItemDto cartItemDto :cartItems){
            totalCost += (cartItemDto.getProduct().getPrice()* cartItemDto.getQuantity());
        }
        return new CartDto(cartItems,totalCost);
    }


    public static CartItemDto getDtoFromCart(Cart cart) {
        return new CartItemDto(cart);
    }


    public void updateCartItem(AddToCartDto cartDto, Employee employee,Product product){
        Cart cart = cartRepository.getOne(cartDto.getId());
        cart.setQuantity(cartDto.getQuantity());
        cart.setDate(new Date());
        cartRepository.save(cart);
    }

    public void deleteCartItem(int id,int employeeId) throws NoSuchElementException {
        if (!cartRepository.existsById(id))
            throw new NoSuchElementException("Cart id is invalid : " + id);
        cartRepository.deleteById(id);

    }

    public void deleteCartItems(int employeeId) {
        cartRepository.deleteAll();
    }


    public void deleteEmployeeCartItems(Employee employee) {
        cartRepository.deleteByEmployee(employee);
    }
}
