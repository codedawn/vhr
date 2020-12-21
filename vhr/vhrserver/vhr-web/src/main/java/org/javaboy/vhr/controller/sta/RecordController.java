package org.javaboy.vhr.controller.sta;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.Employeeec;
import org.javaboy.vhr.service.DepartmentService;
import org.javaboy.vhr.service.EmploeeecService;
import org.javaboy.vhr.uitls.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * @author codedawn
 * @date 2020-12-20 15:29
 */
@RestController
@RequestMapping("/record")
public class RecordController {
    @Autowired
    DepartmentService departmentService;

    @Autowired
    private EmploeeecService emploeeecService;


    @GetMapping({"/",""})
    public R getEmployeeEc(Integer pagenum, Integer pageSize, Integer departmentId, Date beginDate, Date endDate){

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String beginDateTime = "";
        String endDateTime = "";
        if(beginDate!=null){
             beginDateTime = sf.format(beginDate);
        }
        if(endDate!=null){
             endDateTime = sf.format(endDate);
        }
        PageHelper.startPage(pagenum, pageSize);
        List<Employeeec> list = emploeeecService.selectEmployeeecAndQuery(departmentId,beginDateTime,endDateTime);
        PageInfo<Employeeec> pageInfo = new PageInfo<>(list);

        return R.ok().data("pageinfo",pageInfo);
    }

    @GetMapping("/all")
    public R getAllEmployeeEc( Integer departmentId, Date beginDate, Date endDate){

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String beginDateTime = "";
        String endDateTime = "";
        if(beginDate!=null){
            beginDateTime = sf.format(beginDate);
        }
        if(endDate!=null){
            endDateTime = sf.format(endDate);
        }
        List<Employeeec> list = emploeeecService.selectEmployeeecAndQuery(departmentId,beginDateTime,endDateTime);

        return R.ok().data("list",list);
    }

    /**
     * 获取所有的部门
     * @return
     */
    @GetMapping("/departments")
    public R getAllDepartments() {
        List<Department> departments = departmentService.getAllDepartments();
        return R.ok().data("departments",departments);
    }
}
