package org.example;

import org.example.model.Employee;
import org.example.model.Job;
import org.example.service.EmployeeService;
import org.example.service.EmployeeServicesImpl;
import org.example.service.JobServices;
import org.example.service.JobServicesImpl;

import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Scanner in =new Scanner(System.in);
        //Config.getConnection();
        JobServices jobServices=new JobServicesImpl();
        EmployeeService employeeService=new EmployeeServicesImpl();
        //jobServices.createJobTable();
        //jobServices.addJob(new Job("Instructor","Java","Backend developer",2));
        //System.out.println(jobServices.getJobById(3L));
        //System.out.println("Введите asc or desc");
        //jobServices.sortByExperience(in.nextLine()).forEach(System.out::println);
        //employeeService.createEmployee();
        //employeeService.dropTable();
        //employeeService.cleanTable();
        //employeeService.updateEmployee(1L,new Employee("Beksultan","Kadyrov",19,"beka@gmail.com",3));
        //employeeService.getAllEmployees().forEach(System.out::println);
        //System.out.println("Введите email ");
        //System.out.println(employeeService.findByEmail(in.nextLine()));
        //System.out.println(jobServices.getJobByEmployeeId(1L));
        //jobServices.deleteDescriptionColumn();
        //System.out.println(employeeService.getEmployeeById(2L));
        System.out.println(employeeService.getEmployeeByPosition("Mentor"));
    }
}
