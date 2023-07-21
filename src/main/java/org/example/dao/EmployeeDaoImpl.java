package org.example.dao;

import org.example.config.Config;
import org.example.dao.interfaces.EmployeeDao;
import org.example.model.Employee;
import org.example.model.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDaoImpl implements EmployeeDao {
    private final Connection connection = Config.getConnection();
    Employee employee = new Employee();

    @Override
    public void createEmployee() {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table employees(" +
                    "id serial primary key," +
                    "first_name varchar," +
                    "last_name varchar," +
                    "age int," +
                    "email varchar," +
                    "jobId int references jobs(id))");
            statement.close();
            System.out.println("Added table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addEmployee(Employee employee) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into employees(" +
                    "first_name,last_name, age, email,jobId)" +
                    "values(?,?,?,?,?)");
            preparedStatement.setString(1, employee.getFirstName());
                    preparedStatement.setString(2, employee.getLastName());
                    preparedStatement.setInt(3, employee.getAge());
                    preparedStatement.setString(4, employee.getEmail());
                    preparedStatement.setInt(5, employee.getJobId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Successfully saved");
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void dropTable() {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("drop table employees");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Dropped employees");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void cleanTable() {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("delete from employees");
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Clean table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateEmployee(Long id, Employee employee) {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("update employees set first_name=?,last_name=?, age=?, email=?,jobId=? where id="+id);
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setInt(3, employee.getAge());
            preparedStatement.setString(4, employee.getEmail());
            preparedStatement.setLong(5, employee.getJobId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Updating table");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Employee> getAllEmployees() {
        List<Employee>employees=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from employees");
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("jobid")
                ));
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }

    @Override
    public Employee findByEmail(String email) {
        Employee employee1=null;
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select * from employees where email=?");
            preparedStatement.setString(1,email);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                employee1=new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("jobid")
                );
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employee1;
    }

    @Override
    public Map<Employee, Job> getEmployeeById(Long employeeId) {
        Map<Employee, Job> employeeJobMap = new HashMap<>();
        String sql = "SELECT e.*, j.* FROM employees e " +
                "JOIN jobs j ON e.jobid = j.id " +
                "WHERE e.id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Employee employee = new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("jobid")
                );

                Job job = new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );

                employeeJobMap.put(employee, job);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employeeJobMap;
    }


    @Override
    public List<Employee> getEmployeeByPosition(String position) {
        Job job=new Job();
        List<Employee>employees=new ArrayList<>();
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("select e.* " +
                    "from employees e join jobs j on e.jobid=j.id where j.position=?");
            preparedStatement.setString(1,position);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                employees.add(new Employee(
                        resultSet.getLong("id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getInt("age"),
                        resultSet.getString("email"),
                        resultSet.getInt("jobid")
                ));
                preparedStatement.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return employees;
    }
}
