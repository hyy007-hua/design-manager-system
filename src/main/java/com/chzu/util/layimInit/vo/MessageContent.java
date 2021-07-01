package com.chzu.util.layimInit.vo;

public class MessageContent {
    private String id;
    private String username;
    private String avatar;
    private String type;
    private String content;
    private String toId;

    public MessageContent() {
    }

    public MessageContent(String id, String username, String avatar, String type, String content, String toId) {
        this.id = id;
        this.username = username;
        this.avatar = avatar;
        this.type = type;
        this.content = content;
        this.toId = toId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }
}
