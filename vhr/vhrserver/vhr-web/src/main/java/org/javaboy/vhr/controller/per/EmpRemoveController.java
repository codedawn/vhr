package org.javaboy.vhr.controller.per;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.javaboy.vhr.model.Employeeremove;
import org.javaboy.vhr.service.EmployeeRemoveService;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.uitls.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-29 12:39
 */
@Api("员工调动的crud")
@RestController
@RequestMapping("/employeeremove")
public class EmpRemoveController {
    @Autowired
    private EmployeeRemoveService employeeRemoveService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping({"/",""})
    public R getEmployeeRemove(Integer pagenum, Integer pageSize, String query){

        PageHelper.startPage(pagenum, pageSize);
        List<Employeeremove> list = employeeRemoveService.selectAllEmployeeRemove(query);
        PageInfo<Employeeremove> pageInfo = new PageInfo<>(list);

        return R.ok().data("pageinfo",pageInfo);
    }

    @GetMapping("{id}")
    public R getEmployeeEc(@PathVariable("id") Integer id){

        Employeeremove employeeremove = employeeRemoveService.selectByPrimaryKey(id);
        return R.ok().data("employeeremove",employeeremove);
    }


    @PostMapping({"/",""})
    public R insertEmployeeEc(@RequestBody Employeeremove employeeremove){
        employeeRemoveService.insert(employeeremove);
        return R.ok().message("添加成功");
    }

    @PutMapping("")
    public R updateEmployeeEc(@RequestBody Employeeremove employeeremove){
        employeeRemoveService.updateByPrimaryKeySelective(employeeremove);
        return R.ok().message("更新完成");
    }

    @DeleteMapping("{id}")
    public R deleteEmployeeEc(@PathVariable("id") Integer id){
        employeeRemoveService.deleteByPrimaryKey(id);
        return R.ok().message("删除成功");
    }



}
