package com.example.demo.repo;

import com.example.demo.model.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepo extends CrudRepository<Review, Integer> {
}
