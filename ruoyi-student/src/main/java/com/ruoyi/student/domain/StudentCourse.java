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

/**
 * @Author 范佳兴
 * @date 2025/4/14 16:46
 */
@TableName("student_course")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentCourse implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 学分
     */
    private Integer credits;

    /**
     * 学时
     */
    private Integer hours;

    /**
     * 老师ID
     */
    private Long userId;

    /**
     * 学期
     */
    private String semester;

    /**
     * 课程描述
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
