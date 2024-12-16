<template>
  <div class="message-list">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>留言管理</span>
        </div>
      </template>

      <el-table :data="tableData" style="width: 100%" v-loading="loading">
        <el-table-column prop="id" label="ID" width="60" />
        <el-table-column prop="xingming" label="姓名" width="120" />
        <el-table-column prop="lianxidianhua" label="联系电话" width="120" />
        <el-table-column prop="liuyanneirong" label="留言内容" min-width="200">
          <template #default="scope">
            <el-tooltip
              :content="scope.row.liuyanneirong"
              placement="top"
              :hide-after="0"
            >
              <span>{{ scope.row.liuyanneirong }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="liuyanren" label="留言人" width="100" />
        <el-table-column prop="huifuneirong" label="回复内容" min-width="200">
          <template #default="scope">
            <el-tooltip
              v-if="scope.row.huifuneirong"
              :content="scope.row.huifuneirong"
              placement="top"
              :hide-after="0"
            >
              <span>{{ scope.row.huifuneirong }}</span>
            </el-tooltip>
            <span v-else class="no-reply">未回复</span>
          </template>
        </el-table-column>
        <el-table-column prop="addtime" label="留言时间" width="160" />
        <el-table-column label="操作" width="180" fixed="right">
          <template #default="scope">
            <el-button
              size="small"
              type="primary"
              link
              @click="handleReply(scope.row)"
              :disabled="!!scope.row.huifuneirong"
            >
              回复
            </el-button>
            <el-button size="small" type="danger" link @click="handleDelete(scope.row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 回复对话框 -->
      <el-dialog
        title="回复留言"
        v-model="dialogVisible"
        width="50%"
      >
        <el-form
          :model="form"
          :rules="rules"
          ref="formRef"
          label-width="100px"
        >
          <el-form-item label="留言内容">
            <div class="message-content">{{ currentMessage?.liuyanneirong }}</div>
          </el-form-item>
          <el-form-item label="回复内容" prop="huifuneirong">
            <el-input
              type="textarea"
              v-model="form.huifuneirong"
              :rows="4"
              placeholder="请输入回复内容"
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

const loading = ref(false)
const tableData = ref([])
const dialogVisible = ref(false)
const currentMessage = ref(null)

const formRef = ref(null)
const form = ref({
  huifuneirong: ''
})

const rules = {
  huifuneirong: [{ required: true, message: '请输入回复内容', trigger: 'blur' }]
}

// 获取留言列表
const getList = async () => {
  loading.value = true
  try {
    const res = await request({
      url: '/travel/liuyanban/list',
      method: 'get'
    })
    tableData.value = res.data || []
  } catch (error) {
    ElMessage.error('获取留言列表失败')
  } finally {
    loading.value = false
  }
}

// 回复留言
const handleReply = (row) => {
  currentMessage.value = row
  form.value = {
    huifuneirong: ''
  }
  dialogVisible.value = true
}

// 删除留言
const handleDelete = (row) => {
  ElMessageBox.confirm(
    '确定要删除这条留言吗？',
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    }
  ).then(async () => {
    try {
      await request({
        url: `/travel/liuyanban/${row.id}`,
        method: 'delete'
      })
      ElMessage.success('删除成功')
      getList()
    } catch (error) {
      ElMessage.error('删除失败')
    }
  })
}

// 提交回复
const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await request({
          url: `/travel/liuyanban/reply/${currentMessage.value.id}`,
          method: 'put',
          data: form.value
        })
        ElMessage.success('回复成功')
        dialogVisible.value = false
        getList()
      } catch (error) {
        ElMessage.error('回复失败')
      }
    }
  })
}

onMounted(() => {
  getList()
})
</script>

<style scoped>
.message-list {
  padding: 0 15px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.message-content {
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
  min-height: 60px;
}

.no-reply {
  color: #909399;
  font-style: italic;
}

:deep(.el-table .cell) {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
</style>
