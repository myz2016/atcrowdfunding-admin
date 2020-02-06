<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="UTF-8">
<%@ include file="/WEB-INF/include-head.jsp" %>
<link rel="stylesheet" href="css/pagination.css">
<script type="text/javascript" src="jquery/jquery.pagination.js"></script>
<script type="text/javascript">
    $(function () {
        // 对分页导航条显示进行初始化
        initPagination();
        $("#summaryBox").click(function () {
            // 获取当前#summaryBox的勾选状态
            // this代表当前多选框对象（DOM对象）
            var checkedStatus = this.checked;
            // checked属性为true时表示被勾选，为false时表示没有被勾选
            // 使用checkStatus设置.itemBox的状态
            $(".itemBox").prop("checked", checkedStatus);
        });

        // 批量删除
        $("#batchRemoveBtn").click(function () {
            var adminIdArr = [];
            var loginAccArr = [];
            $(".itemBox:checked").each(function () {
                var $this = $(this);
                adminIdArr.push($this.attr('adminId'));
                loginAccArr.push($this.parent("td").next().text());
            });

            if (adminIdArr.length === 0) {
                alert("请勾选您要删除的记录！");
                return;
            }

            var confirmStatus = confirm("您确认要删除" + loginAccArr + "吗？操作不可逆，请谨慎决定！");
            if (!confirmStatus) {
                return;
            }
            // 将 JSON 数组转成 JSON 字符串
            // var a = [1,2,3,4];                   // 数组类型
            // var b = "[1,2,3,4]";                 // 字符串类型
            // var c = {"userName": "tom"};         // 对象类型
            // var d = "{\"userName\": \"tom\"}";   // 字符串类型
            var adminIds = JSON.stringify(adminIdArr);
            $.ajax({
                // 服务器端接收请求的 url 地址
               "url":"/admin/batch/remove.json",
                // 设置请求方式为 post
                "type":"post",
                // 设置请求体内容类型，告诉服务器当前请求体发送的是JSON数据
                "contentType":"application/json;charset=UTF-8",
                // 请求体真正要发送给服务器的数据
                "data":adminIds,
                // 把服务器端返回的数据当做 json 格式解析
                "dataType":"json",
                // 服务器处理请求成功后执行的函数，响应体以参数形式传入当前函数
                "success": function (resp) {
                    if (resp.result === "SUCCESS") {
                        window.location.href = "admin/query/for/search.html?pageNum=${requestScope['PAGE-INFO'].pageNum}&keyword=${param.keyword}";
                    } else {
                        alert(resp.message);
                    }
                },
                // 服务器处理请求失败后执行的函数，响应体以参数形式传入当前函数
                "error": function (resp) {
                    alert(resp.message);
                }
            });
        });

    });

    // 声明函数封装导航条初始化操作
    function initPagination() {
        // 声明变量存储总记录数
        var totalRecord = ${requestScope['PAGE-INFO'].total};
        // 声明变量存储分页导航条显示时的属性设置
        var paginationProperties = {
            num_edge_entries: 3,			//边缘页数
            num_display_entries: 5,		    //主体页数
            callback: pageSelectCallback,	//回调函数
            items_per_page: ${requestScope['PAGE-INFO'].pageSize},	//每页显示数据数量，就是pageSize
            current_page: ${requestScope['PAGE-INFO'].pageNum - 1}, //当前页页码
            prev_text: "上一页",			    //上一页文本
            next_text: "下一页"			    //下一页文本
        };

        // 显示分页导航条
        $("#Pagination").pagination(totalRecord, paginationProperties);
    }

    // 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
    function pageSelectCallback(pageIndex, jq) {
        // pageIndex从0开始，pageNum从1开始
        var pageNum = pageIndex + 1;
        // 跳转页面
        window.location.href = "admin/query/for/search.html?pageNum=" + pageNum + "&keyword=${param.keyword}";
        return false;
    }

</script>
<body>
<%@ include file="/WEB-INF/include-nav.jsp" %>
<div class="container-fluid">
    <div class="row">
        <%@ include file="/WEB-INF/include-sidebar.jsp" %>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;" action="/admin/query/for/search.html" method="post">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" name="keyword" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="submit" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询 </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;"
                            onclick="window.location.href='add.html'"><i class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>账号</th>
                                <th>名称</th>
                                <th>邮箱地址</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${empty requestScope['PAGE-INFO'].list}">
                                <tr>
                                    <td style="text-align: center" colspan="6">抱歉！没有符合您要求的查询结果！</td>
                                </tr>
                            </c:if>
                            <c:if test="${!empty requestScope['PAGE-INFO'].list}">
                                <c:forEach items="${requestScope['PAGE-INFO'].list}" var="admin" varStatus="myStatus">
                                    <tr>
                                            <%--从0开始用count,从1开始用index--%>
                                        <td>${myStatus.count}</td>
                                        <td><input adminId="${admin.id}" class="itemBox" type="checkbox"></td>
                                        <td>${admin.loginAcct}</td>
                                        <td>${admin.userName}</td>
                                        <td>${admin.email}</td>
                                        <td>
                                            <button type="button" class="btn btn-success btn-xs">
                                                <i class=" glyphicon glyphicon-check"></i>
                                            </button>
                                            <button type="button" class="btn btn-primary btn-xs">
                                                <i class=" glyphicon glyphicon-pencil"></i>
                                            </button>
                                            <button type="button" class="btn btn-danger btn-xs">
                                                <i class=" glyphicon glyphicon-remove"></i>
                                            </button>
                                        </td>

                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <div id="Pagination" class="pagination"><!-- 这里显示分页 --></div>
                                </td>
                            </tr>
                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>