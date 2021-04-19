package com.example.demo.repo;

import com.example.demo.model.Painting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaintingRepo extends CrudRepository<Painting, Long> {
}
