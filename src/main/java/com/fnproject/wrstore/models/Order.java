package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name="orders")

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Column(name = "date") Date date;
    @Column(name = "total_price")
    double totalPrice;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order", orphanRemoval = true)
    private List<OrderDetails> orderItems = new java.util.ArrayList<>();

    @NonNull
    @ManyToOne(cascade = {CascadeType.REMOVE})
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    private Employee employee;


    public Order( Employee employee) {
        this.date = new Date();
        this.employee = employee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return id == order.id && Double.compare(order.totalPrice, totalPrice) == 0 && Objects.equals(date, order.date) && employee.equals(order.employee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, totalPrice, employee);
    }
}
