package com.chzu.websocket.controller;

import com.chzu.util.Constant;
import com.chzu.util.JSONResult;
import com.chzu.util.layimInit.vo.MessageContent;
import com.chzu.websocket.MyWebSocketHandler;
import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

@Controller
@RequestMapping("websocket")
public class WebSocketController {

    @Bean//这个注解会从Spring容器拿出Bean
    public MyWebSocketHandler infoHandler() {
        return new MyWebSocketHandler();
    }

    @PostMapping("sendMessage")
    @ResponseBody
    public JSONResult sendMessage(MessageContent message){
        System.out.println(message.getUsername() + "发送消息给" + message.getToId() + "，内容是" + message.getContent());
        Gson gson = new Gson();
        String messageJson = gson.toJson(message);
        TextMessage textMessage = new TextMessage(messageJson);
        infoHandler().sendToSinglePerson(message.getToId(),textMessage);
        if (Constant.onlineUsers.containsKey(message.getToId()))
            return JSONResult.ok("用户在线");
        else return JSONResult.ok("用户离线");
    }
}
