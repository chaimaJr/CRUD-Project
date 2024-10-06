package com.example.backend.controller;

import com.example.backend.model.Task;
import com.example.backend.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@CrossOrigin(origins = "http://localhost:8081") // for configuring allowed origins
@RestController
@RequestMapping("/api")
public class TaskController {

    @Autowired  // to inject CrudRepo bean to local variable
    TaskRepository taskRepository;


    @GetMapping("/tasks")
    public ResponseEntity<List<Task>> getAllTasks(@RequestParam(required = false) String title){
        List<Task> tasks = new ArrayList<Task>();
        if (title == null){
            taskRepository.findAll().forEach(tasks::add);
        } else {
            taskRepository.findByTitleContaining(title).forEach(tasks::add);
        }

        if (tasks.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tasks, HttpStatus.OK);

    }


    @GetMapping("/tasks/{id}")
    public ResponseEntity<Task> getTaskById(@PathVariable("id") long id){
        Optional<Task> taskData = taskRepository.findById(id);

        if (taskData.isPresent()){
            return new ResponseEntity<>(taskData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/tasks")
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        try {
            Task _task = taskRepository.save(new Task(task.getTitle(), task.getDescription(), false));
            return new ResponseEntity<>(_task, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable("id") long id, @RequestBody Task task){
        Optional<Task> taskData = taskRepository.findById(id);

        if(taskData.isPresent()){
            Task _task = taskData.get();
            _task.setTitle(task.getTitle());
            _task.setDescription(task.getDescription());
            _task.setPublished(task.isPublished());
            return new ResponseEntity<>(taskRepository.save(_task), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }









}
