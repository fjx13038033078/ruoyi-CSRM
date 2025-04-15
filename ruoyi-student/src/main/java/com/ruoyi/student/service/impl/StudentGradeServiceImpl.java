package com.ruoyi.student.service.impl;

import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.StudentGrade;
import com.ruoyi.student.mapper.StudentGradeMapper;
import com.ruoyi.student.service.StudentCourseService;
import com.ruoyi.student.service.StudentGradeService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 10:25
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StudentGradeServiceImpl implements StudentGradeService {
    private final StudentGradeMapper studentGradeMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    private final StudentCourseService studentCourseService;

    @Override
    public List<StudentGrade> getAllStudentGrades() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin")) {
            List<StudentGrade> allGrades = studentGradeMapper.getAllGrades();
            fillUserInfo(allGrades);
            return allGrades;
        } else if (role.equalsIgnoreCase("teacher")){
            List<StudentGrade> gradesByTeacherId = studentGradeMapper.getGradesByTeacherId(userId);
            fillUserInfo(gradesByTeacherId);
            return gradesByTeacherId;
        } else {
            List<StudentGrade> gradesByUserId = studentGradeMapper.getGradesByUserId(userId);
            fillUserInfo(gradesByUserId);
            return gradesByUserId;
        }
    }

    @Override
    public StudentGrade getStudentGradeById(Long gradeId) {
        return studentGradeMapper.getGradeById(gradeId);
    }

    @Override
    public boolean addStudentGrade(StudentGrade studentGrade) {
        Long courseId = studentGrade.getCourseId();
        studentGrade.setTeacherId(studentCourseService.getCourseById(courseId).getUserId());
        return studentGradeMapper.addGrade(studentGrade) > 0;
    }

    @Override
    public boolean updateStudentGrade(StudentGrade studentGrade) {
        return studentGradeMapper.updateGrade(studentGrade) > 0;
    }

    @Override
    public boolean deleteStudentGrade(Long gradeId) {
        return studentGradeMapper.deleteGrade(gradeId) > 0;
    }

    private void fillUserInfo(List<StudentGrade> studentGrades) {
        for (StudentGrade studentGrade : studentGrades) {
            Long userId = studentGrade.getUserId();
            Long teacherId = studentGrade.getTeacherId();
            Long courseId = studentGrade.getCourseId();
            studentGrade.setUserName(iSysUserService.selectUserById(userId).getNickName());
            studentGrade.setTeacherName(iSysUserService.selectUserById(teacherId).getNickName());
            studentGrade.setCourseName(studentCourseService.getCourseById(courseId).getCourseName());
        }
    }
}
