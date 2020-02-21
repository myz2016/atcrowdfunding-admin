<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="css/pagination.css">
    <link rel="stylesheet" href="ztree/zTreeStyle.css" />
    <script type="text/javascript" src="jquery/jquery.pagination.js"></script>
    <script type="text/javascript" src="script/my-role.js"></script>
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" charset="utf-8">
        $(function () {
            initGlobalVariable();
            showPage();
            $("#search").click(function () {
                var keyword = $.trim($("#keyword").val());
                if (keyword) {
                    window.keyword = keyword;
                    showPage();
                }
            });
            $("#summaryBox").click(function () {
                var checkedStatus = this.checked;
                $(".itemClass").prop("checked", checkedStatus);
            });
            $("#batchRemoveBtn").click(function () {
                var checkedBox = $(".itemClass:checked");
                var checkedLength = checkedBox.length;
                if (checkedLength === 0) {
                    layer.msg("请勾选要删除的数据！");
                    return;
                }
                window.roleIdArr = [];
                checkedBox.each(function () {
                    var $this = $(this);
                    window.roleIdArr.push($this.attr("roleId"));
                });
                showRemoveConfirmModal();
            });

            $("#confirmModalBtn").click(function () {
                var requestBody = JSON.stringify(window.roleIdArr);
                $.ajax({
                    "url": "role/batch/remove.json",
                    "type": "post",
                    "data": requestBody,
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result === "SUCCESS") {
                            layer.msg("操作成功");
                            showPage();
                        }
                        if (result === "ERROR") {
                            layer.msg(response.message);
                        }

                        $("#confirmModal").modal("hide");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
            });
            // 针对.removeBtn这样动态生成的元素对象使用on()函数方式绑定单击响应函数
            // $("动态元素所依附的静态元素").on("事件类型","具体要绑定事件的动态元素的选择器", 事件响应函数);
            $("#roleTableBody").on("click", ".removeBtn", function () {
                window.roleIdArr = new Array();
                window.roleIdArr.push($(this).attr("roleId"));
                showRemoveConfirmModal();
            });

            $("#addBtn").click(function () {
                $("#addModal").modal("show");
            });

            $("#addModalBtn").click(function () {
                var roleName = $.trim($("#roleNameInput").val());
                if (roleName) {
                    $.ajax({
                        "url": "role/save/role.json",
                        "type": "post",
                        "data": {"roleName": $("#roleNameInput").val()},
                        "dataType": "json",
                        "success": function (response) {
                            if (response.result === "SUCCESS") {
                                layer.msg("添加成功！");
                                window.pageNum = 0x7fffffff;
                                showPage();
                            }
                            if (response.result === "FAILED") {
                                layer.msg(response.message);
                            }
                            $("#addModal").modal("hide");
                            $("#roleNameInput").val("");
                        },
                        "error": function (response) {
                            layer.msg(response.message);
                        }
                    });
                } else {
                    layer.msg("请输入角色名称！");
                }

            });

            $("#roleTableBody").on("click", ".editBtn", function () {
                window.roleId = $(this).attr("roleId");
                $("#editModal").modal("show");
                $("#roleNameInputEdit").val($(this).parents("tr").children("td:eq(2)").text());
            });

            $("#roleTableBody").on("click", ".checkBtn", function () {
                window.roleId = $(this).attr("roleId");
                $("#roleAssignAuthModal").modal("show");
                // 初始化模态框中显示的树形结构
                // 1.创建setting对象
                var setting = {
                    "data": {
                        "simpleData": {
                            "enable": true,
                            "pIdKey": "categoryId"
                        },
                        "key": {
                            "name": "title"
                        }
                    },
                    "check": {
                        "enable": true
                    }
                };
                // 2.获取JSON数据
                var ajaxResult = $.ajax({
                    "url":"assign/get/all/auth.json",
                    "type":"post",
                    "dataType":"json",
                    "async":false
                });
                var response = ajaxResult.responseJSON;
                if (response.result === "FAILED") {
                    layer.msg(response.message);
                    return ;
                }
                var zNodes = response.data;
                // 3.初始化树形结构
                $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                // 展开全部节点
                $.fn.zTree.getZTreeObj("treeDemo").expandAll(true);

                var ajaxRs = $.ajax({
                    "url":"/assign/get/assign/auth.json",
                    "type":"post",
                    "dataType":"json",
                    "data":{
                        "roleId":window.roleId,
                        // 产生随机数防止浏览器缓存数据
                        "random":Math.random()
                    },
                    "async":false
                });

                var responseRs = ajaxRs.responseJSON;
                if (responseRs.result === "FAILED") {
                    layer.msg(responseRs.message);
                    return;
                }
                var authIdList = responseRs.data;
                // 6.使用authIdList勾选对应的树形节点
                // ①遍历authIdList
                for (var i = 0; i < authIdList.length; i++) {
                    // ②在遍历过程中获取每一个authId
                    var authId = authIdList[i];

                    // ③根据authId查询到一个具体的树形节点
                    // key：查询节点的属性名
                    // value：查询节点的属性值，这里使用authId
                    var key = "id";

                    var treeNode = $.fn.zTree.getZTreeObj("treeDemo").getNodeByParam(key, authId);

                    // ④勾选找到的节点
                    // treeNode：当前要勾选的节点
                    // true：表示设置为勾选状态
                    // false：表示不联动
                    $.fn.zTree.getZTreeObj("treeDemo").checkNode(treeNode, true, false);

                }

            });

            $("#editModalBtn").click(function () {
                $.ajax({
                    "url": "role/update/role.json",
                    "type": "post",
                    "data": {
                        "roleId": window.roleId,
                        "roleName": $("#roleNameInputEdit").val()
                    },
                    "dataType": "json",
                    "success": function (response) {
                        if (response.result === "SUCCESS") {
                            layer.msg("更新成功！");
                            showPage();
                        }
                        if (response.result === "FAILED") {
                            layer.msg(response.message);
                        }
                        $("#editModal").modal("hide");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
            });

            $("#roleAssignAuthBtn").click(function () {
                var authIdArray = new Array();

                // 调用zTreeObj的方法获取当前已经被勾选的节点
                var checkedNodes = $.fn.zTree.getZTreeObj("treeDemo").getCheckedNodes();

                // 遍历checkedNodes
                for(var i = 0; i < checkedNodes.length; i++) {

                    // 获取具体的一个节点
                    var node = checkedNodes[i];

                    // 获取当前节点的id属性
                    var authId = node.id;

                    // 将authId存入数组
                    authIdArray.push(authId);
                }

                // 在handler方法中使用@RequestBody接收
                // 方便使用的数据类型是：@RequestBody Map<String, List<Integer>>
                // {"roleIdList":[2],"authIdList":[2,3,5,7]}
                // 封装要发送给handler的JSON数据
                var requestBody = {"roleIdList":[window.roleId], "authIdList": authIdArray};

                // 发送请求
                var ajaxResult = $.ajax({
                    "url": "assign/do/assign.json",
                    "type": "post",
                    "data": JSON.stringify(requestBody),
                    "contentType": "application/json;charset=UTF-8",
                    "dataType": "json",
                    "async": false
                });

                if(ajaxResult.responseJSON.result == "SUCCESS") {

                    layer.msg("操作成功！");

                }

                if(ajaxResult.responseJSON.result == "FAILED") {

                    layer.msg(ajaxResult.responseJSON.message);

                }

                $("#roleAssignAuthModal").modal("hide");

            });
        });
    </script>
</head>
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
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="keyword" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="search" type="button" class="btn btn-warning"><i
                                class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button id="batchRemoveBtn" type="button" class="btn btn-danger"
                            style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 批量删除
                    </button>
                    <button id="addBtn" type="button" class="btn btn-primary" style="float:right;"><i
                            class="glyphicon glyphicon-plus"></i> 新增
                    </button>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr>
                                <th width="30">#</th>
                                <th width="30"><input id="summaryBox" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody id="roleTableBody">
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
<%@include file="/WEB-INF/include-modal-role-confirm.jsp" %>
<%@include file="/WEB-INF/include-moda-role-add.jsp" %>
<%@include file="/WEB-INF/include-modal-role-edit.jsp" %>
<%@include file="/WEB-INF/include-modal-assign-auth.jsp"%>
</body>
</html>