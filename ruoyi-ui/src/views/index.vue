<template>
  <div>
    <div ref="echartsText" style="height: 100px; display: flex; justify-content: center; align-items: center;">
      <!-- 这里 ECharts 动画文本会被渲染 -->
    </div>
    <div>
      <el-carousel :interval="4000" type="card" height="300px">
        <el-carousel-item>
          <a href="https://www.baidu.com" target="_blank">
            <img src="../assets/images/01.jpg" alt="Image 1" style="width: 100%;">
          </a>
        </el-carousel-item>
        <el-carousel-item>
          <a href="https://www.jd.com" target="_blank">
            <img src="../assets/images/02.jpg" alt="Image 2" style="width: 100%;">
          </a>
        </el-carousel-item>
        <el-carousel-item>
          <a href="https://www.taobao.com" target="_blank">
            <img src="../assets/images/03.jpg" alt="Image 3" style="width: 100%;">
          </a>
        </el-carousel-item>
      </el-carousel>
      <!-- 通知公告 -->
      <el-row style="margin-top: 20px;">
        <el-col :span="12">
          <el-card style="margin-right: 20px; height: 420px;">
            <h3 slot="header">通知公告</h3>
            <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
              <el-table-column label="序号" align="center" prop="noticeId" width="100"/>
              <el-table-column
                label="公告标题"
                align="center"
                prop="noticeTitle"
                :show-overflow-tooltip="true"
              >
                <template slot-scope="scope">
                  <span @click="showNoticeContent(scope.row)">{{ scope.row.noticeTitle }}</span>
                </template>
              </el-table-column>
              <el-table-column label="公告类型" align="center" prop="noticeType" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.sys_notice_type" :value="scope.row.noticeType"/>
                </template>
              </el-table-column>
              <el-table-column label="创建时间" align="center" prop="createTime" width="100">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
            </el-table>
          </el-card>
        </el-col>

        <el-col :span="12">
          <el-card style="margin-right: 20px; height: 420px;">
            <h3 slot="header">系统简介</h3>
<!--            <p>欢迎使用 自习室管理系统 🎉！本系统旨在提供便捷、高效的自习室预约与管理服务，帮助用户轻松查找、预订和管理自习室座位。<br/><br/>-->
<!--              ✨ <b>主要功能：</b><br/>-->
<!--              ✅ <b>在线预约：</b> 随时随地预订座位，支持日期与时间选择<br/>-->
<!--              ✅ <b>座位管理：</b> 查看实时座位使用情况，支持取消或调整预约<br/>-->
<!--              ✅ <b>规则管理：</b> 设定预约规则，避免资源浪费，提升使用效率<br/><br/>-->
<!--              本系统采用 <b>Spring Boot + Vue.js</b> 开发，确保高效、稳定的服务体验。立即开始，享受更智能的自习室管理！📚🚀</p>-->
          </el-card>
        </el-col>
      </el-row>
      <!-- 弹出的公告内容卡片 -->
      <el-dialog :title="selectedNotice.title" :visible.sync="showNoticeDialog" width="780px" append-to-body>
        <div slot="title" style="text-align: center;">{{ selectedNotice.title }}</div>
        <div v-html="selectedNotice.content" class="notice-content"></div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {listNotice, getNotice} from "@/api/system/notice";
import * as echarts from 'echarts'


export default {
  name: "Notice",
  dicts: ['sys_notice_status', 'sys_notice_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 公告表格数据
      noticeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeTitle: undefined,
        createBy: undefined,
        status: undefined
      },
      selectedNotice: {
        title: '',
        content: ''
      },
      showNoticeDialog: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        noticeTitle: [
          {required: true, message: "公告标题不能为空", trigger: "blur"}
        ],
        noticeType: [
          {required: true, message: "公告类型不能为空", trigger: "change"}
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.initEchartsText(); // 初始化 ECharts 动画文本
  },
  methods: {
    /** 查询公告列表 */
    getList() {
      this.loading = true;
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    showNoticeContent(row) {
      this.loading = true;
      getNotice(row.noticeId).then((response) => {
        this.selectedNotice.title = response.data.noticeTitle;
        this.selectedNotice.content = response.data.noticeContent;
        this.showNoticeDialog = true;
        this.loading = false;
      });
    },
    // 初始化 ECharts 动画文本
    initEchartsText() {
      const chartDom = this.$refs.echartsText;
      const myChart = echarts.init(chartDom);
      const option = {
        graphic: {
          elements: [
            {
              type: 'text',
              left: 'center',
              top: 'center',
              style: {
                text: '本科生学籍管理系统',
                fontSize: 80,
                fontWeight: 'bold',
                lineDash: [0, 200],
                lineDashOffset: 0,
                fill: 'transparent',
                stroke: '#000',
                lineWidth: 1
              },
              keyframeAnimation: {
                duration: 3000,
                loop: true,
                keyframes: [
                  {
                    percent: 0.7,
                    style: {
                      fill: 'transparent',
                      lineDashOffset: 200,
                      lineDash: [200, 0]
                    }
                  },
                  {
                    percent: 0.8,
                    style: {
                      fill: 'transparent'
                    }
                  },
                  {
                    percent: 1,
                    style: {
                      fill: 'black'
                    }
                  }
                ]
              }
            }
          ]
        }
      };
      myChart.setOption(option);
    }
  },
};
</script>

<style scoped lang="scss">
.notice-content::v-deep img {
  max-width: 100%;
  height: auto;
  display: block;
  margin: 0 auto;
}

</style>
