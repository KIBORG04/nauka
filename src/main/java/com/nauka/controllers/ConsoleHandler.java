package com.nauka.controllers;

import com.nauka.models.Employee;
import com.nauka.services.DBInitializer;
import com.nauka.services.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ConsoleHandler implements CommandLineRunner {

    private final Logger LOG = LoggerFactory.getLogger(ConsoleHandler.class);
    private final DBInitializer dbInitialized;
    private final EmployeeService employeeService;

    public ConsoleHandler(@Autowired DBInitializer dbInitialized, @Autowired EmployeeService employeeService) {
        this.dbInitialized = dbInitialized;
        this.employeeService = employeeService;
    }

    @Override
    public void run(String... args) throws Exception {
        dbInitialized.FillInWithExamples();

        while (true) {
            help();

            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            LOG.info("[CONSOLE] Handle Command: {}", line);

            String[] arguments = line.split(" ");
            switch (arguments[0].toLowerCase()) {
                case "find_by_id":
                    Optional<Employee> employee = employeeService.findById(Long.parseLong(arguments[1]));
                    if (employee.isPresent()) {
                        LOG.info("[CONSOLE] Employee found: {}", employee.get());
                    } else {
                        LOG.info("[CONSOLE] Employee not found");
                    }
                    continue;
                case "group_by_name":
                    List<String> names = employeeService.groupByName();
                    LOG.info("[CONSOLE] Group by: {}", names.toString());
                    continue;
                case "find_between":
                    List<Employee> employees = employeeService.findBetween(Integer.parseInt(arguments[1]), Integer.parseInt(arguments[2]));
                    LOG.info("[CONSOLE] Find between: {}", employees.toString());
                    continue;
                case "exit":
                    return;
                default:
                    continue;
            }
        }
    }

    private void help() {
        LOG.info("[CONSOLE] Commands:");
        LOG.info("[CONSOLE] find_by_id 1 - find employee by id");
        LOG.info("[CONSOLE] group_by_name - get employees names");
        LOG.info("[CONSOLE] find_between 1990 1992 - find employees with birth date between 1990 and 1992");
        LOG.info("[CONSOLE] exit - stop the app");
    }
}
