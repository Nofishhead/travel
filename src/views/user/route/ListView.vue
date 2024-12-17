<template>
  <div class="route-list-container">
    <el-header class="header">
      <div class="header-left">
        <h2>智慧旅游</h2>
      </div>
      <div class="header-right">
        <el-button @click="router.push('/user/home')">返回首页</el-button>
      </div>
    </el-header>

    <div class="main-content">
      <div class="section-header">
        <h2>旅游线路</h2>
      </div>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索线路名称"
          class="search-input"
          clearable
          @clear="handleSearch"
          @keyup.enter="handleSearch"
        >
          <template #append>
            <el-button @click="handleSearch">
              <el-icon><Search /></el-icon>
            </el-button>
          </template>
        </el-input>
      </div>

      <!-- 线路列表 -->
      <el-row :gutter="20">
        <el-col :span="8" v-for="route in routes" :key="route.id">
          <el-card class="route-card">
            <img :src="getDestinationImage(route.tupian)" class="route-image">
            <div class="route-info">
              <h4>{{ route.xianlumingcheng }}</h4>
              <p>{{ `${route.chufadi} → ${route.zhongdian}` }}</p>
              <div class="route-footer">
                <span class="price">¥{{ route.jiage }}</span>
                <el-button type="primary" size="small" @click="handleBookRoute(route)">立即预订</el-button>
              </div>
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
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Search } from '@element-plus/icons-vue'
import request from '@/utils/request'

const router = useRouter()
const routes = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const baseUrl = 'http://localhost:8081'

// 获取线路列表
const getRoutes = async () => {
  try {
    const res = await request.get('/travel/lvyouxianlu/list')
    if (res.code === '200') {
      routes.value = res.data
      total.value = res.data.length
    }
  } catch (error) {
    console.error('获取线路列表失败:', error)
    ElMessage.error('获取线路列表失败')
  }
}

// 处理搜索
const handleSearch = () => {
  // 实现搜索功能
  getRoutes()
}

// 处理页码改变
const handleCurrentChange = (page) => {
  currentPage.value = page
  getRoutes()
}

// 处理每页条数改变
const handleSizeChange = (size) => {
  pageSize.value = size
  getRoutes()
}

// 处理图片URL
const getDestinationImage = (imageStr) => {
  if (!imageStr) return ''
  const images = imageStr.split(',')
  const destinationImage = images[1] || images[0]
  return destinationImage.startsWith('http')
    ? destinationImage
    : `${baseUrl}/${destinationImage.replace('/uploads/diqu/', '')}`
}

// 处理预订
const handleBookRoute = (route) => {
  // 实现预订功能
  router.push({
    path: '/user/home',
    query: { bookRoute: route.id }
  })
}

onMounted(() => {
  getRoutes()
})
</script>

<style scoped>
.route-list-container {
  min-height: 100vh;
  background-color: #f5f7fa;
}

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
}

.main-content {
  padding: 80px 20px 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.section-header {
  margin-bottom: 20px;
}

.search-bar {
  margin-bottom: 20px;
  .search-input {
    width: 300px;
  }
}

.route-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  &:hover {
    transform: translateY(-5px);
  }
}

.route-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.route-info {
  padding: 15px;
  h4 {
    margin: 0 0 10px;
    font-size: 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .price {
    color: #f56c6c;
    font-size: 18px;
    font-weight: bold;
  }
}

.route-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
