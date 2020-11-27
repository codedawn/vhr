package org.javaboy.vhr.controller.per;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.javaboy.vhr.model.Employeeec;
import org.javaboy.vhr.service.EmploeeecService;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.uitls.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-23 21:58
 */
@Api("奖罚的crud")
@RestController
@RequestMapping("/employeeec")
public class EmpEcController {

    @Autowired
    private EmploeeecService emploeeecService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/",""})
    public R getEmployeeEc(Integer pagenum,Integer pageSize,String query){

        PageHelper.startPage(pagenum, pageSize);
        List<Employeeec> list = emploeeecService.getAllEmployeeec(query);
        PageInfo<Employeeec> pageInfo = new PageInfo<>(list);

        return R.ok().data("pageinfo",pageInfo);
    }

    @GetMapping("{id}")
    public R getEmployeeEc(@PathVariable("id") Integer id){

        Employeeec employeeec = emploeeecService.selectByPrimaryKey(id);
        return R.ok().data("employeeec",employeeec);
    }

    @GetMapping("/employee")
    public R getAllEmployee(){
        return R.ok().data("employees",employeeService.getAllEmployee());
    }

    @PostMapping({"/",""})
    public R insertEmployeeEc(@RequestBody Employeeec employeeec){
        emploeeecService.insertSelective(employeeec);
        return R.ok().message("添加成功");
    }

    @PutMapping("")
    public R updateEmployeeEc(@RequestBody Employeeec employeeec){
        emploeeecService.updateByPrimaryKeySelective(employeeec);
        return R.ok().message("更新完成");
    }

    @DeleteMapping("{id}")
    public R deleteEmployeeEc(@PathVariable("id") Integer id){
        emploeeecService.deleteByPrimaryKey(id);
        return R.ok().message("删除成功");
    }

}
