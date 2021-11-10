<template>
  <a-spin :spinning="confirmLoading">
    <j-form-container :disabled="formDisabled">
      <a-form-model ref="form" :model="model" :rules="validatorRules" slot="detail">
        <a-row>
          <a-col :span="24">
            <a-form-model-item label="单据号" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="djh">
              <a-input v-model="model.djh" placeholder="请输入单据号" disabled ></a-input>
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="厂站" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="siteId">
              <j-search-select-tag v-model="model.siteId" dict="sys_depart,depart_name,org_code"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="产出品" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="productId">
              <j-search-select-tag v-model="model.productId" dict="hte_product,product_name,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="采样口" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="collectId">
              <j-search-select-tag v-model="model.collectId" dict="hte_collect_point,collect_point,id"  />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="数量（吨）" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="slT">
              <a-input-number v-model="model.slT" placeholder="请输入数量（吨）" style="width: 100%" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="调整日期" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="tzDate">
              <j-date placeholder="请选择调整日期" v-model="model.tzDate"  style="width: 100%" />
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
            <a-form-model-item label="类型" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="type">
              <j-dict-select-tag type="list" v-model="model.type" dictCode="produce_type" placeholder="请选择类型" />
            </a-form-model-item>
          </a-col>
          <a-col :span="24">
            <a-form-model-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol" prop="creater">
              <a-input v-model="model.creater" placeholder="请输入创建人" disabled ></a-input>
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
    name: 'HteCkProductForm',
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
          add: "/ckproduct/hteCkProduct/add",
          edit: "/ckproduct/hteCkProduct/edit",
          queryById: "/ckproduct/hteCkProduct/queryById"
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