package com.fnproject.wrstore.services;

import com.fnproject.wrstore.data.EmployeeRepository;
import com.fnproject.wrstore.data.OrderDetailsRepository;
import com.fnproject.wrstore.data.OrderRepository;
import com.fnproject.wrstore.data.ProductRepository;
import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
//@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Transactional(rollbackOn = {DataAccessException.class})
public class OrderService {

    //OrderRepository orderRepository;
    EmployeeRepository employeeRepository;

    //CartService cartService;
    OrderRepository orderRepository;
    OrderDetailsRepository orderDetailsRepository;
    ProductRepository productRepository;
    @Autowired
    public OrderService(EmployeeRepository employeeRepository, OrderRepository orderRepository, OrderDetailsRepository orderDetailsRepository, ProductRepository productRepository) {
        this.employeeRepository = employeeRepository;
        this.orderRepository = orderRepository;
        this.orderDetailsRepository = orderDetailsRepository;
        this.productRepository = productRepository;
    }

    public void newOrder(int employeeId) {
        Order newOrder = new Order();
        Employee employee = employeeRepository.findById(employeeId);
        newOrder.setEmployee(employee);
    }
//    @Value("${BASE_URL}")
//    private String baseURL;
//
//    @Value("${STRIPE_SECRET_KEY}")
//    private String apiKey;

    // create total price
//    SessionCreateParams.LineItem.PriceData createPriceData(CheckoutItemDto checkoutItemDto) {
//        return SessionCreateParams.LineItem.PriceData.builder()
//                .setCurrency("usd")
//                .setUnitAmount((long)(checkoutItemDto.getPrice()*100))
//                .setProductData(
//                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
//                                .setName(checkoutItemDto.getProductName())
//                                .build())
//                .build();
//    }
//
//    // build each product in the stripe checkout page
//    SessionCreateParams.LineItem createSessionLineItem(CheckoutItemDto checkoutItemDto) {
//        return SessionCreateParams.LineItem.builder()
//                // set price for each product
//                .setPriceData(createPriceData(checkoutItemDto))
//                // set quantity for each product
//                .setQuantity(Long.parseLong(String.valueOf(checkoutItemDto.getQuantity())))
//                .build();
//    }
//
//    // create session from list of checkout items
//    public Session createSession(List<CheckoutItemDto> checkoutItemDtoList) throws StripeException {
//
//        // supply success and failure url for stripe
//        String successURL = baseURL + "payment/success";
//        String failedURL = baseURL + "payment/failed";

        // set the private key
        //Stripe.apiKey = apiKey;

        //List<SessionCreateParams.LineItem> sessionItemsList = new ArrayList<>();

        // for each product compute SessionCreateParams.LineItem
//        for (CheckoutItemDto checkoutItemDto : checkoutItemDtoList) {
//            sessionItemsList.add(createSessionLineItem(checkoutItemDto));
//        }

//        // build the session param
//        SessionCreateParams params = SessionCreateParams.builder()
//                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setCancelUrl(failedURL)
//                .addAllLineItem(sessionItemsList)
//                .setSuccessUrl(successURL)
//                .build();
//        return Session.create(params);
//    }


    public void placeOrder (Order order) {

        // first let get cart items for the user
        // pull List <OrderDetails> , count total, place in total field
        int orderId = order.getId();
        List<OrderDetails> orderDetails = orderDetailsRepository.findOrderDetailsByOrderId(orderId);
        double total = 0;
        for (OrderDetails  details: orderDetails) {
            Product product = productRepository.findById(details.getProduct().getProdId()).orElseThrow();
            total+= details.getQty() * product.getPrice();
        }
        order.setTotalPrice(total);
        orderRepository.save(order);
    }

    public List<Order> listOrders(Employee employee) {
        return orderRepository.findAllByEmployeeOrderByDateDesc(employee);
    }

    public List<Order> findAll(){
        return orderRepository.findAll();
    }

    @Transactional(rollbackOn = {NoSuchElementException.class})
    public Order findById(int id) throws NoSuchElementException{
        return orderRepository.findById(id).orElseThrow();
    }

    public void save(Order order){
       // order.setEmployee(employeeRepository.findById(id).get());
        orderRepository.save(order);
        log.info(String.format("Order ID created: %d Order Employee Name: %s",order.getId(),order.getEmployee()));
    }

//    public void Update(Order order){
//       // order.setEmployee(employeeRepository.findById(id).get());
//        orderRepository.save(order);
//        log.info(String.format("Order ID created: %d Order Employee Name: %s",order.getId(),order.getEmployee()));
//    }
    public void delete(Order order){
        orderRepository.delete(order);
    }

}
