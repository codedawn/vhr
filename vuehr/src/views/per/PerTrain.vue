<template>
  <div>

    <el-row :gutter="20"  style="margin-bottom:10px">
      <el-col :span="6">
        <el-input @keydown.enter.native="getDataList" placeholder="请输入员工名、工号进行搜索，可以直接回车搜索..." v-model="queryinfo.query"
                  clearable @clear="getDataList">
          <el-button slot="append" icon="el-icon-search" @click="getDataList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="addEmployeeTrain">添加培训</el-button>
      </el-col>
    </el-row>

    <!--        添加培训的dialog-->
    <el-dialog

            :close-on-click-modal="false"
            title="添加培训"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose" modal>

      <el-form :rules="rules" ref="form" :model="addQuery" label-width="80px">

        <el-form-item label="员工姓名" prop="eid">
          <template>
            <el-select v-model="addQuery.eid" filterable placeholder="请选择">
              <el-option
                      v-for="item in employees"
                      :key="item.id"
                      :label="item.name+'('+item.workID+')'"
                      :value="item.id">
              </el-option>
            </el-select>
          </template>
        </el-form-item>


        <el-form-item label="奖罚日期" prop="traindate">
          <el-date-picker type="date" placeholder="选择日期" v-model="addQuery.traindate" value-format="yyyy-MM-dd"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>

        <el-form-item label="培训内容" prop="traincontent">
          <el-input v-model="addQuery.traincontent"></el-input>
        </el-form-item>

        <el-form-item label="备注" prop="remark">
          <el-input type="textarea" v-model="addQuery.remark"></el-input>
        </el-form-item>


      </el-form>

      <span slot="footer" class="dialog-footer">
    <el-button @click="handleClose">取 消</el-button>
    <el-button type="primary" @click="submitEmployee">确 定</el-button>
  </span>
    </el-dialog>

    <el-table
            border
            :data="tableData"
            style="width: 100%">
      <el-table-column type="index">

      </el-table-column>
      <el-table-column
              prop="traindate"
              label="日期"
              sortable
              width="180" align="center">
      </el-table-column>
      <el-table-column
              prop="employee.name"
              label="姓名"
              width="180" align="center">
      </el-table-column>

      <el-table-column
              prop="employee.workID"
              label="工号"
              width="180" align="center">
      </el-table-column>

      <el-table-column
              prop="employee.gender"
              label="性别"
              width="180" align="center">
      </el-table-column>

      <el-table-column
              prop="employee.department.name"
              label="所属部门"
              width="180" align="center">
      </el-table-column>

      <el-table-column
              prop="traincontent"
              label="培训内容" align="center">
      </el-table-column>

      <el-table-column
              label="备注" prop="remark" align="center">

      </el-table-column>


      <el-table-column label="操作" align="center" width="190px">
        <template slot-scope="scope">
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑</el-button>
          <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeEmployeeecById(scope.row.id)">删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>


    <!--    分页-->
    <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="queryinfo.pagenum"
            :page-sizes="[5, 20, 30, 40]"
            :page-size="queryinfo.pageSize"
            layout="total, sizes, prev, pager, next, jumper"
            :total="total">
    </el-pagination>
  </div>
</template>

<script>
    export default {
        name: "PerTrain",
        data() {
            return {
                tableData: [],
                queryinfo: {
                    query: '',
                    pagenum: 1,
                    pageSize: 5,
                },
                total: 0,
                //1是添加，2是编辑
                flag: 1,
                dialogVisible: false,
                rules: {
                    eid: [
                        {required: true, message: '请输入员工姓名', trigger: 'blur'}
                    ]
                    ,
                    traindate: [
                        {required: true, message: '请输入培训日期', trigger: 'blur'}
                    ],
                    traincontent: [
                        {required: true, message: '请输入培训内容', trigger: 'blur'}
                    ],

                },
                addQuery: {
                    eid: 1,
                    traindate: '',
                    traincontent: '',
                    remark: ''
                },
                employees: []

            }
        },
        created() {
            this.getDataList()
        },
        methods: {
            getDataList() {
                this.getRequest("/employeetrain", this.queryinfo).then(resp => {
                    console.log(resp)
                    this.tableData = resp.data.pageinfo.list
                    this.total = resp.data.pageinfo.total
                }).catch(reason => {
                    console.log(reason)
                })
            },
            handleSizeChange(val) {
                this.queryinfo.pageSize = val;
                this.getDataList();
                // console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.queryinfo.pagenum = val;
                this.getDataList();
                // console.log(`当前页: ${val}`);
            },
            addEmployeeTrain() {
                this.addQuery= {
                    eid: 1,
                    traindate: '',
                    traincontent: '',
                    remark: ''
                }
                this.flag = 1;
                this.dialogVisible = true
                this.getRequest("/employeeec/employee").then(resp => {
                    if (resp) {
                        console.log(resp)
                        this.employees = resp.data.employees
                    }
                })
            },
            handleClose() {
                // console.log(this)
                this.$refs.form.resetFields()
                this.$message.success("取消操作");
                this.dialogVisible = false
            },
            submitEmployee() {
                this.$refs.form.validate(valid => {
                    if (!valid) return
                    if (this.flag === 1) {
                        this.postRequest("/employeetrain", this.addQuery).then(resp => {
                            if (resp.code === 20000) {
                                this.$message.success(resp.message);
                                this.dialogVisible = false
                                this.employees = []
                                this.$refs.form.resetFields()
                                this.getDataList()
                            }
                        }).catch(reason => {
                            console.log(reason)
                        })
                    } else {
                        this.putRequest("/employeetrain", this.addQuery).then(resp => {
                            if (resp.code === 20000) {
                                this.$message.success(resp.message);
                                this.dialogVisible = false
                                this.employees = []
                                this.$refs.form.resetFields()

                                this.getDataList()
                            }
                        }).catch(reason => {
                            console.log(reason)
                        })
                    }


                })
            }
            ,
            //编辑
            showEditDialog(id) {
                this.flag = 2;
                this.getRequest("employeetrain/" + id).then(resp => {
                    console.log(resp)
                    this.addQuery = resp.data.employeetrain
                    this.employees = []
                    this.employees.push(resp.data.employeetrain.employee)
                    this.dialogVisible = true
                }).catch(reason => {
                    console.log(reason)
                })
            },
            //删除
            removeEmployeeecById(id) {
                this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("employeetrain/" + id).then(resp => {
                        this.$message.success(resp.message)
                        this.getDataList()
                    }).catch(reason => {
                        console.log(reason)
                    })

                }).catch(() => {
                    this.$message({
                        type: 'info',
                        message: '已取消删除'
                    });
                });

            }


        }
    }
</script>

<style scoped>

</style>
