package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.SalaryMapper;
import org.javaboy.vhr.model.Salary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SalaryService {
    @Autowired
    SalaryMapper salaryMapper;

    /**
     * 获取所有的工资帐套
     * @return
     */
    public List<Salary> getAllSalaries() {
        return salaryMapper.getAllSalaries();
    }

    /**
     * 添加工资帐套
     * @param salary
     * @return
     */
    public Integer addSalary(Salary salary) {
        salary.setCreateDate(new Date());
        return salaryMapper.insertSelective(salary);
    }

    /**
     * 删除工资帐套
     * @param id
     * @return
     */
    public Integer deleteSalaryById(Integer id) {
        return salaryMapper.deleteByPrimaryKey(id);
    }

    /**
     * 更新工资帐套通过id
     * @param salary
     * @return
     */
    public Integer updateSalaryById(Salary salary) {
        return salaryMapper.updateByPrimaryKeySelective(salary);
    }
}
