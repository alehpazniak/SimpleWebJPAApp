package com.mastery.java.task.dto;

import com.mastery.java.task.postProcessor.Adult;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @NotBlank
    @Length(min = 3, max = 15)
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 15)
    private String lastName;

    @NotNull
    private Integer departmentId;

    @NotBlank
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Adult
    private LocalDate dateOfBirth;

}
