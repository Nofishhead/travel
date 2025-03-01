<template>
  <div class="scenic-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>景点管理</span>
          <el-button type="primary" @click="handleAdd">添加景点</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="jingdianbianhao" label="编号" width="120" />
        <el-table-column prop="jingdianmingcheng" label="名称" width="130" />
        <el-table-column prop="suoshudiquName" label="地区" width="90" />
        <el-table-column prop="tupian" label="图片" width="100">
          <template #default="scope">
            <el-image
              style="width: 70px; height: 50px"
              :src="getImageUrl(scope.row.tupian)"
              fit="cover"
              :preview-src-list="[getImageUrl(scope.row.tupian)]"
            >
              <template #error>
                <el-image
                  src="/uploads/scenic/default.jpg"
                  style="width: 50px; height: 38px"
                />
              </template>
            </el-image>
          </template>
        </el-table-column>
        <el-table-column prop="kaifangshijian" label="开放时间" width="100" />
        <el-table-column prop="dizhi" label="地址" min-width="150" show-overflow-tooltip />
        <el-table-column prop="piaojia" label="票价" width="80" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.piaojia }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="liulanliang" label="浏览量" width="70" align="center" />
        <el-table-column label="操作" width="120" fixed="right">
          <template #default="scope">
            <el-button size="small" type="primary" link @click="handleEdit(scope.row)">编辑</el-button>
            <el-button
              size="small"
              type="danger"
              link
              @click="handleDelete(scope.row)"
            >
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
        :before-close="handleClose"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="景点名称" prop="jingdianmingcheng">
            <el-input v-model="form.jingdianmingcheng" />
          </el-form-item>
          <el-form-item label="所属地区" prop="suoshudiqu">
            <el-input v-model="form.suoshudiqu" placeholder="请输入地区编号" />
          </el-form-item>
          <el-form-item label="景点图片" prop="tupian">
            <el-upload
              class="avatar-uploader"
              :action="`${baseUrl}/travel/upload`"
              :data="{ type: 'scenic' }"
              :show-file-list="false"
              :on-success="handleImageSuccess"
              :before-upload="beforeImageUpload"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
            </el-upload>
          </el-form-item>
          <el-form-item label="开放时间" prop="kaifangshijian">
            <el-input v-model="form.kaifangshijian" />
          </el-form-item>
          <el-form-item label="地址" prop="dizhi">
            <el-input v-model="form.dizhi" />
          </el-form-item>
          <el-form-item label="票价" prop="piaojia">
            <el-input-number v-model="form.piaojia" :precision="2" :step="0.1" />
          </el-form-item>
          <el-form-item label="景点描述" prop="miaoshu">
            <el-input
              type="textarea"
              v-model="form.miaoshu"
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
  jingdianmingcheng: '',
  suoshudiqu: '',
  tupian: '',
  kaifangshijian: '',
  dizhi: '',
  piaojia: 0,
  miaoshu: ''
})

const rules = {
  jingdianmingcheng: [
    { required: true, message: '请输入景点名称', trigger: 'blur' }
  ],
  suoshudiqu: [
    { required: true, message: '请选择所属地区', trigger: 'change' }
  ]
}

// 获取景点列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/jingdianxinxi/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    console.error('获取景点列表失败:', error)
    ElMessage.error('获取景点列表失败')
  } finally {
    loading.value = false
  }
}

// 处理图片URL
const getImageUrl = (path) => {
  if (!path) return ''
  if (path.startsWith('http')) return path
  // 移除重复的路径前缀
  const cleanPath = path.replace(/^uploads\/scenic\//, '')
  return `${baseUrl}/uploads/scenic/${cleanPath}`
}

// 图片上传成功
const handleImageSuccess = (res) => {
  if (res.code === '200' || res.code === 200) {
    const imagePath = res.data;
    imageUrl.value = getImageUrl(imagePath);
    form.value.tupian = imagePath;
    console.log('上传成功，图片路径:', imagePath);
  } else {
    ElMessage.error('图片上传失败');
  }
}

// 图片上传前的验证
const beforeImageUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  const isLt2M = file.size / 1024 / 1024 < 2

  if (!isImage) {
    ElMessage.error('上传文件只能是图片格式!')
  }
  if (!isLt2M) {
    ElMessage.error('上传图片大小不能超过 2MB!')
  }
  return isImage && isLt2M
}

// 添加景点
const handleAdd = () => {
  dialogTitle.value = '添加景点'
  form.value = {
    jingdianmingcheng: '',
    suoshudiqu: '',
    tupian: '',
    kaifangshijian: '',
    dizhi: '',
    piaojia: 0,
    miaoshu: ''
  }
  imageUrl.value = ''
  dialogVisible.value = true
}

// 编辑景点
const handleEdit = (row) => {
  dialogTitle.value = '编辑景点'
  form.value = { ...row }
  imageUrl.value = getImageUrl(row.tupian)
  dialogVisible.value = true
}

// 删除景点
const handleDelete = (row) => {
  ElMessageBox.confirm(
    `确定要删除景点 ${row.jingdianmingcheng} 吗？`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/jingdianxinxi/${row.id}`,
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
          ? `/travel/jingdianxinxi/${form.value.id}`
          : '/travel/jingdianxinxi'

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
.scenic-list {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

/* 整表格内容的样式 */
:deep(.el-table) {
  font-size: 14px;
}

:deep(.el-table td) {
  padding: 6px 0;
}

/* 票价样式 */
.price {
  color: #f56c6c;
  font-weight: bold;
  font-size: 13px;
}

/* 数字列的样式 */
:deep(.el-table .cell) {
  padding: 0 4px;
}

/* 添加表格行hover效果 */
:deep(.el-table tr:hover) {
  background-color: #f5f7fa;
}

/* 调整按钮间距 */
:deep(.el-button + .el-button) {
  margin-left: 8px;
}

/* 调整表格内字体大小 */
:deep(.el-table) {
  font-size: 13px;
}

.avatar-uploader {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
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
