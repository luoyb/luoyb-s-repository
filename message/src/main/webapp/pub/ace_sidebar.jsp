<%@ page contentType="text/html;charset=UTF-8"%>

<div id="sidebar" class="sidebar                  responsive                    ace-save-state">
	<script type="text/javascript">
		try{ace.settings.loadState('sidebar')}catch(e){}
	</script>

	<div class="sidebar-shortcuts" id="sidebar-shortcuts">
		<div class="sidebar-shortcuts-large" id="sidebar-shortcuts-large">
			<button class="btn btn-success">
				<i class="ace-icon fa fa-signal"></i>
			</button>

			<button class="btn btn-info">
				<i class="ace-icon fa fa-pencil"></i>
			</button>

			<button class="btn btn-warning">
				<i class="ace-icon fa fa-users"></i>
			</button>

			<button class="btn btn-danger">
				<i class="ace-icon fa fa-cogs"></i>
			</button>
		</div>

		<div class="sidebar-shortcuts-mini" id="sidebar-shortcuts-mini">
			<span class="btn btn-success"></span>

			<span class="btn btn-info"></span>

			<span class="btn btn-warning"></span>

			<span class="btn btn-danger"></span>
		</div>
	</div><!-- /.sidebar-shortcuts -->

	<ul class="nav nav-list">
		<li id="nav-index">
			<a href="${ctx}/wrap_index.jsp">
				<i class="menu-icon fa fa-tachometer"></i>
				<span class="menu-text"> Dashboard </span>
			</a>

			<b class="arrow"></b>
		</li>
		
		<li id="nav-customer-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					客户管理
				</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				
				<li id="nav-customer-list">
					<a href="${ctx}/customer/toListPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						客户列表
					</a>

					<b class="arrow"></b>
				</li>
				
			</ul>
		</li>

		<li id="nav-sms-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list"></i>
				<span class="menu-text"> 短信管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li id="nav-sendsms">
					<a href="${ctx}/sms/toSendSms.do">
						<i class="menu-icon fa fa-caret-right"></i>
						发送短信
					</a>

					<b class="arrow"></b>
				</li>

				<li id="nav-sendhist">
					<a href="${ctx}/sms/toSmsSendHistPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						短信发送历史
					</a>

					<b class="arrow"></b>
				</li>
				
			</ul>
		</li>
		
		
		
		<li id="nav-sms-blacklist-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-list"></i>
				<span class="menu-text"> 短信黑名单管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li id="nav-sms-blacklist-import">
					<a href="${ctx}/smsBlacklist/toImportPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						导入黑名单
					</a>
					<b class="arrow"></b>
				</li>

				<li id="nav-sms-blacklist">
					<a href="${ctx}/smsBlacklist/toListPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						黑名单列表
					</a>

					<b class="arrow"></b>
				</li>
				
			</ul>
		</li>
		
		
		<li id="nav-smsTemp-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-pencil-square-o"></i>
				<span class="menu-text"> 短信模板管理 </span>

				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
			
				<shiro:hasPermission name="sms-template:verify">
				<li id="nav-waitverify-smstemp">
					<a href="${ctx}/smsTemplate/toWaitVerifyListPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						待审核短信模板
					</a>

					<b class="arrow"></b>
				</li>
				</shiro:hasPermission>

				<li id="nav-smstemp">
					<a href="${ctx}/smsTemplate/toListPage.do">
						<i class="menu-icon fa fa-caret-right"></i>
						短信模板列表
					</a>

					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="nav-wx-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					微信管理
				</span>

				<b class="arrow fa fa-angle-down"></b>
			</a>
			<b class="arrow"></b>
			<ul class="submenu">
				<li id="nav-group-send-wx">
					<a href="${ctx}/wx/toSendGroupWx.do">
						<i class="menu-icon fa fa-caret-right"></i>
						群发微信消息
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			
			<ul class="submenu">
				<li id="nav-wx-user-group">
					<a href="${ctx}/wx/toWxUserGroup.do">
						<i class="menu-icon fa fa-caret-right"></i>
						微信用户分组
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="nav-wx-reply-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					微信自动回复管理
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li id="nav-wx-subscribe-reply">
					<a href="${ctx}/wxReply/toSubscribeReply.do">
						<i class="menu-icon fa fa-caret-right"></i>
						被添加自动回复
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			<ul class="submenu">
				<li id="nav-wx-text-default-reply">
					<a href="${ctx}/wxReply/toTextDefaultReply.do">
						<i class="menu-icon fa fa-caret-right"></i>
						消息自动回复
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			<ul class="submenu">
				<li id="nav-wx-text-keyword-reply">
					<a href="${ctx}/wxReply/toTextKeywordReplyList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						关键词自动回复
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="nav-wx-menu-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					微信菜单管理
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li id="nav-wx-default-menu">
					<a href="${ctx}/wxMenu/toWxDefaultMenuList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						微信默认菜单
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			
			<ul class="submenu">
				<li id="nav-wx-conditional-menu-rule">
					<a href="${ctx}/wxMenu/toWxConditionalMenuRuleList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						微信个性化菜单
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
		<li id="nav-wx-material-mgt">
			<a href="#" class="dropdown-toggle">
				<i class="menu-icon fa fa-desktop"></i>
				<span class="menu-text">
					微信素材管理
				</span>
				<b class="arrow fa fa-angle-down"></b>
			</a>

			<b class="arrow"></b>

			<ul class="submenu">
				<li id="nav-wx-image-material">
					<a href="${ctx}/wxMaterial/toWxImageMaterialList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						图片素材
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			
			<ul class="submenu">
				<li id="nav-wx-voice-material">
					<a href="${ctx}/wxMaterial/toWxVoiceMaterialList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						语音素材
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			<ul class="submenu">
				<li id="nav-wx-video-material">
					<a href="${ctx}/wxMaterial/toWxVideoMaterialList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						视频素材
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
			<ul class="submenu">
				<li id="nav-wx-news-material">
					<a href="${ctx}/wxMaterial/toWxNewsMaterialList.do">
						<i class="menu-icon fa fa-caret-right"></i>
						图文素材
					</a>
					<b class="arrow"></b>
				</li>
			</ul>
		</li>
		
	</ul><!-- /.nav-list -->

	<div class="sidebar-toggle sidebar-collapse" id="sidebar-collapse">
		<i id="sidebar-toggle-icon" class="ace-icon fa fa-angle-double-left ace-save-state" data-icon1="ace-icon fa fa-angle-double-left" data-icon2="ace-icon fa fa-angle-double-right"></i>
	</div>
</div>