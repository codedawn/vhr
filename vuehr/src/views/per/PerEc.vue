<template>
  <div>

    <el-row :gutter="20">
      <el-col :span="6">
        <el-input @keydown.enter.native="getDataList" placeholder="请输入员工名、工号进行搜索，可以直接回车搜索..." v-model="queryinfo.query"
                  clearable @clear="getDataList">
          <el-button slot="append" icon="el-icon-search" @click="getDataList"></el-button>
        </el-input>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" @click="addEmployeec">添加奖罚</el-button>
      </el-col>
    </el-row>

    <!--    添加employeeec的dialog-->
    <el-dialog
            :close-on-click-modal="false"
            title="添加奖罚"
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

        <el-form-item label="奖罚日期" prop="ecdate">
          <el-date-picker type="date" placeholder="选择日期" v-model="addQuery.ecdate" value-format="yyyy-MM-dd"
                          style="width: 100%;"></el-date-picker>
        </el-form-item>

        <el-form-item label="奖罚原因" prop="ecreason">
          <el-input v-model="addQuery.ecreason"></el-input>
        </el-form-item>
        <el-form-item label="奖罚分" prop="ecpoint">
          <el-input v-model.number="addQuery.ecpoint" type="number"></el-input>
        </el-form-item>
        <el-form-item label="奖罚类别" prop="ectype">
          <el-switch
                  style="display: block"
                  v-model="addQuery.ectype"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  inactive-text="罚"
                  :inactive-value="1"
                  active-text="奖"
                  :active-value="0"

          >
          </el-switch>
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

            :data="tableData"
            style="width: 100%">
      <el-table-column type="index">

      </el-table-column>
      <el-table-column
              prop="ecdate"
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
              prop="ecreason"
              label="奖惩原因" align="center">
      </el-table-column>

      <el-table-column
              prop="ecpoint"
              label="奖罚分"
              align="center">
      </el-table-column>
      <el-table-column label="备注" prop="remark" align="center">

      </el-table-column>
      <el-table-column
              prop="ectype"
              label="奖罚"
              width="100"
              :filters="[{ text: '奖', value: 0 }, { text: '罚', value: 1 }]"
              :filter-method="filterTag"
              filter-placement="bottom-end" align="center">
        <template slot-scope="scope">
          <el-tag
                  :type="scope.row.ectype === 0 ? 'primary' : 'success'"
                  close-transition>{{scope.row.ectype===0?'奖':'罚'}}
          </el-tag>
        </template>
      </el-table-column>


      <el-table-column label="操作" align="center">
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
    import {getRequest} from "../../utils/api";

    export default {
        data() {
            return {
                //1是新添加，2是更新
                flag: 1,
                queryinfo: {
                    query: '',
                    pagenum: 1,
                    pageSize: 5,

                },
                dialogVisible: false,
                total: 0,
                tableData: [{
                    ecdate: '2016-05-02',
                    name: '王小虎',
                    ectype: 0,
                    ecreason: '',
                    remark: '',
                    ecpoint: 1
                }]

                ,
                employees: [],
                addQuery: {
                    eid: 1,
                    ecdate: '',
                    ecreason: '',
                    ecpoint: 1,
                    ectype: 0,
                    remark: '',
                },
                rules: {
                    eid: [
                        {required: true, message: '请输入员工姓名', trigger: 'blur'}
                    ]
                    ,
                    ecdate: [
                        {required: true, message: '请输入奖罚日期', trigger: 'blur'}
                    ],
                    ecreason: [
                        {required: true, message: '请输入奖罚原因', trigger: 'blur'}
                    ],
                    ecpoint: [
                        {required: true, message: '请输入奖罚分', trigger: 'blur'}, {type: 'number', message: '奖罚分必须为数字值'}
                    ]


                }


            }
        },
        created() {
            this.getDataList();
        },
        methods: {
            filterTag(value, row) {
                return row.ectype === value;

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
            //获取数据
            getDataList() {
                this.getRequest("/employeeec", this.queryinfo).then(resp => {
                    if (resp) {
                        this.tableData = resp.data.pageinfo.list;
                        this.total = resp.data.pageinfo.total
                        console.log(resp)
                    }
                })

            }
            ,
            //添加
            addEmployeec() {
                this.addQuery={
                    eid: 1,
                    ecdate: '',
                    ecreason: '',
                    ecpoint: 1,
                    ectype: 0,
                    remark: '',
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
            //提交添加
            submitEmployee() {
                this.$refs.form.validate(valid => {
                    if (!valid) return
                    if (this.flag === 1) {
                        this.postRequest("/employeeec", this.addQuery).then(resp => {
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
                        this.putRequest("/employeeec", this.addQuery).then(resp => {
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
            handleClose() {
                // console.log(this)
                this.$refs.form.resetFields()
                this.$message.success("取消操作");
                this.dialogVisible = false
            },
            //编辑
            showEditDialog(id) {
                this.flag = 2;
                this.getRequest("employeeec/" + id).then(resp => {
                    console.log(resp)
                    this.addQuery = resp.data.employeeec
                    this.employees = []
                    this.employees.push(resp.data.employeeec.employee)
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
                    this.deleteRequest("employeeec/" + id).then(resp => {
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
<styly>
</styly>
