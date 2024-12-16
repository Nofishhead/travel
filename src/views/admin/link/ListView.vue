<template>
  <div class="link-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>友情链接管理</span>
          <el-button type="primary" @click="handleAdd">添加链接</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="wangzhanmingcheng" label="网站名称" width="200" />
        <el-table-column prop="wangzhi" label="网址" min-width="300">
          <template #default="scope">
            <el-link :href="scope.row.wangzhi" target="_blank" type="primary">
              {{ scope.row.wangzhi }}
            </el-link>
          </template>
        </el-table-column>
        <el-table-column prop="addtime" label="添加时间" width="160" />
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
        width="50%"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="网站名称" prop="wangzhanmingcheng">
            <el-input v-model="form.wangzhanmingcheng" placeholder="请输入网站名称" />
          </el-form-item>
          <el-form-item label="网址" prop="wangzhi">
            <el-input v-model="form.wangzhi" placeholder="请输入网址">
              <template #prepend>http://</template>
            </el-input>
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
  wangzhanmingcheng: '',
  wangzhi: ''
})

const rules = {
  wangzhanmingcheng: [
    { required: true, message: '请输入网站名称', trigger: 'blur' }
  ],
  wangzhi: [
    { required: true, message: '请输入网址', trigger: 'blur' },
    {
      pattern: /^(https?:\/\/)?([\da-z.-]+)\.([a-z.]{2,6})([/\w .-]*)*\/?$/,
      message: '请输入正确的网址格式',
      trigger: 'blur'
    }
  ]
}

// 获取友情链接列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/youqinglianjie/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    ElMessage.error('获取友情链接列表失败')
  } finally {
    loading.value = false
  }
}

// 添加友情链接
const handleAdd = () => {
  dialogTitle.value = '添加友情链接'
  form.value = {
    wangzhanmingcheng: '',
    wangzhi: ''
  }
  dialogVisible.value = true
}

// 编辑友情链接
const handleEdit = (row) => {
  dialogTitle.value = '编辑友情链接'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除友情链接
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除友情链接 ${row.wangzhanmingcheng} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/youqinglianjie/${row.id}`,
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
        // 处理网址格式
        if (!form.value.wangzhi.startsWith('http')) {
          form.value.wangzhi = 'http://' + form.value.wangzhi
        }

        const method = form.value.id ? 'put' : 'post'
        const url = form.value.id
          ? `/travel/youqinglianjie/${form.value.id}`
          : '/travel/youqinglianjie'

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

onMounted(() => {
  getList()
})
</script>

<style scoped>
.link-list {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>
