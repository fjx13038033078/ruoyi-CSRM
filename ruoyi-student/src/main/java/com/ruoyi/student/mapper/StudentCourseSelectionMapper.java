package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.StudentCourseSelection;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 17:34
 */
@Mapper
public interface StudentCourseSelectionMapper {
    /**
     * 获取所有选课记录
     *
     * @return 选课记录列表
     */
    List<StudentCourseSelection> getAllSelections();

    /**
     * 根据ID查询选课记录
     *
     * @param selectionId 选课ID
     * @return 对应选课记录
     */
    StudentCourseSelection getSelectionById(Long selectionId);

    /**
     * 根据用户ID查询选课记录
     *
     * @param userId 用户ID
     * @return 选课记录列表
     */
    List<StudentCourseSelection> getSelectionByUserId(Long userId);

    /**
     * 添加选课记录
     *
     * @param selection 待添加记录
     * @return 影响行数
     */
    int addSelection(StudentCourseSelection selection);

    /**
     * 更新选课记录
     *
     * @param selection 待更新记录
     * @return 影响行数
     */
    int updateSelection(StudentCourseSelection selection);

    /**
     * 删除选课记录
     *
     * @param selectionId 待删除记录ID
     * @return 影响行数
     */
    int deleteSelection(Long selectionId);
}
