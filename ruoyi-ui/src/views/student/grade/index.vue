<script>
import { listStudentGrades, getStudentGrade, updateStudentGrade, deleteStudentGrade } from "@/api/student/grade";

export default {
  name: "StudentGrade",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 成绩信息表格数据
      gradeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 表单参数
      form: {
        gradeId: null,
        userId: null,
        courseId: null,
        grade: null,
        semester: ''
      },
      // 表单校验规则
      rules: {
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
    /** 查询成绩信息列表 */
    getList() {
      this.loading = true;
      listStudentGrades(this.queryParams).then(response => {
        this.gradeList = response.rows;
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
        gradeId: null,
        userId: null,
        courseId: null,
        grade: null,
        semester: ''
      };
      this.resetForm("form");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.form = {
        gradeId: row.gradeId,
        userId: row.userId,
        courseId: row.courseId,
        grade: row.grade,
        semester: row.semester,
        userName: row.userName,
        courseName: row.courseName
      };
      this.open = true;
      this.title = "修改成绩信息";
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateStudentGrade(this.form).then(response => {
            this.$message.success("修改成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除该成绩记录?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        return deleteStudentGrade(row.gradeId);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    }
  }
};
</script>

<template>
  <div class="app-container">
    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="gradeList" border style="width: 100%">
      <el-table-column label="成绩ID" align="center" prop="gradeId" />
      <el-table-column label="学生姓名" align="center" prop="userName" />
      <el-table-column label="课程名称" align="center" prop="courseName" />
      <el-table-column label="授课教师" align="center" prop="teacherName" />
      <el-table-column label="成绩" align="center" prop="grade" />
      <el-table-column label="学期" align="center" prop="semester" />
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)" v-hasPermi="['student:grade:edit']">修改</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['student:grade:delete']">删除</el-button>
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

    <!-- 修改成绩对话框 -->
    <el-dialog :visible.sync="open" :title="title" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生">
          <el-input v-model="form.userName" disabled />
        </el-form-item>
        <el-form-item label="课程">
          <el-input v-model="form.courseName" disabled />
        </el-form-item>
        <el-form-item label="成绩" prop="grade">
          <el-input-number v-model="form.grade" :min="0" :max="100" :precision="2" :step="0.5" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="例如：2023-2024学年第一学期" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
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
