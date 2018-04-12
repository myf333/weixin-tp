package com.myf.weixin;

import com.myf.weixin.mapper.UserMapper;
import com.myf.weixin.model.WeChatUser;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WeixinApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Test
	public void testUser() {
		WeChatUser user = userMapper.findByOpenId("dddd");
		Assert.assertNull(user);
	}

	@Test
	public void testRedis(){
		String value = "ymj";
		String key = "myf";
		redisTemplate.opsForValue().set(key,value);

		String data = redisTemplate.opsForValue().get(key);
		Assert.assertEquals(value,data);
	}
}
