package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentEnrollment;
import com.ruoyi.student.mapper.StudentEnrollmentMapper;
import com.ruoyi.student.service.StudentEnrollmentService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 11:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentEnrollmentServiceImpl implements StudentEnrollmentService {

    private final StudentEnrollmentMapper enrollmentMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    @Override
    public List<StudentEnrollment> getAllEnrollments() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("temporary") || role.equalsIgnoreCase("common")) {
            StudentEnrollment enrollmentByUserId = enrollmentMapper.getEnrollmentByUserId(userId);
            List<StudentEnrollment> enrollmentByUserIdL = new ArrayList<>();
            if (enrollmentByUserId != null) {
                enrollmentByUserIdL.add(enrollmentByUserId);
            }
            fillUserName(enrollmentByUserIdL);
            return enrollmentByUserIdL;
        } else {
            List<StudentEnrollment> allEnrollments = enrollmentMapper.getAllEnrollments();
            fillUserName(allEnrollments);
            return allEnrollments;
        }
    }

    @Override
    public StudentEnrollment getEnrollmentById(Long enrollmentId) {
        return enrollmentMapper.getEnrollmentById(enrollmentId);
    }

    @Override
    public boolean addEnrollment(StudentEnrollment enrollment) {
        Long userId = SecurityUtils.getUserId();
        enrollment.setUserId(userId);
        enrollment.setEnrollmentStatus(1);
        enrollment.setLeaveRequest(3);
        enrollment.setReportDate(LocalDate.now());
        return enrollmentMapper.addEnrollment(enrollment) > 0;
    }

    @Override
    public boolean updateEnrollmentStatus(StudentEnrollment enrollment) {
        return enrollmentMapper.updateEnrollmentStatus(enrollment) > 0;
    }

    @Override
    public boolean updateEnrollment(StudentEnrollment enrollment) {
        return enrollmentMapper.updateEnrollment(enrollment) > 0;
    }

    @Override
    public boolean deleteEnrollment(Long enrollmentId) {
        return enrollmentMapper.deleteEnrollment(enrollmentId) > 0;
    }

    //填充用户名
    private void fillUserName(List<StudentEnrollment> enrollments) {
        for (StudentEnrollment enrollment : enrollments) {
            Long userId = enrollment.getUserId();
            String userName = iSysUserService.selectUserById(userId).getNickName()== null ? "" : iSysUserService.selectUserById(userId).getNickName();
            enrollment.setUserName(userName);
        }
    }
}
