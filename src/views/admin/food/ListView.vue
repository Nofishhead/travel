<template>
  <div class="food-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>美食管理</span>
          <el-button type="primary" @click="handleAdd">添加美食</el-button>
        </div>
      </template>

      <!-- 美食列表表格 -->
      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="meishibianhao" label="编号" width="120" />
        <el-table-column prop="mingcheng" label="名称" width="130" />
        <el-table-column prop="fujinjingdian" label="附近景点" width="130" />
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
        <el-table-column prop="jiage" label="价格" width="80" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.jiage }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="meishijianjie" label="简介" show-overflow-tooltip />
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
          <el-form-item label="美食名称" prop="mingcheng">
            <el-input v-model="form.mingcheng" />
          </el-form-item>
          <el-form-item label="附近景点" prop="fujinjingdian">
            <el-input v-model="form.fujinjingdian" />
          </el-form-item>
          <el-form-item label="分类" prop="fenlei">
            <el-select v-model="form.fenlei" placeholder="请选择分类">
              <el-option
                v-for="item in categories"
                :key="item.id"
                :label="item.fenleimingcheng"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图片" prop="tupian">
            <el-upload
              class="avatar-uploader"
              :action="`${baseUrl}/travel/upload`"
              :data="{ type: 'food' }"
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
          <el-form-item label="简介" prop="meishijianjie">
            <el-input
              type="textarea"
              v-model="form.meishijianjie"
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
const categories = ref([])

const formRef = ref(null)
const form = ref({
  mingcheng: '',
  fujinjingdian: '',
  fenlei: '',
  tupian: '',
  jiage: 0,
  meishijianjie: ''
})

// 获取美食列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/difangmeishi/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取美食列表失败:', error)
    ElMessage.error('获取美食列表失败')
  } finally {
    loading.value = false
  }
}

// 获取分类列表
const getCategories = async () => {
  try {
    const res = await request({
      url: '/travel/meishifenlei/list',
      method: 'get'
    })
    categories.value = res.data || []
  } catch (error) {
    console.error('获取分类列表失败:', error)
  }
}

// 处理图片URL
const getImageUrl = (path) => {
  if (!path) return ''
  if (path.startsWith('http')) return path
  // 移除可能的重复路径
  const cleanPath = path.replace(/^uploads\//, '')
  return `${baseUrl}/uploads/${cleanPath}`
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

// 添加美食
const handleAdd = () => {
  dialogTitle.value = '添加美食'
  form.value = {
    mingcheng: '',
    fujinjingdian: '',
    fenlei: '',
    tupian: '',
    jiage: 0,
    meishijianjie: ''
  }
  imageUrl.value = ''
  dialogVisible.value = true
}

// 编辑美食
const handleEdit = (row) => {
  dialogTitle.value = '编辑美食'
  form.value = { ...row }
  imageUrl.value = getImageUrl(row.tupian)
  dialogVisible.value = true
}

// 删除美食
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除美食 ${row.mingcheng} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/difangmeishi/${row.id}`,
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
          ? `/travel/difangmeishi/${form.value.id}`
          : '/travel/difangmeishi'

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
  getCategories()
})
</script>

<style scoped>
.food-list {
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
