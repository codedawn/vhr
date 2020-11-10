package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.DepartmentMapper;
import org.javaboy.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @作者 江南一点雨
 * @公众号 江南一点雨
 * @微信号 a_java_boy
 * @GitHub https://github.com/lenve
 * @博客 http://wangsong.blog.csdn.net
 * @网站 http://www.javaboy.org
 * @时间 2019-10-21 8:04
 */
@Service
public class DepartmentService {


    /**
     * 获取所有的部门信息通过ParentId
     */
    @Autowired
    DepartmentMapper departmentMapper;
    public List<Department> getAllDepartments() {
        return departmentMapper.getAllDepartmentsByParentId(-1);
    }

    /**
     * 添加部门
     * @param dep 需要添加的部门
     */
    public void addDep(Department dep) {
        dep.setEnabled(true);
        departmentMapper.addDep(dep);
    }

    /**
     * 删除部门
     * @param dep
     */
    public void deleteDepById(Department dep) {
        departmentMapper.deleteDepById(dep);
    }


    /**
     * 获取所有的部门信息用于execel输出
     * @return
     */
    public List<Department> getAllDepartmentsWithOutChildren() {
        return departmentMapper.getAllDepartmentsWithOutChildren();
    }
}
