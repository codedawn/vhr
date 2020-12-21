package org.javaboy.vhr.mapper;

import org.javaboy.vhr.model.Employeeec;

import java.util.List;

public interface EmployeeecMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employeeec record);

    int insertSelective(Employeeec record);

    Employeeec selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Employeeec record);

    int updateByPrimaryKey(Employeeec record);


    /**
     * 查找使用Employeeec
     * @return
     */
    List<Employeeec> selectAllEmployeeec(String query);
    List<Employeeec> selectEmployeeecAndQuery(Integer departmentId,String beginDateTime,String endDateTime);






}
