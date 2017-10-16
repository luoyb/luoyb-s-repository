<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>我的微信详情</title>
  </head>
  
  <body>
  	<div>头像：<img src="${user.headImgUrl }"></div>
  	<div>昵称：${user.nickname }</div>
  	<div>openId：${user.openId }</div>
  </body>
</html>
