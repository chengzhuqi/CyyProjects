package com.intrusion.cyy.WebDetectionSystem.config.Encoder;

import com.alibaba.fastjson.JSONObject;
import com.intrusion.cyy.WebDetectionSystem.dao.Pojo.MyPacket;
import jakarta.websocket.EncodeException;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @desc: WebSocket编码器
 * @author: CYY
 * @since: 2023/4/17
 */
public class ServerEncoder implements Encoder.Text<MyPacket> {
    private static final Logger log = LoggerFactory.getLogger(ServerEncoder.class);

    /**
     * 这里的参数 myPacket 要和  Encoder.Text<T>保持一致
     * @param myPacket
     * @return
     * @throws EncodeException
     */
    @Override
    public String encode(MyPacket myPacket) throws EncodeException {
        /*
         * 这里是重点，只需要返回Object序列化后的json字符串就行
         * 你也可以使用gosn，fastJson来序列化。
         * 这里我使用fastjson
         */
       try {
           return JSONObject.toJSONString(myPacket);
       }catch (Exception e){
           log.error("",e);
       }
        return null;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        //可忽略
    }

    @Override
    public void destroy() {
        //可忽略
    }
}

