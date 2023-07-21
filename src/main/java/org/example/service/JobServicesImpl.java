package org.example.service;

import org.example.dao.interfaces.JobDao;
import org.example.dao.jobDaoImpl;
import org.example.model.Job;

import java.util.List;

public class JobServicesImpl implements JobServices {
    JobDao jobDao=new jobDaoImpl();
    @Override
    public void createJobTable() {
        jobDao.createJobTable();
    }

    @Override
    public void addJob(Job job) {
        jobDao.addJob(job);
    }

    @Override
    public Job getJobById(Long jobId) {

        return jobDao.getJobById(jobId);
    }

    @Override
    public List<Job> sortByExperience(String ascOrDesc) {
        return jobDao.sortByExperience(ascOrDesc);
    }

    @Override
    public Job getJobByEmployeeId(Long employeeId) {
        return jobDao.getJobByEmployeeId(employeeId);
    }

    @Override
    public void deleteDescriptionColumn() {
        jobDao.deleteDescriptionColumn();
    }
}
