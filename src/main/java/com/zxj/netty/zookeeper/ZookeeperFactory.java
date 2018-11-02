package com.zxj.netty.zookeeper;

import com.zxj.netty.common.RpcConstants;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

public class ZookeeperFactory {

    private static CuratorFramework client = null;
    public static CuratorFramework create() {
        if (client == null) {
            // 间隔1s重连,重试3次
            RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
            client = CuratorFrameworkFactory.newClient(RpcConstants.REGISTRY_ADDR, policy);
            client.start();
        }
        return client;

    }

    public static void main(String[] args) throws Exception {
        CuratorFramework curatorFramework = create();
        curatorFramework.create().forPath("/" + RpcConstants.APP_NAME);
    }
}
