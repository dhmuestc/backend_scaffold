package com.example.backend_scaffold.infrastructure.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;

/**
 * 缓存服务类
 * <p>
 * 提供缓存的存取方法，使用Spring Cache抽象，支持不同的缓存实现
 * </p>
 *
 * @author example
 */
@Service
public class CacheService {

    private final CacheManager cacheManager;

    /**
     * 构造函数
     *
     * @param cacheManager 缓存管理器
     */
    public CacheService(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    /**
     * 获取缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param type      值类型
     * @param <T>       泛型参数
     * @return 缓存值
     */
    public <T> T get(String cacheName, Object key, Class<T> type) {
        Cache cache = getCache(cacheName);
        return cache.get(key, type);
    }

    /**
     * 获取缓存，如果不存在则通过valueLoader加载
     *
     * @param cacheName   缓存名称
     * @param key         缓存键
     * @param valueLoader 值加载器
     * @param <T>         泛型参数
     * @return 缓存值
     */
    public <T> T get(String cacheName, Object key, Callable<T> valueLoader) {
        Cache cache = getCache(cacheName);
        return cache.get(key, valueLoader);
    }

    /**
     * 放入缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     * @param value     缓存值
     */
    public void put(String cacheName, Object key, Object value) {
        Cache cache = getCache(cacheName);
        cache.put(key, value);
    }

    /**
     * 删除缓存
     *
     * @param cacheName 缓存名称
     * @param key       缓存键
     */
    public void evict(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        cache.evict(key);
    }

    /**
     * 清空缓存
     *
     * @param cacheName 缓存名称
     */
    public void clear(String cacheName) {
        Cache cache = getCache(cacheName);
        cache.clear();
    }

    /**
     * 获取缓存对象
     *
     * @param cacheName 缓存名称
     * @return 缓存对象
     */
    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            throw new IllegalArgumentException("Cache '" + cacheName + "' not found");
        }
        return cache;
    }
}