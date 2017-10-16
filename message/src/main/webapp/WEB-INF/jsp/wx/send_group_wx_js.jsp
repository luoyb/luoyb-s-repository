<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	jQuery(function($){
		$('#nav-wx-mgt').addClass('active open');
		$('#nav-group-send-wx').addClass('active');
	});
	
	jQuery(function($){
		$("#btn-send-wx").click(function(){
			bootbox.confirm("确认填写正确要提交了？",function(result){
				if(!result){
					return;
				}
				var url = '${ctx}/wx/sendGroupWx.do';
				var formData = $("#form-wx").serialize();
		   		$.ajax({
		        	type:"post", url:url, dataType:"json", data:formData,
		       		success:function(resp){
		       			var obj = eval(resp);
		       	   		if(resp.code == '1'){
		       	   			bootbox.alert('发送成功',function(){
		       	   				window.location.href=window.location.href;
		       	   			});
		       			}else{
		       				bootbox.alert(obj.message);
		       			}
		       		}
		      	});
			});
		});
	});
</script>