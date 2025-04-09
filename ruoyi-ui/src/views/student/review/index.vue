<script>
import { listStudentReviews, getStudentReview, addStudentReview, updateStudentReview, deleteStudentReview } from "@/api/student/review";

export default {
  name: "StudentReview",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 复查信息表格数据
      reviewList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情弹出层
      detailOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {
        reviewId: null,
        userId: null,
        reviewDate: null,
        documentValid: 1,
        identityMatch: 1,
        physicalStatus: 1,
        finalResult: 0
      },
      // 详情表单
      detailForm: {},
      // 表单校验
      rules: {
        userId: [
          { required: true, message: "学生ID不能为空", trigger: "blur" },
          { type: "number", message: "学生ID必须为数字", trigger: "blur" }
        ],
        reviewDate: [
          { required: true, message: "复查时间不能为空", trigger: "change" }
        ],
        documentValid: [
          { required: true, message: "请选择材料是否合规", trigger: "change" }
        ],
        identityMatch: [
          { required: true, message: "请选择身份是否匹配", trigger: "change" }
        ],
        physicalStatus: [
          { required: true, message: "请选择体检是否合格", trigger: "change" }
        ],
        finalResult: [
          { required: true, message: "请选择复查结论", trigger: "change" }
        ]
      },
      // 复查结论选项
      finalResultOptions: [
        { value: 0, label: '未复查' },
        { value: 1, label: '合格' },
        { value: 2, label: '不合格' }
      ]
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询复查信息列表 */
    getList() {
      this.loading = true;
      listStudentReviews(this.queryParams).then(response => {
        this.reviewList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        reviewId: null,
        userId: null,
        reviewDate: null,
        documentValid: 1,
        identityMatch: 1,
        physicalStatus: 1,
        finalResult: 0
      };
      this.resetForm("form");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.reviewId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加入学复查信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const reviewId = row.reviewId || this.ids[0];
      getStudentReview(reviewId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改入学复查信息";
      });
    },
    /** 查看详情按钮操作 */
    handleDetail(row) {
      const reviewId = row.reviewId || this.ids[0];
      getStudentReview(reviewId).then(response => {
        const data = response.data;
        this.detailForm = {
          ...data,
          documentValidText: data.documentValid === 1 ? '合规' : '不合规',
          identityMatchText: data.identityMatch === 1 ? '匹配' : '不匹配',
          physicalStatusText: data.physicalStatus === 1 ? '合格' : '不合格',
          finalResultText: data.finalResult === 0 ? '未复查' : data.finalResult === 1 ? '合格' : '不合格'
        };
        this.detailOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.reviewId != null) {
            updateStudentReview(this.form).then(response => {
              this.$message.success("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudentReview(this.form).then(response => {
              this.$message.success("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const reviewIds = row.reviewId || this.ids;
      this.$confirm('是否确认删除复查信息编号为"' + reviewIds + '"的数据项?').then(function() {
        return deleteStudentReview(reviewIds);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    },
    // 获取复查结论标签类型
    getFinalResultType(status) {
      switch (status) {
        case 0: return 'info';    // 未复查
        case 1: return 'success'; // 合格
        case 2: return 'danger';  // 不合格
        default: return 'info';
      }
    }
  }
};
</script>

<template>
  <div class="app-container">
    <!-- 操作按钮 -->
    <el-row :gutter="20" class="mb-20">
      <el-col>
        <el-button type="primary" @click="handleAdd" v-hasPermi="['student:review:add']">新增复查</el-button>
      </el-col>
    </el-row>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="reviewList" @selection-change="handleSelectionChange" border style="width: 100%">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="复查ID" align="center" prop="reviewId" width="120" />
      <el-table-column label="学生姓名" align="center" prop="userName" width="120" />
      <el-table-column label="复查时间" align="center" prop="reviewDate" />
      <el-table-column label="材料是否合规" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.documentValid === 1 ? 'success' : 'danger'">
            {{ scope.row.documentValid === 1 ? '合规' : '不合规' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="身份是否匹配" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.identityMatch === 1 ? 'success' : 'danger'">
            {{ scope.row.identityMatch === 1 ? '匹配' : '不匹配' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="体检是否合格" align="center">
        <template slot-scope="scope">
          <el-tag :type="scope.row.physicalStatus === 1 ? 'success' : 'danger'">
            {{ scope.row.physicalStatus === 1 ? '合格' : '不合格' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="复查结论" align="center">
        <template slot-scope="scope">
          <el-tag :type="getFinalResultType(scope.row.finalResult)">
            {{ scope.row.finalResult === 0 ? '未复查' : 
              scope.row.finalResult === 1 ? '合格' : '不合格' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="270px">
        <template slot-scope="scope">
          <el-button type="info" size="mini" @click="handleDetail(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)" v-hasPermi="['student:review:edit']">修改</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['student:review:delete']">删除</el-button>
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

    <!-- 添加或修改复查信息对话框 -->
    <el-dialog :visible.sync="open" :title="title" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="学生ID" prop="userId">
          <el-input v-model.number="form.userId" placeholder="请输入学生ID" />
        </el-form-item>
        <el-form-item label="复查时间" prop="reviewDate">
          <el-date-picker
            v-model="form.reviewDate"
            type="date"
            placeholder="选择复查时间"
            value-format="yyyy-MM-dd"
            style="width: 100%;">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="材料是否合规" prop="documentValid">
          <el-radio-group v-model="form.documentValid">
            <el-radio :label="1">合规</el-radio>
            <el-radio :label="0">不合规</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="身份是否匹配" prop="identityMatch">
          <el-radio-group v-model="form.identityMatch">
            <el-radio :label="1">匹配</el-radio>
            <el-radio :label="0">不匹配</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="体检是否合格" prop="physicalStatus">
          <el-radio-group v-model="form.physicalStatus">
            <el-radio :label="1">合格</el-radio>
            <el-radio :label="0">不合格</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="复查结论" prop="finalResult">
          <el-select v-model="form.finalResult" placeholder="请选择复查结论" style="width: 100%;">
            <el-option
              v-for="dict in finalResultOptions"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 查看复查信息对话框 -->
    <el-dialog title="复查详情" :visible.sync="detailOpen" width="40%">
      <el-form :model="detailForm" label-width="120px">
        <el-form-item label="复查ID">
          <el-input v-model="detailForm.reviewId" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生ID">
          <el-input v-model="detailForm.userId" disabled></el-input>
        </el-form-item>
        <el-form-item label="学生姓名">
          <el-input v-model="detailForm.userName" disabled></el-input>
        </el-form-item>
        <el-form-item label="复查时间">
          <el-input v-model="detailForm.reviewDate" disabled></el-input>
        </el-form-item>
        <el-form-item label="材料是否合规">
          <el-input v-model="detailForm.documentValidText" disabled></el-input>
        </el-form-item>
        <el-form-item label="身份是否匹配">
          <el-input v-model="detailForm.identityMatchText" disabled></el-input>
        </el-form-item>
        <el-form-item label="体检是否合格">
          <el-input v-model="detailForm.physicalStatusText" disabled></el-input>
        </el-form-item>
        <el-form-item label="复查结论">
          <el-input v-model="detailForm.finalResultText" disabled></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="detailOpen = false">关 闭</el-button>
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
