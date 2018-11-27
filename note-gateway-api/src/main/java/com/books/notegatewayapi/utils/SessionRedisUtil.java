package com.books.notegatewayapi.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.books.notebasecore.util.ServiceResult;
import com.books.notebasecore.util.SystemException;
import com.books.notegatewayapi.service.system.IUserMstService;
import com.books.notesystemservers.entity.UserMstEntity;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessionRedisUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(SessionRedisUtil.class);

    @Autowired
    private IUserMstService userMstService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @Value("${environment.config}")
    private String environmentConfig;

    public static void main(String[] args) {
    }

    public void saveSessionCache(int userId) {
        Map<String, Object> params = null;

        try {
            params = new HashMap<String, Object>();
            params.put("userId", userId);
            ServiceResult<UserMstEntity> result = userMstService
                    .findInfo(params);
            if (result.getSuccess() && result.getResult() != null){
                UserMstEntity userMstEntity = result.getResult();
                saveCache(String.valueOf(userId), userMstEntity.getUserId()+"");
            }
        } catch (SystemException e) {
            e.printStackTrace();
        }
    }

    public void saveCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(environmentConfig + key, value);
    }

    public String getCache(String key) {
        String result = stringRedisTemplate.opsForValue().get(environmentConfig + key);
        return result;
    }

    public void removeCache(String key) {
        if (stringRedisTemplate.hasKey(environmentConfig + key)) {
            String result = stringRedisTemplate.opsForValue().get(environmentConfig + key);
            stringRedisTemplate.delete(environmentConfig + key);
            result = stringRedisTemplate.opsForValue().get(environmentConfig + key);
        }

    }
}
