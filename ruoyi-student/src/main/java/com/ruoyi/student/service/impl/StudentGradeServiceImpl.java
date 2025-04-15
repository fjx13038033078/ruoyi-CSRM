package com.ruoyi.student.service.impl;

import com.ruoyi.student.domain.StudentGrade;
import com.ruoyi.student.mapper.StudentGradeMapper;
import com.ruoyi.student.service.StudentGradeService;
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

    @Override
    public List<StudentGrade> getAllStudentGrades() {
        return studentGradeMapper.getAllGrades();
    }

    @Override
    public StudentGrade getStudentGradeById(Long gradeId) {
        return studentGradeMapper.getGradeById(gradeId);
    }

    @Override
    public boolean addStudentGrade(StudentGrade studentGrade) {
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
}
