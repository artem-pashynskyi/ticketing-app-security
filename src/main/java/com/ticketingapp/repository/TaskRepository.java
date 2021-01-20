package com.ticketingapp.repository;

import com.ticketingapp.entity.Project;
import com.ticketingapp.entity.Task;
import com.ticketingapp.entity.User;
import com.ticketingapp.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Optional<Task> findById(Long id);

    @Query("select count(t) from Task t where t.project.projectCode = ?1 and t.taskStatus <> 'COMPLETE'")
    int totalNonCompleteTasks(String projectCode);

    @Query(value = "select count(*) from tasks t join projects p on p.id = t.project_id where p.project_code = ?1 and t.task_status = 'COMPLETE'", nativeQuery = true)
    int totalCompleteTasks(String projectCode);

    List<Task> findByProject(Project project);

    List<Task> findByTaskStatusAndAssignedEmployee(Status status, User user);
    List<Task> findByTaskStatusIsNotAndAssignedEmployee(Status status, User user);

    List<Task> findAllByProjectAssignedManager(User manager);
    List<Task> findAllByAssignedEmployee(User user);
}
