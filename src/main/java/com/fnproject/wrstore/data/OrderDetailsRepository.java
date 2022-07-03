package com.fnproject.wrstore.data;

import com.fnproject.wrstore.models.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    List<OrderDetails> findOrderDetailsByOrderId(int id);

    //@Query(value = "sum(item_total) from OrderDetails group by orderId where Orderid = id")
}
