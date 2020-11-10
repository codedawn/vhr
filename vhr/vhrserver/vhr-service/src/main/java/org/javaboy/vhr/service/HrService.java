package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.HrMapper;
import org.javaboy.vhr.mapper.HrRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.utils.HrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @作者 江南一点雨
 * @公众号 江南一点雨
 * @微信号 a_java_boy
 * @GitHub https://github.com/lenve
 * @博客 http://wangsong.blog.csdn.net
 * @网站 http://www.javaboy.org
 * @时间 2019-09-20 8:21
 */
@Service
public class HrService implements UserDetailsService {
    @Autowired
    HrMapper hrMapper;
    @Autowired
    HrRoleMapper hrRoleMapper;

    /**
     *加载hr
     * @param username
     * @return
     * @throws UsernameNotFoundException
     *
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //先获取hr
        Hr hr = hrMapper.loadUserByUsername(username);
        if (hr == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        //装填hr的role
        hr.setRoles(hrMapper.getHrRolesById(hr.getId()));
        return hr;
    }

    /**
     * 获取所有的hr
     * @param keywords
     * @return
     */
    public List<Hr> getAllHrs(String keywords) {
        return hrMapper.getAllHrs(HrUtils.getCurrentHr().getId(),keywords);
    }

    /**
     * 更新hr
     * @param hr
     * @return
     */
    public Integer updateHr(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    /**
     *更新hr的role
     * @param hrid
     * @param rids
     * @return
     */
    @Transactional
    public boolean updateHrRole(Integer hrid, Integer[] rids) {
        hrRoleMapper.deleteByHrid(hrid);
        return hrRoleMapper.addRole(hrid, rids) == rids.length;
    }

    /**
     * 删除hr通过id
     * @param id
     * @return
     */
    public Integer deleteHrById(Integer id) {
        return hrMapper.deleteByPrimaryKey(id);
    }

    /**
     * 获取除当前hr的所有hr
     * @return
     */
    public List<Hr> getAllHrsExceptCurrentHr() {
        return hrMapper.getAllHrsExceptCurrentHr(HrUtils.getCurrentHr().getId());
    }

    public Integer updateHyById(Hr hr) {
        return hrMapper.updateByPrimaryKeySelective(hr);
    }

    /**
     * 更新hr通过密码
     * @param oldpass
     * @param pass
     * @param hrid
     * @return
     */
    public boolean updateHrPasswd(String oldpass, String pass, Integer hrid) {
        Hr hr = hrMapper.selectByPrimaryKey(hrid);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (encoder.matches(oldpass, hr.getPassword())) {
            String encodePass = encoder.encode(pass);
            Integer result = hrMapper.updatePasswd(hrid, encodePass);
            if (result == 1) {
                return true;
            }
        }
        return false;
    }

    public Integer updateUserface(String url, Integer id) {
        return hrMapper.updateUserface(url, id);
    }
}
