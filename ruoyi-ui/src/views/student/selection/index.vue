<script>
import { listSelections, deleteSelection } from "@/api/student/selection";

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
      <el-table-column label="操作" align="center" width="200px">
        <template slot-scope="scope">
          <el-button type="danger" size="mini" @click="handleCancelSelection(scope.row)">取消选课</el-button>
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
