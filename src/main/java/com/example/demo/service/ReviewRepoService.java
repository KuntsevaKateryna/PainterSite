package com.example.demo.service;

import com.example.demo.model.Painting;
import com.example.demo.model.Review;
import com.example.demo.model.User;

import java.util.List;


public interface ReviewRepoService {
    public void addReview(String userName,
                            String comment,
                            Long paintingId);

    public List<Review> selectAllPaintingReviews(Long paintingId);
}
