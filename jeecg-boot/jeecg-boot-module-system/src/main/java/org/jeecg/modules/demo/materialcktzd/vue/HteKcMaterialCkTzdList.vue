<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('出库_调整单表')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <!-- 高级查询区域 -->
      <j-super-query :fieldList="superFieldList" ref="superQueryModal" @handleSuperQuery="handleSuperQuery"></j-super-query>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        :scroll="{x:true}"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        class="j-table-force-nowrap"
        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="downloadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a @click="handleDetail(record)">详情</a>
              </a-menu-item>
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <hte-kc-material-ck-tzd-modal ref="modalForm" @ok="modalFormOk"></hte-kc-material-ck-tzd-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import HteKcMaterialCkTzdModal from './modules/HteKcMaterialCkTzdModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'HteKcMaterialCkTzdList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      HteKcMaterialCkTzdModal
    },
    data () {
      return {
        description: '出库_调整单表管理页面',
        // 表头
        columns: [
          {
            title: '#',
            dataIndex: '',
            key:'rowIndex',
            width:60,
            align:"center",
            customRender:function (t,r,index) {
              return parseInt(index)+1;
            }
          },
          {
            title:'单据类型',
            align:"center",
            dataIndex: 'type_dictText'
          },
          {
            title:'入库单号',
            align:"center",
            dataIndex: 'rkDjh_dictText'
          },
          {
            title:'单据号',
            align:"center",
            dataIndex: 'djh'
          },
          {
            title:'厂站',
            align:"center",
            dataIndex: 'siteId_dictText'
          },
          {
            title:'物料',
            align:"center",
            dataIndex: 'materialId_dictText'
          },
          {
            title:'供应商',
            align:"center",
            dataIndex: 'supplyId_dictText'
          },
          {
            title:'数量(千克)',
            align:"center",
            dataIndex: 'slKg'
          },
          {
            title:'审核标识',
            align:"center",
            dataIndex: 'checkFlag_dictText'
          },
          {
            title:'审核日期',
            align:"center",
            dataIndex: 'checkDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'操作员',
            align:"center",
            dataIndex: 'creater'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            fixed:"right",
            width:147,
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/materialcktzd/hteKcMaterialCkTzd/list",
          delete: "/materialcktzd/hteKcMaterialCkTzd/delete",
          deleteBatch: "/materialcktzd/hteKcMaterialCkTzd/deleteBatch",
          exportXlsUrl: "/materialcktzd/hteKcMaterialCkTzd/exportXls",
          importExcelUrl: "materialcktzd/hteKcMaterialCkTzd/importExcel",
          
        },
        dictOptions:{},
        superFieldList:[],
      }
    },
    created() {
    this.getSuperFieldList();
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      },
    },
    methods: {
      initDictConfig(){
      },
      getSuperFieldList(){
        let fieldList=[];
        fieldList.push({type:'int',value:'type',text:'单据类型',dictCode:'ck_type'})
        fieldList.push({type:'sel_search',value:'rkDjh',text:'入库单号',dictTable:'hte_kc_material_rk', dictText:'djh', dictCode:'id'})
        fieldList.push({type:'string',value:'djh',text:'单据号',dictCode:''})
        fieldList.push({type:'string',value:'siteId',text:'厂站',dictCode:'sys_depart,depart_name,org_code'})
        fieldList.push({type:'string',value:'materialId',text:'物料',dictCode:'hte_kc_material,material,id'})
        fieldList.push({type:'string',value:'supplyId',text:'供应商',dictCode:'hte_supply,supply_name,id'})
        fieldList.push({type:'double',value:'slKg',text:'数量(千克)',dictCode:''})
        fieldList.push({type:'int',value:'checkFlag',text:'审核标识',dictCode:'check_flag'})
        fieldList.push({type:'date',value:'checkDate',text:'审核日期'})
        fieldList.push({type:'string',value:'creater',text:'操作员',dictCode:''})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>