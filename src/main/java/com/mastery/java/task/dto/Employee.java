package com.mastery.java.task.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.mastery.java.task.validator.Adult;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString
@Table(name = "employees")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    @Length(min = 3, max = 15, message = "The firstname must be between {min} and {max} characters long")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 15, message = "The lastname must be between {min} and {max} characters long")
    private String lastName;

    @NotNull(message = "The department id must not be null")
    private Integer departmentId;

    @NotBlank(message = "The job title must not be null")
    private String jobTitle;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Adult(message = "Employee must be adult")
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate dateOfBirth;

    @Email(message = "The Email must have the format of an email address")
    private String email;

}
