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
            <el-card @click="handleQuickNav('scenic')">
              <el-icon><Location /></el-icon>
              <span>景点门票</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="handleQuickNav('route')">
              <el-icon><Guide /></el-icon>
              <span>旅游线路</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="handleQuickNav('food')">
              <el-icon><Food /></el-icon>
              <span>特色美食</span>
            </el-card>
          </el-col>
          <el-col :span="6">
            <el-card @click="handleQuickNav('news')">
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
          <el-button text @click="handleViewMore('scenic')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="scenic in hotScenics" :key="scenic.id">
            <el-card :body-style="{ padding: '0px' }" class="scenic-card">
              <img
                :src="getImageUrl(scenic.tupian)"
                class="scenic-image"
                @click="handleViewScenicDetail(scenic.id)"
              >
              <div class="scenic-info">
                <h4>{{ scenic.jingdianmingcheng }}</h4>
                <p class="price">¥{{ scenic.piaojia }}</p>
                <el-button
                  type="primary"
                  size="small"
                  @click="handleViewScenicDetail(scenic.id)"
                >
                  查看详情
                </el-button>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 精选线路 -->
      <div class="section">
        <div class="section-header">
          <h3>精选线路</h3>
          <el-button text @click="handleViewMore('route')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="8" v-for="route in featuredRoutes" :key="route.id">
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
      </div>

      <!-- 美食推荐 -->
      <div class="section">
        <div class="section-header">
          <h3>美食推荐</h3>
          <el-button text @click="handleViewMore('food')">查看更多</el-button>
        </div>
        <el-row :gutter="20">
          <el-col :span="6" v-for="food in recommendedFoods" :key="food.id">
            <el-card class="food-card">
              <img :src="getFirstImage(food.tupian)" class="food-image">
              <div class="food-info">
                <h4>{{ food.meishimingcheng }}</h4>
                <div class="food-footer">
                  <el-button type="primary" size="small">查看详情</el-button>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </div>

      <!-- 预订对话框 -->
      <el-dialog
        v-model="bookingDialogVisible"
        title="线路预订"
        width="500px"
      >
        <el-form
          ref="bookingFormRef"
          :model="bookingForm"
          :rules="bookingRules"
          label-width="100px"
        >
          <el-form-item label="线路名称">
            <span>{{ selectedRoute.xianlumingcheng }}</span>
          </el-form-item>
          <el-form-item label="行程">
            <span>{{ `${selectedRoute.chufadi} → ${selectedRoute.zhongdian}` }}</span>
          </el-form-item>
          <el-form-item label="价格">
            <span class="price">¥{{ selectedRoute.jiage }}</span>
          </el-form-item>
          <el-form-item label="预订账号" prop="yudingren">
            <el-input v-model="bookingForm.yudingren" placeholder="请输入您的账号" />
          </el-form-item>
          <el-form-item label="预订人姓名" prop="yudingrenxingming">
            <el-input v-model="bookingForm.yudingrenxingming" />
          </el-form-item>
          <el-form-item label="联系方式" prop="lianxifangshi">
            <el-input v-model="bookingForm.lianxifangshi" />
          </el-form-item>
          <el-form-item label="预订时间" prop="yudingshijian">
            <el-date-picker
              v-model="bookingForm.yudingshijian"
              type="date"
              placeholder="选择出发日期"
              :disabled-date="disabledDate"
              value-format="YYYY-MM-DD"
            />
          </el-form-item>
          <el-form-item label="备注" prop="beizhu">
            <el-input
              v-model="bookingForm.beizhu"
              type="textarea"
              :rows="3"
              placeholder="请输入备注信息"
            />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="bookingDialogVisible = false">取消</el-button>
            <el-button type="primary" @click="submitBooking">确认预订</el-button>
          </span>
        </template>
      </el-dialog>
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
const bookingDialogVisible = ref(false)
const bookingFormRef = ref()
const selectedRoute = ref({})
const bookingForm = ref({
  yudingrenxingming: '',
  lianxifangshi: '',
  yudingshijian: '',
  beizhu: '',
  yudingren: ''
})
const bookingRules = {
  yudingrenxingming: [
    { required: true, message: '请输入预订人姓名', trigger: 'blur' }
  ],
  lianxifangshi: [
    { required: true, message: '请输入联系方式', trigger: 'blur' },
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号码', trigger: 'blur' }
  ],
  yudingshijian: [
    { required: true, message: '请选择预订时间', trigger: 'change' }
  ],
  yudingren: [
    { required: true, message: '请输入预订账号', trigger: 'blur' }
  ]
}

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
  // 先从 localStorage 获取缓存的轮播图数据
  const cachedBanners = localStorage.getItem('banners')
  const cacheTime = localStorage.getItem('bannersTime')
  const now = new Date().getTime()

  // 如果有缓存且缓存时间不超过1小时，直接使用缓存据
  if (cachedBanners && cacheTime && (now - parseInt(cacheTime)) < 3600000) {
    banners.value = JSON.parse(cachedBanners)
    return
  }

  try {
    const res = await request.get('/travel/lunbotu/list')
    if (res.code === '200') {
      banners.value = res.data
      // 缓存轮播图数据和缓存时间
      localStorage.setItem('banners', JSON.stringify(res.data))
      localStorage.setItem('bannersTime', now.toString())
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
    const res = await request.get('/travel/lvyouxianlu/list')
    featuredRoutes.value = res.data
  } catch (error) {
    console.error('获取精选线路失败:', error)
  }
}

// 获取推荐美食
const getRecommendedFoods = async () => {
  try {
    const res = await request.get('/travel/difangmeishi/list')
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

// 获取目的地图片（第二张图片）
const getDestinationImage = (imageStr) => {
  if (!imageStr) return ''
  // 分割图片路径字符串
  const images = imageStr.split(',')
  // 获取第二张图片（目的地图片）
  const destinationImage = images[1] || images[0] // 如果没有第二张就用第一张
  return getImageUrl(destinationImage.trim())
}

// 获取第一张图片
const getFirstImage = (imageStr) => {
  if (!imageStr) return ''
  // 分割图片路径字符串，获第一张图片
  const firstImage = imageStr.split(',')[0]
  return getImageUrl(firstImage.trim())
}

// 查看景点详情
const handleViewScenicDetail = (id) => {
  router.push(`/scenic/detail/${id}`)
}

// 禁用今天之前的日期
const disabledDate = (time) => {
  return time.getTime() < Date.now() - 8.64e7
}

// 处理预订
const handleBookRoute = (route) => {
  selectedRoute.value = route
  bookingForm.value = {
    yudingrenxingming: '',
    lianxifangshi: '',
    yudingshijian: '',
    beizhu: '',
    yudingren: ''
  }
  bookingDialogVisible.value = true
}

// 提交预订
const submitBooking = async () => {
  if (!bookingFormRef.value) return

  await bookingFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const bookingData = {
          lvyouxianluid: selectedRoute.value.id,
          xianlubianhao: selectedRoute.value.xianlubianhao,
          xianlumingcheng: selectedRoute.value.xianlumingcheng,
          chufadi: selectedRoute.value.chufadi,
          tujingdi: selectedRoute.value.tujingdi,
          zhongdian: selectedRoute.value.zhongdian,
          jiage: selectedRoute.value.jiage,
          yudingrenxingming: bookingForm.value.yudingrenxingming,
          lianxifangshi: bookingForm.value.lianxifangshi,
          yudingshijian: bookingForm.value.yudingshijian,
          beizhu: bookingForm.value.beizhu || '',
          yudingren: bookingForm.value.yudingren,
          zhuangtai: '待确认',
          iszf: '否'
        }

        // 验证所有必要字段
        const requiredFields = [
          'lvyouxianluid', 'xianlubianhao', 'xianlumingcheng',
          'yudingrenxingming', 'lianxifangshi', 'yudingshijian', 'yudingren'
        ]

        for (const field of requiredFields) {
          if (!bookingData[field]) {
            ElMessage.error(`${field} 不能为空`)
            return
          }
        }

        console.log('预订请求数据:', JSON.stringify(bookingData, null, 2))
        const res = await request.post('/travel/yuding', bookingData)
        console.log('预订响应:', res)
        if (res.code === '200' || res.code === 200) {
          ElMessage.success('预订成功')
          bookingDialogVisible.value = false
        } else {
          throw new Error(res.msg || '预订失败')
        }
      } catch (error) {
        console.error('预订失败:', error)
        ElMessage.error(error.message || '预订失败，请稍后重试')
      }
    }
  })
}

// 处理查看更多
const handleViewMore = (type) => {
  switch (type) {
    case 'scenic':
      router.push({
        path: '/scenic/list',
        query: { from: 'home' }
      })
      break
    case 'route':
      router.push({
        path: '/route',
        query: { from: 'home' }
      })
      break
    case 'food':
      router.push({
        path: '/food',
        query: { from: 'home' }
      })
      break
    case 'news':
      router.push({
        path: '/news',
        query: { from: 'home' }
      })
      break
  }
}

// 统一的导航处理函数
const handleQuickNav = (type) => {
  switch (type) {
    case 'scenic':
      router.push('/scenic/list')
      break
    case 'route':
      router.push('/route')
      break
    case 'food':
      router.push('/food')
      break
    case 'news':
      router.push('/news')
      break
  }
}

onMounted(() => {
  const storedUserInfo = localStorage.getItem('userInfo')
  userInfo.value = storedUserInfo ? JSON.parse(storedUserInfo) : {}
  getBanners()
  getHotScenics()
  getFeaturedRoutes()
  getRecommendedFoods()
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
  cursor: pointer;
  transition: all 0.3s;
  &:hover {
    opacity: 0.9;
  }
}

.scenic-info, .route-info, .food-info {
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
    margin-bottom: 10px;
  }
  .el-button {
    width: 100%;
  }
}

.route-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 10px;
}

.food-info {
  padding: 15px;
  h4 {
    margin: 0 0 10px;
    font-size: 16px;
    text-align: center;
  }
  .food-footer {
    display: flex;
    justify-content: center;
    margin-top: 10px;
  }
}

.dialog-footer {
  display: flex;
  justify-content: flex-end;
  gap: 10px;
}

:deep(.el-dialog__body) {
  padding-top: 10px;
}
</style>
