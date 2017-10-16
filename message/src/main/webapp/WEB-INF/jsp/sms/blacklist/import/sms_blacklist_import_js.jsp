<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	jQuery(function($){
		$('#nav-sms-blacklist-mgt').addClass('active open');
		$('#nav-sms-blacklist-import').addClass('active');
	});
	
	jQuery(function($){
		$("#btn-sms-blacklist-import").click(function(){
			bootbox.confirm("确认填写正确要提交了？",function(result){
				if(!result){
					return;
				}
				var url = '${ctx}/smsBlacklist/importExcel.do';
				$("#form-sms-blacklist-import").ajaxSubmit({
					type: "POST",
					url:url,
					dataType: "json",
				    success: function(resp){
				    	if(resp.code == '1'){
		       	   			bootbox.alert('导入成功',function(){
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