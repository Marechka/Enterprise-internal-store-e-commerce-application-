package com.fnproject.wrstore.DTO;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmployeeInfoDTO {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    int employeeId;
    @NonNull
    String firstName;
    @NonNull
    String lastName;
    @NonNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date startDate;

}
