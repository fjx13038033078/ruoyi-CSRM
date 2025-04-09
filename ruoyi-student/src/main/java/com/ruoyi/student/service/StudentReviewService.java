package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentReview;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 17:49
 */
public interface StudentReviewService {
    /**
     * 获取所有学生复查记录
     * @return 学生复查记录列表
     */
    List<StudentReview> getAllStudentReviews();

    /**
     * 根据ID获取复查详情
     * @param reviewId 复查ID
     * @return 学生复查信息
     */
    StudentReview getStudentReviewById(Long reviewId);

    /**
     * 添加学生复查记录
     * @param studentReview 待添加的复查记录
     * @return 是否添加成功
     */
    boolean addStudentReview(StudentReview studentReview);

    /**
     * 更新学生复查记录
     * @param studentReview 待更新的复查记录
     * @return 是否更新成功
     */
    boolean updateStudentReview(StudentReview studentReview);

    /**
     * 删除学生复查记录
     * @param reviewId 复查ID
     * @return 是否删除成功
     */
    boolean deleteStudentReview(Long reviewId);
}
