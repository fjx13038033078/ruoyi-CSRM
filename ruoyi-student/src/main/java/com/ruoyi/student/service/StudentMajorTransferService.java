package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentMajorTransfer;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 15:11
 */
public interface StudentMajorTransferService {
    /**
     * 查询所有转专业记录
     * @return 转专业记录列表
     */
    List<StudentMajorTransfer> getAllTransfers();

    /**
     * 根据ID获取转专业详情
     * @param transferId 转专业记录ID
     * @return 转专业记录
     */
    StudentMajorTransfer getTransferById(Long transferId);

    /**
     * 添加转专业记录
     * @param transfer 转专业对象
     * @return 是否添加成功
     */
    boolean addTransfer(StudentMajorTransfer transfer);

    /**
     * 更新转专业记录
     * @param transfer 转专业对象
     * @return 是否更新成功
     */
    boolean updateTransfer(StudentMajorTransfer transfer);

    /**
     * 更新转专业记录的审批状态
     * @param transfer 转专业对象
     * @return 是否更新成功
     */
    boolean updateApprovalStatus(StudentMajorTransfer transfer);

    /**
     * 更新转专业记录的审批状态
     * @param transfer 转专业对象
     * @return 是否更新成功
     */
    boolean assignNewStudentNum(StudentMajorTransfer transfer);

    /**
     * 删除转专业记录
     * @param transferId 转专业记录ID
     * @return 是否删除成功
     */
    boolean deleteTransfer(Long transferId);
}
