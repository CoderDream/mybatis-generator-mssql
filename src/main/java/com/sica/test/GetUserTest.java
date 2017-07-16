package com.sica.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;
import com.sica.domain.User;
import com.sica.service.IUserService;

/**
 * Created by xiang.li on 2015/2/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-mybatis.xml")
public class GetUserTest {

	private static String UUID = "3";
	@Resource
	private IUserService userService;
	private static Logger logger = LoggerFactory.getLogger(GetUserTest.class);

	@Test
	public void test1() {
		User user = userService.getUserById(UUID);
		logger.info(JSON.toJSONString(user));
	}

	/**
	 * 测试联合查询
	 */
	@Test
	public void test2() {
		List<User> users = userService.queryUserList();
		logger.info(JSON.toJSONString(users));
	}
}