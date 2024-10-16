package com.nauka.services;

import com.nauka.models.Employee;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @PersistenceContext
    private EntityManager entityManager;

    public EmployeeService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /*
    По заданию, я не совсем понял ожидаемую реализацию этих функций, но остановился на этом способе через EntityManager.
    Я понимаю, что можно сделать проще, используя возможности репозиторией, например JpaRepository.
    Я бы мог написать то же самое, и с помощью @Query-аннотации, но решил остановиться только на ОДНОМ способе.
     */
    public Optional<Employee> findById(long id) {
        try {
            Employee employee = (Employee) entityManager.
                    createNativeQuery("SELECT * FROM employees e WHERE e.id = :id", Employee.class).
                    setParameter("id", id).
                    setMaxResults(1).
                    getSingleResult();
            return Optional.ofNullable(employee);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public List<String> groupByName() {
        List<String> names = entityManager.
                createNativeQuery("SELECT e.first_name FROM employees e GROUP BY e.first_name", String.class).
                getResultList();
        return names;
    }

    public List<Employee> findBetween(int startYear, int endYear) {
        List<Employee> employees = entityManager.
                createNativeQuery("SELECT * FROM employees e WHERE EXTRACT(YEAR FROM e.birth_date) between :start_date AND :end_date", Employee.class).
                setParameter("start_date", startYear).
                setParameter("end_date", endYear).
                getResultList();
        return employees;
    }

}
