package com.ruoyi.student.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Author 范佳兴
 * @date 2025/4/15 10:22
 */
@TableName("student_grade")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentGrade implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long gradeId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    @TableField(exist = false)
    private String userName;

    /**
     * 课程ID
     */
    private Long courseId;

    /**
     * 课程名称
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 教师ID
     */
    private Long teacherId;

    /**
     * 教师名
     */
    @TableField(exist = false)
    private String teacherName;

    /**
     * 成绩
     */
    private BigDecimal grade;

    /**
     * 学期
     */
    private String semester;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
