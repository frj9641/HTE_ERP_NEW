<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="单据号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="djh">
              <a-input v-model="model.djh" placeholder="请输入单据号"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="厂站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="siteId">
              <j-search-select-tag v-model="model.siteId" dict="hte_site,site_name,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="物料" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="materialId">
              <j-search-select-tag v-model="model.materialId" dict="hte_kc_material,material,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="supplyId">
              <j-search-select-tag v-model="model.supplyId" dict="hte_supply,supply_name,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量（吨）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="slT">
              <a-input-number v-model="model.slT" placeholder="请输入数量（吨）" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="采购单价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="dj">
              <a-input-number v-model="model.dj" placeholder="请输入采购单价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="运费单价" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="yfdj">
              <a-input-number v-model="model.yfdj" placeholder="请输入运费单价" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="zje">
              <a-input v-model="model.zje" placeholder="请输入总金额"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="采购员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="creater">
              <a-input v-model="model.creater" placeholder="请输入采购员" disabled ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="remark">
              <a-input v-model="model.remark" placeholder="请输入备注"  ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkFlag">
              <j-dict-select-tag type="list" v-model="model.checkFlag" dictCode="check_flag" placeholder="请选择审核状态" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkDate">
              <j-date placeholder="请选择审核日期" v-model="model.checkDate"  style="width: 100%" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="入库状态" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="rkFlag">
              <j-dict-select-tag type="list" v-model="model.rkFlag" dictCode="rk_flag" placeholder="请选择入库状态" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="采购方式" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="purchaseWay">
              <j-dict-select-tag type="list" v-model="model.purchaseWay" dictCode="purchase_way" placeholder="请选择采购方式" />
            </a-form-model-item>
          </a-col>
        </a-row>
      </a-form-model>
    </j-form-container>
  </a-spin>
</template>

<script>

  import { httpAction, getAction } from '@/api/manage'
  import { validateDuplicateValue } from '@/utils/util'

  export default {
    name: 'HteKcMaterialPurchaseForm',
    components: {
    },
    props: {
      //表单禁用
      disabled: {
        type: Boolean,
        default: false,
        required: false
      }
    },
    data () {
      return {
        model:{
            checkFlag:1,
            rkFlag:0,
         },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        confirmLoading: false,
        validatorRules: {
        },
        url: {
          add: "/purchase/hteKcMaterialPurchase/add",
          edit: "/purchase/hteKcMaterialPurchase/edit",
          queryById: "/purchase/hteKcMaterialPurchase/queryById"
        }
      }
    },
    computed: {
      formDisabled(){
        return this.disabled
      },
    },
    created () {
       //备份model原始值
      this.modelDefault = JSON.parse(JSON.stringify(this.model));
    },
    methods: {
      add () {
        this.edit(this.modelDefault);
      },
      edit (record) {
        this.model = Object.assign({}, record);
        this.visible = true;
      },
      submitForm () {
        const that = this;
        // 触发表单验证
        this.$refs.form.validate(valid => {
          if (valid) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            httpAction(httpurl,this.model,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
            })
          }
         
        })
      },
    }
  }
</script>