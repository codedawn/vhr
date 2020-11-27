package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeetrainMapper;
import org.javaboy.vhr.model.Employeetrain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author codedawn
 * @date 2020-11-26 12:55
 */
@Service
public class EmployeeTrainService {

    @Autowired
    private EmployeetrainMapper employeetrainMapper;

    public List<Employeetrain> selectAllEmployeeTrain(String query) {
        return employeetrainMapper.selectAllEmployeeTrain(query);
    }

    public int insertSelective(Employeetrain record){
        return employeetrainMapper.insertSelective(record);
    }

    public int deleteByPrimaryKey(Integer id){
        return employeetrainMapper.deleteByPrimaryKey(id);
    }
    public int updateByPrimaryKeySelective(Employeetrain record){
        return employeetrainMapper.updateByPrimaryKeySelective(record);
    }
    public Employeetrain selectByPrimaryKey(Integer id){
        return employeetrainMapper.selectByPrimaryKey(id);
    }
}
