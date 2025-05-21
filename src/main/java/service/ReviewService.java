package service;

import dao.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import model.*;

import java.util.List;

@RequestScoped
public class ReviewService {
    @Inject
    ReviewDAO reviewDAO;

    @Inject
    ProductDAO productDAO;

    @Inject
    UserDAO userDAO;

    @Transactional
    public void addReview(Long userId, Long productId, int rating, String comment) {
        Review review = new Review();
        review.setUser(userDAO.findById(userId));
        review.setProduct(productDAO.findById(productId));
        review.setRating(rating);
        review.setComment(comment);
        reviewDAO.save(review);
    }

    public List<Review> getReviewsForProduct(Long productId) {
        return reviewDAO.findAll().stream()
            .filter(r -> r.getProduct().getId().equals(productId))
            .toList();
    }
}
