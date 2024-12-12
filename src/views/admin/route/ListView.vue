<template>
  <div class="route-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>线路管理</span>
          <el-button type="primary" @click="handleAdd">添加线路</el-button>
        </div>
      </template>

      <!-- 线路列表表格 -->
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="xianlubianhao" label="编号" width="120" />
        <el-table-column prop="xianlumingcheng" label="名称" width="150" />
        <el-table-column prop="tupian" label="图片" width="100">
          <template #default="scope">
            <el-image
              style="width: 70px; height: 50px"
              :src="getImageUrl(scope.row.tupian)"
              fit="cover"
              :preview-src-list="[getImageUrl(scope.row.tupian)]"
            />
          </template>
        </el-table-column>
        <el-table-column prop="chufadi" label="出发地" width="100" />
        <el-table-column prop="tujingdi" label="途经地" width="150" />
        <el-table-column prop="jiage" label="价格" width="80" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.jiage }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="tianshu" label="天数" width="80" align="center" />
        <el-table-column prop="xianluxiangqing" label="详情" show-overflow-tooltip />
        <el-table-column label="操作" width="150" fixed="right">
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
        width="60%"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="线路名称" prop="xianlumingcheng">
            <el-input v-model="form.xianlumingcheng" />
          </el-form-item>
          <el-form-item label="出发地" prop="chufadi">
            <el-input v-model="form.chufadi" />
          </el-form-item>
          <el-form-item label="途经地" prop="tujingdi">
            <el-input v-model="form.tujingdi" />
          </el-form-item>
          <el-form-item label="图片" prop="tupian">
            <el-upload
              class="avatar-uploader"
              :action="`${baseUrl}/travel/upload`"
              :data="{ type: 'route' }"
              :show-file-list="false"
              :on-success="handleImageSuccess"
              :before-upload="beforeImageUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="价格" prop="jiage">
            <el-input-number v-model="form.jiage" :precision="2" :step="0.1" />
          </el-form-item>
          <el-form-item label="天数" prop="tianshu">
            <el-input-number v-model="form.tianshu" :min="1" :max="30" />
          </el-form-item>
          <el-form-item label="详情" prop="xianluxiangqing">
            <el-input
              type="textarea"
              v-model="form.xianluxiangqing"
              :rows="4"
            />
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
import { Plus } from '@element-plus/icons-vue'
import request from '@/utils/request'

const baseUrl = 'http://localhost:8081'
const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const imageUrl = ref('')

const formRef = ref(null)
const form = ref({
  xianlumingcheng: '',
  chufadi: '',
  tujingdi: '',
  tupian: '',
  jiage: 0,
  tianshu: 1,
  xianluxiangqing: ''
})

const rules = {
  xianlumingcheng: [{ required: true, message: '请输入线路名称', trigger: 'blur' }],
  chufadi: [{ required: true, message: '请输入出发地', trigger: 'blur' }],
  tujingdi: [{ required: true, message: '请输入途经地', trigger: 'blur' }],
  jiage: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  tianshu: [{ required: true, message: '请输入天数', trigger: 'blur' }]
}

// 获取线路列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/lvyouxianlu/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取线路列表失败:', error)
    ElMessage.error('获取线路列表失败')
  } finally {
    loading.value = false
  }
}

// 处理图片URL
const getImageUrl = (path) => {
  if (!path) return ''
  if (path.startsWith('http')) return path
  return `${baseUrl}${path}`
}

// 图片上传成功
const handleImageSuccess = (res) => {
  if (res.code === '200' || res.code === 200) {
    imageUrl.value = getImageUrl(res.data)
    form.value.tupian = res.data
  }
}

// 图片上传前的验证
const beforeImageUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2
  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
  }
  if (!isLt2M) {
    ElMessage.error('图片大小不能超过 2MB!')
  }
  return isImage && isLt2M
}

// 添加线路
const handleAdd = () => {
  dialogTitle.value = '添加线路'
  form.value = {
    xianlumingcheng: '',
    chufadi: '',
    tujingdi: '',
    tupian: '',
    jiage: 0,
    tianshu: 1,
    xianluxiangqing: ''
  }
  imageUrl.value = ''
  dialogVisible.value = true
}

// 编辑线路
const handleEdit = (row) => {
  dialogTitle.value = '编辑线路'
  form.value = { ...row }
  imageUrl.value = getImageUrl(row.tupian)
  dialogVisible.value = true
}

// 删除线路
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除线路 ${row.xianlumingcheng} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/lvyouxianlu/${row.id}`,
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
          ? `/travel/lvyouxianlu/${form.value.id}`
          : '/travel/lvyouxianlu'

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
.route-list {
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

.avatar-uploader {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader:hover {
  border-color: var(--el-color-primary);
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
  line-height: 178px;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
