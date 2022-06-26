package com.fnproject.wrstore.data;

import com.fnproject.wrstore.models.Employee;
import com.fnproject.wrstore.models.OrderDetails;
import com.fnproject.wrstore.models.OrderDetailsID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsID> {
}
