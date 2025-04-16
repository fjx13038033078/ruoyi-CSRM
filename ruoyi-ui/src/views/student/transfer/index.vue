<script>
import { listTransfers, getTransfer, addTransfer, updateTransfer, updateApprovalStatus, assignNewStudentNum, deleteTransfer } from "@/api/student/transfer";

export default {
  name: "StudentTransfer",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 转专业信息表格数据
      transferList: [],
      // 弹出层标题
      title: "",
      // 是否显示申请弹出层
      open: false,
      // 是否显示审批弹出层
      approvalOpen: false,
      // 是否显示新学号弹出层
      assignNumOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 申请表单参数
      form: {
        transferId: null,
        userId: null,
        fromMajor: "",
        toMajor: "",
        reason: "",
        approvalStatus: 0,
        newNum: null,
        oldNum: null
      },
      // 审批表单参数
      approvalForm: {
        transferId: null,
        approvalStatus: 1
      },
      // 新学号表单参数
      studentNumForm: {
        transferId: null,
        newNum: null
      },
      // 审批状态选项
      approvalStatusOptions: [
        { value: 0, label: '待审批' },
        { value: 1, label: '已批准' },
        { value: 2, label: '驳回' }
      ],
      // 表单校验规则
      rules: {
        fromMajor: [
          { required: true, message: "请输入原专业", trigger: "blur" }
        ],
        toMajor: [
          { required: true, message: "请输入目标专业", trigger: "blur" }
        ],
        reason: [
          { required: true, message: "请输入转专业理由", trigger: "blur" }
        ]
      },
      // 审批表单校验规则
      approvalRules: {
        approvalStatus: [
          { required: true, message: "请选择审批状态", trigger: "change" }
        ]
      },
      // 学号表单校验规则
      studentNumRules: {
        newNum: [
          { required: true, message: "请输入新学号", trigger: "blur" }
        ]
      },
      // 当前操作的行数据
      currentRow: {}
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询转专业信息列表 */
    getList() {
      this.loading = true;
      listTransfers(this.queryParams).then(response => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 审批取消按钮
    cancelApproval() {
      this.approvalOpen = false;
    },
    // 学号分配取消按钮
    cancelAssignNum() {
      this.assignNumOpen = false;
    },
    // 表单重置
    reset() {
      this.form = {
        transferId: null,
        userId: null,
        fromMajor: "",
        toMajor: "",
        reason: "",
        approvalStatus: 0,
        newNum: null,
        oldNum: null
      };
      this.resetForm("form");
    },
    /** 发起申请按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "发起转专业申请";
    },
    /** 提交申请 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addTransfer(this.form).then(response => {
            this.$message.success("申请提交成功");
            this.open = false;
            this.getList();
          }).catch(error => {
            console.error("申请提交失败", error);
          });
        }
      });
    },
    /** 审批操作 */
    handleApproval(row) {
      this.currentRow = row;
      this.approvalForm = {
        transferId: row.transferId,
        approvalStatus: row.approvalStatus
      };
      this.approvalOpen = true;
    },
    /** 提交审批 */
    submitApproval() {
      this.$refs["approvalForm"].validate(valid => {
        if (valid) {
          updateApprovalStatus(this.approvalForm).then(response => {
            this.$message.success("审批操作成功");
            this.approvalOpen = false;
            this.getList();
          }).catch(error => {
            console.error("审批操作失败", error);
          });
        }
      });
    },
    /** 分配新学号操作 */
    handleAssignNum(row) {
      this.currentRow = row;
      this.studentNumForm = {
        userId: row.userId,
        transferId: row.transferId,
        newNum: null
      };
      this.assignNumOpen = true;
    },
    /** 提交新学号 */
    submitStudentNum() {
      this.$refs["studentNumForm"].validate(valid => {
        if (valid) {
          assignNewStudentNum(this.studentNumForm).then(response => {
            this.$message.success("新学号分配成功");
            this.assignNumOpen = false;
            this.getList();
          }).catch(error => {
            console.error("新学号分配失败", error);
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除该转专业申请?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return deleteTransfer(row.transferId);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    },
    // 获取审批状态标签类型
    getApprovalStatusType(status) {
      switch (status) {
        case 0: return 'info';    // 待审批
        case 1: return 'success'; // 已批准
        case 2: return 'danger';  // 驳回
        default: return 'info';
      }
    },
    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString();
    }
  }
};
</script>

<template>
  <div class="app-container">
    <!-- 新增按钮 -->
    <div class="mb-20">
      <el-button type="primary" @click="handleAdd" v-hasPermi="['student:transfer:add']">发起申请</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="transferList" border style="width: 100%">
      <el-table-column label="申请ID" align="center" prop="transferId" width="80" />
      <el-table-column label="申请人" align="center" prop="userName" width="120" />
      <el-table-column label="原专业" align="center" prop="fromMajor" />
      <el-table-column label="目标专业" align="center" prop="toMajor" />
      <el-table-column label="申请日期" align="center" width="120">
        <template slot-scope="scope">
          {{ formatDate(scope.row.applyDate) }}
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" width="100">
        <template slot-scope="scope">
          <el-tag :type="getApprovalStatusType(scope.row.approvalStatus)">
            {{ scope.row.approvalStatus === 0 ? '待审批' :
               scope.row.approvalStatus === 1 ? '已批准' : '驳回' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="原学号" align="center" prop="oldNum" width="120" />
      <el-table-column label="新学号" align="center" prop="newNum" width="120" />
      <el-table-column label="操作" align="center">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['student:transfer:delete']">删除</el-button>
          <el-button type="primary" size="mini" @click="handleApproval(scope.row)" v-hasPermi="['student:transfer:approval']">审核</el-button>
          <el-button
            type="success"
            size="mini"
            @click="handleAssignNum(scope.row)"
            v-hasPermi="['student:transfer:assign']"
            v-if="scope.row.approvalStatus === 1 && !scope.row.newNum">设置学号</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 申请表单对话框 -->
    <el-dialog :visible.sync="open" :title="title" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="原专业" prop="fromMajor">
          <el-input v-model="form.fromMajor" placeholder="请输入原专业" />
        </el-form-item>
        <el-form-item label="目标专业" prop="toMajor">
          <el-input v-model="form.toMajor" placeholder="请输入目标专业" />
        </el-form-item>
        <el-form-item label="转专业理由" prop="reason">
          <el-input type="textarea" v-model="form.reason" placeholder="请输入转专业理由" :rows="4" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">提 交</el-button>
      </div>
    </el-dialog>

    <!-- 审批对话框 -->
    <el-dialog title="审批转专业申请" :visible.sync="approvalOpen" width="30%">
      <el-form ref="approvalForm" :model="approvalForm" :rules="approvalRules" label-width="100px">
        <el-form-item label="申请人">
          <el-input v-model="currentRow.userName" disabled />
        </el-form-item>
        <el-form-item label="原专业">
          <el-input v-model="currentRow.fromMajor" disabled />
        </el-form-item>
        <el-form-item label="目标专业">
          <el-input v-model="currentRow.toMajor" disabled />
        </el-form-item>
        <el-form-item label="申请理由">
          <el-input type="textarea" v-model="currentRow.reason" disabled :rows="3" />
        </el-form-item>
        <el-form-item label="审批结果" prop="approvalStatus">
          <el-radio-group v-model="approvalForm.approvalStatus">
            <el-radio :label="1">批准</el-radio>
            <el-radio :label="2">驳回</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelApproval">取 消</el-button>
        <el-button type="primary" @click="submitApproval">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 设置新学号对话框 -->
    <el-dialog title="设置新学号" :visible.sync="assignNumOpen" width="30%">
      <el-form ref="studentNumForm" :model="studentNumForm" :rules="studentNumRules" label-width="100px">
        <el-form-item label="申请人">
          <el-input v-model="currentRow.userName" disabled />
        </el-form-item>
        <el-form-item label="目标专业">
          <el-input v-model="currentRow.toMajor" disabled />
        </el-form-item>
        <el-form-item label="原学号">
          <el-input v-model="currentRow.oldNum" disabled />
        </el-form-item>
        <el-form-item label="新学号" prop="newNum">
          <el-input v-model="studentNumForm.newNum" placeholder="请输入新学号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelAssignNum">取 消</el-button>
        <el-button type="primary" @click="submitStudentNum">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style scoped lang="scss">
.app-container {
  padding: 20px;
}

.mb-20 {
  margin-bottom: 20px;
}

.dialog-footer {
  text-align: right;
}
</style>
