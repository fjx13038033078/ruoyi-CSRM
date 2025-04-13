package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentEnrollment;
import com.ruoyi.student.domain.StudentReview;
import com.ruoyi.student.mapper.StudentReviewMapper;
import com.ruoyi.student.service.StudentReviewService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 17:49
 */
@Service
@RequiredArgsConstructor
public class StudentReviewServiceImpl implements StudentReviewService {

    private final StudentReviewMapper studentReviewMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    @Override
    public List<StudentReview> getAllStudentReviews() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("common")) {
            StudentReview reviewByUserId = studentReviewMapper.getReviewByUserId(userId);
            List<StudentReview> StudentReviewL = new ArrayList<>();
            if (StudentReviewL != null) {
                StudentReviewL.add(reviewByUserId);
            }
            fillReview(StudentReviewL);
            return StudentReviewL;
        } else {
            List<StudentReview> allReviews = studentReviewMapper.getAllReviews();
            fillReview(allReviews);
            return allReviews;
        }
    }

    @Override
    public StudentReview getStudentReviewById(Long reviewId) {
        StudentReview reviewById = studentReviewMapper.getReviewById(reviewId);
        fillReview(reviewById);
        return reviewById;
    }

    @Override
    public boolean addStudentReview(StudentReview studentReview) {
        studentReview.setReviewDate(LocalDate.now());
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

    private void fillReview(List<StudentReview> studentReviews) {
        for (StudentReview studentReview : studentReviews) {
            Long userId = studentReview.getUserId();
            String nickName = iSysUserService.selectUserById(userId).getNickName()== null ? "" : iSysUserService.selectUserById(userId).getNickName();
            studentReview.setNickName(nickName);
            String userName = iSysUserService.selectUserById(userId).getUserName()== null ? "" : iSysUserService.selectUserById(userId).getUserName();
            studentReview.setUserName(userName);
        }
    }

    private void fillReview(StudentReview studentReview) {
        Long userId = studentReview.getUserId();
        String nickName = iSysUserService.selectUserById(userId).getNickName()== null ? "" : iSysUserService.selectUserById(userId).getNickName();
        studentReview.setNickName(nickName);
        String userName = iSysUserService.selectUserById(userId).getUserName()== null ? "" : iSysUserService.selectUserById(userId).getUserName();
        studentReview.setUserName(userName);
    }
}
