<template>
  <!-- 顶部导航栏 -->
  <el-header class="header">
    <div class="header-left">
      <h2 class="logo" @click="router.push('/user/home')">智慧旅游</h2>
    </div>
    <div class="header-right">
      <el-dropdown @command="handleCommand">
        <el-avatar :size="40" icon="UserFilled" />
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item command="profile">个人信息</el-dropdown-item>
            <el-dropdown-item command="orders">我的订单</el-dropdown-item>
            <el-dropdown-item command="logout">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
    </div>
  </el-header>

  <div class="scenic-list">
    <!-- 页面标题 -->
    <div class="page-header">
      <h2>景点列表</h2>
      <div class="search-box">
        <el-input
          v-model="searchQuery"
          placeholder="搜索景点名称"
          clearable
          @input="handleSearch"
        >
          <template #prefix>
            <el-icon><Search /></el-icon>
          </template>
        </el-input>
      </div>
    </div>

    <!-- 景点列表 -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="scenic in filteredScenics" :key="scenic.id">
        <el-card :body-style="{ padding: '0px' }" class="scenic-card">
          <img
            :src="getImageUrl(scenic.tupian)"
            class="scenic-image"
            @click="handleViewDetail(scenic.id)"
          >
          <div class="scenic-info">
            <h4>{{ scenic.jingdianmingcheng }}</h4>
            <div class="scenic-meta">
              <span class="price">¥{{ scenic.piaojia }}</span>
              <span class="views">
                <el-icon><View /></el-icon>
                {{ scenic.liulanliang }}
              </span>
            </div>
            <el-button
              type="primary"
              size="small"
              @click="handleViewDetail(scenic.id)"
            >
              查看详情
            </el-button>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :total="total"
        :page-sizes="[12, 24, 36, 48]"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search, View } from '@element-plus/icons-vue'
import request from '@/utils/request'

const router = useRouter()
const scenics = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)

// 处理下拉菜单命令
const handleCommand = (command) => {
  switch (command) {
    case 'profile':
      router.push('/user/profile')
      break
    case 'orders':
      router.push('/user/orders')
      break
    case 'logout':
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      router.push('/user/login')
      break
  }
}

// 过滤景点列表
const filteredScenics = computed(() => {
  if (!searchQuery.value) return scenics.value
  const query = searchQuery.value.toLowerCase()
  return scenics.value.filter(scenic =>
    scenic.jingdianmingcheng.toLowerCase().includes(query)
  )
})

// 获取景点列表
const getScenics = async () => {
  try {
    const res = await request.get('/travel/jingdianxinxi/list')
    if (res.code === '200') {
      scenics.value = res.data
      total.value = res.data.length
    } else {
      ElMessage.error('获取景点列表失败')
    }
  } catch (error) {
    console.error('获取景点列表失败:', error)
    ElMessage.error('获取景点列表失败')
  }
}

// 处理搜索
const handleSearch = () => {
  currentPage.value = 1
}

// 处理查看详情
const handleViewDetail = (id) => {
  router.push(`/scenic/detail/${id}`)
}

// 处理分页大小变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1
}

// 处理页码变化
const handleCurrentChange = (val) => {
  currentPage.value = val
}

// 处理图片URL
const getImageUrl = (path) => {
  if (!path) return ''
  if (path.startsWith('http')) return path
  const baseUrl = 'http://localhost:8081'
  const normalizedPath = path
    .replace(/^uploads\//, '')
    .replace(/^\/uploads\//, '')
    .replace(/^upload\//, '')
    .replace(/^\/upload\//, '')
  return `${baseUrl}/uploads/${normalizedPath}`
}

onMounted(() => {
  getScenics()
})
</script>

<style scoped>
.header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 1000;
  .logo {
    cursor: pointer;
    &:hover {
      color: #409EFF;
    }
  }
}

.scenic-list {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
  padding-top: 80px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  h2 {
    margin: 0;
  }
  .search-box {
    width: 300px;
  }
}

.scenic-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  &:hover {
    transform: translateY(-5px);
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  }
}

.scenic-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    opacity: 0.9;
  }
}

.scenic-info {
  padding: 15px;
  h4 {
    margin: 0 0 10px;
    font-size: 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
}

.scenic-meta {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
  .price {
    color: #f56c6c;
    font-size: 18px;
    font-weight: bold;
  }
  .views {
    color: #909399;
    font-size: 14px;
    display: flex;
    align-items: center;
    gap: 4px;
  }
}

.el-button {
  width: 100%;
}

.pagination {
  margin-top: 30px;
  display: flex;
  justify-content: center;
}
</style>
