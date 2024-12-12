<template>
  <div class="dashboard">
    <el-row :gutter="20">
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>总访问量</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">1,234</div>
            <div class="text">较昨日增长 12%</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>用户数量</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">566</div>
            <div class="text">较昨日增长 5%</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>景点数量</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">89</div>
            <div class="text">本周新增 3 个</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover">
          <template #header>
            <div class="card-header">
              <span>评论数量</span>
            </div>
          </template>
          <div class="card-body">
            <div class="number">2,431</div>
            <div class="text">本周新增 156 条</div>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
// 这里可以添加获取统计数据的逻辑
</script>

<style scoped>
.dashboard {
  .el-card {
    margin-bottom: 20px;
  }

  .card-header {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .card-body {
    text-align: center;

    .number {
      font-size: 24px;
      font-weight: bold;
      margin: 10px 0;
    }

    .text {
      color: #666;
      font-size: 14px;
    }
  }
}
</style>
