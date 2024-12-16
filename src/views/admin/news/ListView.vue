<template>
  <div class="news-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>新闻列表</span>
          <el-button type="primary" @click="handleAdd">添加新闻</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="biaoti" label="标题" width="200" />
        <el-table-column prop="fenlei" label="分类" width="120">
          <template #default="scope">
            {{ getFenleiName(scope.row.fenlei) }}
          </template>
        </el-table-column>
        <el-table-column prop="tupian" label="图片" width="120">
          <template #default="scope">
            <el-image
              style="width: 80px; height: 50px"
              :src="getImageUrl(scope.row.tupian)"
              fit="cover"
              :preview-src-list="[getImageUrl(scope.row.tupian)]"
            />
          </template>
        </el-table-column>
        <el-table-column prop="tianjiaren" label="添加人" width="120" />
        <el-table-column prop="dianjilv" label="点击率" width="80" align="center" />
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
          <el-form-item label="标题" prop="biaoti">
            <el-input v-model="form.biaoti" />
          </el-form-item>
          <el-form-item label="分类" prop="fenlei">
            <el-select v-model="form.fenlei" placeholder="请选择分类">
              <el-option
                v-for="item in fenleiOptions"
                :key="item.id"
                :label="item.fenleimingcheng"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="图片" prop="tupian">
            <el-upload
              class="avatar-uploader"
              :action="`${baseUrl}/upload`"
              :headers="{ token: token }"
              :show-file-list="true"
              :on-success="handleImageSuccess"
              :before-upload="beforeImageUpload"
            >
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="内容" prop="neirong">
            <el-input
              type="textarea"
              v-model="form.neirong"
              :rows="6"
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
import request from '@/utils/request'

const baseUrl = 'http://localhost:8080'
const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const token = localStorage.getItem('token')
const fenleiOptions = ref([])

const formRef = ref(null)
const form = ref({
  biaoti: '',
  fenlei: '',
  tupian: '',
  neirong: '',
  tianjiaren: localStorage.getItem('username') || ''
})

const rules = {
  biaoti: [{ required: true, message: '请输入标题', trigger: 'blur' }],
  fenlei: [{ required: true, message: '请选择分类', trigger: 'change' }],
  neirong: [{ required: true, message: '请输入内容', trigger: 'blur' }]
}

// 获取新闻列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/xinwenxinxi/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    ElMessage.error('获取新闻列表失败')
  } finally {
    loading.value = false
  }
}

// 获取分类列表
const getFenleiList = async () => {
  try {
    const res = await request({
      url: '/travel/xinwenfenlei/list',
      method: 'get'
    })
    fenleiOptions.value = res.data || []
  } catch (error) {
    ElMessage.error('获取分类列表失败')
  }
}

// 获取分类名称
const getFenleiName = (fenleiId) => {
  const fenlei = fenleiOptions.value.find(item => item.id === fenleiId)
  return fenlei ? fenlei.fenleimingcheng : ''
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
    form.value.tupian = res.data
  }
}

// 图片上传前的验证
const beforeImageUpload = (file) => {
  const isImage = file.type.startsWith('image/')
  if (!isImage) {
    ElMessage.error('只能上传图片文件!')
    return false
  }
  return true
}

// 添加新闻
const handleAdd = () => {
  dialogTitle.value = '添加新闻'
  form.value = {
    biaoti: '',
    fenlei: '',
    tupian: '',
    neirong: '',
    tianjiaren: localStorage.getItem('username') || ''
  }
  dialogVisible.value = true
}

// 编辑新闻
const handleEdit = (row) => {
  dialogTitle.value = '编辑新闻'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除新闻
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确定要删除这条新闻吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/xinwenxinxi/${row.id}`,
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
        if (form.value.id) {
          await request({
            url: `/travel/xinwenxinxi/${form.value.id}`,
            method: 'put',
            data: form.value
          })
          ElMessage.success('修改成功')
        } else {
          await request({
            url: '/travel/xinwenxinxi',
            method: 'post',
            data: form.value
          })
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        getList()
      } catch (error) {
        ElMessage.error(form.value.id ? '修改失败' : '添加失败')
      }
    }
  })
}

onMounted(() => {
  getList()
  getFenleiList()
})
</script>

<style scoped>
.news-list {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.avatar-uploader {
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
