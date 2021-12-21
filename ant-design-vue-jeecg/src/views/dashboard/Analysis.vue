<template>
  <div>
    <!-- <index-chart v-if="indexStyle==1"></index-chart>
    <index-bdc v-if="indexStyle==2"></index-bdc>
    <index-task v-if="indexStyle==3"></index-task>
    <div style="width: 100%;text-align: right;margin-top: 20px">
      请选择首页样式：
      <a-radio-group v-model="indexStyle">
        <a-radio :value="1">统计图表</a-radio>
        <a-radio :value="2">统计图表2</a-radio>
        <a-radio :value="3">任务表格</a-radio>
      </a-radio-group>
    </div> -->
    <iframe
      frameborder="0"
      height="800"
      width="100%"
      src="http://172.16.2.21:8075/webroot/decision/view/form?viewlet=%25E8%25BF%2590%25E8%2590%25A5%25E7%25AE%25A1%25E7%2590%2586%2B-%2B%25E5%2589%25AF%25E6%259C%25AC.frm&ref_t=design&ref_c=77ff813b-48de-4fba-9326-111b59a5ab54"
    />
  </div>
</template>

<script>
import IndexChart from './IndexChart'
import IndexTask from './IndexTask'
import IndexBdc from './IndexBdc'

export default {
  name: 'Analysis',
  components: {
    IndexChart,
    IndexTask,
    IndexBdc,
  },
  data() {
    return {
      indexStyle: 1,
    }
  },
  created() {},
  methods: {},
}
</script>