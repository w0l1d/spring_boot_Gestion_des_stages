package com.storactive.stg.service;

import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TaskService {

    final TaskRepo taskRepo;
    final StageService internshipSer;

    @Autowired
    public TaskService(TaskRepo taskRepo, StageService internshipSer) {
        this.taskRepo = taskRepo;
        this.internshipSer = internshipSer;
    }

    public List<Task> getAll() {
        return taskRepo.findAll();
    }

    public Task create(Task task) {
        task.setId(null);
        return taskRepo.save(task);
    }

    public Task update(Task task) {
        Task task1 = taskRepo.findById(task.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found")
        );
        task.setInternship(task1.getInternship());
        return taskRepo.save(task);
    }

    public void delete(Integer id) {
        Task task = findById(id);
        Internship internship = task.getInternship();
        internship.getTasks().remove(task);
        internshipSer.update(internship);
        taskRepo.deleteById(id);
    }


    public Task findById(int id) {
        return taskRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found"));
    }


}
