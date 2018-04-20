package com.example.zookeeper.example2_curator;

import java.util.concurrent.CountDownLatch;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.RetryNTimes;

/**
 * 客户端开源工具： “Curator” framework watch test.
 */
public class CuratorWatcherTest {

	/** Zookeeper info */
	private static final String ZK_ADDRESS = "10.200.11.196:2181";
//	private static final String ZK_ADDRESS = "10.200.69.90:2181";
//	private static final String ZK_ADDRESS = "master:2181,datanode1:2181,datanode2:2181";
	private static final String ZK_PATH = "/";
//	private static final String ZK_PATH = "/zktest";
	
	static CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws Exception {
        // 1.Connect to zk
        CuratorFramework client = CuratorFrameworkFactory.newClient( ZK_ADDRESS, new RetryNTimes(10, 5000) );
        client.start();
        System.out.println("zk client start successfully!");

        // 2.Register watcher
        PathChildrenCache watcher = new PathChildrenCache(
                client,
                ZK_PATH,
                true    // if cache data
        );
        watcher.getListenable().addListener(new PathChildrenCacheListener() {
			
			@Override
			public void childEvent(CuratorFramework client1, PathChildrenCacheEvent event)
					throws Exception {
				// TODO Auto-generated method stub
				ChildData data = event.getData();
	            if (data == null) {
	            	//连接断开会自动重连，此处会打印相关连接挂起重新连接日志
	                System.out.println("No data in event[" + event + "]");
	            } else {
	                System.out.println("Receive event: "
	                        + "type=[" + event.getType() + "]"
	                        + ", path=[" + data.getPath() + "]"
	                        + ", data=[" + new String(data.getData()) + "]"
	                        + ", stat=[" + data.getStat() + "]");
	            }
			}
		});
        
        watcher.start(StartMode.BUILD_INITIAL_CACHE);
        System.out.println("Register zk watcher successfully!");

        latch.await();
    }
}
