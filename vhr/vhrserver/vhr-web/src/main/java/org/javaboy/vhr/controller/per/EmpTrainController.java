package org.javaboy.vhr.controller.per;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import org.javaboy.vhr.model.Employeetrain;
import org.javaboy.vhr.service.EmployeeTrainService;
import org.javaboy.vhr.uitls.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-26 12:51
 */
@Api("培训的crud")
@RestController
@RequestMapping("/employeetrain")
public class EmpTrainController {

    @Autowired
    private EmployeeTrainService employeeTrainService;

    @GetMapping("")
    public R getAllEmployeeTrain(Integer pagenum,Integer pageSize,String query){

        PageHelper.startPage(pagenum, pageSize);
        List<Employeetrain> list = employeeTrainService.selectAllEmployeeTrain(query);
        PageInfo<Employeetrain> pageInfo = new PageInfo<>(list);
        return R.ok().data("pageinfo",pageInfo);
    }

    @GetMapping("{id}")
    public R getEmployeeTrain(@PathVariable("id") Integer id){

        Employeetrain employeetrain = employeeTrainService.selectByPrimaryKey(id);
        return R.ok().data("employeetrain",employeetrain);
    }

    @PostMapping("")
    public R insertEmployeeTrain(@RequestBody Employeetrain employeetrain){
        employeeTrainService.insertSelective(employeetrain);
        return R.ok().message("添加完成");
    }


    @DeleteMapping("{id}")
    public R deleteEmployeeTrain(@PathVariable("id") Integer id){
        employeeTrainService.deleteByPrimaryKey(id);
        return R.ok().message("删除完成");
    }

    @PutMapping("")
    public R updateEmployeeTrain(@RequestBody Employeetrain employeetrain){
        employeeTrainService.updateByPrimaryKeySelective(employeetrain);
        return R.ok().message("更新成功");
    }
}
