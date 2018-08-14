package com.example.test;

import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import tk.mybatis.mapper.entity.Example;

import java.util.List;


@Slf4j
@RunWith(SpringJUnit4ClassRunner.class) //使用junit4进行测试
@ContextConfiguration(locations={"classpath:application.yml"}) //加载配置文件
public class DemoMapperTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testMapper() {
        log.info("size->{}", 123);
    }
    @Test
    public void testMapper1() {
        Example example=new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andCondition("id=",1);
        List<User> users = userMapper.selectByExample(example);
        log.info("size->{}", users);
    }
}
