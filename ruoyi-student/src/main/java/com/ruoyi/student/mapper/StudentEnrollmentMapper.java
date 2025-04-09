package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentEnrollment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 11:06
 */
@Mapper
public interface StudentEnrollmentMapper {
    List<StudentEnrollment> getAllEnrollments();

    StudentEnrollment getEnrollmentById(Long enrollmentId);

    StudentEnrollment getEnrollmentByUserId(Long userId);

    int addEnrollment(StudentEnrollment enrollment);

    int updateEnrollmentStatus(StudentEnrollment enrollment);

    int updateEnrollment(StudentEnrollment enrollment);

    int deleteEnrollment(Long enrollmentId);
}
