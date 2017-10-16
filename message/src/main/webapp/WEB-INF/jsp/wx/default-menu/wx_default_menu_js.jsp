<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	jQuery(function($){
		$('#nav-wx-menu-mgt').addClass('active open');
		$('#nav-wx-default-menu').addClass('active');
	});
	
	jQuery(function($){
		
		<c:forEach var="wxMenuItem" items="${wxMenuItemLs}">
			$("#isValid-${wxMenuItem.id}").val('${wxMenuItem.isValid}');
			$("#menuType-${wxMenuItem.id}").val('${wxMenuItem.menuType}');
			$("#replyType-${wxMenuItem.id}").val('${wxMenuItem.replyType}');
		</c:forEach>
		
		$("#btn-save-wx-default-menu").click(function(){
			bootbox.confirm("确认填写正确要提交了？",function(result){
				if(!result){
					return;
				}
				
				var wxMenuItemArray = [];
				$(".wxMenuItem").each(function(){
					var wxMenuItem = new Object();
					wxMenuItem.id = $.trim($(this).find(".id").val());
					wxMenuItem.menuRuleId = $.trim($(this).find(".menuRuleId").val());
					wxMenuItem.menuName = $.trim($(this).find(".menuName").val());
					wxMenuItem.isValid = $.trim($(this).find(".isValid").val());
					wxMenuItem.menuRank = $.trim($(this).find(".menuRank").val());
					wxMenuItem.menuType = $.trim($(this).find(".menuType").val());
					wxMenuItem.replyType = $.trim($(this).find(".replyType").val());
					wxMenuItem.menuKey = $.trim($(this).find(".menuKey").val());
					wxMenuItemArray.push(wxMenuItem);
				});
				/* var wxMenuItemsJson = encodeURI(JSON.stringify(wxMenuItemArray)); */
				var wxMenuItemsJson = JSON.stringify(wxMenuItemArray);
				var url = '${ctx}/wxMenu/saveWxMenu.do';
				$.ajax({
					url:url,
					data:wxMenuItemsJson,
					type:'post',
					contentType:'application/json;charset=UTF-8',
					success:function(resp){
						if(resp.code == '1'){
		       	   			bootbox.alert('保存成功',function(){
		       	   				window.location.href=window.location.href;
		       	   			});
		       			}else{
		       				bootbox.alert(resp.message);
		       			}
					}
				});
				/* $.post(url,wxMenuItemsJson,function(resp,status){
					if(resp.code == '1'){
	       	   			bootbox.alert('保存成功',function(){
	       	   				window.location.href=window.location.href;
	       	   			});
	       			}else{
	       				bootbox.alert(resp.message);
	       			}
				}); */
				/* $("#form-wx-default-menu").ajaxSubmit({
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
				}); */
			});
		});
	});
</script>