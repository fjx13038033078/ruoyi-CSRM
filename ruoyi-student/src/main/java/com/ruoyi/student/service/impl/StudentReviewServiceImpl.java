package com.ruoyi.student.service.impl;

import com.ruoyi.student.domain.StudentReview;
import com.ruoyi.student.mapper.StudentReviewMapper;
import com.ruoyi.student.service.StudentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 17:49
 */
@Service
@RequiredArgsConstructor
public class StudentReviewServiceImpl implements StudentReviewService {
    private final StudentReviewMapper studentReviewMapper;

    @Override
    public List<StudentReview> getAllStudentReviews() {
        return studentReviewMapper.getAllReviews();
    }

    @Override
    public StudentReview getStudentReviewById(Long reviewId) {
        return studentReviewMapper.getReviewById(reviewId);
    }

    @Override
    public boolean addStudentReview(StudentReview studentReview) {
        return studentReviewMapper.addReview(studentReview) > 0;
    }

    @Override
    public boolean updateStudentReview(StudentReview studentReview) {
        return studentReviewMapper.updateReview(studentReview) > 0;
    }

    @Override
    public boolean deleteStudentReview(Long reviewId) {
        return studentReviewMapper.deleteReview(reviewId) > 0;
    }
}
