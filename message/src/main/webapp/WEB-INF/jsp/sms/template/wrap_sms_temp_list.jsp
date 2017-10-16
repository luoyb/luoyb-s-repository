<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/pub/tag.jsp" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Dashboard - Ace Admin</title>

		<meta name="description" content="overview &amp; stats" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<%@ include file="/pub/ace_css.jsp" %>
	</head>

	<body class="no-skin">
		<%@ include file="/pub/ace_header.jsp" %>

		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>

			<%@ include file="/pub/ace_sidebar.jsp" %>
			
			<div class="main-content">
				<div class="main-content-inner">
					<!-- 需进行替换的主体内容 -->
					<%@ include file="sms_temp_list.jsp" %>
				</div>
			</div>

			<%@ include file="/pub/ace_footer.jsp" %>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		<%@ include file="/pub/ace_js.jsp" %>
		
		<!-- 需进行替换的主体内容js -->
		<%@ include file="sms_temp_list_js.jsp" %>
	</body>
</html>
