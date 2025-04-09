package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentReview;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 17:45
 */
@Mapper
public interface StudentReviewMapper {
    List<StudentReview> getAllReviews();

    StudentReview getReviewById(Long reviewId);

    StudentReview getReviewByUserId(Long userId);

    int addReview(StudentReview studentReview);

    int updateReview(StudentReview studentReview);

    int deleteReview(Long reviewId);
}
