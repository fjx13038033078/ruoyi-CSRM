<template>
  <div class="app-container">
    <!-- 学籍注册列表 -->
    <el-table :data="enrollmentList" v-loading="loading" border style="width: 100%">
      <el-table-column label="学籍ID" prop="enrollmentId" align="center" width="120"></el-table-column>
      <el-table-column label="姓名" prop="userName" align="center" width="120"></el-table-column>
      <el-table-column label="录取通知书编号" prop="noticeNumber" align="center"></el-table-column>
      <el-table-column label="是否请假" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.needLeave === 1 ? 'warning' : 'info'">
            {{ scope.row.needLeave === 1 ? '需要请假' : '不需请假' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="请假时间(天)" prop="leaveDate" align="center"></el-table-column>
      <el-table-column label="报到时间" align="center">
        <template slot-scope="scope">
          {{ formatDate(scope.row.reportDate) }}
        </template>
      </el-table-column>
      <el-table-column label="请假审批状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="getLeaveRequestType(scope.row.leaveRequest)">
            {{ scope.row.leaveRequest === 0 ? '待审核' : scope.row.leaveRequest === 1 ? '批准' : scope.row.leaveRequest === 2 ? '驳回' : '未请假' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="报到状态" align="center">
        <template slot-scope="scope">
          <el-tag :type="getEnrollmentStatusType(scope.row.enrollmentStatus)">
            {{ scope.row.enrollmentStatus === 0 ? '未报到' :
               scope.row.enrollmentStatus === 1 ? '已报到' :
               scope.row.enrollmentStatus === 2 ? '逾期未报到' : '放弃资格' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="370px">
        <template slot-scope="scope">
          <!-- 临时用户显示查看和修改按钮 -->
          <template v-if="isTemporary">
            <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
            <el-button type="primary" size="mini" @click="handleEdit(scope.row)">修改</el-button>
          </template>

          <!-- 管理员用户显示查看、审批和删除按钮 -->
          <template v-if="isAdmin">
            <el-button type="info" size="mini" @click="handleView(scope.row)">查看</el-button>
            <el-button 
              type="warning" 
              size="mini" 
              @click="handleReview(scope.row)" 
              v-hasPermi="['student:review:add']"
              v-if="scope.row.enrollmentStatus === 1">入学复查</el-button>
            <el-button type="primary" size="mini" @click="handleApprove(scope.row)" v-hasPermi="['student:enrollment:edit']">审批</el-button>
            <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['student:enrollment:delete']">删除</el-button>
          </template>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页组件 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="fetchEnrollments"
    />

    <!-- 添加/编辑学籍注册对话框 -->
    <el-dialog :visible.sync="dialogVisible" :title="dialogTitle" width="40%">
      <el-form :model="enrollmentForm" :rules="rules" ref="enrollmentForm" label-width="120px">
        <el-form-item label="录取通知书编号" prop="noticeNumber">
          <el-input v-model="enrollmentForm.noticeNumber"></el-input>
        </el-form-item>
        <!-- 临时用户需要填写身份证号 -->
        <el-form-item v-if="isTemporary" label="身份证号" prop="idNumber">
          <el-input v-model="enrollmentForm.idNumber" placeholder="请输入18位身份证号码"></el-input>
        </el-form-item>
        <el-form-item label="是否需要请假" prop="needLeave">
          <el-radio-group v-model="enrollmentForm.needLeave" @change="handleNeedLeaveChange">
            <el-radio :label="0">否</el-radio>
            <el-radio :label="1">是</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="请假时间(天)" prop="leaveDate" v-if="enrollmentForm.needLeave === 1">
          <el-input-number v-model="enrollmentForm.leaveDate" :min="1" :max="30" style="width: 100%;"></el-input-number>
        </el-form-item>
        <!-- 管理员才需要填写这些字段 -->
        <template v-if="isAdmin">
          <el-form-item label="用户ID" prop="userId">
            <el-input v-model.number="enrollmentForm.userId" type="number"></el-input>
          </el-form-item>
          <el-form-item label="报到时间" prop="reportDate">
            <el-date-picker
              v-model="enrollmentForm.reportDate"
              type="date"
              placeholder="选择报到时间"
              style="width: 100%;">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="请假审批状态" prop="leaveRequest">
            <el-select v-model="enrollmentForm.leaveRequest" style="width: 100%;">
              <el-option
                v-for="item in leaveRequestOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="报到状态" prop="enrollmentStatus">
            <el-select v-model="enrollmentForm.enrollmentStatus" style="width: 100%;">
              <el-option
                v-for="item in enrollmentStatusOptions"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </template>
        <el-form-item label="附件上传">
          <FileUpload v-model="enrollmentForm.fileName"></FileUpload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">{{ dialogButtonText }}</el-button>
      </div>
    </el-dialog>

    <!-- 查看学籍注册对话框 -->
    <el-dialog :visible.sync="viewDialogVisible" title="学籍注册详情" width="40%">
      <el-form :model="viewEnrollmentForm" label-width="120px">
        <el-form-item label="学籍ID">
          <el-input v-model="viewEnrollmentForm.enrollmentId" disabled></el-input>
        </el-form-item>
        <el-form-item label="姓名">
          <el-input v-model="viewEnrollmentForm.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="录取通知书编号">
          <el-input v-model="viewEnrollmentForm.noticeNumber" disabled></el-input>
        </el-form-item>
        <el-form-item label="身份证号">
          <el-input v-model="viewEnrollmentForm.idNumber" disabled></el-input>
        </el-form-item>
        <el-form-item label="是否需要请假">
          <el-input v-model="viewEnrollmentForm.needLeaveText" disabled></el-input>
        </el-form-item>
        <el-form-item label="请假时间(天)" v-if="viewEnrollmentForm.needLeave === 1">
          <el-input v-model="viewEnrollmentForm.leaveDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="报到时间">
          <el-input v-model="viewEnrollmentForm.reportDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="请假审批状态">
          <el-input v-model="viewEnrollmentForm.leaveRequestText" disabled></el-input>
        </el-form-item>
        <el-form-item label="报到状态">
          <el-input v-model="viewEnrollmentForm.enrollmentStatusText" disabled></el-input>
        </el-form-item>
        <el-form-item label="附件查看" v-if="viewEnrollmentForm.fileName">
          <FileUpload v-model="viewEnrollmentForm.fileName" :is-show-tip="false" :disabled="true"></FileUpload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewDialogVisible = false">关闭</el-button>
      </div>
    </el-dialog>

    <!-- 审批对话框 -->
    <el-dialog :visible.sync="approveDialogVisible" title="学籍注册审批" width="40%">
      <el-form :model="approveForm" :rules="approveRules" ref="approveForm" label-width="120px">
        <el-form-item label="是否需要请假">
          <el-tag :type="approveForm.needLeave === 1 ? 'warning' : 'info'">
            {{ approveForm.needLeave === 1 ? '需要请假' : '不需请假' }}
          </el-tag>
        </el-form-item>
        <el-form-item v-if="approveForm.needLeave === 1" label="请假天数">
          <el-input v-model="approveForm.leaveDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="请假审批状态" prop="leaveRequest">
          <el-select v-model="approveForm.leaveRequest" style="width: 100%;" :disabled="approveForm.needLeave === 0">
            <el-option
              v-for="item in getLeaveRequestOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="报到状态" prop="enrollmentStatus">
          <el-select v-model="approveForm.enrollmentStatus" style="width: 100%;">
            <el-option
              v-for="item in enrollmentStatusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="approveDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="submitApprove">确定</el-button>
      </div>
    </el-dialog>

    <!-- 入学复查对话框 -->
    <el-dialog :visible.sync="reviewDialogVisible" title="入学复查" width="40%">
      <el-form ref="reviewForm" :model="reviewForm" :rules="reviewRules" label-width="120px">
        <el-form-item label="材料是否合规" prop="documentValid">
          <el-radio-group v-model="reviewForm.documentValid">
            <el-radio :label="1">合规</el-radio>
            <el-radio :label="0">不合规</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份是否匹配" prop="identityMatch">
          <el-radio-group v-model="reviewForm.identityMatch">
            <el-radio :label="1">匹配</el-radio>
            <el-radio :label="0">不匹配</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="体检是否合格" prop="physicalStatus">
          <el-radio-group v-model="reviewForm.physicalStatus">
            <el-radio :label="1">合格</el-radio>
            <el-radio :label="0">不合格</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="复查结论" prop="finalResult">
          <el-select v-model="reviewForm.finalResult" placeholder="请选择复查结论" style="width: 100%;">
            <el-option
              v-for="dict in reviewResultOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reviewDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitReview">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listEnrollments, getEnrollment, addEnrollment, updateEnrollment, deleteEnrollment, updateEnrollmentStatus } from "@/api/student/enrollment";
import { addStudentReview } from "@/api/student/review";
import { getInfo } from "@/api/login";

export default {
  name: "StudentEnrollment",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 学籍注册列表
      enrollmentList: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 对话框显示
      dialogVisible: false,
      viewDialogVisible: false,
      approveDialogVisible: false,
      reviewDialogVisible: false,
      dialogTitle: "",
      dialogButtonText: "",
      // 用户角色权限
      permissions: [],
      roles: [],
      isAdmin: false,
      isTemporary: false,
      // 表单数据
      enrollmentForm: {
        enrollmentId: null,
        userId: null,
        noticeNumber: "",
        idNumber: "",
        reportDate: null,
        leaveRequest: 0,
        enrollmentStatus: 0,
        fileName: "",
        needLeave: 0,
        leaveDate: 1
      },
      // 查看表单数据
      viewEnrollmentForm: {},
      // 审批表单数据
      approveForm: {
        enrollmentId: null,
        leaveRequest: 0,
        enrollmentStatus: 0,
        needLeave: 0,
        leaveDate: 0
      },
      // 入学复查表单数据
      reviewForm: {
        userId: null,
        documentValid: 1,
        identityMatch: 1,
        physicalStatus: 1,
        finalResult: 1,
        reviewDate: null
      },
      // 表单校验规则
      rules: {
        noticeNumber: [{ required: true, message: "请输入录取通知书编号", trigger: "blur" }],
        idNumber: [
          { required: true, message: "请输入身份证号", trigger: "blur" },
          { pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: "请输入正确的身份证号格式", trigger: "blur" }
        ],
        needLeave: [{ required: true, message: "请选择是否需要请假", trigger: "change" }],
        leaveDate: [{ required: true, message: "请输入请假天数", trigger: "change" }],
        reportDate: [{ required: true, message: "请选择报到时间", trigger: "change" }],
        leaveRequest: [{ required: true, message: "请选择请假审批状态", trigger: "change" }],
        enrollmentStatus: [{ required: true, message: "请选择报到状态", trigger: "change" }]
      },
      // 审批表单校验规则
      approveRules: {
        leaveRequest: [{ required: true, message: "请选择请假审批状态", trigger: "change" }],
        enrollmentStatus: [{ required: true, message: "请选择报到状态", trigger: "change" }]
      },
      // 入学复查表单校验规则
      reviewRules: {
        documentValid: [{ required: true, message: "请选择材料是否合规", trigger: "change" }],
        identityMatch: [{ required: true, message: "请选择身份是否匹配", trigger: "change" }],
        physicalStatus: [{ required: true, message: "请选择体检是否合格", trigger: "change" }],
        finalResult: [{ required: true, message: "请选择复查结论", trigger: "change" }]
      },
      // 请假状态选项
      leaveRequestOptions: [
        { value: 0, label: '待审核' },
        { value: 1, label: '批准' },
        { value: 2, label: '驳回' },
        { value: 3, label: '未请假'}
      ],
      // 报到状态选项
      enrollmentStatusOptions: [
        { value: 0, label: '未报到' },
        { value: 1, label: '已报到' },
        { value: 2, label: '逾期未报到' },
        { value: 3, label: '放弃资格' }
      ],
      // 入学复查结果选项
      reviewResultOptions: [
        { value: 1, label: '合格' },
        { value: 0, label: '不合格' }
      ]
    };
  },
  computed: {
    // 根据是否需要请假返回不同的审批选项
    getLeaveRequestOptions() {
      if (this.approveForm.needLeave === 1) {
        // 需要请假，显示批准、驳回和待审核选项
        return [
          { value: 0, label: '待审核' },
          { value: 1, label: '批准' },
          { value: 2, label: '驳回' }
        ];
      } else {
        // 不需要请假，只显示未请假选项
        return [
          { value: 3, label: '未请假' }
        ];
      }
    }
  },
  created() {
    this.getInfo();
  },
  methods: {
    // 获取用户信息
    getInfo() {
      getInfo().then(response => {
        const { permissions, roles } = response;
        this.permissions = permissions;
        this.roles = roles;

        // 判断用户角色
        this.isAdmin = roles.includes('admin');
        this.isTemporary = roles.includes('temporary');

        // 获取学籍信息
        this.fetchEnrollments();
      });
    },
    // 获取学籍注册列表
    fetchEnrollments() {
      this.loading = true;
      listEnrollments(this.queryParams).then(response => {
        this.enrollmentList = response.rows;
        this.total = response.total;
        this.loading = false;

        // 如果是临时用户且没有学籍信息，显示填写对话框
        if (this.isTemporary && (!this.enrollmentList || this.enrollmentList.length === 0)) {
          this.openEnrollmentDialog();
        }
      }).catch(error => {
        console.error("获取学籍注册列表失败", error);
        this.loading = false;
      });
    },
    // 打开学籍注册填写对话框
    openEnrollmentDialog(type, row) {
      if (type === 'edit') {
        this.dialogTitle = "修改学籍注册";
        this.dialogButtonText = "修改";
        this.enrollmentForm = { ...row };
      } else {
        this.dialogTitle = "学籍注册";
        this.dialogButtonText = "提交";
        this.clearForm();
      }
      this.dialogVisible = true;
    },
    // 清空表单
    clearForm() {
      this.enrollmentForm = {
        enrollmentId: null,
        userId: null,
        noticeNumber: "",
        idNumber: "",
        reportDate: null,
        leaveRequest: 3, // 默认为未请假
        enrollmentStatus: 0,
        fileName: "",
        needLeave: 0, // 默认为不需要请假
        leaveDate: 0  // 默认为0天
      };
    },
    // 查看学籍信息
    handleView(row) {
      this.viewEnrollmentForm = {
        ...row,
        leaveRequestText: this.leaveRequestOptions.find(item => item.value === row.leaveRequest)?.label || '未知',
        enrollmentStatusText: this.enrollmentStatusOptions.find(item => item.value === row.enrollmentStatus)?.label || '未知',
        needLeaveText: row.needLeave === 1 ? '需要请假' : '不需请假'
      };
      this.viewDialogVisible = true;
    },
    // 审批学籍
    handleApprove(row) {
      this.approveForm = {
        enrollmentId: row.enrollmentId,
        leaveRequest: row.leaveRequest,
        enrollmentStatus: row.enrollmentStatus,
        needLeave: row.needLeave,
        leaveDate: row.leaveDate
      };

      // 如果用户不需要请假，则强制设置请假审批状态为未请假
      if (row.needLeave === 0) {
        this.approveForm.leaveRequest = 3; // 未请假
      } else {
        // 如果用户需要请假，但状态是未请假，则改为待审核
        if (this.approveForm.leaveRequest === 3) {
          this.approveForm.leaveRequest = 0;
        }
      }

      // 打印当前approveForm的状态，方便调试
      console.log("审批表单状态:", this.approveForm);

      this.approveDialogVisible = true;
    },
    // 提交审批
    submitApprove() {
      this.$refs.approveForm.validate(valid => {
        if (valid) {
          // 如果用户不需要请假，强制确保请假审批状态为未请假
          if (this.approveForm.needLeave === 0) {
            this.approveForm.leaveRequest = 3;
          }

          updateEnrollmentStatus(this.approveForm).then(() => {
            this.$message.success("审批成功");
            this.fetchEnrollments();
            this.approveDialogVisible = false;
          }).catch(error => {
            console.error("审批失败", error);
            this.$message.error("审批失败");
          });
        }
      });
    },
    // 提交表单
    handleSubmit() {
      this.$refs.enrollmentForm.validate(valid => {
        if (valid) {
          // 确保不需要请假时，请假天数为0
          if (this.enrollmentForm.needLeave === 0) {
            this.enrollmentForm.leaveDate = 0;
          }

          if (this.isTemporary) {
            // 设置请假审批状态
            this.enrollmentForm.leaveRequest = this.enrollmentForm.needLeave === 1 ? 0 : 3;

            // 临时用户根据是否有enrollmentId判断是新增还是修改
            const action = this.enrollmentForm.enrollmentId ? updateEnrollment : addEnrollment;
            action(this.enrollmentForm).then(() => {
              this.$message.success(this.enrollmentForm.enrollmentId ? "修改成功" : "添加成功");
              this.fetchEnrollments();
              this.dialogVisible = false;
            }).catch(error => {
              console.error("保存学籍注册失败", error);
              this.$message.error("保存失败");
            });
          } else if (this.isAdmin) {
            // 管理员提交表单
            const action = this.enrollmentForm.enrollmentId ? updateEnrollment : addEnrollment;
            action(this.enrollmentForm).then(() => {
              this.$message.success(this.enrollmentForm.enrollmentId ? "修改成功" : "添加成功");
              this.fetchEnrollments();
              this.dialogVisible = false;
            }).catch(error => {
              console.error("保存学籍注册失败", error);
              this.$message.error("保存失败");
            });
          }
        }
      });
    },
    // 删除学籍注册
    handleDelete(enrollment) {
      this.$confirm("确认删除该学籍注册信息吗？", "提示", {
        type: "warning",
      }).then(() => {
        deleteEnrollment(enrollment.enrollmentId).then(() => {
          this.$message.success("删除成功");
          this.fetchEnrollments();
        }).catch(error => {
          console.error("删除学籍注册失败", error);
          this.$message.error("删除失败");
        });
      }).catch(() => {});
    },
    // 格式化日期
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString();
    },
    // 根据状态获取标签类型
    getLeaveRequestType(status) {
      switch (status) {
        case 0: return 'info';    // 待审核
        case 1: return 'success'; // 批准
        case 2: return 'danger';  // 驳回
        case 3: return 'success'; // 未请假
        default: return 'info';
      }
    },
    getEnrollmentStatusType(status) {
      switch (status) {
        case 0: return 'warning'; // 未报到
        case 1: return 'success'; // 已报到
        case 2: return 'danger';  // 逾期未报到
        case 3: return 'danger';  // 放弃资格
        default: return 'info';
      }
    },
    // 处理修改操作
    handleEdit(row) {
      this.openEnrollmentDialog('edit', row);
    },

    // 处理是否需要请假选项变化
    handleNeedLeaveChange(value) {
      // 根据是否需要请假自动设置请假审批状态
      this.enrollmentForm.leaveRequest = value === 1 ? 0 : 3;

      // 当选择不需要请假时，将请假天数设置为0
      if (value === 0) {
        this.enrollmentForm.leaveDate = 0;
      } else if (this.enrollmentForm.leaveDate === 0) {
        // 如果选择需要请假，但请假天数为0，设置为默认值1
        this.enrollmentForm.leaveDate = 1;
      }
    },

    // 打开入学复查对话框
    openReviewDialog(row) {
      this.reviewForm = {
        userId: row.userId,
        documentValid: 1,
        identityMatch: 1,
        physicalStatus: 1,
        finalResult: 1,
        reviewDate: new Date().toISOString().substr(0, 10) // 设置为当前日期
      };
      this.currentRow = row; // 保存当前操作的行
      this.reviewDialogVisible = true;
    },

    // 提交入学复查
    submitReview() {
      this.$refs.reviewForm.validate(valid => {
        if (valid) {
          // 调用添加复查记录API
          addStudentReview(this.reviewForm).then(response => {
            this.$message.success("入学复查提交成功");
            this.reviewDialogVisible = false;
          }).catch(error => {
            console.error("入学复查提交失败", error);
            this.$message.error("提交失败");
          });
        }
      });
    },

    // 处理入学复查
    handleReview(row) {
      this.openReviewDialog(row);
    }
  }
};
</script>

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
