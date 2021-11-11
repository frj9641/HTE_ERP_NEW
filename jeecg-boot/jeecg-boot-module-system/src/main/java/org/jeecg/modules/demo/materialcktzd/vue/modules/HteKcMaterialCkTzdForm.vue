<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="单据类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
              <j-dict-select-tag type="list" v-model="model.type" dictCode="ck_type" placeholder="请选择单据类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="入库单号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="rkDjh">
              <j-search-select-tag v-model="model.rkDjh" dict="hte_kc_material_rk,djh,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="单据号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="djh">
              <a-input v-model="model.djh" placeholder="请输入单据号" disabled ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="厂站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="siteId">
              <j-dict-select-tag type="list" v-model="model.siteId" dictCode="sys_depart,depart_name,org_code" placeholder="请选择厂站" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="物料" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="materialId">
              <j-dict-select-tag type="list" v-model="model.materialId" dictCode="hte_kc_material,material,id" placeholder="请选择物料" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="供应商" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="supplyId">
              <j-dict-select-tag type="list" v-model="model.supplyId" dictCode="hte_supply,supply_name,id" placeholder="请选择供应商" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量(千克)" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="slKg">
              <a-input-number v-model="model.slKg" placeholder="请输入数量(千克)" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="审核标识" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="checkFlag">
              <j-dict-select-tag type="list" v-model="model.checkFlag" dictCode="check_flag" placeholder="请选择审核标识" disabled/>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="操作员" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="creater">
              <a-input v-model="model.creater" placeholder="请输入操作员" disabled ></a-input>
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
    name: 'HteKcMaterialCkTzdForm',
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
            type:0,
            checkFlag:1,
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
          add: "/materialcktzd/hteKcMaterialCkTzd/add",
          edit: "/materialcktzd/hteKcMaterialCkTzd/edit",
          queryById: "/materialcktzd/hteKcMaterialCkTzd/queryById"
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