package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;


    @ManyToOne
    @JoinColumn(name = "user_id") // user_id is a name of this column in this table.
    private User user;


    @ManyToOne
    @JoinColumn(name = "painting_id")
    private Painting painting;

    @Column(name="full_text")
    private String full_text;

    @Column(name="review_date")
    private Date review_date;
}
