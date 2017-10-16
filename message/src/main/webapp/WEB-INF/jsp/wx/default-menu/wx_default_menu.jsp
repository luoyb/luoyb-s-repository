<%@ page contentType="text/html;charset=UTF-8"%>


<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="ace-icon fa fa-home home-icon"></i>
			<a href="#">微信菜单管理</a>
		</li>
		<li class="active">微信默认菜单</li>
	</ul><!-- /.breadcrumb -->

	<div class="nav-search" id="nav-search">
		<form class="form-search">
			<span class="input-icon">
				<input type="text" placeholder="Search ..." class="nav-search-input" id="nav-search-input" autocomplete="off" />
				<i class="ace-icon fa fa-search nav-search-icon"></i>
			</span>
		</form>
	</div><!-- /.nav-search -->
</div>

<div class="page-content">
	<div class="ace-settings-container" id="ace-settings-container">
		<div class="btn btn-app btn-xs btn-warning ace-settings-btn" id="ace-settings-btn">
			<i class="ace-icon fa fa-cog bigger-130"></i>
		</div>

		<div class="ace-settings-box clearfix" id="ace-settings-box">
			<div class="pull-left width-50">
				<div class="ace-settings-item">
					<div class="pull-left">
						<select id="skin-colorpicker" class="hide">
							<option data-skin="no-skin" value="#438EB9">#438EB9</option>
							<option data-skin="skin-1" value="#222A2D">#222A2D</option>
							<option data-skin="skin-2" value="#C6487E">#C6487E</option>
							<option data-skin="skin-3" value="#D0D0D0">#D0D0D0</option>
						</select>
					</div>
					<span>&nbsp; Choose Skin</span>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-navbar" autocomplete="off" />
					<label class="lbl" for="ace-settings-navbar"> Fixed Navbar</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-sidebar" autocomplete="off" />
					<label class="lbl" for="ace-settings-sidebar"> Fixed Sidebar</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-breadcrumbs" autocomplete="off" />
					<label class="lbl" for="ace-settings-breadcrumbs"> Fixed Breadcrumbs</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-rtl" autocomplete="off" />
					<label class="lbl" for="ace-settings-rtl"> Right To Left (rtl)</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2 ace-save-state" id="ace-settings-add-container" autocomplete="off" />
					<label class="lbl" for="ace-settings-add-container">
						Inside
						<b>.container</b>
					</label>
				</div>
			</div><!-- /.pull-left -->

			<div class="pull-left width-50">
				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-hover" autocomplete="off" />
					<label class="lbl" for="ace-settings-hover"> Submenu on Hover</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-compact" autocomplete="off" />
					<label class="lbl" for="ace-settings-compact"> Compact Sidebar</label>
				</div>

				<div class="ace-settings-item">
					<input type="checkbox" class="ace ace-checkbox-2" id="ace-settings-highlight" autocomplete="off" />
					<label class="lbl" for="ace-settings-highlight"> Alt. Active Item</label>
				</div>
			</div><!-- /.pull-left -->
		</div><!-- /.ace-settings-box -->
	</div><!-- /.ace-settings-container -->

	<div class="page-header">
		<h1>
			微信默认菜单
			<small>
				<i class="ace-icon fa fa-angle-double-right"></i>
				默认菜单 &amp; 微信
			</small>
		</h1>
	</div><!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<table class="table  table-bordered table-hover">
				<tr class="">
					<th>菜单名称</th>
					<th>开关</th>
					<th>排序</th>
					<th>菜单类型</th>
					<th>消息类型</th>
					<th>跳转配置</th>
				</tr>
				<c:forEach var="wxMenuItem" items="${wxMenuItemLs}">
					<tr class="wxMenuItem <c:if test="${ wxMenuItem.menuLevel eq '1'}">btn-info</c:if>">
						<input class="id" type="hidden" value="${wxMenuItem.id }">
						<input class="menuRuleId" type="hidden" value="${wxMenuItem.menuRuleId }">
						<td><input class="menuName" id="menuName-${wxMenuItem.id }" type="text" value="${wxMenuItem.menuName }"></td>
						<td>
							<select id="isValid-${wxMenuItem.id}" class="isValid">
								<option value="0">关闭</option>
								<option value="1">开启</option>
							</select>
						</td>
						<td><input class="menuRank" id="menuRank-${wxMenuItem.id }" type="text" value="${wxMenuItem.menuRank }" size="3"></td>
						<td>
							<select id="menuType-${wxMenuItem.id}" class="menuType">
								<option value="">多级菜单</option>
								<option value="click">发送消息</option>
								<option value="view">跳转网页</option>
							</select>
						</td>
						<td>
							<select id="replyType-${wxMenuItem.id}" class="replyType">
								<option value="">无</option>
								<option value="text">文字</option>
								<option value="image">照片</option>
								<option value="voice">语音</option>
								<option value="video">视频</option>
								<option value="news">图文</option>
							</select>
						</td>
						<td><input class="menuKey" id="menuKey-${wxMenuItem.id}" type="text" value = "${wxMenuItem.content == null ? wxMenuItem.menuKey : wxMenuItem.content}"></td>
					</tr>
				</c:forEach>
			</table>
			
			<form action="${ctx}/wxMenu/saveWxDefaultMenu.do" method="post" id="form-wx-default-menu" enctype="multipart/form-data">
				<button type="button" class="btn btn-success btn-block" id="btn-save-wx-default-menu">保存</button>
			</form>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->

