package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.common.BaseResult;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import tk.mybatis.spring.annotation.MapperScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@MapperScan("com.example.demo.mapper")
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping("/showUser")
    @ResponseBody
    public User toIndex(HttpServletRequest request, Model model){
        int userId = Integer.parseInt(request.getParameter("id"));
        User user = this.userService.getUserById(userId);
        return user;
    }
    @RequestMapping("/insetUser")
    @ResponseBody
    public String insetUser(HttpServletRequest request){
        BaseResult br=new BaseResult();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String age = request.getParameter("age");
        User user=new User();
        user.setUserName(username);
        user.setPassword(password);
        user.setAge(Integer.parseInt(age));
        boolean b = userService.addUser(user);
        if (b){
            br.setSuccess(true);
        }else {
            br.setSuccess(false);
        }
        return JSON.toJSONString(br);
    }
    @RequestMapping("/showAllUser")
    @ResponseBody
    public List<User> showAllUser(HttpServletRequest request){
        List<User> users = userService.selectTest();
        log.info("into->{}",users);
        return users;
    }
}