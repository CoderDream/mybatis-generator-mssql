package com.sica.service;

import com.sica.domain.User;

import java.util.List;

/**
 * Created by xiang.li on 2015/1/31.
 */
public interface IUserService {

	/**
	 * 根据Id查询用户对象
	 * 
	 * @param id
	 *          编号
	 * @return 用户对象
	 */
	User getUserById(String id);

	/**
	 * 根据用户名查询用户对象
	 * 
	 * @return List
	 */
	List<User> queryUserList();
}
