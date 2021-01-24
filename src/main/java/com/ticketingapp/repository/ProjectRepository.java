package com.ticketingapp.repository;

import com.ticketingapp.entity.Project;
import com.ticketingapp.entity.User;
import com.ticketingapp.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    Project findByProjectCode(String code);
    List<Project> findByAssignedManager(User manager);
    List<Project> findAllByProjectStatusIsNot(Status status);
}
