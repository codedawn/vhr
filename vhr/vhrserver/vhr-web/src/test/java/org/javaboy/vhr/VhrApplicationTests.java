package org.javaboy.vhr;

import org.javaboy.vhr.mapper.EmployeeecMapper;
import org.javaboy.vhr.model.Employeeec;
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
    EmployeeecMapper employeeecMapper;



    @Test
    public void contextLoads() {
    }

    @Test
    public void employeeecMapperTest(){

        List<Employeeec> allEmployeeec = employeeecMapper.selectAllEmployeeec("");
        System.out.println(allEmployeeec);
    }

}
