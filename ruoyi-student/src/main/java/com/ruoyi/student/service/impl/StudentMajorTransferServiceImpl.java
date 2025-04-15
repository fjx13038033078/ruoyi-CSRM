package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentMajorTransfer;
import com.ruoyi.student.mapper.StudentMajorTransferMapper;
import com.ruoyi.student.service.StudentMajorTransferService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 15:11
 */
@Service
@RequiredArgsConstructor
public class StudentMajorTransferServiceImpl implements StudentMajorTransferService {

    private final StudentMajorTransferMapper transferMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    /**
     * 获取所有转专业记录
     */
    @Override
    public List<StudentMajorTransfer> getAllTransfers() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            List<StudentMajorTransfer> allTransfers = transferMapper.getAllTransfers();
            fillUserInfo(allTransfers);
            return allTransfers;
        } else {
            StudentMajorTransfer transferByUserId = transferMapper.getTransferByUserId(userId);
            fillUserInfo(transferByUserId);
            List<StudentMajorTransfer> transferByUserIds = new ArrayList<>();
            transferByUserIds.add(transferByUserId);
            return transferByUserIds;
        }
    }

    /**
     * 根据ID获取转专业记录
     */
    @Override
    public StudentMajorTransfer getTransferById(Long transferId) {
        return transferMapper.getTransferById(transferId);
    }

    /**
     * 添加转专业记录
     */
    @Override
    public boolean addTransfer(StudentMajorTransfer transfer) {
        Long userId = SecurityUtils.getUserId();
        transfer.setUserId(userId);
        transfer.setApplyDate(LocalDate.now());
        return transferMapper.addTransfer(transfer) > 0;
    }

    /**
     * 更新转专业记录
     */
    @Override
    public boolean updateTransfer(StudentMajorTransfer transfer) {
        return transferMapper.updateTransfer(transfer) > 0;
    }

    @Override
    public boolean updateApprovalStatus(StudentMajorTransfer transfer) {
        if (transfer.getApprovalStatus() == 1) {
            Long userId = SecurityUtils.getUserId();
            transfer.setOldNum(iSysUserService.selectUserById(userId).getUserName());
        }
        return transferMapper.updateApprovalStatus(transfer) > 0;
    }

    @Override
    public boolean assignNewStudentNum(StudentMajorTransfer transfer) {
        return transferMapper.assignNewStudentNum(transfer) > 0;
    }

    /**
     * 删除转专业记录
     */
    @Override
    public boolean deleteTransfer(Long transferId) {
        return transferMapper.deleteTransfer(transferId) > 0;
    }

    private void fillUserInfo(StudentMajorTransfer transfer) {
        Long userId = SecurityUtils.getUserId();
        String userName = iSysUserService.selectUserById(userId).getNickName();
        transfer.setUserName(userName);
    }

    private void fillUserInfo(List<StudentMajorTransfer> transfers) {
        for (StudentMajorTransfer transfer : transfers) {
            fillUserInfo(transfer);
        }
    }
}
