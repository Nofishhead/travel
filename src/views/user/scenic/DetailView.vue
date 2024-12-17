<template>
  <div class="scenic-detail">
    <el-card class="detail-card">
      <!-- 景点标题 -->
      <div class="scenic-title">
        <h2>{{ scenic.jingdianmingcheng }}</h2>
        <el-tag>{{ scenic.jingdianbianhao }}</el-tag>
      </div>

      <!-- 景点图片 -->
      <div class="scenic-image">
        <img :src="getImageUrl(scenic.tupian)" :alt="scenic.jingdianmingcheng">
      </div>

      <!-- 基本信息 -->
      <div class="info-section">
        <h3>基本信息</h3>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="门票价格">
            <span class="price">¥{{ scenic.piaojia }}</span>
          </el-descriptions-item>
          <el-descriptions-item label="开放时间">
            {{ scenic.kaifangshijian }}
          </el-descriptions-item>
          <el-descriptions-item label="所属地区">
            {{ scenic.suoshudiqu }}
          </el-descriptions-item>
          <el-descriptions-item label="浏览量">
            {{ scenic.liulanliang }}
          </el-descriptions-item>
          <el-descriptions-item label="地址" :span="2">
            {{ scenic.dizhi }}
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <!-- 景点描述 -->
      <div class="description-section">
        <h3>景点描述</h3>
        <div class="description-content" v-html="scenic.miaoshu"></div>
      </div>

      <!-- 操作按钮 -->
      <div class="action-buttons">
        <el-button type="primary" @click="handleBook">立即预订</el-button>
        <el-button @click="router.back()">返回</el-button>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '@/utils/request'

const route = useRoute()
const router = useRouter()
const scenic = ref({})

// 获取景点详情
const getScenicDetail = async () => {
  try {
    const res = await request.get(`/travel/jingdianxinxi/${route.params.id}`)
    if (res.code === '200') {
      scenic.value = res.data
      // 增加浏览量
      updateViewCount()
    } else {
      ElMessage.error('获取景点详情失败')
    }
  } catch (error) {
    console.error('获取景点详情失败:', error)
    ElMessage.error('获取景点详情失败')
  }
}

// 更新浏览量
const updateViewCount = async () => {
  try {
    const updatedScenic = {
      ...scenic.value,
      liulanliang: (scenic.value.liulanliang || 0) + 1
    }
    await request.put(`/travel/jingdianxinxi/${scenic.value.id}`, updatedScenic)
  } catch (error) {
    console.error('更新浏览量失败:', error)
  }
}

// 处理预订
const handleBook = () => {
  // TODO: 实现预订功能
  ElMessage.info('预订功能开发中...')
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
  getScenicDetail()
})
</script>

<style scoped>
.scenic-detail {
  max-width: 1200px;
  margin: 20px auto;
  padding: 0 20px;
}

.detail-card {
  margin-bottom: 20px;
}

.scenic-title {
  display: flex;
  align-items: center;
  margin-bottom: 20px;
  h2 {
    margin: 0;
    margin-right: 15px;
  }
}

.scenic-image {
  margin-bottom: 20px;
  img {
    width: 100%;
    max-height: 500px;
    object-fit: cover;
    border-radius: 8px;
  }
}

.info-section {
  margin-bottom: 30px;
  h3 {
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
  }
}

.price {
  color: #f56c6c;
  font-size: 18px;
  font-weight: bold;
}

.description-section {
  margin-bottom: 30px;
  h3 {
    margin-bottom: 15px;
    padding-bottom: 10px;
    border-bottom: 1px solid #eee;
  }
  .description-content {
    line-height: 1.8;
  }
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}
</style>
