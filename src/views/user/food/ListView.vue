<template>
  <div class="food-list-container">
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
        <h2>特色美食</h2>
      </div>

      <!-- 搜索栏 -->
      <div class="search-bar">
        <el-input
          v-model="searchQuery"
          placeholder="搜索美食名称"
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

      <!-- 美食列表 -->
      <el-row :gutter="20">
        <el-col :span="6" v-for="food in foods" :key="food.id">
          <el-card class="food-card">
            <img :src="getFirstImage(food.tupian)" class="food-image">
            <div class="food-info">
              <h4>{{ food.meishimingcheng }}</h4>
              <p class="description">{{ food.miaoshu }}</p>
              <div class="food-footer">
                <el-button type="primary" size="small" @click="handleViewDetail(food)">查看详情</el-button>
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
const foods = ref([])
const searchQuery = ref('')
const currentPage = ref(1)
const pageSize = ref(12)
const total = ref(0)
const baseUrl = 'http://localhost:8081'

// 获取美食列表
const getFoods = async () => {
  try {
    const res = await request.get('/travel/difangmeishi/list')
    if (res.code === '200') {
      foods.value = res.data
      total.value = res.data.length
    }
  } catch (error) {
    console.error('获取美食列表失败:', error)
    ElMessage.error('获取美食列表失败')
  }
}

// 处理搜索
const handleSearch = () => {
  getFoods()
}

// 处理页码改变
const handleCurrentChange = (page) => {
  currentPage.value = page
  getFoods()
}

// 处理每页条数改变
const handleSizeChange = (size) => {
  pageSize.value = size
  getFoods()
}

// 处理图片URL
const getFirstImage = (imageStr) => {
  if (!imageStr) return ''
  const firstImage = imageStr.split(',')[0]
  return firstImage.startsWith('http')
    ? firstImage
    : `${baseUrl}/${firstImage.replace('/uploads/diqu/', '')}`
}

// 查看详情
const handleViewDetail = (food) => {
  // TODO: 实现美食详情页面
  ElMessage.info('美食详情功能开发中...')
}

onMounted(() => {
  getFoods()
})
</script>

<style scoped>
.food-list-container {
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

.food-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  &:hover {
    transform: translateY(-5px);
  }
}

.food-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.food-info {
  padding: 15px;
  h4 {
    margin: 0 0 10px;
    font-size: 16px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }
  .description {
    color: #666;
    font-size: 14px;
    margin-bottom: 10px;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
    overflow: hidden;
    text-overflow: ellipsis;
  }
}

.food-footer {
  display: flex;
  justify-content: center;
  margin-top: 10px;
  .el-button {
    width: 100%;
  }
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}
</style>
