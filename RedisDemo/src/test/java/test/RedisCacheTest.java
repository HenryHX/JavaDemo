package test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Administrator
 * 2019-04-11
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext-*.xml")
public class RedisCacheTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisPoolConfig jedisPoolConfig;

    @Autowired
    @Qualifier("clusterRedisTemplate")
    private RedisTemplate clusterRedisTemplate;


    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testFindUserById() {
        String user = userService.findUserById(12L);
        System.out.println(user);
        String user2 = userService.findUserById(15L);
        System.out.println(user2);
        String user3 = userService.findUserById(12111L);
        System.out.println(user3);
    }

    @Test
    public void testDeleteUserById() {
        userService.deleteUserById(12L);
        System.out.println("删除成功");
    }

    @Test
    public void testUpdateUserById() {
        String user = userService.updateUserById(12L, "luct");
        System.out.println(user);
    }

    @Test
    public void testRedisTemplate() {
        redisTemplate.opsForValue().set("test1", "gsuhfdapkf[a");
    }


    @Test
    public void testClusterRedisTemplate() {
        clusterRedisTemplate.opsForValue().set("test1", "gsuhfdapkf[a1111111111111111");
    }

    @Test
    public void testJedisCluster() {
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("127.0.0.1", 7001));
        nodes.add(new HostAndPort("127.0.0.1", 7002));
        nodes.add(new HostAndPort("127.0.0.1", 7003));
        nodes.add(new HostAndPort("127.0.0.1", 7004));
        nodes.add(new HostAndPort("127.0.0.1", 7005));
        nodes.add(new HostAndPort("127.0.0.1", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);

        jedisCluster.set("test2", "gsuhfdapkf[a1111");

    }
}