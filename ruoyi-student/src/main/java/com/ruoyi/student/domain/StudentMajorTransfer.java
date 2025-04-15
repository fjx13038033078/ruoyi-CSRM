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

/**
 * @Author 范佳兴
 * @date 2025/4/15 15:03
 */
@TableName("student_major_transfer")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentMajorTransfer implements Serializable {
    /**
     * 转专业申请ID（主键自增）
     */
    @TableId(type = IdType.AUTO)
    private Long transferId;

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
     * 原专业
     */
    private String fromMajor;

    /**
     * 目标专业
     */
    private String toMajor;

    /**
     * 转专业理由
     */
    private String reason;

    /**
     * 申请时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate applyDate;

    /**
     * 审批状态（0=待审批 1=已批准 2=驳回）
     */
    private Integer approvalStatus;

    /**
     * 新学号
     */
    private String newNum;

    /**
     * 旧学号
     */
    private String oldNum;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
