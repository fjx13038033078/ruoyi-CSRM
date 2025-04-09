<template>
  <div class="home-container">
    <div class="gradient-title">
      欢迎使用本科生学籍管理系统
    </div>
    
    <!-- 轮播图区域 -->
    <div class="carousel-container">
      <el-carousel :interval="4000" type="card" height="320px" indicator-position="outside">
        <el-carousel-item v-for="(item, index) in carouselItems" :key="index">
          <a :href="item.link" target="_blank">
            <img :src="item.image" :alt="item.alt" class="carousel-image">
            <div class="carousel-caption">{{item.caption}}</div>
          </a>
        </el-carousel-item>
      </el-carousel>
    </div>
    
    <!-- 内容区域 -->
    <div class="content-container">
      <el-row :gutter="20">
        <!-- 通知公告卡片 -->
        <el-col :span="12">
          <el-card class="notice-card" shadow="hover">
            <div slot="header" class="card-header">
              <span><i class="el-icon-bell"></i> 通知公告</span>
              <el-button style="float: right; padding: 3px 0" type="text">更多 <i class="el-icon-arrow-right"></i></el-button>
            </div>
            <el-table
              v-loading="loading"
              :data="noticeList"
              @selection-change="handleSelectionChange"
              :header-cell-style="{background:'#f5f7fa',color:'#606266'}"
              style="width: 100%">
              <el-table-column label="序号" align="center" prop="noticeId" width="80"/>
              <el-table-column
                label="公告标题"
                align="center"
                prop="noticeTitle"
                :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <el-link type="primary" @click="showNoticeContent(scope.row)">{{ scope.row.noticeTitle }}</el-link>
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

        <!-- 系统简介卡片 -->
        <el-col :span="12">
          <el-card class="info-card" shadow="hover">
            <div slot="header" class="card-header">
              <span><i class="el-icon-info"></i> 系统简介</span>
            </div>
            <div class="system-intro">
              <p>欢迎使用<strong>本科生学籍管理系统</strong>！本系统旨在提供便捷、高效的学籍管理服务，帮助学生和管理人员轻松处理学籍相关事务。</p>
              <div class="feature-list">
                <div class="feature-item">
                  <i class="el-icon-check"></i>
                  <span>学籍信息管理：全面记录学生基本信息和学籍状态</span>
                </div>
                <div class="feature-item">
                  <i class="el-icon-check"></i>
                  <span>注册报到：便捷的在线注册流程，减少纸质办理</span>
                </div>
                <div class="feature-item">
                  <i class="el-icon-check"></i>
                  <span>请假审批：一体化的请假流程，提高审批效率</span>
                </div>
                <div class="feature-item">
                  <i class="el-icon-check"></i>
                  <span>学籍变动：规范化处理休学、复学、退学等变动事项</span>
                </div>
              </div>
              <p class="system-footer">本系统采用 <strong>Spring Boot + Vue.js</strong> 开发，确保高效、稳定的服务体验。</p>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </div>
      
    <!-- 通知公告弹窗 -->
    <el-dialog 
      :title="selectedNotice.title" 
      :visible.sync="showNoticeDialog" 
      width="780px" 
      center
      append-to-body>
      <div slot="title" class="dialog-title">
        <i class="el-icon-document"></i> {{selectedNotice.title}}
      </div>
      <div v-html="selectedNotice.content" class="notice-content"></div>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="showNoticeDialog = false">我知道了</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {listNotice, getNotice} from "@/api/system/notice";

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
      },
      // 轮播图数据
      carouselItems: [
        {
          image: require("@/assets/images/01.jpg"),
          alt: "校园风景",
          link: "https://www.baidu.com",
          caption: "美丽校园"
        },
        {
          image: require("@/assets/images/02.jpg"),
          alt: "学生活动",
          link: "https://www.jd.com",
          caption: "丰富多彩的校园活动"
        },
        {
          image: require("@/assets/images/03.jpg"),
          alt: "教学楼",
          link: "https://www.taobao.com",
          caption: "现代化教学环境"
        }
      ]
    };
  },
  created() {
    this.getList();
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
    // 处理复选框选中事件
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.noticeId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    }
  },
};
</script>

<style scoped lang="scss">
.home-container {
  padding: 0 20px 20px;
}

.gradient-title {
  height: 120px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 20px 0;
  font-size: 50px;
  font-weight: bold;
  font-family: 'Microsoft YaHei', sans-serif;
  background-image: linear-gradient(to right, #42b983, #409EFF, #2d3e8b);
  -webkit-background-clip: text;
  background-clip: text;
  color: transparent;
  text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.1);
  letter-spacing: 2px;
}

.carousel-container {
  margin-bottom: 30px;
}

.carousel-image {
  width: 100%;
  height: 100%;
  object-fit: cover;
  border-radius: 8px;
}

.carousel-caption {
  position: absolute;
  bottom: 0;
  left: 0;
  right: 0;
  background: rgba(0, 0, 0, 0.5);
  color: white;
  padding: 10px;
  text-align: center;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
}

.content-container {
  margin-top: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 16px;
  font-weight: bold;
}

.notice-card, .info-card {
  height: 450px;
  margin-bottom: 20px;
  border-radius: 8px;
  transition: all 0.3s;
}

.system-intro {
  padding: 10px;
  line-height: 1.8;
}

.feature-list {
  margin: 20px 0;
}

.feature-item {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
  
  i {
    color: #42b983;
    margin-right: 10px;
    font-size: 18px;
  }
  
  span {
    flex: 1;
  }
}

.system-footer {
  margin-top: 20px;
  text-align: center;
  color: #909399;
  font-style: italic;
}

.dialog-title {
  text-align: center;
  font-weight: bold;
}

.notice-content::v-deep {
  img {
    max-width: 100%;
    height: auto;
    display: block;
    margin: 0 auto;
  }
  
  padding: 15px;
  line-height: 1.8;
}

.dialog-footer {
  text-align: center;
}
</style>
