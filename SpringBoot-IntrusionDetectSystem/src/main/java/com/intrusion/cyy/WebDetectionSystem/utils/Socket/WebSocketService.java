package com.intrusion.cyy.WebDetectionSystem.utils.Socket;

import com.intrusion.cyy.WebDetectionSystem.config.Encoder.ServerEncoder;
import com.intrusion.cyy.WebDetectionSystem.dao.Pojo.MyPacket;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;
@Component
@Slf4j
@ServerEndpoint(value = "/websocket",encoders = {ServerEncoder.class})
public class WebSocketService {
    //用来存放每个客户端对应的MyWebSocket对象。
    /*@Autowired
    Packet packet;*/
    private static CopyOnWriteArraySet<WebSocketService> webSocketSet = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("开始");
        this.session = session;
        webSocketSet.add(this);     //加入set中
        System.out.println("有新连接加入,连接数为：" + webSocketSet.size());
//        this.session.getAsyncRemote().sendText("恭喜您成功连接上WebSocket-->当前在线人数为："+webSocketSet.size());
    }
    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("有一连接关闭,连接数为：" + webSocketSet.size());
    }
    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息
//        broadcast(message);
    }
    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }
    /**
     * 群发自定义消息
     * */
//    public void broadcast(String message){
//        for (WebSocketService item : webSocketSet) {
//            //同步异步说明参考：http://blog.csdn.net/who_is_xiaoming/article/details/53287691
//            //this.session.getBasicRemote().sendText(message);
//            item.session.getAsyncRemote().sendText(message);//异步发送消息.
//        }
//    }

    //发送数据包
    public void sendPacket(MyPacket packet){

        for (WebSocketService webSocketService : webSocketSet){
            try {
                webSocketService.session.getBasicRemote().sendObject(packet);
            } catch (IOException e) {
                System.err.println(this + "发送消息错误:" + e.getClass() + ":" + e.getMessage());
            } catch (EncodeException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
