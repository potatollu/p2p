<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>蓝源Eloan-P2P平台(系统管理平台)</title> <#include "../common/header.ftl"/>
<script type="text/javascript" src="/js/plugins/jquery.form.js"></script>
<script type="text/javascript"
	src="/js/plugins/jquery-validation/jquery.validate.js"></script>
<script type="text/javascript"
	src="/js/plugins/jquery.twbsPagination.min.js"></script>
<script type="text/javascript" src="/js/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="/js/plugins/bootstrap3-typeahead.min.js"></script>

    <script type="text/javascript">
        $(function(){
            $("#beginDate,#endDate").click(function(){
                WdatePicker();
            });
			//分页
            $('#pagination').twbsPagination({
                totalPages : ${qo.pageSize},
                startPage : ${qo.currentPage},
                visiblePages : 3,
                first : "首页",
                prev : "上一页",
                next : "下一页",
                last : "最后一页",
                onPageClick : function(event, page) {
                    $("#currentPage").val(page);
                    $("#searchForm").submit();
                }
            });

            $("#query").click(function(){
                $("#currentPage").val(1);
                $("#searchForm").submit();
            });

            //表单回显
            $(".audit").click(function () {

                var json = $(this).data("json");
                console.log(json.username);
                console.log($("#username"));
                $("#id").val(json.id);
                $("#usernameId").html(json.username);
                $("#orderTime").html(json.orderTime);
                $("#vedioAuthModal").modal('show');
            });

            //提交审核
			$(".btn_audit").click(function () {
				$("#auditState").val($(this).val());
				$("#editForm").ajaxSubmit(function (result) {
					resulthandler(result,function () {
						location.href="/vedioAuth"
                    })
                })
            });

        });
    </script>
</head>

<body>
	<div class="container">
		<#include "../common/top.ftl"/>
		<div class="row">
			<div class="col-sm-3"><#assign currentMenu="vedioAuth" />
				<#include "../common/menu.ftl" /></div>
			<div class="col-sm-9">
				<div class="page-header">
					<h3>视频认证管理</h3>
				</div>
				<div class="row">
					<!-- 提交分页的表单 -->
					<form id="searchForm" class="form-inline" method="post" action="/vedioAuth.do">
											<input type="hidden" name="currentPage" value="1" />
											<div class="form-group">
												<label>状态</label> <select class="form-control" name="state">
													<option value="-1">全部</option>
													<option value="0">申请中</option>
													<option value="1">审核通过</option>
													<option value="2">审核拒绝</option>
												</select>
												<script type="text/javascript">
											    	$("[name=state] option[value='${(qo.state)!''}']").attr("selected","selected");
											    </script>
											</div>
											<div class="form-group">
												<label>预约时间</label> <input class="form-control"
													style="width: 130px;" type="text" name="beginDate"
													id="beginDate" value="${(qo.beginDate?string('yyyy-MM-dd'))!''}" />到
												<input class="form-control" style="width: 130px;" type="text"
													name="endDate" id="endDate"
													value="${(qo.endDate?string('yyyy-MM-dd'))!''}" />
											</div>
											<div class="form-group">
												<label>预约用户</label>
												<input class="form-control" style="width: 130px;" type="text" name="username" 
												id="username" value="${qo.username!''}" />
											</div>
											<div class="form-group">
												<button id="query" class="btn btn-success">
													<i class="icon-search"></i> 查询
												</button>
											</div>
					</form>
				</div>
				<div class="row">
					<table class="table">
											<thead>
												<tr>
													<th>预约用户</th>
													<th width="220px">预约时间</th>
													<th>状态</th>
													<th>审核说明</th>
													<th>审核时间</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
												<#list pageResult.list as vo>
												<tr>
													<td>${vo.applier.username}</td>
													<td>
														${vo.orderBeginDate?string("yyyy-MM-dd HH:mm")} -
														${vo.orderEndDate?string("HH:mm")}
													</td>
													<td>${vo.stateDisplay}</td>
													<td>${(vo.remark)!''}</td>
													<td>${(vo.auditTime?string("yyyy-MM-dd"))!""}</td>
													<td><a class="audit" data-json='${vo.jsonString}'>审核</a> </td>

												</tr>
												</#list>
											</tbody>
										</table>

					<div style="text-align: center;">
						<ul id="pagination" class="pagination"></ul>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="vedioAuthModal" class="modal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">添加视频审核</h4>
					</div>
					<div class="modal-body">
						<form id="editForm" class="form-horizontal" method="post" action="/vedioAuth_audit">
							<input type="hidden" name="id" id="id" value="" />
							<input type="hidden" name="state" id="auditState" value="" />
							<div class="form-group">
								<label class="col-sm-2 control-label">用户名</label>
								<div class="col-sm-6">
									<div class="dropdown" id="autocomplate">
	                                    <label  class="form-control" id="usernameId" autocomplete="off" ></label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">预约时间</label>
								<div class="col-sm-6">
									<div class="dropdown" id="autocomplate">
										<label  class="form-control" id="orderTime" autocomplete="off" ></label>
									</div>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label" for="name">审核备注</label>
								<div class="col-sm-6">
									<textarea name="remark" rows="4" cols="40"></textarea>
								</div>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
						<button type="button" class="btn btn-success btn_audit" value="1">审核通过</button>
						<button type="button" class="btn btn-warning btn_audit" value="2">审核拒绝</button>
					</div>
				</div>
			</div>
		</div>
</body>
</html>