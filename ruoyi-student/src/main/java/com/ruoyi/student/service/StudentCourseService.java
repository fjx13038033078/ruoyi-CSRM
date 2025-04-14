package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentCourse;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 16:49
 */
public interface StudentCourseService {
    /**
     * 获取所有课程
     * @return 课程列表
     */
    List<StudentCourse> getAllCourses();

    /**
     * 根据课程ID获取课程信息
     * @param courseId 课程ID
     * @return 课程信息
     */
    StudentCourse getCourseById(Long courseId);

    /**
     * 添加课程
     * @param studentCourse 课程实体
     * @return 是否成功
     */
    boolean addCourse(StudentCourse studentCourse);

    /**
     * 更新课程信息
     * @param studentCourse 课程实体
     * @return 是否成功
     */
    boolean updateCourse(StudentCourse studentCourse);

    /**
     * 删除课程
     * @param courseId 课程ID
     * @return 是否成功
     */
    boolean deleteCourse(Long courseId);
}
