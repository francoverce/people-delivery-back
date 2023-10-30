package com.example.userint.services

import com.example.userint.domain.entities.Reviews
import com.example.userint.domain.requests.CreateReviewsRequest
import com.example.userint.repositories.sql.ReviewsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class ReviewsService {

    @Autowired
    private lateinit var reviewsRepository: ReviewsRepository


    fun createReview(userCode: UUID, createReviewsRequest: CreateReviewsRequest): Reviews {
        val review = reviewsRepository.save(createReviewsRequest.toModel(userCode))
        updateRatingRestaurant(createReviewsRequest.restaurantCode)
        return review
    }

    fun updateRatingRestaurant(restaurantCode: UUID) {
        var rating: Float = 0F
        var counter: Int = 0

        reviewsRepository.findByRestaurantCode(restaurantCode)?.let { review ->
            review.map {
                counter++
                rating += it.rating
            }
        }
        //if (counter >= 1)
         //   restaurantervice.updateRatingRestaurant(restaurantCode, rating / counter)
    }

    fun getReviewsByRestaurantCode(restaurantCode: UUID) =
        reviewsRepository.findByRestaurantCode(restaurantCode)
}
