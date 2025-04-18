<script>
import { listStudentCourses, getStudentCourse, addStudentCourse, updateStudentCourse, deleteStudentCourse } from "@/api/student/course";
import { listTrainer } from "@/api/system/user";
import { addSelection } from "@/api/student/selection";

export default {
  name: "StudentCourse",
  data() {
    return {
      // 遮罩层
      loading: false,
      // 总条数
      total: 0,
      // 课程信息表格数据
      courseList: [],
      // 教师列表
      trainerList: [],
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
        courseId: null,
        courseName: "",
        credits: null,
        hours: null,
        userId: null,
        semester: "",
        description: ""
      },
      // 详情表单
      detailForm: {},
      // 当前教师名称
      currentTrainerName: "",
      // 是否为管理员
      isAdmin: false,
      // 表单校验
      rules: {
        courseName: [
          { required: true, message: "请输入课程名称", trigger: "blur" }
        ],
        credits: [
          { required: true, message: "请输入学分", trigger: "blur" },
          { type: 'number', message: "学分必须为数字", trigger: "blur" }
        ],
        hours: [
          { required: true, message: "请输入学时", trigger: "blur" },
          { type: 'number', message: "学时必须为数字", trigger: "blur" }
        ],
        userId: [
          { required: true, message: "请选择教师", trigger: "change" }
        ],
        semester: [
          { required: true, message: "请输入学期", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getInfo();
    this.getList();
    this.getTrainerList();
  },
  methods: {
    /** 获取当前用户信息 */
    getInfo() {
      this.$store.dispatch('GetInfo').then(res => {
        this.isAdmin = res.roles.includes('admin');
      });
    },
    /** 查询课程信息列表 */
    getList() {
      this.loading = true;
      listStudentCourses(this.queryParams).then(response => {
        this.courseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询教师列表 */
    getTrainerList() {
      listTrainer().then(response => {
        this.trainerList = response.rows || [];
      });
    },
    // 获取教师姓名
    getTrainerName(userId) {
      const trainer = this.trainerList.find(item => item.userId === userId);
      return trainer ? trainer.nickName : `未知教师(ID:${userId})`;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        courseId: null,
        courseName: "",
        credits: null,
        hours: null,
        userId: null,
        semester: "",
        description: ""
      };
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加课程信息";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const courseId = row.courseId;
      getStudentCourse(courseId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改课程信息";
      });
    },
    /** 查看详情按钮操作 */
    handleDetail(row) {
      const courseId = row.courseId;
      getStudentCourse(courseId).then(response => {
        this.detailForm = response.data;
        this.currentTrainerName = this.getTrainerName(response.data.userId);
        this.detailOpen = true;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.courseId != null) {
            updateStudentCourse(this.form).then(response => {
              this.$message.success("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addStudentCourse(this.form).then(response => {
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
      this.$confirm('是否确认删除该课程信息?').then(function() {
        return deleteStudentCourse(row.courseId);
      }).then(() => {
        this.getList();
        this.$message.success("删除成功");
      }).catch(() => {});
    },
    /** 选课按钮操作 */
    handleSelection(row) {
      this.$confirm('确认选择课程"' + row.courseName + '"?', "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        const selectionData = {
          courseId: row.courseId
        };
        addSelection(selectionData).then(() => {
          this.$message.success("选课成功");
        })
      });
    }
  }
};
</script>

<template>
  <div class="app-container">
    <!-- 新增按钮 -->
    <div class="mb-20">
      <el-button type="primary" @click="handleAdd" v-hasPermi="['student:course:add']">新增课程</el-button>
    </div>

    <!-- 数据表格 -->
    <el-table v-loading="loading" :data="courseList" border style="width: 100%">
      <el-table-column label="课程ID" align="center" prop="courseId" />
      <el-table-column label="课程名称" align="center" prop="courseName"  />
      <el-table-column label="学分" align="center" prop="credits"  />
      <el-table-column label="学时" align="center" prop="hours"  />
      <el-table-column label="授课教师" align="center" width="120">
        <template slot-scope="scope">
          {{ getTrainerName(scope.row.userId) }}
        </template>
      </el-table-column>
      <el-table-column label="学期" align="center" prop="semester" width="140" />
      <el-table-column label="操作" align="center" width="340px">
        <template slot-scope="scope">
          <el-button type="info" size="mini" @click="handleDetail(scope.row)">查看</el-button>
          <el-button type="primary" size="mini" @click="handleUpdate(scope.row)" v-hasPermi="['student:course:edit']">修改</el-button>
          <el-button type="danger" size="mini" @click="handleDelete(scope.row)" v-hasPermi="['student:course:delete']">删除</el-button>
          <el-button v-if="!isAdmin" type="success" size="mini" @click="handleSelection(scope.row)" v-hasPermi="['student:course:select']">选课</el-button>
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

    <!-- 添加或修改课程信息对话框 -->
    <el-dialog :visible.sync="open" :title="title" width="40%">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="课程名称" prop="courseName">
          <el-input v-model="form.courseName" placeholder="请输入课程名称" />
        </el-form-item>
        <el-form-item label="学分" prop="credits">
          <el-input-number v-model="form.credits" :min="0" :max="10" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="学时" prop="hours">
          <el-input-number v-model="form.hours" :min="0" :max="100" style="width: 100%;" />
        </el-form-item>
        <el-form-item label="授课教师" prop="userId">
          <el-select v-model="form.userId" placeholder="请选择教师" style="width: 100%;">
            <el-option v-for="item in trainerList" :key="item.userId" :label="item.nickName" :value="item.userId"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="学期" prop="semester">
          <el-input v-model="form.semester" placeholder="请输入学期，如：2023-2024学年第一学期" />
        </el-form-item>
        <el-form-item label="课程描述" prop="description">
          <el-input v-model="form.description" type="textarea" :rows="3" placeholder="请输入课程描述" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>

    <!-- 查看课程信息对话框 -->
    <el-dialog title="课程详情" :visible.sync="detailOpen" width="40%">
      <el-form :model="detailForm" label-width="100px">
        <el-form-item label="课程ID">
          <el-input v-model="detailForm.courseId" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程名称">
          <el-input v-model="detailForm.courseName" disabled></el-input>
        </el-form-item>
        <el-form-item label="学分">
          <el-input v-model="detailForm.credits" disabled></el-input>
        </el-form-item>
        <el-form-item label="学时">
          <el-input v-model="detailForm.hours" disabled></el-input>
        </el-form-item>
        <el-form-item label="授课教师">
          <el-input v-model="currentTrainerName" disabled></el-input>
        </el-form-item>
        <el-form-item label="学期">
          <el-input v-model="detailForm.semester" disabled></el-input>
        </el-form-item>
        <el-form-item label="课程描述">
          <el-input v-model="detailForm.description" type="textarea" :rows="3" disabled></el-input>
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
