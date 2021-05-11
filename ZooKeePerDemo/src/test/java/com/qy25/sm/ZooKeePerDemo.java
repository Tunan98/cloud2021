package com.qy25.sm;

import com.qy25.sm.entity.User;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class ZooKeePerDemo {
    private static ZkClient zkClient;

    @Before
    public void Before(){
         zkClient = new ZkClient("192.168.184.132:2181", 60000 * 30, 60000, new SerializableSerializer());
        System.out.println(zkClient);
    }


    //创建节点
    @Test
    public void createNode(){
      //1.持久节点
        zkClient.create("/node1","zhangsan", CreateMode.PERSISTENT);
      //2.持久顺序节点
        zkClient.create("/node1/name","xiaohei", CreateMode.PERSISTENT_SEQUENTIAL);
      //3.临时节点
      zkClient.create("/node1/lists","xioaxioa",CreateMode.EPHEMERAL);
      //4.临时顺序节点
        zkClient.create("/node1/list1","xiaohong", CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    //4.查看节点
    @Test
    public void findNodeData(){
        Object o = zkClient.readData("/node1");
        System.out.println(o);
    }
    //5.查看节点状态信息
    @Test
    public void findNodeDataAndStat(){
        Stat stat = new Stat();
        Object o = zkClient.readData("/node1", stat);
        System.out.println(o);
        System.out.println(stat);

    }
    //6.删除节点
    @Test
    public void deleteNode(){
        //删除没有子节点的节点，返回值:是否删除成功！
//        boolean delete = zkClient.delete("/node1");

        //递归删除适合删除有子节点的node
        boolean b = zkClient.deleteRecursive("/node1");
        System.out.println(b);
    }
    @Test//7.修改节点
    public void setNode(){
        User user = new User();
        user.setName("小周");
        user.setAddress("地球");
        user.setId(11);
        user.setSex("男");
        zkClient.writeData("/node1",user);
        Object o = zkClient.readData("/node1");
    }

    //监听节点目录变化
    @Test
    public void ListenNodesChange() throws IOException {
        //当节点目录发送变化时，会自动调用这个方法,参数1:父节点名称,参数2:父节点中的所有子节点名称
        zkClient.subscribeChildChanges("/node1", new IZkChildListener() {

            public void handleChildChange(String nodeName, List<String> list) throws Exception {
                System.out.println("父节点名称:"+nodeName);
                System.out.println("发生变更后字节孩子节点名称:");
                for (String s : list) {
                    System.out.println(s);
                }
            }
        });
        //阻塞客户端
        System.in.read();
    }

    @After
    public void After(){
        try {
            //10秒之后线程，当前线程执行的zk关闭资源，回话结束，临时节点将被删除
            Thread.sleep(10000);
            zkClient.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
