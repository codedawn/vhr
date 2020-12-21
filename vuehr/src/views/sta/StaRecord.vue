<template>
  <div>
    <el-row :gutter="20" style="margin-bottom:10px">
      <el-col :span="4">
        <div>
          部门：
          <el-cascader
                  :options="departments"
                  v-model="queryinfo.departmentId"
                  :props="cascaderProps"
                  :show-all-levels="false"
                  @change="changeData">
          </el-cascader>
        </div>


      </el-col>
      <el-col :span="6">
        <div class="block">
          <span class="demonstration">日期：</span>
          <el-date-picker
                  v-model="rangeDate"
                  type="daterange"
                  align="right"
                  unlink-panels
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  :picker-options="pickerOptions2"
                  value-format="yyyy-MM-dd"
                  @change="changeData"
          >
          </el-date-picker>
        </div>
      </el-col>
      <el-col :span="4">
        <el-button type="primary" icon="el-icon-search" @click="changeData">搜索</el-button>
      </el-col>
    </el-row>
    <el-tabs v-model="activeName2" type="card">
      <el-tab-pane label="表格" name="first">
        <el-table
                border
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
      </el-tab-pane>
      <el-tab-pane label="图表" name="second">
    <el-row>
        <ve-histogram
                :extend="extend"    width="1700px" height="500px" :data="chartData" :settings="chartSettings" ></ve-histogram>
    </el-row>

      </el-tab-pane>
    </el-tabs>


  </div>
</template>

<script>
    import {getRequest} from "../../utils/api";

    export default {
        data() {
            return {
                chartSettings:{
                    metrics: ['奖', '罚'],
                    dimension: ['name'],
                },
                extend:{
                    series: {
                        label: { show: true, position: "top" },
                        barMaxWidth: 50,
                    },
                    xAxis: {
                        // ------------------------横坐标垂直显示
                        axisLabel: {
                            interval:0,
                            formatter:function(value)
                            {
                                return value.split("").join("\n");
                            }
                        }
                    }
                },
                chartData: {
                    columns: ['姓名', '奖', '罚'],
                    rows: []
                },
                queryinfo: {
                    query: '',
                    pagenum: 1,
                    pageSize: 5,
                    departmentId: 8,
                    beginDate: '',
                    endDate: ''
                },
                rangeDate: [],
                total: 0,
                activeName2: 'first',
                tableData: [{
                    ecdate: '2016-05-02',
                    name: '王小虎',
                    ectype: 0,
                    ecreason: '',
                    remark: '',
                    ecpoint: 1
                }]
                ,
                pickerOptions2: {
                    shortcuts: [{
                        text: '最近一周',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近一个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
                            picker.$emit('pick', [start, end]);
                        }
                    }, {
                        text: '最近三个月',
                        onClick(picker) {
                            const end = new Date();
                            const start = new Date();
                            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
                            picker.$emit('pick', [start, end]);

                        }
                    }]
                },
                departments: [],

                cascaderProps: {
                    value: 'id',
                    label: 'name',
                    children: 'children',
                },
            }
        },
        created() {
            this.getDataList();
            this.getDepartments()
        },
        watch: {
            rangeDate: function (newVal, oldVal) {
                if (newVal !== null) {
                    this.queryinfo.beginDate = newVal[0]
                    this.queryinfo.endDate = newVal[1]
                } else {
                    this.queryinfo.beginDate = null
                    this.queryinfo.endDate = null
                }
            }
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
                console.log(this.myTime)
            },
            changeData(){
                this.queryinfo.pagenum=1
                this.getChartData()
                this.getDataList()
            },
            //获取数据
            getDataList() {

                if (this.queryinfo.departmentId instanceof Array) {

                    this.queryinfo.departmentId = this.queryinfo.departmentId[this.queryinfo.departmentId.length - 1]
                }
                this.getRequest("/record", this.queryinfo).then(resp => {
                    if (resp) {
                        this.tableData = resp.data.pageinfo.list;
                        this.total = resp.data.pageinfo.total
                        console.log(this.tableData)
                    }
                })

            },
            getChartData(){
                this.getRequest('/record/all',this.queryinfo).then(resp=>{
                    if(resp){
                        let list=resp.data.list
                        let a=new Array()
                        for(let v in list){
                            if(!a[list[v].employee.name]){
                                a[list[v].employee.name]={name:list[v].employee.name,'罚':0,'奖':0}
                                if(list[v].ectype==0){
                                    a[list[v].employee.name].奖+=1
                                }else {
                                    a[list[v].employee.name].罚+=1
                                }

                            }
                            else {
                                if(list[v].ectype==0){
                                    a[list[v].employee.name].奖+=1
                                }else {
                                    a[list[v].employee.name].罚+=1
                                }
                            }
                        }
                        this.chartData.rows=Object.values(a)

                        console.log(this.chartData)
                        // this.chartData.rows.push()
                    }

                })

            }
            ,
            getDepartments() {
                this.getRequest("/record/departments").then(resp => {
                    this.departments = this.getTreeData(resp.data.departments)
                    console.log(resp)
                }).catch(reason => {
                    console.log(reason)
                })
            },
            // 递归判断列表，把最后的children设为undefined
            getTreeData(data) {
                for (let i = 0; i < data.length; i++) {
                    if (data[i].children.length < 1) {
                        // children若为空数组，则将children设为undefined
                        data[i].children = undefined;
                    } else {
                        // children若不为空数组，则继续 递归调用 本方法
                        this.getTreeData(data[i].children);
                    }
                }
                return data;
            },

        }
    }
</script>
<styly>
</styly>
