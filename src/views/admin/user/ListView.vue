<template>
  <div class="user-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>用户管理</span>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="yonghuming" label="用户名" width="120" />
        <el-table-column prop="xingming" label="姓名" width="120" />
        <el-table-column prop="shouji" label="手机号" width="120" />
        <el-table-column prop="youxiang" label="邮箱" width="180" />
        <el-table-column prop="xingbie" label="性别" width="80" />
<!--        <el-table-column prop="nianling" label="年龄" width="80" />-->
        <el-table-column prop="touxiang" label="头像" width="100">
          <template #default="scope">
            <el-image
              style="width: 40px; height: 40px"
              :src="getAvatarUrl(scope.row.touxiang)"
              fit="cover"
            >
              <template #error>
                <el-image
                  style="width: 40px; height: 40px"
                  :src="defaultAvatar"
                  fit="cover"
                />
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="操作" fixed="right" width="150">
          <template #default="scope">
            <el-button
              size="small"
              type="danger"
              @click="handleDelete(scope.row)"
            >
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

// 默认头像路径
const defaultAvatar = 'http://localhost:8081/uploads/avatar/default.png'

// 获取用户列表
const getUserList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/yonghu/list',
      method: 'get'
    })
    console.log('用户列表数据:', res)
    // 打印每个用户的头像路径
    res.data?.forEach(user => {
      console.log('用户头像路径:', user.touxiang, '处理后:', getAvatarUrl(user.touxiang))
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取用户列表失败:', error)
    ElMessage.error('获取用户列表失败')
  } finally {
    loading.value = false
  }
}

// 删除用户
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除用户 ${row.username} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/yonghu/${row.id}`,
        method: 'delete'
      })
      ElMessage.success('删除成功')
      getUserList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

// 处理头像URL
const getAvatarUrl = (path) => {
  console.log('原始路径:', path);
  if (!path) {
    console.log('使用默认头像');
    return defaultAvatar;
  }
  if (path.startsWith('http')) {
    console.log('使用完整URL:', path);
    return path;
  }
  // 确保路径以 /uploads 开头
  if (!path.startsWith('/uploads')) {
    path = '/uploads/avatar/' + path;
  }
  const fullUrl = `http://localhost:8081${path}`;
  console.log('完整URL:', fullUrl);
  return fullUrl;
}

onMounted(() => {
  getUserList()
})
</script>

<style scoped>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
