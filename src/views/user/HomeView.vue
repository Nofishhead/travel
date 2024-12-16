<template>
  <div class="home-container">
    <!-- 顶部导航栏 -->
    <el-header class="header">
      <div class="header-left">
        <h2>智慧旅游</h2>
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

    <!-- 主要内容区 -->
    <div class="main-content">
      <!-- 轮播图 -->
      <div class="banner-container" v-if="banners.length > 0">
        <el-carousel
          height="400px"
          class="banner"
          :initial-index="currentIndex"
          :autoplay="true"
          :interval="5000"
          trigger="click"
          @change="handleCarouselChange"
        >
          <el-carousel-item
            v-for="(banner, index) in banners"
            :key="banner.id"
          >
            <div class="banner-item" :style="getBannerStyle(banner)">
              <div class="banner-title" v-if="banner.title">{{ banner.title }}</div>
            </div>
          </el-carousel-item>
        </el-carousel>
      </div>
      <div v-else>暂无轮播图数据</div>

      <!-- 快捷导航 -->
      <div class="quick-nav">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-card @click="router.push('/scenic')">
              <el-icon><Location /></el-icon>
              <span>景点门票</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="router.push('/route')">
              <el-icon><Guide /></el-icon>
              <span>旅游线路</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="router.push('/food')">
              <el-icon><Food /></el-icon>
              <span>特色美食</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="router.push('/news')">
              <el-icon><Document /></el-icon>
              <span>旅游资讯</span>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 热门景点 -->
      <div class="section">
        <div class="section-header">
          <h3>热门景点</h3>
          <el-button text @click="router.push('/scenic')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="scenic in hotScenics" :key="scenic.id">
            <el-card :body-style="{ padding: '0px' }" class="scenic-card">
              <img :src="getImageUrl(scenic.tupian)" class="scenic-image">
              <div class="scenic-info">
                <h4>{{ scenic.jingdianmingcheng }}</h4>
                <p class="price">¥{{ scenic.piaojia }}</p>
                <el-button type="primary" size="small">立即预订</el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 精选线路 -->
      <div class="section">
        <div class="section-header">
          <h3>精选线路</h3>
          <el-button text @click="router.push('/route')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="8" v-for="route in featuredRoutes" :key="route.id">
            <el-card class="route-card">
              <img :src="getImageUrl(route.tupian)" class="route-image">
              <div class="route-info">
                <h4>{{ route.xianlumingcheng }}</h4>
                <p>{{ route.xianlutese }}</p>
                <div class="route-footer">
                  <span class="price">¥{{ route.jiage }}</span>
                  <el-button type="primary" size="small">查看详情</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 美食推荐 -->
      <div class="section">
        <div class="section-header">
          <h3>美食推荐</h3>
          <el-button text @click="router.push('/food')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="food in recommendedFoods" :key="food.id">
            <el-card class="food-card">
              <img :src="getImageUrl(food.tupian)" class="food-image">
              <div class="food-info">
                <h4>{{ food.meishimingcheng }}</h4>
                <p>{{ food.meishijianjie }}</p>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { Location, Guide, Food, Document } from '@element-plus/icons-vue'
import request from '@/utils/request'

const router = useRouter()
const userInfo = ref({})
const banners = ref([])
const currentIndex = ref(0)
const hotScenics = ref([])
const featuredRoutes = ref([])
const recommendedFoods = ref([])
const baseUrl = 'http://localhost:8081'

// 处理图片URL
const getImageUrl = (path) => {
  if (!path) return ''
  if (path.startsWith('http')) return path
  const normalizedPath = path
    .replace(/^uploads\//, '')
    .replace(/^\/uploads\//, '')
    .replace(/^upload\//, '')
    .replace(/^\/upload\//, '')
  const url = `${baseUrl}/uploads/${normalizedPath}`
  console.log('Image URL:', {
    originalPath: path,
    fullUrl: url,
    baseUrl: baseUrl
  })
  return url
}

// 获取轮播图数据
const getBanners = async () => {
  try {
    const res = await request.get('/travel/lunbotu/list')
    if (res.code === '200' && Array.isArray(res.data)) {
      banners.value = res.data
      banners.value.forEach((banner, index) => {
        console.log(`轮播图 ${index + 1}:`, {
          id: banner.id,
          title: banner.title,
          image: banner.image,
          originalUrl: banner.image,
          processedUrl: getImageUrl(banner.image)
        })
      })
    } else {
      console.error('轮播图数据格式错误:', res)
    }
  } catch (error) {
    console.error('获取轮播图失败:', error)
  }
}

// 获取热门景点
const getHotScenics = async () => {
  try {
    const res = await request.get('/travel/jingdianxinxi/hot')
    if (res.code === '200') {
      hotScenics.value = res.data
    } else {
      ElMessage.error('获取热门景点失败')
    }
  } catch (error) {
    console.error('获取热门景点失败:', error)
    ElMessage.error('获取热门景点失败')
  }
}

// 获取精选线路
const getFeaturedRoutes = async () => {
  try {
    const res = await request.get('/travel/lvyouxianlu/featured')
    featuredRoutes.value = res.data
  } catch (error) {
    console.error('获取精选线路失败:', error)
  }
}

// 获取推荐美食
const getRecommendedFoods = async () => {
  try {
    const res = await request.get('/travel/meishi/recommended')
    recommendedFoods.value = res.data
  } catch (error) {
    console.error('获取推荐美食失败:', error)
  }
}

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
      handleLogout()
      break
  }
}

// 退出登录
const handleLogout = async () => {
  try {
    const res = await request({
      url: '/travel/yonghu/logout',
      method: 'post',
      headers: {
        token: localStorage.getItem('token')
      }
    })
    if (res.code === '200') {
      localStorage.removeItem('token')
      localStorage.removeItem('userInfo')
      ElMessage.success('退出成功')
      router.push('/user/login')
    }
  } catch (error) {
    ElMessage.error('退出失败')
  }
}

// 处理轮播图切换
const handleCarouselChange = (index) => {
  currentIndex.value = index
  console.log('当前轮播图索引:', index)
}

// 获取轮播图背景样式
const getBannerStyle = (banner) => {
  const imageUrl = getImageUrl(banner.image) + '?t=' + Date.now()
  return {
    backgroundImage: `url(${imageUrl})`,
    backgroundSize: 'cover',
    backgroundPosition: 'center',
    height: '100%'
  }
}

onMounted(() => {
  const storedUserInfo = localStorage.getItem('userInfo')
  if (storedUserInfo) {
    userInfo.value = JSON.parse(storedUserInfo)
    // 获取首页数据
    getBanners()
    getHotScenics()
    getFeaturedRoutes()
    getRecommendedFoods()
  } else {
    router.push('/user/login')
  }
})
</script>

<style scoped>
.home-container {
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

.banner-container {
  margin-bottom: 30px;
  border-radius: 8px;
  overflow: hidden;
}

.banner {
  height: 400px;
}

.banner-item {
  width: 100%;
  height: 100%;
  position: relative;
}

.banner-title {
  position: absolute;
  bottom: 20px;
  left: 20px;
  color: #fff;
  font-size: 24px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.5);
  z-index: 1;
  padding: 5px 15px;
  background-color: rgba(0, 0, 0, 0.3);
  border-radius: 4px;
}

:deep(.el-carousel__arrow) {
  background-color: rgba(0, 0, 0, 0.3);
}

:deep(.el-carousel__button) {
  width: 10px;
  height: 10px;
  border-radius: 50%;
}

.quick-nav {
  margin-bottom: 30px;
  .el-card {
    cursor: pointer;
    text-align: center;
    padding: 20px;
    transition: all 0.3s;
    &:hover {
      transform: translateY(-5px);
    }
    .el-icon {
      font-size: 30px;
      margin-bottom: 10px;
      color: #409EFF;
    }
  }
}

.section {
  margin-bottom: 30px;
  .section-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 20px;
  }
}

.scenic-card, .route-card, .food-card {
  margin-bottom: 20px;
  transition: all 0.3s;
  &:hover {
    transform: translateY(-5px);
  }
}

.scenic-image, .route-image, .food-image {
  width: 100%;
  height: 200px;
  object-fit: cover;
}

.scenic-info, .route-info, .food-info {
  padding: 15px;
  h4 {
    margin: 0 0 10px;
    font-size: 16px;
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
</style>
