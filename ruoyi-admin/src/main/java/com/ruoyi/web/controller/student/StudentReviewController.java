package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentReview;
import com.ruoyi.student.service.StudentReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/9 17:50
 */
@RestController
@RequestMapping("/student/review")
@RequiredArgsConstructor
public class StudentReviewController extends BaseController {
    private final StudentReviewService studentReviewService;

    /**
     * 获取所有学生复查列表
     * @return 学生复查记录
     */
    @GetMapping("/listAll")
    public TableDataInfo listAll() {
        startPage();
        List<StudentReview> reviews = studentReviewService.getAllStudentReviews();
        return getDataTable(reviews);
    }

    /**
     * 获取指定复查信息
     * @param reviewId 复查ID
     * @return 单条复查记录
     */
    @GetMapping("/detail")
    public AjaxResult detail(@RequestParam Long reviewId) {
        return success(studentReviewService.getStudentReviewById(reviewId));
    }

    /**
     * 添加复查信息
     * @param studentReview 待添加对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult add(@RequestBody StudentReview studentReview) {
        return toAjax(studentReviewService.addStudentReview(studentReview));
    }

    /**
     * 更新复查信息
     * @param studentReview 待更新对象
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult update(@RequestBody StudentReview studentReview) {
        return toAjax(studentReviewService.updateStudentReview(studentReview));
    }

    /**
     * 删除复查记录
     * @param reviewId 复查ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult delete(@RequestParam Long reviewId) {
        return toAjax(studentReviewService.deleteStudentReview(reviewId));
    }
}
