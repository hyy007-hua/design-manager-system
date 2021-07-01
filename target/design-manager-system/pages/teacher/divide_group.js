layui.use(['jquery','form','element','upload','layer'],function () {
    var $ = layui.jquery,
        form = layui.form,
        element = layui.element,
        upload = layui.upload,
        layer = layui.layer;

    //表单选择栏预填充
    var teaId = localStorage.getItem("userId");
    var fileType = "groupList";
    var str = '';
    $.ajax({
        url:'/class/getClassInfoByTeaId',
        data:{"teaId":teaId},
        type:'post',
        success:function (callBack) {
            if(callBack.status == 200){
                var data = callBack.data;
                for(var key in data){
                    if(data[key]['isRight'] == "true")
                        str+='<option value="'+data[key]['value']+'">' + data[key]['title'] + '</option>';
                }
            }else{
                str+='<option value="'+0+'">'+callBack.msg+'</option>';
            }
            $("#classId").append(str);
            form.render('select');
        }
    });

    //随机分组表单提交
    form.on('submit(sure)',function(data){
        $.ajax({
            url:"/group/randomGrouping",
            type:"post",
            data:data.field,
            success:function(callback){
                if(callback.status === 200){
                    layer.alert("操作成功", {
                        icon: 6
                    }, function() {
                        var index = parent.layer.getFrameIndex(window.name);
                        parent.layer.close(index);
                    });
                }else{
                    layer.msg(callback.msg);
                }
            }
        });
        return false;
    });

    upload.render({
        elem: '#groupListFileBtn',
        url: '/file/uploadData',
        auto: false,
        accept: 'file',
        //,multiple: true
        data:{upId:teaId,fileType:fileType},   //额外数据
        bindAction: '#submitFile',
        choose: function(res) {
            //var files = res.pushFile();
            //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
            res.preview(function(index, file, result) {
                //console.log(index); //得到文件索引
                //console.log(file); //得到文件对象
                //console.log(result); //得到文件base64编码，比如图片
                $('input[name=data]').val(file.name);
                //console.log($('input[name=topicModel]').val())

            });
        },
        done: function(res, index, upload){  //上传的回调
            if(res.status == 200){
                layer.alert("操作成功", {
                    icon: 6
                }, function() {
                    // 获得frame索引
                    var index = parent.layer.getFrameIndex(window.name);
                    //关闭当前frame
                    parent.layer.close(index);
                });
            }else{
                layer.msg(res.msg);
            }
        }
    });

    //自主分组表单提交
    form.on('submit(go)',function(data){

        return false;
    });
});