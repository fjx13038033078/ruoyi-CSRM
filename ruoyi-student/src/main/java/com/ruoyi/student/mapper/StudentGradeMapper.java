package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentGrade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 10:23
 */
@Mapper
public interface StudentGradeMapper {
    /**
     * 获取所有成绩记录
     * @return 成绩列表
     */
    List<StudentGrade> getAllGrades();

    /**
     * 根据成绩ID获取成绩信息
     * @param gradeId 成绩ID
     * @return 成绩信息
     */
    StudentGrade getGradeById(Long gradeId);

    /**
     * 根据用户ID获取成绩列表
     * @param userId 用户ID
     * @return 成绩列表
     */
    List<StudentGrade> getGradesByUserId(Long userId);

    /**
     * 根据教师ID获取成绩列表
     * @param userId 教师ID
     * @return 成绩列表
     */
    List<StudentGrade> getGradesByTeacherId(Long userId);

    /**
     * 添加成绩记录
     * @param studentGrade 成绩对象
     * @return 影响行数
     */
    int addGrade(StudentGrade studentGrade);

    /**
     * 更新成绩记录
     * @param studentGrade 成绩对象
     * @return 影响行数
     */
    int updateGrade(StudentGrade studentGrade);

    /**
     * 删除成绩记录
     * @param gradeId 成绩ID
     * @return 影响行数
     */
    int deleteGrade(Long gradeId);
}
