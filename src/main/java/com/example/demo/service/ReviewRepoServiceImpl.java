package com.example.demo.service;

import com.example.demo.model.Painting;
import com.example.demo.model.Review;
import com.example.demo.model.User;
import com.example.demo.repo.PaintingRepo;
import com.example.demo.repo.ReviewRepo;
import com.example.demo.repo.UserRepo;
import com.example.demo.security.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReviewRepoServiceImpl implements ReviewRepoService {

    @Autowired
    private ReviewRepo reviewRepo;

    @Autowired
    private PaintingRepoImpl paintingRepo;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void addReview(String userName, String comment, Long paintingId) {
        Optional<User> user1 = userRepo.findByEmail(userName);
        User user = user1.isPresent() ? user1.get() : new User();
        Review review = new Review();
        review.setPainting(paintingRepo.getPainting(paintingId));
        review.setFull_text(comment);
        review.setReview_date(new Date());
        review.setUser(user);
        reviewRepo.save(review);
    }

    @Override
    public List<Review> selectAllPaintingReviews(Long paintingId) {
        ArrayList<Review> reviews = new ArrayList<>();
       Iterator<Review> iter = reviewRepo.findAll().iterator();
        while (iter.hasNext()) {
            reviews.add(iter.next());
        }
        return reviews.stream().filter(s -> s.getPainting().getId().equals(paintingId))
                .collect(Collectors.toList());
    }
}
