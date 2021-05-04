package com.example.demo.repo;


import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

Optional<User> findByEmail(String email);
  // User findByEmail( String emailAddress);
}
