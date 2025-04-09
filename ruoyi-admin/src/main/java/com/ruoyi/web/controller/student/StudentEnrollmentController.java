package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentEnrollment;
import com.ruoyi.student.service.StudentEnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 11:12
 */
@RestController
@RequestMapping("/student/enrollment")
@RequiredArgsConstructor
public class StudentEnrollmentController extends BaseController {
    private final StudentEnrollmentService enrollmentService;

    /**
     * 获取所有录取信息列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAll() {
        startPage();
        List<StudentEnrollment> list = enrollmentService.getAllEnrollments();
        return getDataTable(list);
    }

    /**
     * 根据ID获取录取信息详情
     */
    @GetMapping("/detail")
    public AjaxResult getDetail(@RequestParam Long enrollmentId) {
        return success(enrollmentService.getEnrollmentById(enrollmentId));
    }

    /**
     * 添加录取信息
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody StudentEnrollment enrollment) {
        return toAjax(enrollmentService.addEnrollment(enrollment));
    }

    @PostMapping("/updateStatus")
    public AjaxResult updateStatus(@RequestBody StudentEnrollment enrollment) {
        return toAjax(enrollmentService.updateEnrollmentStatus(enrollment));
    }

    /**
     * 修改录取信息
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody StudentEnrollment enrollment) {
        return toAjax(enrollmentService.updateEnrollment(enrollment));
    }

    /**
     * 删除录取信息
     */
    @GetMapping("/delete")
    public AjaxResult delete(@RequestParam Long enrollmentId) {
        return toAjax(enrollmentService.deleteEnrollment(enrollmentId));
    }
}
