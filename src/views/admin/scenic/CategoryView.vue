<template>
  <div class="scenic-category">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>景点分类管理</span>
          <el-button type="primary" @click="handleAdd">添加分类</el-button>
        </div>
      </template>

      <!-- 分类列表表格 -->
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="fenleimingcheng" label="分类名称" />
        <el-table-column prop="addtime" label="添加时间" width="180">
          <template #default="scope">
            {{ formatDate(scope.row.addtime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="handleEdit(scope.row)">
              编辑
            </el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 添加/编辑对话框 -->
      <el-dialog
        :title="dialogTitle"
        v-model="dialogVisible"
        width="30%"
        :before-close="handleClose"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="分类名称" prop="fenleimingcheng">
            <el-input v-model="form.fenleimingcheng" placeholder="请输入分类名称" />
          </el-form-item>
        </el-form>
        <template #footer>
          <span class="dialog-footer">
            <el-button @click="dialogVisible = false">取消</el-button>
            <el-button type="primary" @click="handleSubmit">确定</el-button>
          </span>
        </template>
      </el-dialog>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import request from '@/utils/request'

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref(null)

const form = ref({
  fenleimingcheng: ''
})

const rules = {
  fenleimingcheng: [
    { required: true, message: '请输入分类名称', trigger: 'blur' }
  ]
}

// 格式化日期
const formatDate = (dateStr) => {
  if (!dateStr) return ''
  const date = new Date(dateStr)
  return date.toLocaleString()
}

// 获取分类列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/meishifenlei/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取分类列表失败:', error)
    ElMessage.error('获取分类列表失败')
  } finally {
    loading.value = false
  }
}

// 添加分类
const handleAdd = () => {
  dialogTitle.value = '添加分类'
  form.value = {
    fenleimingcheng: ''
  }
  dialogVisible.value = true
}

// 编辑分类
const handleEdit = (row) => {
  dialogTitle.value = '编辑分类'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除分类
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除分类 ${row.fenleimingcheng} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/meishifenlei/${row.id}`,
        method: 'delete'
      })
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

// 提交表单
const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        const method = form.value.id ? 'put' : 'post'
        const url = form.value.id
          ? `/travel/meishifenlei/${form.value.id}`
          : '/travel/meishifenlei'

        await request({
          url,
          method,
          data: form.value
        })

        ElMessage.success(`${form.value.id ? '修改' : '添加'}成功`)
        dialogVisible.value = false
        getList()
      } catch (error) {
        ElMessage.error(`${form.value.id ? '修改' : '添加'}失败`)
      }
    }
  })
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  formRef.value?.resetFields()
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.scenic-category {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

/* 表格样式优化 */
:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table td) {
  padding: 6px 0;
}

/* 添加表格行hover效果 */
:deep(.el-table tr:hover) {
  background-color: #f5f7fa;
}

/* 调整按钮间距 */
:deep(.el-button + .el-button) {
  margin-left: 8px;
}
</style>
