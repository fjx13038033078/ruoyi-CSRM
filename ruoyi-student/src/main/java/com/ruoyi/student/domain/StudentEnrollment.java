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
 * @date 2025/4/9 11:05
 */
@TableName("student_enrollment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentEnrollment implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long enrollmentId;

    /**
     * 用户ID
     */
    private Long userId;

    @TableField(exist = false)
    private String userName;

    /**
     * 录取通知书编号
     */
    private String noticeNumber;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 报到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    /**
     * 请假是否批准（0=待审核，1=批准，2=驳回，3=未请假）
     */
    private Integer leaveRequest;

    /**
     * 报到状态（0=未报到，1=已报到，2=逾期未报到，3=放弃资格）
     */
    private Integer enrollmentStatus;

    /**
     * 上传的文件
     */
    private String fileName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
