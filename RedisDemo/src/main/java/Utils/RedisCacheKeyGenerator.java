package Utils;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by Administrator
 * 2019-04-11
 */
@Component(value = "keyGenerator")
public class RedisCacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object o, Method method, Object... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(o.getClass().getName());
        sb.append(method.getName());
        for (Object param : params) {
            sb.append(param.toString());
        }
        return sb.toString();
    }
}
