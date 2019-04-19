package test;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator
 * 2019-04-11
 */

@Service
public class UserService {

    @Cacheable(value = "AccountCache", key = "'USER:' + #userId")
    public String findUserById(Long userId) {
        System.out.println("real find user by id" + userId);
        return userId + "username:lili, age:18";
    }

    @CacheEvict(value = "AccountCache", key = "'USER:' + #userId", beforeInvocation = true)
    public void deleteUserById(Long userId) {
        System.out.println("real delete user by id" + userId);
    }

    @CachePut(value = "UserCache", keyGenerator = "keyGenerator")
    public String updateUserById(Long userId, String userName) {
        System.out.println("real update user by id" + userId);
        return userId + "username:" + userName + ", age:18";
    }
}