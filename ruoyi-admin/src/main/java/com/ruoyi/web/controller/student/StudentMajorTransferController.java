package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.StudentMajorTransfer;
import com.ruoyi.student.service.StudentMajorTransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/4/15 15:12
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/student/transfer")
public class StudentMajorTransferController extends BaseController {
    private final StudentMajorTransferService transferService;

    /**
     * 获取所有转专业记录列表（带分页）
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllTransfers() {
        startPage();
        List<StudentMajorTransfer> list = transferService.getAllTransfers();
        return getDataTable(list);
    }

    /**
     * 获取指定转专业记录详情
     * @param transferId 转专业ID
     */
    @GetMapping("/detail")
    public AjaxResult getTransfer(@RequestParam Long transferId) {
        return success(transferService.getTransferById(transferId));
    }

    /**
     * 添加转专业记录
     * @param transfer 转专业对象
     */
    @PostMapping("/add")
    public AjaxResult addTransfer(@RequestBody StudentMajorTransfer transfer) {
        return toAjax(transferService.addTransfer(transfer));
    }

    /**
     * 更新转专业记录
     * @param transfer 转专业对象
     */
    @PostMapping("/update")
    public AjaxResult updateTransfer(@RequestBody StudentMajorTransfer transfer) {
        return toAjax(transferService.updateTransfer(transfer));
    }

    /**
     * 更新转专业记录的审批状态
     * @param transfer 转专业对象
     */
    @PostMapping("/updateApprovalStatus")
    public AjaxResult updateApprovalStatus(@RequestBody StudentMajorTransfer transfer) {
        return toAjax(transferService.updateApprovalStatus(transfer));
    }

    /**
     * 更新转专业记录的审批状态
     * @param transfer 转专业对象
     */
    @PostMapping("/assignNewStudentNum")
    public AjaxResult assignNewStudentNum(@RequestBody StudentMajorTransfer transfer) {
        return toAjax(transferService.assignNewStudentNum(transfer));
    }

    /**
     * 删除转专业记录
     * @param transferId 转专业ID
     */
    @GetMapping("/delete")
    public AjaxResult deleteTransfer(@RequestParam Long transferId) {
        return toAjax(transferService.deleteTransfer(transferId));
    }
}
