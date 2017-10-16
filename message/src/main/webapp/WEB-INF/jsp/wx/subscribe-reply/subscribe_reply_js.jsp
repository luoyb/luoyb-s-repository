<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	jQuery(function($){
		$('#nav-wx-reply-mgt').addClass('active open');
		$('#nav-wx-subscribe-reply').addClass('active');
	});
	
	jQuery(function($){
		
		if('${replyMsg.replyType}' != ''){
			$("#replyType").val('${replyMsg.replyType}');
		}
		
		$("#replyType").change(function(){
			$.get("${ctx}/wxReply/findUniqueReply.do?msgType=subscribe",function(data,status){
				if(data != null && $("#replyType").val() == data.replyType){
					$("#content").val(data.content);
				}else{
					$("#content").val('');
				}
			});
		});
		
		$("#btn-save-subscribe-reply").click(function(){
			bootbox.confirm("确认填写正确要提交了？",function(result){
				if(!result){
					return;
				}
				var url = '${ctx}/wxReply/saveSubscribeReply.do';
				$("#form-subscribe-reply").ajaxSubmit({
					type: "POST",
					url:url,
					dataType: "json",
				    success: function(resp){
				    	if(resp.code == '1'){
		       	   			bootbox.alert('保存成功',function(){
		       	   				window.location.href=window.location.href;
		       	   			});
		       			}else{
		       				bootbox.alert(resp.message);
		       			}
					}
				});
			});
		});
	});
</script>