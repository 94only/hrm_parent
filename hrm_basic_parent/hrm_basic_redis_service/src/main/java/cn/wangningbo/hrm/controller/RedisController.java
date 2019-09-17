package cn.wangningbo.hrm.controller;

import cn.wangningbo.hrm.util.RedisUtils;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

@RestController
@RequestMapping("/cache")
public class RedisController {
    @PostMapping
    public void set(@RequestParam("key") String key, @RequestParam("value") String value) {
        if (value.equals("[]"))
            RedisUtils.INSTANCE.getSource().setex(key, 5 * 60, value);
        else
            RedisUtils.INSTANCE.set(key, value);
    }

    @GetMapping
    public String get(@RequestParam("key") String key) {
        return RedisUtils.INSTANCE.get(key);
    }

    /**
     * 设置key-value并设置过期时间
     *
     * @param key
     * @param value
     * @param timeout
     */
    @PostMapping("/timeout")
    void set(@RequestParam("key") String key, @RequestParam("value") String value, @RequestParam("timeout") int timeout) {
        System.out.println(key);
        System.out.println(value);
        System.out.println(timeout);

        //获取连接
        Jedis jedis = RedisUtils.INSTANCE.getSource();
        // 存储key-value，并设置过期时间
        jedis.setex(key, timeout, value);
        //关闭连接
        jedis.close();
    }
}