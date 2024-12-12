<template>
  <div class="route-order">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>线路预订管理</span>
        </div>
      </template>

      <!-- 预订列表表格 -->
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="yudingbianhao" label="预订编号" width="120" />
        <el-table-column prop="xianlubianhao" label="线路编号" width="120" />
        <el-table-column prop="xianlumingcheng" label="线路名称" width="150" />
        <el-table-column prop="yonghuming" label="预订用户" width="120" />
        <el-table-column prop="lianxidianhua" label="联系电话" width="120" />
        <el-table-column prop="jiage" label="价格" width="80" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.jiage }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="beizhu" label="备注" show-overflow-tooltip />
        <el-table-column prop="zhuangtai" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.zhuangtai)">
              {{ scope.row.zhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="addtime" label="预订时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.addtime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150" fixed="right">
          <template #default="scope">
            <el-button
              size="small"
              type="success"
              link
              @click="handleStatus(scope.row)"
              v-if="scope.row.zhuangtai === '待确认'"
            >
              确认
            </el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

const loading = ref(false)
const tableData = ref([])

// 获取���订列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/yuding/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取预订列表失败:', error)
    ElMessage.error('获取预订列表失败')
  } finally {
    loading.value = false
  }
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '待确认':
      return 'warning'
    case '已确认':
      return 'success'
    case '已取消':
      return 'info'
    default:
      return ''
  }
}

// 确认预订
const handleStatus = (row) => {
  ElMessageBox.confirm(
    `确定要确认此预订吗？`,
    '提示',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'info',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/yuding/confirm/${row.id}`,
        method: 'put'
      })
      ElMessage.success('确认成功')
      getList()
    } catch (error) {
      ElMessage.error('确认失败')
    }
  })
}

// 删除预订
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除此预订记录吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/yuding/${row.id}`,
        method: 'delete'
      })
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.route-order {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.price {
  color: #f56c6c;
  font-weight: bold;
}
</style>
