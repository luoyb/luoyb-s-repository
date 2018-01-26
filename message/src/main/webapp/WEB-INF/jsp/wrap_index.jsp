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

	<shiro:user>
	<body class="no-skin">
		<%@ include file="/pub/ace_header.jsp" %>

		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>

			<%@ include file="/pub/ace_sidebar.jsp" %>

			<div class="main-content">
				<div class="main-content-inner">
					<%@ include file="index.jsp" %>
				</div>
			</div>

			<%@ include file="/pub/ace_footer.jsp" %>

			<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
				<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
			</a>
		</div><!-- /.main-container -->
		
		<%@ include file="/pub/ace_js.jsp" %>
		
		<script src="index.js"></script>
	</body>
	</shiro:user>
	<shiro:guest>
		<div class="alert alert-block alert-success">
			<button type="button" class="close" data-dismiss="alert">
				<i class="ace-icon fa fa-times"></i>
			</button>
			<i class="ace-icon fa fa-check green"></i>
			游客，欢迎您来到
			<strong class="green">
				M信云系统，
			</strong>
			<a style="padding-left:20px;" href="${ctx}/login.jsp">请先登陆！</a>
		</div>
	</shiro:guest>
</html>
