package com.fnproject.wrstore.data;

import com.fnproject.wrstore.models.Cart;
import com.fnproject.wrstore.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findAllByEmployeeOrderByDateDesc(Employee employee);

    List<Cart> deleteByEmployee(Employee employee);
}
