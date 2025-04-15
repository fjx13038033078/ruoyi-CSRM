package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentGrade;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 10:25
 */
public interface StudentGradeService {
    /**
     * 获取所有学生成绩列表
     *
     * @return 学生成绩列表
     */
    List<StudentGrade> getAllStudentGrades();

    /**
     * 根据成绩ID获取学生成绩信息
     *
     * @param gradeId 成绩ID
     * @return 学生成绩信息
     */
    StudentGrade getStudentGradeById(Long gradeId);

    /**
     * 添加学生成绩
     *
     * @param studentGrade 成绩信息
     * @return 添加结果
     */
    boolean addStudentGrade(StudentGrade studentGrade);

    /**
     * 更新学生成绩
     *
     * @param studentGrade 成绩信息
     * @return 更新结果
     */
    boolean updateStudentGrade(StudentGrade studentGrade);

    /**
     * 删除学生成绩
     *
     * @param gradeId 成绩ID
     * @return 删除结果
     */
    boolean deleteStudentGrade(Long gradeId);
}
