//链接websocket
var socket = new WebSocket('ws://localhost:8080/chat');

if (socket) {
    // 连接发生错误的回调方法
    socket.onerror = function() {
        console.log("连接失败");
    };
    // 连接成功建立的回调方法
    socket.onopen = function(event) {
        console.log("连接成功");
    }


    // 连接关闭的回调方法
    socket.onclose = function() {
        console.log("关闭连接");
    }
}

layui.use(['layim','jquery'], function(layim){
    var $ = layui.jquery;

    var userId = localStorage.getItem("userId");
    var username = localStorage.getItem("username");
    var userType = localStorage.getItem("userType");
    layim.config({
        init:{
            url: '/config/init'     //
            ,type:"post"
            ,data: {'id':userId,"type":userType}
        }
        //brief: true //是否简约模式（如果true则不显示主面板）
        //获取群员接口（返回的数据格式见下文）
        ,members: {
            url: '/config/getMemberByGroId' //接口地址（返回的数据格式见下文）
            ,type: 'post' //默认get，一般可不填
            ,data: {} //额外参数
        }
        //上传图片接口（返回的数据格式见下文），若不开启图片上传，剔除该项即可
        ,uploadImage: {
            url: '' //接口地址
            ,type: 'post' //默认post
        }
        //上传文件接口（返回的数据格式见下文），若不开启文件上传，剔除该项即可
        ,uploadFile: {
            url: '' //接口地址
            ,type: 'post' //默认post
        }
        //,isAudio: true //开启聊天工具栏音频
        //,isVideo: true //开启聊天工具栏视频
        ,notice: true //是否开启桌面消息提醒，默认false
        //扩展工具栏，下文会做进一步介绍（如果无需扩展，剔除该项即可）
        ,tool: [{
            alias: 'code' //工具别名
            ,title: '代码' //工具名称
            ,icon: '&#xe64e;' //工具图标，参考图标文档
        }]
        ,msgbox: layui.cache.dir + 'css/modules/layim/html/msgbox.html' //消息盒子页面地址，若不开启，剔除该项即可
        ,find: layui.cache.dir + 'css/modules/layim/html/find.html' //发现页面地址，若不开启，剔除该项即可
        ,chatLog: layui.cache.dir + 'css/modules/layim/html/chatlog.html' //聊天记录页面地址，若不开启，剔除该项即可

    });

    // 接收到消息的回调方法
    socket.onmessage = function(event) {
        var data = eval('('  + event.data +  ')');
        console.log("websocket收到消息：" + JSON.stringify(data));
        var obj = {
            username: data.username
            ,avatar: data.avatar
            ,id: data.id
            ,type: data.type
            ,content: data.content
        };

        layim.getMessage(obj);
    }

    //监听在线状态的切换事件
    layim.on('online', function(data){
        console.log(data);
    });
    //监听签名修改
    layim.on('sign', function(value){
        //console.log(value);
    });

    //监听自定义工具栏点击，以添加代码为例
    layim.on('tool(code)', function(insert){
        layer.prompt({
            title: '插入代码'
            ,formType: 2
            ,shade: 0
        }, function(text, index){
            layer.close(index);
            insert('[pre class=layui-code]' + text + '[/pre]'); //将内容插入到编辑器
        });
    });

    //监听layim建立就绪
    layim.on('ready', function(res){

    });

    //监听发送消息
    layim.on('sendMessage', function(data){
        var message = {};
        message.username = data.mine.username;
        message.id = data.mine.id;
        message.avatar = data.mine.avatar;
        message.type = data.to.type;
        message.content = data.mine.content;
        message.toId = data.to.id;
        console.log(JSON.stringify(message));

        //socket.send(JSON.stringify(data));  采用websocket连接发送消息。

        //采用ajax发送消息
        $.ajax({
            url:"/websocket/sendMessage",
            type:"post",
            data:message,
            success:function (feedback) {
                layim.setChatStatus('<span style="color:#FF5722;">' + feedback.msg +'</span>');
            }
        });

    });
    //监听查看群员
    layim.on('members', function(data){
        //console.log('查看群成员'+JSON.stringify(data));
    });

    //监听聊天窗口的切换
    layim.on('chatChange', function(res){
        //console.log("聊天窗口切换："+JSON.stringify(res));
    });
});