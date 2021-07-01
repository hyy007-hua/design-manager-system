layui.use("jquery",function () {
    var $ = layui.jquery;
    /**
     * @author hyy
     * 退出方法
     *
     */
    window.quit = function () {
        localStorage.clear();
        sessionStorage.clear();
        location.href = "../../index.html";
        $.ajax({
            url:"logout",
            type:"post",
            data:{"id":localStorage.getItem("userId")},
            success:function (callBack) {
            }
        });
    }

});