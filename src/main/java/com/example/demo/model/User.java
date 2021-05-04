package com.example.demo.model;

import lombok.*;


import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table (name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
   private Integer id;
    @Column(name="email")
   private String email;
    @Column(name="first_name")
   private String firstName;
    @Column(name="last_name")
   private String lastName;
    @Enumerated(value = EnumType.STRING)
    @Column(name="role")
   private Role role;
    @Enumerated(value = EnumType.STRING)
    @Column(name="status")
   private Status status;
    @Column(name="password")
   private String password;
}
