<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	jQuery(function($){
		$('#nav-sms-mgt').addClass('active open');
		$('#nav-sendsms').addClass('active');
	});
	
	jQuery(function($){
		$("#mobileSource").change(function(){
			if(this.value == 'CUSTOM'){
				$("#mobileDiv").show();
				$("#fileDiv").hide();
				$("#smsSendTemplate").hide();
			}
			if(this.value == 'EXCEL'){
				$("#mobileDiv").hide();
				$("#fileDiv").show();
				$("#smsSendTemplate").show();
			}
			if(this.value == 'REGISTER'){
				$("#mobileDiv").hide();
				$("#fileDiv").hide();
				$("#smsSendTemplate").hide();
			}
		});
		$("#btn-send-sms").click(function(){
			bootbox.confirm("确认填写正确要提交了？",function(result){
				if(!result){
					return;
				}
				var url = '${ctx}/sms/sendSms.do';
				$("#form-sms").ajaxSubmit({
					type: "POST",
					url:url,
					dataType: "json",
				    success: function(resp){
				    	if(resp.code == '1'){
		       	   			bootbox.alert('发送成功',function(){
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