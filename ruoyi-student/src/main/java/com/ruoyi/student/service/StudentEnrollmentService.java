package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentEnrollment;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 11:11
 */
public interface StudentEnrollmentService {
    /**
     * 获取所有录取信息
     */
    List<StudentEnrollment> getAllEnrollments();

    /**
     * 根据ID查询录取信息
     */
    StudentEnrollment getEnrollmentById(Long enrollmentId);

    /**
     * 添加录取信息
     */
    boolean addEnrollment(StudentEnrollment enrollment);

    /**
     * 审批录取状态
     */
    boolean updateEnrollmentStatus(StudentEnrollment enrollment);

    /**
     * 修改录取信息
     */
    boolean updateEnrollment(StudentEnrollment enrollment);

    /**
     * 删除录取信息
     */
    boolean deleteEnrollment(Long enrollmentId);
}
