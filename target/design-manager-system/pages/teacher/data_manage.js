//自定义的render渲染输出多列表格
var layout = [{
		name: '菜单名称',
		treeNodes: true,
		headerClass: 'value_col',
		colClass: 'value_col',
		style: 'width: 80%'
	},
	{
		name: '操作',
		headerClass: 'td-manage',
		colClass: 'td-manage',
		style: 'width: 20%',
		render: function(row) {
			if(row.children != null)
				return "";
			else
				return '<a title="下载"  href="/file/download?fileId=' + row.id + '"><i class="layui-icon">&#xe601;</i></a>' +
					'<a title="删除" onclick="del(' + row.id + ')"><i class="layui-icon">&#xe640;</i> </a>'; //列渲染
		}
	},
];

layui.extend({
	admin: '{/}../../static/js/admin',
	treeGird: '{/}../../layui/lay/treeGird' // {/}的意思即代表采用自有路径，即不跟随 base 路径
});
layui.use(['treeGird', 'jquery', 'admin', 'layer'], function() {
	var layer = layui.layer,
		$ = layui.jquery,
		admin = layui.admin,
		treeGird = layui.treeGird;

	var teaId = localStorage.getItem("userId");
	var tree1;

	$.ajax({
		url:"/teacher/queryDataByTeaId",
		data:{teaId:teaId},
		type:"post",
		success:function (res) {
			console.log("data:"+JSON.stringify(res.data));
			tree1 = treeGird({
				elem: '#demo', //传入元素选择器
				spreadable: true, //设置是否全展开，默认不展开
				nodes: res.data,
				layout: layout
			});
		}
	});

	window.downloadFile = function(fileId){
		console.log("downId:"+fileId);
		$.ajax({
			url:"/file/download",
			type:"post",
			data:{fileId:fileId},
			success:function (res) {

			}
		});
	}
	//删除按钮响应
	window.del = function(nodeId){
		layer.confirm('真的删除该文件么', function(index){
			//向服务端发送删除指令
			$.ajax({
				url:'/file/deleteById',
				type:'post',
				data:{'id':nodeId},
				success:function(callBack){
					if(callBack.state == 200){
						layer.msg("操作成功");
					}else{
						layer.msg(callBack.msg);
					}
					layer.close(index);
				}
			});
		});
	}


	$('#collapse').on('click', function() {
		layui.collapse(tree1);
	});

	$('#expand').on('click', function() {
		layui.expand(tree1);
	});
});