package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentCourse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 16:47
 */
@Mapper
public interface StudentCourseMapper {
    /**
     * 查询所有课程
     *
     * @return 课程列表
     */
    List<StudentCourse> getAllCourses();

    /**
     * 根据课程ID查询课程信息
     *
     * @param courseId 课程ID
     * @return 课程对象
     */
    StudentCourse getCourseById(Long courseId);

    /**
     * 根据用户ID查询用户课程
     *
     * @param userId 用户ID
     * @return 课程列表
     */
    List<StudentCourse> getCoursesByUserId(Long userId);

    /**
     * 添加课程
     *
     * @param course 课程信息
     * @return 添加成功返回影响行数
     */
    int addCourse(StudentCourse course);

    /**
     * 更新课程信息
     *
     * @param course 课程对象
     * @return 更新成功返回影响行数
     */
    int updateCourse(StudentCourse course);

    /**
     * 删除课程
     *
     * @param courseId 课程ID
     * @return 删除成功返回影响行数
     */
    int deleteCourse(Long courseId);
}
