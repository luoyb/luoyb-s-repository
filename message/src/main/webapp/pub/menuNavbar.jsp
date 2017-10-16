<%@ page contentType="text/html;charset=UTF-8"%>

<nav class="navbar navbar-default" role="navigation">
   <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" 
         data-target="#menu-navbar-collapse">
         <span class="sr-only">切换导航</span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
         <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="#">消息系统</a>
   </div>
   <div class="collapse navbar-collapse" id="menu-navbar-collapse">
      <ul class="nav navbar-nav">
        <%--  <li id="li-send-sms"><a href="${ctx}/sms/toSendSms.do">发送短信</a></li>
         <li id="li-sms-send-history"><a href="${ctx}/sms/smsSendHistory.do">短信发送记录</a></li> --%>
         
        <li class="dropdown" id="li-send-sms">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               短信管理 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
               <li><a href="${ctx}/sms/toSendSms.do">发送短信</a></li>
               <li><a href="${ctx}/sms/smsSendHistory.do">短信发送记录</a></li>
            </ul>
         </li>
         <li class="dropdown" id="li-send-group-wx">
            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
               微信管理 <b class="caret"></b>
            </a>
            <ul class="dropdown-menu">
               <li><a href="${ctx}/wx/toSendGroupWx.do">群发微信消息</a></li>
            </ul>
         </li>
      </ul>
   </div>
</nav>






<%-- <!-- 页面左侧的菜单栏 -->
<div id="menu-tree" class="collapse navbar-collapse col-sm-3 col-md-3 col-lg-3 panel-group">
	<!-- 第一部分菜单 -->
	<div class="panel panel-default">
	   <div class="panel-heading">
	     <h4 class="panel-title">
	       <a data-toggle="collapse" data-parent="#menu-tree" href="#menu-group-1">
	         短信管理
	       </a>
	     </h4>
	   </div>
	   <div id="menu-group-1" class="panel-collapse collapse in">
	      	<a href="${ctx}/sms/toSendSms.do" class="list-group-item" id="menu-send-sms" style="border: 0; border-top: 1px solid #ddd;">
	      发送短信
	    </a>
	    <a href="${ctx}/sms/smsSendHistory.do" class="list-group-item" style="border: 0; border-top: 1px solid #ddd;">
		      发送历史
		    </a>
	    </div>
	</div>
	<!-- 第二部分菜单 -->
	<div class="panel panel-default">
	    <div class="panel-heading">
	      <h4 class="panel-title">
	        <a data-toggle="collapse" data-parent="#menu-tree" href="#menu-group-2">
	          微信管理
	        </a>
	      </h4>
	    </div>
	    <div id="menu-group-2" class="panel-collapse collapse">
	       	<a href="#" class="list-group-item" style="border: 0; border-top: 1px solid #ddd;">
	      发送文本
	    </a>
	    <a href="#" class="list-group-item" style="border: 0; border-top: 1px solid #ddd;">
		      发送图文
		    </a>
	    </div>
	</div>
</div> --%>