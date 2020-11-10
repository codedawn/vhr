package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.PositionMapper;
import org.javaboy.vhr.model.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @作者 江南一点雨
 * @公众号 江南一点雨
 * @微信号 a_java_boy
 * @GitHub https://github.com/lenve
 * @博客 http://wangsong.blog.csdn.net
 * @网站 http://www.javaboy.org
 * @时间 2019-10-01 15:54
 */
@Service
public class PositionService {
    @Autowired
    PositionMapper positionMapper;

    /**
     * 获取所有的职位
     * @return
     */
    public List<Position> getAllPositions() {
        return positionMapper.getAllPositions();
    }

    /**
     * 添加职位
     * @param position
     * @return
     */
    public Integer addPosition(Position position) {
        position.setEnabled(true);
        position.setCreateDate(new Date());
        return positionMapper.insertSelective(position);
    }

    /**
     * 更新职位
     * @param position
     * @return
     */
    public Integer updatePositions(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    /**
     * 删除职位通过id
     * @param id
     * @return
     */
    public Integer deletePositionById(Integer id) {
        return positionMapper.deleteByPrimaryKey(id);
    }

    /**
     * 通过id批量删除职位
     * @param ids
     * @return
     */
    public Integer deletePositionsByIds(Integer[] ids) {
        return positionMapper.deletePositionsByIds(ids);
    }
}
