# mybatis-generator-mssql


http://blog.csdn.net/happylee6688/article/details/45967763

MyBatis 多表联合查询及优化
2015-05-25 11:50 63602人阅读 评论(10) 收藏 举报



### 序

这篇文章我打算来简单的谈谈 mybatis 的多表联合查询。起初是觉得挺简单的，没必要拿出来写，毕竟 mybatis 这东西现在是个开发的都会用，而且网上的文章也是一搜罗一大堆，根本就用不着我来重复。但是吧，就我前几天在做一个多表联合查询的时候，竟然出了很多意想不到的问题，而且这些问题的出现，并不是对 mybatis 不了解，而是在用的过程中会或多或少的忽略一些东西，导致提示各种错误。


背景
----------


老规矩，开始之前，还是要先说说这件事的背景。也就是最近几天，公司要做一个后台的管理平台，由于之前的一些限制，这次要做成单独的项目进行部署，因此就要重新考虑很多东西。索性这几天有时间，就做了一个小 Demo ，实现 mybatis 的多表联合查询的，由于之前用的是 Hibernate 做的联合查询，众所周知，Hibernate 是全自动的数据库持久层框架，它可以通过实体来映射数据库，通过设置一对多、多对一、一对一、多对多的关联来实现联合查询。


正文
----------


下面就来说一下 mybatis 是通过什么来实现多表联合查询的。首先看一下表关系，如图：



这里，我已经搭好了开发的环境，用到的是 SpringMVC + Spring + MyBatis，当然，为了简单期间，你可以不用搭前端的框架，只使用 Spring + MyBatis 就可以，外加 junit 测试即可。环境我就不带大家搭了，这里只说涉及到联合查询的操作。

设计好表之后，我用到了 mybatis 的自动生成工具 mybatis generator 生成的实体类、mapper 接口、以及 mapper xml 文件。由于是测试多表联合查询，因此需要自己稍加改动。

下面是 User 和 Role 的实体类代码：

User

	package com.sica.domain;  
	  
	import java.io.Serializable;  
	import java.util.List;  
	  
	public class User implements Serializable {  
	    private String id;  
	  
	    private String username;  
	  
	    private String password;  
	  
	    private List<Role> roles;  
	  
	    private static final long serialVersionUID = 1L;  
	  
	    public String getId() {  
	        return id;  
	    }  
	  
	    public void setId(String id) {  
	        this.id = id == null ? null : id.trim();  
	    }  
	  
	    public String getUsername() {  
	        return username;  
	    }  
	  
	    public void setUsername(String username) {  
	        this.username = username == null ? null : username.trim();  
	    }  
	  
	    public String getPassword() {  
	        return password;  
	    }  
	  
	    public void setPassword(String password) {  
	        this.password = password == null ? null : password.trim();  
	    }  
	  
	    public List<Role> getRoles() {  
	        return roles;  
	    }  
	  
	    public void setRoles(List<Role> roles) {  
	        this.roles = roles;  
	    }  
	  
	    @Override  
	    public boolean equals(Object that) {  
	        if (this == that) {  
	            return true;  
	        }  
	        if (that == null) {  
	            return false;  
	        }  
	        if (getClass() != that.getClass()) {  
	            return false;  
	        }  
	        User other = (User) that;  
	        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))  
	            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))  
	            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()));  
	    }  
	  
	    @Override  
	    public int hashCode() {  
	        final int prime = 31;  
	        int result = 1;  
	        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());  
	        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());  
	        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());  
	        return result;  
	    }  
	}

Role

	package com.sica.domain;  
	  
	import java.io.Serializable;  
	  
	public class Role implements Serializable {  
	    private String id;  
	  
	    private String name;  
	  
	    private String jsms;  
	  
	    private String bz;  
	  
	    private Integer jlzt;  
	  
	    private String glbm;  
	  
	    private String userid;  
	  
	    private static final long serialVersionUID = 1L;  
	  
	    public String getId() {  
	        return id;  
	    }  
	  
	    public void setId(String id) {  
	        this.id = id == null ? null : id.trim();  
	    }  
	  
	    public String getName() {  
	        return name;  
	    }  
	  
	    public void setName(String name) {  
	        this.name = name == null ? null : name.trim();  
	    }  
	  
	    public String getJsms() {  
	        return jsms;  
	    }  
	  
	    public void setJsms(String jsms) {  
	        this.jsms = jsms == null ? null : jsms.trim();  
	    }  
	  
	    public String getBz() {  
	        return bz;  
	    }  
	  
	    public void setBz(String bz) {  
	        this.bz = bz == null ? null : bz.trim();  
	    }  
	  
	    public Integer getJlzt() {  
	        return jlzt;  
	    }  
	  
	    public void setJlzt(Integer jlzt) {  
	        this.jlzt = jlzt;  
	    }  
	  
	    public String getGlbm() {  
	        return glbm;  
	    }  
	  
	    public void setGlbm(String glbm) {  
	        this.glbm = glbm == null ? null : glbm.trim();  
	    }  
	  
	    public String getUserid() {  
	        return userid;  
	    }  
	  
	    public void setUserid(String userid) {  
	        this.userid = userid == null ? null : userid.trim();  
	    }  
	  
	    @Override  
	    public boolean equals(Object that) {  
	        if (this == that) {  
	            return true;  
	        }  
	        if (that == null) {  
	            return false;  
	        }  
	        if (getClass() != that.getClass()) {  
	            return false;  
	        }  
	        Role other = (Role) that;  
	        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))  
	            && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))  
	            && (this.getJsms() == null ? other.getJsms() == null : this.getJsms().equals(other.getJsms()))  
	            && (this.getBz() == null ? other.getBz() == null : this.getBz().equals(other.getBz()))  
	            && (this.getJlzt() == null ? other.getJlzt() == null : this.getJlzt().equals(other.getJlzt()))  
	            && (this.getGlbm() == null ? other.getGlbm() == null : this.getGlbm().equals(other.getGlbm()))  
	            && (this.getUserid() == null ? other.getUserid() == null : this.getUserid().equals(other.getUserid()));  
	    }  
	  
	    @Override  
	    public int hashCode() {  
	        final int prime = 31;  
	        int result = 1;  
	        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());  
	        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());  
	        result = prime * result + ((getJsms() == null) ? 0 : getJsms().hashCode());  
	        result = prime * result + ((getBz() == null) ? 0 : getBz().hashCode());  
	        result = prime * result + ((getJlzt() == null) ? 0 : getJlzt().hashCode());  
	        result = prime * result + ((getGlbm() == null) ? 0 : getGlbm().hashCode());  
	        result = prime * result + ((getUserid() == null) ? 0 : getUserid().hashCode());  
	        return result;  
	    }  
	}


首先讲一下业务，这里用到的 User 、Role 的对应关系是，一个用户有多个角色，也就是 User ： Role 是 1 ： n 的关系。因此，在 User 的实体中加入一个 Role 的属性，对应一对多的关系。

然后就是 mapper 接口和 xml 文件了：

mapper接口

UserMapper

	package com.sica.mapper;  
	  
	import com.sica.domain.User;  
	  
	import java.util.List;  
	  
	public interface UserMapper {  
	    int deleteByPrimaryKey(String id);  
	  
	    int insert(User record);  
	  
	    int insertSelective(User record);  
	  
	    User selectByPrimaryKey(String id);  
	  
	    int updateByPrimaryKeySelective(User record);  
	  
	    int updateByPrimaryKey(User record);  
	  
	    List<User> queryForList();  
	}


mapper xml文件

UserMapper

	<?xml version="1.0" encoding="UTF-8" ?>  
	<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >  
	<mapper namespace="com.sica.mapper.UserMapper">  
	    <resultMap id="BaseResultMap" type="com.sica.domain.User">  
	        <id column="id" property="id" jdbcType="VARCHAR"/>  
	        <result column="username" property="username" jdbcType="VARCHAR"/>  
	        <result column="password" property="password" jdbcType="VARCHAR"/>  
	    </resultMap>  
	  
	    <resultMap id="queryForListMap" type="com.sica.domain.User">  
	        <id column="id" property="id" jdbcType="VARCHAR"/>  
	        <result column="username" property="username" jdbcType="VARCHAR"/>  
	        <result column="password" property="password" jdbcType="VARCHAR"/>  
	        <collection property="roles" javaType="java.util.List" ofType="com.sica.domain.Role">  
	            <id column="r_id" property="id" jdbcType="VARCHAR" />  
	            <result column="r_name" property="name" jdbcType="VARCHAR" />  
	            <result column="r_jsms" property="jsms" jdbcType="VARCHAR" />  
	            <result column="r_bz" property="bz" jdbcType="VARCHAR" />  
	            <result column="r_jlzt" property="jlzt" jdbcType="INTEGER" />  
	            <result column="r_glbm" property="glbm" jdbcType="VARCHAR" />  
	        </collection>  
	    </resultMap>  
	    <select id="queryForList" resultMap="queryForListMap">  
	        SELECT  
	          u.id,  
	          u.username,  
	          u.password,  
	          r.id r_id,  
	          r.name r_name,  
	          r.jsms r_jsms,  
	          r.bz r_bz,  
	          r.jlzt r_jlzt,  
	          r.glbm r_glbm  
	        FROM  
	          user u  
	        LEFT JOIN  
	          role r  
	        ON  
	          u.id = r.userid  
	    </select>  
	    <sql id="Base_Column_List">  
	      id, username, password  
	    </sql>  
	    <select id="selectByPrimaryKey" resultMap="BaseResultMap" parameterType="java.lang.String">  
	        select  
	        <include refid="Base_Column_List"/>  
	        from user  
	        where id = #{id,jdbcType=VARCHAR}  
	    </select>  
	    <delete id="deleteByPrimaryKey" parameterType="java.lang.String">  
	    delete from user  
	    where id = #{id,jdbcType=VARCHAR}  
	    </delete>  
	    <insert id="insert" parameterType="com.sica.domain.User">  
	    insert into user (id, username, password  
	      )  
	    values (#{id,jdbcType=VARCHAR}, #{username,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}  
	      )  
	    </insert>  
	    <insert id="insertSelective" parameterType="com.sica.domain.User">  
	        insert into user  
	        <trim prefix="(" suffix=")" suffixOverrides=",">  
	            <if test="id != null">  
	                id,  
	            </if>  
	            <if test="username != null">  
	                username,  
	            </if>  
	            <if test="password != null">  
	                password,  
	            </if>  
	        </trim>  
	        <trim prefix="values (" suffix=")" suffixOverrides=",">  
	            <if test="id != null">  
	                #{id,jdbcType=VARCHAR},  
	            </if>  
	            <if test="username != null">  
	                #{username,jdbcType=VARCHAR},  
	            </if>  
	            <if test="password != null">  
	                #{password,jdbcType=VARCHAR},  
	            </if>  
	        </trim>  
	    </insert>  
	    <update id="updateByPrimaryKeySelective" parameterType="com.sica.domain.User">  
	        update user  
	        <set>  
	            <if test="username != null">  
	                username = #{username,jdbcType=VARCHAR},  
	            </if>  
	            <if test="password != null">  
	                password = #{password,jdbcType=VARCHAR},  
	            </if>  
	        </set>  
	        where id = #{id,jdbcType=VARCHAR}  
	    </update>  
	    <update id="updateByPrimaryKey" parameterType="com.sica.domain.User">  
	    update user  
	    set username = #{username,jdbcType=VARCHAR},  
	      password = #{password,jdbcType=VARCHAR}  
	    where id = #{id,jdbcType=VARCHAR}  
	  </update>  
	</mapper>

之后，我扩展了一个 Dao 接口，当然，你也可以直接使用 mapper 接口，都是一样的。

Dao 接口

IUserDao

	package com.sica.dao;  
	  
	import com.sica.mapper.UserMapper;  
	  
	/** 
	 * Created by IntelliJ IDEA. 
	 * Package: com.sica.dao 
	 * Name: IUserDao 
	 * User: xiang.li 
	 * Date: 2015/5/22 
	 * Time: 15:25 
	 * Desc: To change this template use File | Settings | File Templates. 
	 */  
	public interface IUserDao extends UserMapper {  
	  
	}


下面就是 service 和实现层的代码了。

IUserService

	package com.sica.service;  
	  
	import com.sica.domain.User;  
	  
	import java.util.List;  
	  
	/** 
	 * Created by xiang.li on 2015/1/31. 
	 */  
	public interface IUserService {  
	  
	    /** 
	     * 根据Id查询用户对象 
	     * @param id 编号 
	     * @return 用户对象 
	     */  
	    User getUserById(String id);  
	  
	    /** 
	     * 根据用户名查询用户对象 
	     * @return  List 
	     */  
	    List<User> queryUserList();  
	}

UserServiceImpl

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

当然，还有所谓的 applicationContext.xml 配置，不过，我这里叫 spring-mybatis.xml。


	<?xml version="1.0" encoding="UTF-8"?>  
	<beans xmlns="http://www.springframework.org/schema/beans"  
	       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
	       xmlns:p="http://www.springframework.org/schema/p"  
	       xmlns:context="http://www.springframework.org/schema/context"  
	       xmlns:mvc="http://www.springframework.org/schema/mvc"  
	       xsi:schemaLocation="http://www.springframework.org/schema/beans  
	       http://www.springframework.org/schema/beans/spring-beans.xsd  
	        http://www.springframework.org/schema/context  
	        http://www.springframework.org/schema/context/spring-context.xsd  
	        http://www.springframework.org/schema/mvc  
	        http://www.springframework.org/schema/mvc/spring-mvc.xsd">  
	  
	    <!-- 自动扫描 -->  
	    <context:component-scan base-package="com.sica"/>  
	    <!-- 引入配置文件 -->  
	    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer"  
	          p:location="classpath:jdbc.properties"  
	          />  
	  
	    <!-- 配置数据库连接池 -->  
	    <!-- 初始化连接大小 -->  
	    <!-- 连接池最大数量 -->  
	    <!-- 连接池最大空闲 -->  
	    <!-- 连接池最小空闲 -->  
	    <!-- 获取连接最大等待时间 -->  
	    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close"  
	          p:driverClassName="${jdbc.driver}"  
	          p:url="${jdbc.url}"  
	          p:username="${jdbc.username}"  
	          p:password="${jdbc.password}"  
	          p:initialSize="${jdbc.initialSize}"  
	          p:maxActive="${jdbc.maxActive}"  
	          p:maxIdle="${jdbc.maxIdle}"  
	          p:minIdle="${jdbc.minIdle}"  
	          p:maxWait="${jdbc.maxWait}"  
	          />  
	  
	    <!-- spring和MyBatis完美整合，不需要mybatis的配置映射文件 -->  
	    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean" lazy-init="default"  
	          p:dataSource-ref="dataSource"  
	          p:mapperLocations="classpath:com/sica/mapping/*.xml"  
	          />  
	  
	    <!-- DAO接口所在包名，Spring会自动查找其下的类 -->  
	    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer"  
	          p:basePackage="com.sica.dao"  
	          p:sqlSessionFactoryBeanName="sqlSessionFactory"  
	          />  
	  
	    <!-- (事务管理)transaction manager, use JtaTransactionManager for global tx -->  
	    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager"  
	          p:dataSource-ref="dataSource"  
	          />  
	</beans>

最后，我用到的是 junit 进行的测试，测试代码如下。

GetUserTest


	package com.sica.user;  
	  
	import com.alibaba.fastjson.JSON;  
	import com.sica.domain.User;  
	import com.sica.service.IUserService;  
	import org.junit.Test;  
	import org.junit.runner.RunWith;  
	import org.slf4j.Logger;  
	import org.slf4j.LoggerFactory;  
	import org.springframework.test.context.ContextConfiguration;  
	import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;  
	  
	import javax.annotation.Resource;  
	import java.util.List;  
	  
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
	    public void test() {  
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


测试结果
----------




可以看到，所有的用户和用户对应的角色都全部查出来了，这说明，这次的测试很成功。


关于优化
----------


对于优化嘛，我这里简单的提几点，大家可以考虑一下。首先，就是对表的设计，在设计表初期，不仅仅要考虑到数据库的规范性，还好考虑到所谓的业务，以及对性能的影响，比如，如果从规范性角度考虑的话，可能就会分多个表，但是如果从性能角度来考虑的话，庞大的数据量在多表联合查询的时候，相对于单表来说，就会慢很多，这时，如果字段不是很多的话，可以考虑冗余几个字段采用单表的设计。

其次嘛，就是在 sql 上下功夫了，对于联合查询，sql 的优化是很有必要的，到底是采用 INNER JOIN，还是采用 LEFT JOIN 亦或是 RIGHT JOIN 、OUTTER JOIN 等，都是要在满足业务需求之后，通过测试性能得出的结论。

再次嘛，就是在程序中调用的时候了，是采用懒加载，还是采用非懒加载的方式，这也算是一个因素吧，具体的还是要考虑业务的需要。

最后嘛，就要用到数据库的缓存了，或者在数据库与程序的中间再加一层缓存。不过，还是建议用好数据库本身自带的缓存功能。


结束语
----------


最后的最后，还是要提醒大家，不要因为觉得简单就引不起足够的重视，否则你会由于一点点小的失误，而浪费大把大把的时间的。我就在这上边体会过，所以，这里奉劝大家，还是小心为妙啊@！