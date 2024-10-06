package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "cruds")
public class TutoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String title;
    private String description;
    private boolean published;

    public TutoModel(String title, String description, boolean published) {
        this.title = title;
        this.description = description;
        this.published = published;
    }

    public String toString(){
        return "Tutorial [id="+ id + " , title= "+ title + ", desc= "+ description + "]";
    }

}
