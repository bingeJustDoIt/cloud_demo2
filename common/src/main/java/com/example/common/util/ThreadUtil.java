package com.example.common.util;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@Component
@Slf4j
//@SuppressWarnings("all")
public class ThreadUtil {
    private final Map<ThreadPoolEnum, ThreadPoolTaskExecutor> poolMap = new ConcurrentHashMap<>();

    public synchronized ThreadPoolTaskExecutor getPool(ThreadPoolEnum threadPoolEnum) {

        ThreadPoolTaskExecutor pool = poolMap.get(threadPoolEnum);
        if (pool != null) {
            return pool;
        }
        //初始化的时候poolMap为空
        ThreadPoolTaskExecutor target = null;
        switch (threadPoolEnum) {
            case HEART_BEAT:
                target = init(10, 30, 60, 50, "HEART-BEAT-");
                poolMap.put(threadPoolEnum, target);
                break;
            case REDIS_CONNECTION_TEST:
                target = init(10, 30, 60, 50, "REDIS-CONNECTION-TEST-");
                poolMap.put(threadPoolEnum, target);
                break;
            default:
                target = init(10, 30, 60, 1000, "DEFAULT-POOL-");
                break;
        }
        if (target == null) {
            throw new RuntimeException("threadPool not found");
        }
        return target;
    }


    @SneakyThrows
    public void submitToHeartbeatPool(Runnable runnable) {
        ThreadPoolTaskExecutor heartbeatPool = getPool(ThreadPoolEnum.HEART_BEAT);
        heartbeatPool.submit(runnable);
    }


    @SneakyThrows
    public void submitToRedisConnectionTest(FutureTask<Boolean> callable) {
        ThreadPoolTaskExecutor heartbeatPool = getPool(ThreadPoolEnum.REDIS_CONNECTION_TEST);
        heartbeatPool.submit(callable);
    }

    @SneakyThrows
    public Future submit(Runnable runnable) {
        ThreadPoolTaskExecutor heartbeatPool = getPool(ThreadPoolEnum.DEFAULT);
        return heartbeatPool.submit(() -> {
            runnable.run();
            return null;
        });
    }


    public Future submit(Callable callable) {
        ThreadPoolTaskExecutor heartbeatPool = getPool(ThreadPoolEnum.DEFAULT);
        return heartbeatPool.submit(callable);
    }


  /*  public void adjustPool(int core, int max, int alive, int queue, ThreadPoolEnum threadPoolEnum) {
        ThreadPoolTaskExecutor taskExecutor = getPool(threadPoolEnum);
        taskExecutor.setCorePoolSize(core);
        taskExecutor.setMaxPoolSize(max);
        taskExecutor.setKeepAliveSeconds(alive);
        taskExecutor.setQueueCapacity(queue);
    }*/


    public ThreadPoolTaskExecutor init(int core, int max, int alive, int queue, String prefix) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setTaskDecorator(runnable -> {
            try {
                Optional<Map<String, String>> contextMapOptional = Optional.ofNullable(MDC.getCopyOfContextMap());
                return () -> {
                    try {
                        contextMapOptional.ifPresent(MDC::setContextMap);
                        runnable.run();
                    } finally {
                        MDC.clear();
                    }
                };
            } catch (Exception e) {
                log.error("ThreadPoolTaskExecutor init err:",e);
                return runnable;
            }
        });
        taskExecutor.setCorePoolSize(core);
        taskExecutor.setMaxPoolSize(max);
        taskExecutor.setKeepAliveSeconds(alive);
        taskExecutor.setQueueCapacity(queue);
        taskExecutor.setThreadFactory(ThreadFactoryBuilder.create().setNamePrefix(prefix).build());
        taskExecutor.initialize();
        return taskExecutor;
    }


    public enum ThreadPoolEnum {
        DEFAULT,
        /**
         * 运维心跳
         */
        HEART_BEAT,
        /**
         * redis连接检测
         */
        REDIS_CONNECTION_TEST,

    }

}
