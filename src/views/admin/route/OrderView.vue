<template>
  <div class="order-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>线路预订管理</span>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="lvyouxianluid" label="旅游线路id" width="120" />
        <el-table-column prop="xianlubianhao" label="线路编号" width="120" />
        <el-table-column prop="xianlumingcheng" label="线路名称" width="120" />
        <el-table-column prop="chufadi" label="出发地" width="100" />
        <el-table-column prop="tujingdi" label="途经地" width="120" />
        <el-table-column prop="zhongdian" label="终点" width="100" />
        <el-table-column prop="jiage" label="价格" width="100" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.jiage }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="dingdanhao" label="订单号" width="120" />
        <el-table-column prop="yudingrenxingming" label="预订人" width="100" />
        <el-table-column prop="lianxifangshi" label="联系方式" width="120" />
        <el-table-column prop="zhuangtai" label="状态" width="100">
          <template #default="scope">
            <el-tag :type="getStatusType(scope.row.zhuangtai)">
              {{ scope.row.zhuangtai }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="iszf" label="支付状态" width="100">
          <template #default="scope">
            <el-tag :type="scope.row.iszf === '是' ? 'success' : 'warning'">
              {{ scope.row.iszf === '是' ? '已支付' : '未支付' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="beizhu" label="备注" width="120" show-overflow-tooltip>
          <template #default="scope">
            <el-tooltip :content="scope.row.beizhu" placement="top">
              <span>{{ scope.row.beizhu || '备注...' }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="yudingshijian" label="预订时间" width="160" />
        <el-table-column label="操作" width="100" fixed="right">
          <template #default="scope">
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

// 获取状态标签类型
const getStatusType = (status) => {
  switch (status) {
    case '预定成功':
      return 'success'
    case '待支付':
      return 'warning'
    default:
      return 'info'
  }
}

// 获取预定列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/yuding/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    ElMessage.error('获取预定列表失败')
  } finally {
    loading.value = false
  }
}

// 删除预定
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除订单号为 ${row.dingdanhao} 的预定记录吗？`,
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
.order-list {
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
