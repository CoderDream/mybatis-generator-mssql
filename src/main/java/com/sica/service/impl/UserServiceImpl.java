package com.sica.service.impl;

import com.sica.dao.IUserDao;
import com.sica.domain.User;
import com.sica.service.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by xiang.li on 2015/1/31.
 */
@Service("userService")
public class UserServiceImpl implements IUserService {

	@Resource
	public IUserDao userDao;

	@Override
	public User getUserById(String id) {
		return this.userDao.selectByPrimaryKey(id);
	}

	@Override
	public List<User> queryUserList() {
		return userDao.queryForList();
	}

}
