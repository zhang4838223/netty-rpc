package com.zxj.netty.model;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DefaultFuture {
    public final static ConcurrentHashMap<Long, DefaultFuture> ALL_FAUTUE = new ConcurrentHashMap<>();

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    private NettyResponse response;
    public DefaultFuture(NettyRequest request) {
        ALL_FAUTUE.put(request.getId(), this);
    }

    public NettyResponse get() {
        lock.lock();
        try {
            while (!done()) {
                condition.await(60, TimeUnit.SECONDS);
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return this.response;
    }

    // handler解析服务端返回
    public static void receive(NettyResponse response) {
        DefaultFuture defaultFuture = ALL_FAUTUE.get(response.getId());
        if (defaultFuture != null) {
            defaultFuture.lock.lock();

            try {
                defaultFuture.setResponse(response);
                defaultFuture.condition.signal();
                ALL_FAUTUE.remove(defaultFuture);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                defaultFuture.lock.unlock();
            }
        }
    }

    private boolean done() {
        return this.response != null;
    }

    public void setResponse(NettyResponse response) {
        this.response = response;
    }

    public NettyResponse getResponse() {
        return response;
    }
}
