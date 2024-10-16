package com.nauka.repositories;

import com.nauka.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "select * from Employee e where e.id = :id", nativeQuery = true)
    Optional<Employee> findById(@Param("id") Long id);
}
