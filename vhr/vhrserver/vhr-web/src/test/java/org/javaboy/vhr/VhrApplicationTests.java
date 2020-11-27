package org.javaboy.vhr;

import org.javaboy.vhr.model.Employeeec;
import org.javaboy.vhr.model.Employeetrain;
import org.javaboy.vhr.service.EmploeeecService;
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



    @Test
    public void contextLoads() {
    }

    @Test
    public void employeeecMapperTest(){

        List<Employeetrain> allEmployeeec = employeeTrainService.selectAllEmployeeTrain("江");
        List<Employeeec> allEmployeeec1 = emploeeecService.getAllEmployeeec("");
        System.out.println(allEmployeeec);
    }

}
