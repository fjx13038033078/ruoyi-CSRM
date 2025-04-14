package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentCourseSelection;
import com.ruoyi.student.service.StudentCourseSelectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/14 17:40
 */
@RestController
@RequestMapping("/study/selection")
@RequiredArgsConstructor
public class StudentCourseSelectionController extends BaseController {
    private final StudentCourseSelectionService selectionService;

    /**
     * 获取所有选课信息
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllSelections() {
        startPage();
        List<StudentCourseSelection> list = selectionService.getAllSelections();
        return getDataTable(list);
    }

    /**
     * 查询详情
     */
    @GetMapping("/detail")
    public AjaxResult getSelectionById(@RequestParam Long selectionId) {
        return success(selectionService.getSelectionById(selectionId));
    }

    /**
     * 添加选课信息
     */
    @PostMapping("/add")
    public AjaxResult addSelection(@RequestBody StudentCourseSelection selection) {
        return toAjax(selectionService.addSelection(selection));
    }

    /**
     * 修改选课信息
     */
    @PostMapping("/update")
    public AjaxResult updateSelection(@RequestBody StudentCourseSelection selection) {
        return toAjax(selectionService.updateSelection(selection));
    }

    /**
     * 删除选课信息
     */
    @GetMapping("/delete")
    public AjaxResult deleteSelection(@RequestParam Long selectionId) {
        return toAjax(selectionService.deleteSelection(selectionId));
    }
}
