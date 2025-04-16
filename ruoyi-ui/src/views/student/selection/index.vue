<script>
import { listSelections, deleteSelection } from "@/api/student/selection";
import { addStudentGrade } from "@/api/student/grade";

export default {
  name: "StudentSelection",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 选课信息表格数据
      selectionList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 成绩录入对话框
      gradeDialogVisible: false,
      // 成绩表单
      gradeForm: {
        userId: null,
        courseId: null,
        grade: null,
        semester: ''
      },
      // 当前操作的行
      currentRow: {},
      // 表单校验规则
      gradeRules: {
        grade: [
          { required: true, message: "请输入成绩", trigger: "blur" },
          { pattern: /^([0-9]{1,2}(\.[0-9]{1,2})?|100)$/, message: "成绩必须为0-100之间的数字", trigger: "blur" }
        ],
        semester: [
          { required: true, message: "请输入学期", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询选课信息列表 */
    getList() {
      this.loading = true;
      listSelections(this.queryParams).then(response => {
        this.selectionList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    /** 格式化日期 */
    formatDate(dateString) {
      if (!dateString) return '';
      const date = new Date(dateString);
      return date.toLocaleDateString();
    },

    /** 取消选课操作 */
    handleCancelSelection(row) {
      this.$confirm('确认取消选课"' + row.courseName + '"?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        deleteSelection(row.selectionId).then(() => {
          this.$message.success("取消选课成功");
          this.getList();
        }).catch(error => {
          console.error("取消选课失败", error);
          this.$message.error("取消选课失败");
        });
      }).catch(() => {});
    },

    /** 录入成绩操作 */
    handleInputGrade(row) {
      this.currentRow = row;
      this.gradeForm = {
        userId: row.userId,
        courseId: row.courseId,
        grade: null,
        semester: row.semester || '' // 如果选课记录中有学期信息则使用，否则为空
      };
      this.gradeDialogVisible = true;
    },

    /** 提交成绩 */
    submitGrade() {
      this.$refs.gradeForm.validate(valid => {
        if (valid) {
          addStudentGrade(this.gradeForm).then(() => {
            this.$message.success("成绩录入成功");
            this.gradeDialogVisible = false;
          }).catch(error => {
            console.error("成绩录入失败", error);
            this.$message.error("成绩录入失败");
          });
        }
      });
    },

    /** 取消成绩录入 */
    cancelGrade() {
      this.gradeDialogVisible = false;
      this.resetGradeForm();
    },

    /** 重置成绩表单 */
    resetGradeForm() {
      this.gradeForm = {
        userId: null,
        courseId: null,
        grade: null,
        semester: ''
      };
      if (this.$refs.gradeForm) {
        this.$refs.gradeForm.resetFields();
      }
    }
  }
};
</script>

<template>
  <div class="app-container">
    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="selectionList" border style="width: 100%">
      <el-table-column label="选课ID" align="center" prop="selectionId" />
      <el-table-column label="学生姓名" align="center" prop="userName" />
      <el-table-column label="教师姓名" align="center" prop="teacherName" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="选课时间" align="center">
        <template slot-scope="scope">
          {{ formatDate(scope.row.selectionDate) }}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="280px">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="handleCancelSelection(scope.row)" v-hasPermi="['student:grade:cancel']">取消选课</el-button>
          <el-button type="primary" size="mini" @click="handleInputGrade(scope.row)" v-hasPermi="['student:grade:add']">录入成绩</el-button>
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

    <!-- 成绩录入对话框 -->
    <el-dialog :visible.sync="gradeDialogVisible" title="成绩录入" width="30%">
      <el-form ref="gradeForm" :model="gradeForm" :rules="gradeRules" label-width="80px">
        <el-form-item label="学生">
          <el-input v-model="currentRow.userName" disabled />
        </el-form-item>
        <el-form-item label="课程">
          <el-input v-model="currentRow.courseName" disabled />
        </el-form-item>
        <el-form-item label="成绩" prop="grade">
          <el-input-number v-model="gradeForm.grade" :min="0" :max="100" :precision="2" :step="0.5" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="gradeForm.semester" placeholder="例如：2023-2024学年第一学期" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancelGrade">取 消</el-button>
        <el-button type="primary" @click="submitGrade">确 定</el-button>
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
</style>
