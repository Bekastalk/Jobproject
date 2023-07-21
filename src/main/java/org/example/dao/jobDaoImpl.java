package org.example.dao;

import org.example.config.Config;
import org.example.dao.interfaces.JobDao;
import org.example.model.Job;

import javax.naming.ldap.PagedResultsControl;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class jobDaoImpl implements JobDao {
    private final Connection connection= Config.getConnection();
    @Override
    public void createJobTable() {

            try {
                Statement statement=connection.createStatement();
                statement.executeUpdate("create table jobs(" +
                        "id serial primary key," +
                        "position varchar," +
                        "profession varchar," +
                        "description varchar," +
                        "experience int)");
                System.out.println("Added table");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
    }

    @Override
    public void addJob(Job job) {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("insert into jobs(" +
                    "position,profession, description, experience)" +
                    "values(?,?,?,?)");
            preparedStatement.setString(1,job.getPosition());
            preparedStatement.setString(2,job.getProfession());
            preparedStatement.setString(3,job.getDescription());
            preparedStatement.setInt(4,job.getExperience());

            preparedStatement.executeUpdate();
            System.out.println("Successfully saved");
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Job getJobById(Long jobId) {
        Job job=null;
        String sql="select * from jobs where id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setLong(1, jobId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                job = new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return job;
    }


    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        List<Job> jobs = new ArrayList<>();
        try {
            String sql = "select * from jobs order by experience " + ascOrDesc;
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Job job = new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );
                jobs.add(job);
            }
            preparedStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return jobs;
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        Job job1=null;
        String sql="select j.* from employees e " +
                "left join jobs j on e.jobid=j.id where e.id=?";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setLong(1,employeeId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                job1 = new Job(
                        resultSet.getLong("id"),
                        resultSet.getString("position"),
                        resultSet.getString("profession"),
                        resultSet.getString("description"),
                        resultSet.getInt("experience")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return job1;
    }

    @Override
    public void deleteDescriptionColumn() {
        String sql="update jobs set  description=null";
        try {
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Deleted column description");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
