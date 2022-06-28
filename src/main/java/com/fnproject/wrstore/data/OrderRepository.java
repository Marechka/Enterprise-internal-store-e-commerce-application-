package com.fnproject.wrstore.data;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findAllByEmployeeOrderByDateDesc(Employee employee);

}
