package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
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
@Table(name = "employees")
@Entity
public class Employee {
    @Id
    @NonNull
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NonNull String firstName;

    @NonNull String lastName;

    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date termDate;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orders = new java.util.ArrayList<>();

    public Employee(@NonNull int id, @NonNull String firstName, @NonNull String lastName, @NonNull Date startDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.startDate = startDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return id == employee.id && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && startDate.equals(employee.startDate) && Objects.equals(termDate, employee.termDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, startDate, termDate);
    }
}

