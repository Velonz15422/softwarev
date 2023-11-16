package com.pymextore.softwarev.service;

import com.pymextore.softwarev.model.Product;
import com.pymextore.softwarev.model.ProductRating;
import com.pymextore.softwarev.model.User;
import com.pymextore.softwarev.repository.ProductRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;

    @Override
    public void rateProduct(Product product, User user, int rating) {
        boolean hasRated = productRatingRepository.existsByProductAndUser(product, user);

        if (!hasRated) {
            ProductRating productRating = new ProductRating();
            productRating.setProduct(product);
            productRating.setUser(user);
            productRating.setRating(rating);

            productRatingRepository.save(productRating);
        } else {
        }
    }
}
