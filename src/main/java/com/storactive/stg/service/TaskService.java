package com.storactive.stg.service;

import com.storactive.stg.model.Internship;
import com.storactive.stg.model.Task;
import com.storactive.stg.repository.TaskRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {

    final String OBJ = "Tache";

    final TaskRepo taskRepo;
    final StageService internshipSer;
    final HistoryService historySer;


    public Task create(Task task) {
        task.setId(null);
        Task task1 = taskRepo.save(task);
        historySer.objetCreated(OBJ, task1.getId());
        return task1;
    }

    public Task update(Task task) {
        Task task1 = taskRepo.findById(task.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found")
        );
        task.setInternship(task1.getInternship());
        Task task2 = taskRepo.save(task);
        historySer.objetUpdated(OBJ, task2.getId());
        return task2;
    }

    public void delete(Integer id) {
        Task task = findById(id);
        Internship internship = task.getInternship();
        internship.getTasks().remove(task);
        internshipSer.update(internship);
        taskRepo.deleteById(id);
        historySer.objetDeleted(OBJ, id);
    }


    public Task findById(int id) {
        return taskRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found"));
    }


}
