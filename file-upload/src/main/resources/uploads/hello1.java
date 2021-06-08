package com.skc.parallel_stream;

import com.skc.stream.api.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EmployeeDatabase {

    public static List<Employee> getEmployees(){
        List<Employee> employees = new ArrayList<>();

        for(int i = 1; i<=1000;i++){
            employees.add(new Employee(i, "employee"+1, "A", new Random().nextInt(1000*100)));
        }

        return employees;
    }
}
