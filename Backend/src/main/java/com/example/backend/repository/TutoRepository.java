package com.example.backend.repository;

import com.example.backend.model.TutoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TutoRepository extends JpaRepository<TutoModel, Long> {

    List<TutoModel> findByPublished(boolean published);
    List<TutoModel> findByTitleContaining(String title);

}
