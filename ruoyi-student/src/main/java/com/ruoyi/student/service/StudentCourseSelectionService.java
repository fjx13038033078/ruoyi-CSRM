package com.ruoyi.student.service;

import com.ruoyi.student.domain.StudentCourseSelection;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 17:39
 */
public interface StudentCourseSelectionService {
    /**
     * 查询所有选课信息
     *
     * @return 选课信息列表
     */
    List<StudentCourseSelection> getAllSelections();

    /**
     * 根据选课ID查询详情
     *
     * @param selectionId 选课ID
     * @return 选课信息
     */
    StudentCourseSelection getSelectionById(Long selectionId);

    /**
     * 添加选课信息
     *
     * @param selection 待添加的选课信息
     * @return 是否成功
     */
    boolean addSelection(StudentCourseSelection selection);

    /**
     * 更新选课信息
     *
     * @param selection 待更新的选课信息
     * @return 是否成功
     */
    boolean updateSelection(StudentCourseSelection selection);

    /**
     * 删除选课信息
     *
     * @param selectionId 待删除的选课ID
     * @return 是否成功
     */
    boolean deleteSelection(Long selectionId);
}
