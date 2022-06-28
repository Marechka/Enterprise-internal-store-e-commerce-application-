package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.Date;

import java.util.List;

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

    @Column(name = "date")
    Date date;

    @Column(name = "total_price")
    double totalPrice;

//    @Column(name = "session_id")
//    private String sessionId;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private List<OrderDetails> orderItems;

    @ManyToOne()
    @JoinColumn(name = "employee_id", referencedColumnName = "employeeId")
    private Employee employee;

}
