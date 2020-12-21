package org.javaboy.vhr;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.Employeeremove;
import org.javaboy.vhr.service.DepartmentService;
import org.javaboy.vhr.service.EmploeeecService;
import org.javaboy.vhr.service.EmployeeRemoveService;
import org.javaboy.vhr.service.EmployeeTrainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VhrApplicationTests {

    @Autowired
    EmployeeTrainService employeeTrainService;


    @Autowired
    EmploeeecService emploeeecService;


    @Autowired
    DepartmentService departmentService;


    @Autowired
    EmployeeRemoveService employeeRemoveService;

    @Test
    public void contextLoads() {
    }

    @Test
    public void employeeecMapperTest(){

        ObjectMapper objectMapper = new ObjectMapper();
        List<Employeeremove> employeeremoves = employeeRemoveService.selectAllEmployeeRemove("");

        System.out.println(employeeremoves);
    }

}
