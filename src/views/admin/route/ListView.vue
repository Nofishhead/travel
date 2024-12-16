<template>
  <div class="route-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>线路列表</span>
          <el-button type="primary" @click="handleAdd">添加线路</el-button>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="xianlubianhao" label="线路编号" width="120" />
        <el-table-column prop="xianlumingcheng" label="线路名称" width="180" />
        <el-table-column prop="tupian" label="图片" width="120">
          <template #default="scope">
            <el-image
              style="width: 80px; height: 50px"
              :src="getImageUrl(scope.row.tupian.split(',')[0])"
              fit="cover"
              :preview-src-list="scope.row.tupian.split(',').map(img => getImageUrl(img))"
            />
          </template>
        </el-table-column>
        <el-table-column prop="chufadi" label="出发地" width="100" />
        <el-table-column prop="tujingdi" label="途经地" width="150" />
        <el-table-column prop="zhongdian" label="终点" width="100" />
        <el-table-column prop="jiage" label="价格" width="100" align="center">
          <template #default="scope">
            <span class="price">{{ scope.row.jiage }}元</span>
          </template>
        </el-table-column>
        <el-table-column prop="liulanliang" label="浏览量" width="80" align="center" />
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
          <el-form-item label="线路名称" prop="xianlumingcheng">
            <el-input v-model="form.xianlumingcheng" />
          </el-form-item>
          <el-form-item label="图片" prop="tupian">
            <el-upload
              class="avatar-uploader"
              :action="`${baseUrl}/upload`"
              :headers="{ token: token }"
              :show-file-list="true"
              :on-success="handleImageSuccess"
              :before-upload="beforeImageUpload"
              multiple
            >
              <el-button type="primary">点击上传</el-button>
            </el-upload>
          </el-form-item>
          <el-form-item label="出发地" prop="chufadi">
            <el-input v-model="form.chufadi" />
          </el-form-item>
          <el-form-item label="途经地" prop="tujingdi">
            <el-input v-model="form.tujingdi" />
          </el-form-item>
          <el-form-item label="终点" prop="zhongdian">
            <el-input v-model="form.zhongdian" />
          </el-form-item>
          <el-form-item label="价格" prop="jiage">
            <el-input-number v-model="form.jiage" :precision="2" :step="10" />
          </el-form-item>
          <el-form-item label="线路特色" prop="xianlutese">
            <el-input
              type="textarea"
              v-model="form.xianlutese"
              :rows="4"
            />
          </el-form-item>
          <el-form-item label="线路简介" prop="xianlujianjie">
            <el-input
              type="textarea"
              v-model="form.xianlujianjie"
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
import request from '@/utils/request'

const baseUrl = 'http://localhost:8080'
const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const dialogTitle = ref('')
const token = localStorage.getItem('token')

const formRef = ref(null)
const form = ref({
  xianlumingcheng: '',
  tupian: '',
  chufadi: '',
  tujingdi: '',
  zhongdian: '',
  jiage: 0,
  xianlutese: '',
  xianlujianjie: ''
})

const rules = {
  xianlumingcheng: [{ required: true, message: '请输入线路名称', trigger: 'blur' }],
  chufadi: [{ required: true, message: '请输入出发地', trigger: 'blur' }],
  zhongdian: [{ required: true, message: '请输入终点', trigger: 'blur' }],
  jiage: [{ required: true, message: '请输入价格', trigger: 'blur' }],
  xianlutese: [{ required: true, message: '请输入线路特色', trigger: 'blur' }],
  xianlujianjie: [{ required: true, message: '请输入线路简介', trigger: 'blur' }]
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
    if (form.value.tupian) {
      form.value.tupian += ',' + res.data
    } else {
      form.value.tupian = res.data
    }
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

// 添加线路
const handleAdd = () => {
  dialogTitle.value = '添加线路'
  form.value = {
    xianlumingcheng: '',
    tupian: '',
    chufadi: '',
    tujingdi: '',
    zhongdian: '',
    jiage: 0,
    xianlutese: '',
    xianlujianjie: ''
  }
  dialogVisible.value = true
}

// 编辑线路
const handleEdit = (row) => {
  dialogTitle.value = '编辑线路'
  form.value = { ...row }
  dialogVisible.value = true
}

// 删除线路
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确定要删除这条线路吗？',
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
        if (form.value.id) {
          await request({
            url: `/travel/lvyouxianlu/${form.value.id}`,
            method: 'put',
            data: form.value
          })
          ElMessage.success('修改成功')
        } else {
          await request({
            url: '/travel/lvyouxianlu',
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
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>
