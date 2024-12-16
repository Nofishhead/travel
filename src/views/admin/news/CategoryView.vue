<template>
  <div class="category-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新闻分类管理</span>
          <el-button type="primary" @click="handleAdd">添加分类</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="fenleimingcheng" label="分类名称" />
        <el-table-column label="操作" width="200">
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

      <el-dialog
        :title="dialogTitle"
        v-model="dialogVisible"
        width="30%"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="分类名称" prop="fenleimingcheng">
            <el-input v-model="form.fenleimingcheng" />
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

// 获取分类列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/xinwenfenlei/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
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
        url: `/travel/xinwenfenlei/${row.id}`,
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
          ? `/travel/xinwenfenlei/${form.value.id}`
          : '/travel/xinwenfenlei'

        await request({
          url,
          method,
          data: form.value
        })

        ElMessage.success(`${form.value.id ? '修改' : '添加'}��功`)
        dialogVisible.value = false
        getList()
      } catch (error) {
        ElMessage.error(`${form.value.id ? '修改' : '添加'}失败`)
      }
    }
  })
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.category-list {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
