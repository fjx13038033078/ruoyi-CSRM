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
 * @date 2025/4/9 17:44
 */
@TableName("student_review")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class StudentReview implements Serializable {
    /**
     * 主键ID
     */
    @TableId(type = IdType.AUTO)
    private Long reviewId;

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
     * 姓名
     */
    @TableField(exist = false)
    private String nickName;

    /**
     * 复查时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate reviewDate;

    /**
     * 录取材料是否合规
     */
    private Integer documentValid;

    /**
     * 身份是否匹配
     */
    private Integer identityMatch;

    /**
     * 体检是否合格
     */
    private Integer physicalStatus;

    /**
     * 复查结论（0=未复查，1=合格，2=不合格）
     */
    private Integer finalResult;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
