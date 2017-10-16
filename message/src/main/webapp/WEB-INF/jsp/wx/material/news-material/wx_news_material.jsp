<%@ page contentType="text/html;charset=UTF-8"%>


<div class="breadcrumbs ace-save-state" id="breadcrumbs">
	<ul class="breadcrumb">
		<li>
			<i class="ace-icon fa fa-home home-icon"></i>
			<a href="#">微信素材管理</a>
		</li>
		<li class="active">图文素材</li>
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
			图文素材
			<small>
				<i class="ace-icon fa fa-angle-double-right"></i>
				图文素材 &amp; 微信
			</small>
		</h1>
	</div><!-- /.page-header -->

	<div class="row">
		<div class="col-xs-12">
			<!-- PAGE CONTENT BEGINS -->
			<form action="${ctx}/wxMaterial/toWxNewsMaterialList.do" method="post" id="queryForm">
				<table class="table  table-bordered table-hover">
					<tr class="btn-info">
						<th width="100">多媒体ID</th>
						<th width="100">更新时间</th>
						
						<!-- <th>缩略图media_id</th> -->
						<th>封面图片</th>
						<!-- <th>作者</th> -->
						<th>标题</th>
						<!-- <th>阅读原文URL</th> -->
						<!-- <th>内容</th> -->
						<!-- <th>描述</th> -->
						<!-- <th>是否显示封面</th>
						<th>图文消息跳转链接</th> -->
					</tr>
					<c:forEach var="item" items="${itemLs}">
						<c:forEach var="article" items="${item.content.articles}" varStatus="status">
							<tr>
								<c:if test="${status.index eq 0 }">
									<td rowspan="${fn:length(item.content.articles)}">${item.mediaId }</td>
									<td rowspan="${fn:length(item.content.articles)}">
										<fmt:formatDate value="${item.updateTime}" pattern="yyyy-MM-dd HH:mm:ss" />
									</td>
								</c:if>
								
								<%-- <td>${article.thumbMediaId }</td> --%>
								<td>
									<a href="${ctx}/imageUtil/show.do?imageName=${article.thumbMediaId}" target="_blank">
										<img style="width:100px;height:100px;" alt="微信图片" src="${ctx}/imageUtil/show.do?imageName=${article.thumbMediaId}">
									</a>
								</td>
								<%--<td>${article.author }</td> --%>
								<td>
									<a href="${article.url }" target="_blank">${article.title }</a>
								</td>
								<%-- <td>${article.contentSourceUrl }</td> --%>
								<%-- <td>${article.content }</td> --%>
								<%-- <td>${article.digest }</td> --%>
								<%-- <td>${article.showCoverPic }</td>
								<td>${article.url }</td> --%>
							</tr>
						</c:forEach>
					</c:forEach>
				</table>
				<%@ include file="/pub/pagination.jsp" %>
			</form>
			<!-- PAGE CONTENT ENDS -->
		</div><!-- /.col -->
	</div><!-- /.row -->
</div><!-- /.page-content -->

