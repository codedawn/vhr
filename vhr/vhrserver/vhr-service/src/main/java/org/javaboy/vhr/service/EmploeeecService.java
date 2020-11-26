package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeecMapper;
import org.javaboy.vhr.model.Employeeec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-24 13:08
 */
@Service
public class EmploeeecService {
    @Autowired
    private EmployeeecMapper employeeecMapper;

    public List<Employeeec> getAllEmployeeec(String query){

        return employeeecMapper.selectAllEmployeeec(query);
    }
    public int insertSelective(Employeeec record){
        return employeeecMapper.insertSelective(record);
    }

    public int updateByPrimaryKeySelective(Employeeec record){
        return employeeecMapper.updateByPrimaryKeySelective(record);
    }
    public int deleteByPrimaryKey(Integer id){
        return employeeecMapper.deleteByPrimaryKey(id);
    }
    public Employeeec selectByPrimaryKey(Integer id){
        return employeeecMapper.selectByPrimaryKey(id);
    }
}
