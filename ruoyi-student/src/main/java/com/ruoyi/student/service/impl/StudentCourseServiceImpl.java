package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.mapper.StudentCourseMapper;
import com.ruoyi.student.service.StudentCourseService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 16:50
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseServiceImpl implements StudentCourseService {
    private final StudentCourseMapper studentCourseMapper;

    private final ISysRoleService iSysRoleService;

    @Override
    public List<StudentCourse> getAllCourses() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            return studentCourseMapper.getAllCourses();
        } else {
            return studentCourseMapper.getCoursesByUserId(userId);
        }
    }

    @Override
    public StudentCourse getCourseById(Long courseId) {
        return studentCourseMapper.getCourseById(courseId);
    }

    @Override
    public boolean addCourse(StudentCourse studentCourse) {
        return studentCourseMapper.addCourse(studentCourse) > 0;
    }

    @Override
    public boolean updateCourse(StudentCourse studentCourse) {
        return studentCourseMapper.updateCourse(studentCourse) > 0;
    }

    @Override
    public boolean deleteCourse(Long courseId) {
        return studentCourseMapper.deleteCourse(courseId) > 0;
    }
}
