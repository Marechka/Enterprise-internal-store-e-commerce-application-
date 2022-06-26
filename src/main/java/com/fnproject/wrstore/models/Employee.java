package com.fnproject.wrstore.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "employee")

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int employeeId;

    @NonNull
    String firstName;

    @NonNull
    String lastName;

    @NonNull
    Date startDate;

    Date termDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && firstName.equals(employee.firstName) && lastName.equals(employee.lastName) && startDate.equals(employee.startDate) && Objects.equals(termDate, employee.termDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, firstName, lastName, startDate, termDate);
    }


    //    public Employee(int id, @NonNull String firstName, @NonNull String lastName, @NonNull Date startDate) {
//        this.id = id;
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.startDate = startDate;
//    }



}
