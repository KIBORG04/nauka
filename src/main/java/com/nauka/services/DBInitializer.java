package com.nauka.services;

import com.nauka.models.Employee;
import com.nauka.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBInitializer {
    private final EmployeeRepository employeeRepository;

    public DBInitializer(@Autowired EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void FillInWithExamples() {
        // Table is filled with examples
        if (employeeRepository.count() > 0) {
            return;
        }

        List<String> names = List.of(
                "Евгений", "Константин",
                "Кирилл", "Сергей",
                "Михаил", "Дмитрий",
                "Лев", "Гордей",
                "Ярослав", "Мирон");

        List<String> secondNames = List.of(
                "Карпов", "Смирнов",
                "Вдовин", "Иванов",
                "Мельников", "Ларин",
                "Бирюков", "Уваров",
                "Комаров", "Лукьянов");

        List<String> departments = List.of(
                "IT",
                "Продажи",
                "Кадровый отдел");

        Random random = new Random();
        List<Employee> employees = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            int salary = random.nextInt(80000, 120000);
            salary -= salary % 1000;
            Calendar birthday = new GregorianCalendar(
                    random.nextInt(1980, 2005),
                    random.nextInt(0, 11),
                    random.nextInt(1, 30)
            );
            Employee employee = new Employee(
                    names.get(random.nextInt(names.size())),
                    secondNames.get(random.nextInt(secondNames.size())),
                    birthday,
                    departments.get(random.nextInt(departments.size())),
                    salary

            );
            employees.add(employee);
        }
        employeeRepository.saveAll(employees);
    }
}
