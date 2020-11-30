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
                <el-button type="primary" @click="addEmployeeRemove">调动员工</el-button>
            </el-col>
        </el-row>



        <!--        添加调动的dialog-->
        <el-dialog
                :close-on-click-modal="false"
                title="员工调动"
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

                <el-form-item label="调到部门" prop="afterdepid">
                    <template>
                        <el-cascader
                                :options="departments"
                                v-model="addQuery.afterdepid"
                                :props="cascaderProps"
                                :show-all-levels="false">
                        </el-cascader>
                    </template>

                </el-form-item>

                <el-form-item label="调到职位" prop="afterjobid">
                    <template>
                        <el-select v-model="addQuery.afterjobid" filterable placeholder="请选择">
                            <el-option
                                    v-for="item in jobs"
                                    :key="item.id"
                                    :label="item.name"
                                    :value="item.id">
                            </el-option>
                        </el-select>
                    </template>
                </el-form-item>


                <el-form-item label="调动日期" prop="removedate">
                    <el-date-picker type="date" placeholder="选择日期" v-model="addQuery.removedate" value-format="yyyy-MM-dd"
                                    style="width: 100%;"></el-date-picker>
                </el-form-item>

                <el-form-item label="调动原因" prop="reason">
                    <el-input v-model="addQuery.reason"></el-input>
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
                    prop="removedate"
                    label="调动日期"
                    sortable
                    width="180" align="center">
            </el-table-column>

            <el-table-column
                    prop="department.name"
                    label="调动后部门"
                    width="180" align="center">
            </el-table-column>

            <el-table-column
                    prop="position.name"
                    label="调动后职位"
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
                    label="所属部门(现)"
                    width="180" align="center">
            </el-table-column>

            <el-table-column
                    prop="reason"
                    label="调动原因" align="center">
            </el-table-column>

            <el-table-column
                    label="备注" prop="remark" align="center">

            </el-table-column>


            <el-table-column label="操作" align="center" width="190px">
                <template slot-scope="scope">
                    <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)">编辑</el-button>
                    <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeEmployeeRemoveById(scope.row.id)">删除
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
        name: "PerMv",
        data(){
            return{

                queryinfo: {
                    query: '',
                    pagenum: 1,
                    pageSize: 5,

                },
                dialogVisible: false,
                total: 0,
                tableData:[],
                employees:[],
                departments:[],
                jobs:[],
                addQuery:{
                    eid:1,
                    afterdepid:8,
                    afterjobid:29,
                    removedate:'',
                    reason:'',
                    remark:'',
                },
                rules:{
                    eid: [
                        {required: true, message: '请输入员工姓名', trigger: 'blur'}
                    ],
                    afterdepid:[
                        {required: true, message: '请输入调动部门', trigger: 'blur'}
                    ],
                    afterjobid:[
                        {required: true, message: '请输入员工职位', trigger: 'blur'}
                    ],
                    removedate:[
                        {required: true, message: '请输入调动日期', trigger: 'blur'}
                    ],

                },
                cascaderProps:{
                    value:'id',
                    label:'name',
                    children:'children',
                },
                //1是添加，2是编辑
                flag: 1,
            }
        },
        created() {
            this.getDataList()
        },
        methods:{
            getDataList(){
                this.getRequest("/employeeremove", this.queryinfo).then(resp => {
                    if (resp) {
                        this.tableData = resp.data.pageinfo.list;
                        this.total = resp.data.pageinfo.total
                        console.log(resp)
                    }
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
            handleClose() {
                // console.log(this)
                this.$refs.form.resetFields()
                this.$message.success("取消操作");
                this.dialogVisible = false
            },
            addEmployeeRemove() {
                this.addQuery= {
                    eid:1,
                    afterdepid:8,
                    afterjobid:29,
                    removedate:'',
                    reason:'',
                    remark:'',
                }
                this.flag = 1;
                this.dialogVisible = true
                this.getRequest("/employeeec/employee").then(resp => {
                    if (resp) {
                        console.log(resp)
                        this.employees = resp.data.employees
                    }
                })
                this.getRequest("/system/basic/department/departments").then(resp => {
                    if (resp) {
                        console.log(resp)
                        this.departments = this.getTreeData(resp.data.departments)
                    }
                })
                this.getRequest(" /system/basic/pos").then(resp => {
                    if (resp) {
                        console.log(resp)
                        this.jobs = resp.data.positions
                    }
                })
            },
            // 递归判断列表，把最后的children设为undefined
            getTreeData(data){
                for(let i=0; i<data.length; i++){
                    if(data[i].children.length<1){
                        // children若为空数组，则将children设为undefined
                        data[i].children=undefined;
                    }else {
                        // children若不为空数组，则继续 递归调用 本方法
                        this.getTreeData(data[i].children);
                    }
                }
                return data;
            },
            submitEmployee() {
                this.$refs.form.validate(valid => {
                    if (!valid) return

                    if (this.flag === 1) {
                        if(this.addQuery.afterdepid instanceof Array){

                            this.addQuery.afterdepid=this.addQuery.afterdepid[this.addQuery.afterdepid.length-1]
                        }
                        this.postRequest("/employeeremove", this.addQuery).then(resp => {
                            if (resp.code === 20000) {
                                this.$message.success(resp.message);
                                this.dialogVisible = false
                                this.employees = []
                                this.$refs.form.resetFields()
                                this.getDataList()
                            }else {
                                console.log(resp)
                            }
                        }).catch(reason => {
                            console.log(reason)
                        })
                    } else {
                        this.putRequest("/employeeremove", this.addQuery).then(resp => {
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
            },
            //编辑
            showEditDialog(id) {
                this.flag = 2;
                this.getRequest("employeeremove/" + id).then(resp => {
                    console.log(resp)
                    this.addQuery = resp.data.employeeremove
                    this.employees = []
                    this.departments = []
                    this.jobs = []
                    this.employees.push(resp.data.employeeremove.employee)
                    this.departments.push(resp.data.employeeremove.department)
                    this.departments=this.getTreeData(this.departments)
                    this.jobs.push(resp.data.employeeremove.position)
                    this.dialogVisible = true
                }).catch(reason => {
                    console.log(reason)
                })
            },
            //删除
            removeEmployeeRemoveById(id) {
                this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    this.deleteRequest("employeeremove/" + id).then(resp => {
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
