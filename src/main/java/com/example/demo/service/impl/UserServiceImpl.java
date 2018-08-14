package com.example.demo.service.impl;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.example.demo.model.User;
import com.example.demo.mapper.UserMapper;
import tk.mybatis.mapper.entity.Example;


import java.util.List;

/**
 * @author hzy13811
 */
@Transactional
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userDao;


    @Override
    public User getUserById(int userId) {
        return userDao.selectByPrimaryKey(userId);
    }

    @Override
    public boolean addUser(User record){
        boolean result = false;
        try {
            userDao.insertSelective(record);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public List<User> selectTest() {
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id is not",null);
        List<User> users = userDao.selectByExample(example);
        return users;
    }

}