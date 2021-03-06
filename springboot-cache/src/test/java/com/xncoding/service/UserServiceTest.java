package com.xncoding.service;

import com.xncoding.trans.Application;
import com.xncoding.trans.dao.entity.User;
import com.xncoding.trans.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**
 * UserServiceTest
 *
 * @author XiongNeng
 * @version 1.0
 * @since 2018/2/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    public void testCache() {
        int id = new Random().nextInt(100);
        User user = new User(id, "admin", "admin");
        userService.createUser(user);
        User user1 = userService.getById(id); // 第1次访问
        assertEquals(user1.getPassword(), "admin");
        User user2 = userService.getById(id); // 第2次访问
        assertEquals(user2.getPassword(), "admin");
        User user3 = userService.queryUserById(id); // 第3次访问，使用自定义的KeyGenerator
        assertEquals(user3.getPassword(), "admin");
        user.setPassword("123456");
        userService.updateUser(user);
        User user4 = userService.getById(id); // 第4次访问
        assertEquals(user4.getPassword(), "123456");
        userService.deleteById(id);
        assertNull(userService.getById(id));
    }
}
