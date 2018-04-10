package com.myf.weixin;

import com.myf.weixin.mapper.UserMapper;
import com.myf.weixin.model.WeChatUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void testUser() {
		WeChatUser user = userMapper.findByOpenId("dddd");
		Assert.assertNull(user);
	}

}
