<template>
    <div>
        <el-row :gutter="20" style="margin-bottom:10px">
            <el-col :span="6">
                <el-input @keydown.enter.native="getDataList" placeholder="请输入员工名、工号进行搜索，可以直接回车搜索..." v-model="queryinfo.query"
                          clearable @clear="getDataList">
                    <el-button slot="append" icon="el-icon-search" @click="getDataList"></el-button>
                </el-input>
            </el-col>
            <el-col :span="4">
                <el-button type="primary" @click="showAdvanceSearchView = !showAdvanceSearchView">
                    <i :class="showAdvanceSearchView?'fa fa-angle-double-up':'fa fa-angle-double-down'"
                       aria-hidden="true"></i>
                    高级搜索
                </el-button>
            </el-col>

        </el-row>

        <transition name="el-zoom-in-top">
            <div v-show="showAdvanceSearchView" class="transition-box">
                <el-form :inline="true" :model="formInline" class="demo-form-inline">
                    <el-form-item label="审批人">
                        <el-input v-model="formInline.user" placeholder="审批人"></el-input>
                    </el-form-item>
                </el-form>
            </div>
        </transition>

    </div>
</template>

<script>
    export default {
        name: "StaRecord",
        data(){
            return {
                queryinfo: {
                    query: '',
                    pagenum: 1,
                    pageSize: 5,

                },
                showAdvanceSearchView:false,
                formInline:{}


            }
        }
    }
</script>

<style scoped>

</style>
