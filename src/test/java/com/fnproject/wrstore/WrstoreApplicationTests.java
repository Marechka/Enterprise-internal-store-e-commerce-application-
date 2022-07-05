package com.fnproject.wrstore;

import com.fnproject.wrstore.data.EmployeeRepository;
import com.fnproject.wrstore.data.OrderDetailsRepository;
import com.fnproject.wrstore.data.OrderRepository;
import com.fnproject.wrstore.data.ProductRepository;
import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.Product;
import com.fnproject.wrstore.services.EmployeeService;
import com.fnproject.wrstore.services.OrderDetailsService;
import com.fnproject.wrstore.services.OrderService;
import com.fnproject.wrstore.services.ProductService;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;

import javax.activation.DataSource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(classes=WrstoreApplicationTests.class)
//com.fnproject.wrstore.
//@DataJpaTest
@Slf4j
class WrstoreApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	EmployeeService employeeService;
	@Autowired
	OrderService orderService;
	@Autowired
	OrderDetailsService orderDetailsService;
	@Autowired
	ProductService productService;

	@Autowired
	EmployeeRepository employeeRepository;
	@Autowired
	OrderRepository orderRepository;
	@Autowired
	OrderDetailsRepository orderDetailsRepository;
	@Autowired
	ProductRepository productRepository;

	Employee employee;
	Product product;
	OrderDetails orderDetails;
	Order order;
	@BeforeEach
	public void setup() {
		try {
		employee = new Employee(1,"Katia", "Bortnikova", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022"));
		}catch (Exception e) {
			System.out.println("Date exception");
		}
		product = new Product("Chocolate chip cookie", "Large 100", 2.10);
		order = new Order(employee);
		orderDetails = new OrderDetails(3,order, product);
	}

	@AfterEach
	public void tearDown() {
		orderDetailsRepository.deleteAll();
		productRepository.deleteAll();
		orderRepository.deleteAll();
		employeeRepository.deleteAll();
	}

	// EmployeeRepo tests
	@DisplayName("Test for save() employee operation")
	@Test
	public void saveAndReturnEmployee() throws ParseException {
			try {
				Employee employee = new Employee(2, "Dasha", "Voropai", new SimpleDateFormat("MM/dd/yyyy").parse("01/15/2022"));
			}catch (Exception e) {
				System.out.println("Date exception");
			}
		Employee savedEmployee = employeeRepository.save(employee);
		assertThat(savedEmployee).isNotNull();
		assertThat(savedEmployee.getId()).isPositive();
	}
 	@DisplayName("test findAll() employeeRepo")
	@Test
	public void findAll() {
		employeeRepository.save(employee);
		List<Employee> empList = employeeRepository.findAll();
		log.warn("Found emps: " + empList);
	    assertThat(empList.size()).isEqualTo(1);
	}

	@DisplayName("test findById() employeerepo")
	@Test
	public void findById() {
	employeeRepository.save(employee);
	Employee actual = employeeRepository.findById(1);
	assertThat(actual.getId()).isEqualTo(1);
	}

	// OrderRepo tests

	@DisplayName("test findAllByEmployeeOrderByDateDesc(Employee employee) orderRepo")
	@Test

	public void findAllByEmployeeOrderByDateDesc() {
		employeeRepository.save(employee);
		orderRepository.save(order);
		productRepository.save(product);
		orderDetailsRepository.save(orderDetails);
		List<Order> actual = orderRepository.findAllByEmployeeOrderByDateDesc(employee);
		assertThat(actual.size()).isEqualTo(1);
	}

	@DisplayName("test findOrderById() orderRepo")
	@Test
	public void findOrderById() {
		employeeRepository.save(employee);
		orderRepository.save(order);

		Order actual = orderRepository.findOrderById(1);
		assertThat(actual.getId()).isEqualTo(1);
	}

	//OrderDetails Repo
	@DisplayName("test findOrderDetailsByOrderId() orderDetailsRepo")
	@Test
	public void findOrderDetailsByOrderId() {
		employeeRepository.save(employee);
		orderRepository.save(order);
		productRepository.save(product);
		orderDetailsRepository.save(orderDetails);
		List<OrderDetails> actual = orderDetailsRepository.findOrderDetailsByOrderId(1);
		assertThat(actual.size()).isEqualTo(1);
	}

	//ProductRepo
	@DisplayName("test findOrderDetailsByOrderId() productRepo")
	@Test
	public void findProductByProductName() {
		employeeRepository.save(employee);
		orderRepository.save(order);
		productRepository.save(product);
		orderDetailsRepository.save(orderDetails);
		String product= "Chocolate chip cookie";
		Product actual = productRepository.findProductByProductName(product);
		assertThat(actual.getName()).isEqualTo("Chocolate chip cookie");
	}

	 //EmployeeService
	 @DisplayName("test saveOrUpdate() employeeService")
	 @Test
	 public void saveOrUpdateEmployee() {
		 employeeService.saveOrUpdate(employee);
		 Employee actual = employeeRepository.findById(1);
		 assertThat(actual.getId()).isEqualTo(1);
	 }

    //OrderService
	@DisplayName("test placeOrder() orderService")
	@Test
	public void placeOrder() {
		employeeRepository.save(employee);
		orderRepository.save(order);
		productRepository.save(product);
		orderDetailsRepository.save(orderDetails);

		orderService.placeOrder(order);
		Order order1 = orderRepository.findOrderById(order.getId());
		double expectedOrderTotal = order1.getTotalPrice();
		assertThat(expectedOrderTotal).isPositive();
	}

	//ProductService
	@DisplayName("test delete() productService")
	@Test
	public void deleteProduct() {
		productRepository.save(product);
		productRepository.findProductByProductName(product.getName());
		assertThat(productRepository.findProductByProductName(product.getName())).isNotNull();
		productService.delete(product);
		assertThat(productRepository.findProductByProductName(product.getName())).isNull();

	}

	//OrderDetailsService
	@DisplayName("test saveOrUpdate() OrderDetailsService")
	@Test
	public void saveOrUpdateOrderDetails() {
		employeeRepository.save(employee);
		orderRepository.save(order);
		productRepository.save(product);
		orderDetailsService.saveOrUpdate(orderDetails);
		OrderDetails actual = orderDetailsRepository.findById(1).get();
		assertThat(actual.getId()).isEqualTo(1);
	}








}
