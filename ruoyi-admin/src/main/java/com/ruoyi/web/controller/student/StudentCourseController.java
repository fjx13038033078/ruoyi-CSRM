package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentCourse;
import com.ruoyi.student.service.StudentCourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 16:51
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/student/course")
public class StudentCourseController extends BaseController {
    private final StudentCourseService studentCourseService;

    /**
     * 获取所有课程
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllCourses() {
        startPage();
        List<StudentCourse> list = studentCourseService.getAllCourses();
        return getDataTable(list);
    }

    /**
     * 根据ID获取课程详情
     */
    @GetMapping("/detail")
    public AjaxResult getCourse(@RequestParam Long courseId) {
        return success(studentCourseService.getCourseById(courseId));
    }

    /**
     * 添加课程
     */
    @PostMapping("/add")
    public AjaxResult addCourse(@RequestBody StudentCourse studentCourse) {
        return toAjax(studentCourseService.addCourse(studentCourse));
    }

    /**
     * 修改课程
     */
    @PostMapping("/update")
    public AjaxResult updateCourse(@RequestBody StudentCourse studentCourse) {
        return toAjax(studentCourseService.updateCourse(studentCourse));
    }

    /**
     * 删除课程
     */
    @GetMapping("/delete")
    public AjaxResult deleteCourse(@RequestParam Long courseId) {
        return toAjax(studentCourseService.deleteCourse(courseId));
    }
}
