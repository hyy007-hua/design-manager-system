package com.chzu.util;

import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.Map;

/**
 * @desc 描述全局变量
 *
 */
public class Constant {
    /**
     * onlineUser表，用userId为键，存放在线的用户WebSocketSession
     */
    public static final Map<String, WebSocketSession> onlineUsers = new HashMap<>();

    /**
     * 发送邮件验证码的存储表，以用户账号为键，存放邮件发送的验证码
     */
    public static final Map<String, String> codeMap = new HashMap<>();
}
