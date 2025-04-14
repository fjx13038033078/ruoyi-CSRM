package com.ruoyi.student.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @Author 范佳兴
 * @date 2025/4/14 17:33
 */
@TableName("student_course_selection")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentCourseSelection implements Serializable {
    /**
     * 主键自增ID
     */
    @TableId(type = IdType.AUTO)
    private Long selectionId;

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
     * 课程名
     */
    @TableField(exist = false)
    private String courseName;

    /**
     * 选课时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate selectionDate;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
