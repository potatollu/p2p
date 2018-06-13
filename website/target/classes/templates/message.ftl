<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>蓝源Eloan-P2P平台</title>
		<#include "common/links-tpl.ftl" />
		<link type="text/css" rel="stylesheet" href="/css/account.css" />
		<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
		<script>
			$(function(){
			    //保存数据
				$("#userInfoForm").ajaxForm(function (data) {
					if (data.success){
						$.messager.confirm("提示","保存成功",function () {
							window.location.href="/basicInfo";
						})
					}else {
						$.messager.alert("提示",data.msg);
					}
				})
			})
		</script>		
	</head>
	<body>
		<!-- 网页顶部导航 -->
		<#include "common/head-tpl.ftl" />
		
		<#assign currentNav="personal"/>
		<!-- 网页导航 -->
		<#include "common/navbar-tpl.ftl" />
		
		<div class="container">
			<div class="row">
				<!--导航菜单-->
				<div class="col-sm-3">
					<#assign currentMenu="userInfo" />
					<#include "common/leftmenu-tpl.ftl" />
				</div>
				<!-- 功能页面 -->
				<div class="col-sm-9">
                    <div class="container el-panel">
                        <div class="panel el-panel">
                            <div class="panel-title">
					<span class="pull-left">
						消息公告
					</span>

                            </div>
			<#list sendSms as data>
			 <dl>
                 <dt>${data.bidRequstFaile!''}</dt>
             </dl>
				<dl>
                    <dt>${data.bidRequstSuccess!''}</dt>
                </dl>
				<dl>
                    <dt>${data.bidSuccess!''}</dt>
                </dl>

			</#list>
                        </div>
                    </div>
				</div>
			</div>
		</div>		
		
		<#include "common/footer-tpl.ftl" />
	</body>
</html>