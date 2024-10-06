package com.example.backend.controller;

import com.example.backend.model.TutoModel;
import com.example.backend.repository.TutoRepository;
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
public class CrudController {

    @Autowired  // to inject CrudRepo bean to local variable
    TutoRepository tutoRepository;


    @GetMapping("/tutorials")
    public ResponseEntity<List<TutoModel>> getAllTutorials(@RequestParam(required = false) String title){
        List<TutoModel> tutorials = new ArrayList<TutoModel>();
        if (title == null){
            tutoRepository.findAll().forEach(tutorials::add);
        } else {
            tutoRepository.findByTitleContaining(title).forEach(tutorials::add);
        }

        if (tutorials.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(tutorials, HttpStatus.OK);

    }


    @GetMapping("/tutorials/{id}")
    public ResponseEntity<TutoModel> getTutorialById(@PathVariable("id") long id){
        Optional<TutoModel> tutoData = tutoRepository.findById(id);

        if (tutoData.isPresent()){
            return new ResponseEntity<>(tutoData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    @PostMapping("/tutorials")
    public ResponseEntity<TutoModel> createTutorial(@RequestBody TutoModel tutorial){
        try {
            TutoModel tuto = tutoRepository.save(new TutoModel(tutorial.getTitle(), tutorial.getDescription(), false));
            return new ResponseEntity<>(tuto, HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/tutorials/{id}")
    public ResponseEntity<TutoModel> updateTutorial(@PathVariable("id") long id, @RequestBody TutoModel tutorial){
        Optional<TutoModel> tutoData = tutoRepository.findById(id);

        if(tutoData.isPresent()){
            TutoModel tuto = tutoData.get();
            tuto.setTitle(tutorial.getTitle());
            tuto.setDescription(tutorial.getDescription());
            tuto.setPublished(tutorial.isPublished());
            return new ResponseEntity<>(tutoRepository.save(tuto), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }









}
