package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee> {

    Page<Employee> findByFirstNameIsContainingAndLastNameIsContaining(String firstName, String lastName, Pageable pageable);
}
