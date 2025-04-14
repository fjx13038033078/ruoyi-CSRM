package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentCourseSelection;
import com.ruoyi.student.mapper.StudentCourseSelectionMapper;
import com.ruoyi.student.service.StudentCourseSelectionService;
import com.ruoyi.student.service.StudentCourseService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 17:39
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentCourseSelectionServiceImpl implements StudentCourseSelectionService {

    private final StudentCourseSelectionMapper selectionMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    private final StudentCourseService studentCourseService;

    @Override
    public List<StudentCourseSelection> getAllSelections() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            List<StudentCourseSelection> allSelections = selectionMapper.getAllSelections();
            fillUserName(allSelections);
            return allSelections;
        } else {
            List<StudentCourseSelection> selectionByUserId = selectionMapper.getSelectionByUserId(userId);
            fillUserName(selectionByUserId);
            return selectionByUserId;
        }
    }

    @Override
    public StudentCourseSelection getSelectionById(Long selectionId) {
        return selectionMapper.getSelectionById(selectionId);
    }

    @Override
    public boolean addSelection(StudentCourseSelection selection) {
        return selectionMapper.addSelection(selection) > 0;
    }

    @Override
    public boolean updateSelection(StudentCourseSelection selection) {
        return selectionMapper.updateSelection(selection) > 0;
    }

    @Override
    public boolean deleteSelection(Long selectionId) {
        return selectionMapper.deleteSelection(selectionId) > 0;
    }

    /**
     * 填充用户名
     * @param selections
     */
    private void fillUserName(List<StudentCourseSelection> selections) {
        for (StudentCourseSelection selection : selections) {
            Long userId = selection.getUserId();
            String userName = iSysUserService.selectUserById(userId).getNickName()== null ? "" : iSysUserService.selectUserById(userId).getNickName();
            selection.setUserName(userName);
            selection.setCourseName(studentCourseService.getCourseById(selection.getCourseId()).getCourseName());
        }
    }
}
