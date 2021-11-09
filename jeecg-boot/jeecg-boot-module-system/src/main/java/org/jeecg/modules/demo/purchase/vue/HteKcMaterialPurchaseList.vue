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
      <a-button type="primary" icon="download" @click="handleExportXls('采购表')">导出</a-button>
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

    <hte-kc-material-purchase-modal ref="modalForm" @ok="modalFormOk"></hte-kc-material-purchase-modal>
  </a-card>
</template>

<script>

  import '@/assets/less/TableExpand.less'
  import { mixinDevice } from '@/utils/mixin'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import HteKcMaterialPurchaseModal from './modules/HteKcMaterialPurchaseModal'
  import {filterMultiDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'HteKcMaterialPurchaseList',
    mixins:[JeecgListMixin, mixinDevice],
    components: {
      HteKcMaterialPurchaseModal
    },
    data () {
      return {
        description: '采购表管理页面',
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
            title:'创建日期',
            align:"center",
            dataIndex: 'createTime'
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
            title:'数量（吨）',
            align:"center",
            dataIndex: 'slT'
          },
          {
            title:'采购单价',
            align:"center",
            dataIndex: 'dj'
          },
          {
            title:'运费单价',
            align:"center",
            dataIndex: 'yfdj'
          },
          {
            title:'总金额',
            align:"center",
            dataIndex: 'zje'
          },
          {
            title:'采购员',
            align:"center",
            dataIndex: 'creater'
          },
          {
            title:'备注',
            align:"center",
            dataIndex: 'remark'
          },
          {
            title:'审核状态',
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
            title:'入库状态',
            align:"center",
            dataIndex: 'rkFlag_dictText'
          },
          {
            title:'采购方式',
            align:"center",
            dataIndex: 'purchaseWay_dictText'
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
          list: "/purchase/hteKcMaterialPurchase/list",
          delete: "/purchase/hteKcMaterialPurchase/delete",
          deleteBatch: "/purchase/hteKcMaterialPurchase/deleteBatch",
          exportXlsUrl: "/purchase/hteKcMaterialPurchase/exportXls",
          importExcelUrl: "purchase/hteKcMaterialPurchase/importExcel",
          
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
        fieldList.push({type:'datetime',value:'createTime',text:'创建日期'})
        fieldList.push({type:'string',value:'djh',text:'单据号',dictCode:''})
        fieldList.push({type:'sel_search',value:'siteId',text:'厂站',dictTable:'hte_site', dictText:'site_name', dictCode:'id'})
        fieldList.push({type:'sel_search',value:'materialId',text:'物料',dictTable:'hte_kc_material', dictText:'material', dictCode:'id'})
        fieldList.push({type:'sel_search',value:'supplyId',text:'供应商',dictTable:'hte_supply', dictText:'supply_name', dictCode:'id'})
        fieldList.push({type:'double',value:'slT',text:'数量（吨）',dictCode:''})
        fieldList.push({type:'double',value:'dj',text:'采购单价',dictCode:''})
        fieldList.push({type:'double',value:'yfdj',text:'运费单价',dictCode:''})
        fieldList.push({type:'string',value:'zje',text:'总金额',dictCode:''})
        fieldList.push({type:'string',value:'creater',text:'采购员',dictCode:''})
        fieldList.push({type:'string',value:'remark',text:'备注',dictCode:''})
        fieldList.push({type:'int',value:'checkFlag',text:'审核状态',dictCode:'check_flag'})
        fieldList.push({type:'date',value:'checkDate',text:'审核日期'})
        fieldList.push({type:'int',value:'rkFlag',text:'入库状态',dictCode:'rk_flag'})
        fieldList.push({type:'int',value:'purchaseWay',text:'采购方式',dictCode:'purchase_way'})
        this.superFieldList = fieldList
      }
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less';
</style>