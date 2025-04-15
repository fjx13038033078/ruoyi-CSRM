package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentMajorTransfer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 15:04
 */
@Mapper
public interface StudentMajorTransferMapper {
    /**
     * 获取所有转专业申请
     *
     * @return 申请列表
     */
    List<StudentMajorTransfer> getAllTransfers();

    /**
     * 根据ID获取申请信息
     *
     * @param transferId 转专业申请ID
     * @return 申请信息
     */
    StudentMajorTransfer getTransferById(Long transferId);

    /**
     * 根据用户ID获取转专业申请
     *
     * @param userId 用户ID
     * @return 转专业申请信息
     */
    StudentMajorTransfer getTransferByUserId(Long userId);

    /**
     * 添加转专业申请
     *
     * @param transfer 转专业申请信息
     * @return 插入影响行数
     */
    int addTransfer(StudentMajorTransfer transfer);

    /**
     * 更新转专业申请
     *
     * @param transfer 转专业申请信息
     * @return 更新影响行数
     */
    int updateTransfer(StudentMajorTransfer transfer);

    /**
     * 仅更新新学号
     * @param transfer 转专业对象（只需包含 transferId 和 newNum）
     * @return 更新成功返回影响行数
     */
    int assignNewStudentNum(StudentMajorTransfer transfer);

    /**
     * 仅更新审批状态
     * @param transfer 包含 transferId 和 approvalStatus
     * @return 更新成功返回影响的行数
     */
    int updateApprovalStatus(StudentMajorTransfer transfer);

    /**
     * 删除转专业申请
     *
     * @param transferId 待删除申请ID
     * @return 删除影响行数
     */
    int deleteTransfer(Long transferId);
}
