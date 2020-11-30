package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeremoveMapper;
import org.javaboy.vhr.model.Employeeremove;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-29 12:49
 */
@Service
public class EmployeeRemoveService {
    @Autowired
    private EmployeeremoveMapper employeeremoveMapper;


    public List<Employeeremove> selectAllEmployeeRemove(String query){
        return employeeremoveMapper.selectAllEmployeeRemove(query);
    }
    public int insert(Employeeremove record){
        return employeeremoveMapper.insert(record);
    }

    public  int deleteByPrimaryKey(Integer id){
        return employeeremoveMapper.deleteByPrimaryKey(id);
    }

    public Employeeremove selectByPrimaryKey(Integer id){
        return employeeremoveMapper.selectByPrimaryKey(id);
    }
    public int updateByPrimaryKeySelective(Employeeremove record){
        return employeeremoveMapper.updateByPrimaryKeySelective(record);
    }
}
