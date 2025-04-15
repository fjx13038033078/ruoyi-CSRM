package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentGrade;
import com.ruoyi.student.service.StudentGradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ruoyi.common.core.domain.AjaxResult.success;
import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @Author 范佳兴
 * @date 2025/4/15 10:27
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/student/grade")
public class StudentGradeController extends BaseController {
    private final StudentGradeService studentGradeService;

    @GetMapping("/listAll")
    public TableDataInfo listAllGrades() {
        startPage();
        List<StudentGrade> grades = studentGradeService.getAllStudentGrades();
        return getDataTable(grades);
    }

    @GetMapping("/detail")
    public AjaxResult getGradeDetail(@RequestParam Long gradeId) {
        return success(studentGradeService.getStudentGradeById(gradeId));
    }

    @PostMapping("/add")
    public AjaxResult addGrade(@RequestBody StudentGrade studentGrade) {
        return toAjax(studentGradeService.addStudentGrade(studentGrade));
    }

    @PostMapping("/update")
    public AjaxResult updateGrade(@RequestBody StudentGrade studentGrade) {
        return toAjax(studentGradeService.updateStudentGrade(studentGrade));
    }

    @GetMapping("/delete")
    public AjaxResult deleteGrade(@RequestParam Long gradeId) {
        return toAjax(studentGradeService.deleteStudentGrade(gradeId));
    }
}
