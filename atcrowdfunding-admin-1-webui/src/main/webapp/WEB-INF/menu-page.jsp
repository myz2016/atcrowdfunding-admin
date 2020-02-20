<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="script/my-menu.js"></script>
    <script type="text/javascript" charset="utf-8">
        $(document).ready(function () {
            initWholeTree();
            $("#menuAddBtn").click(function () {
                var menuName = $("#menuAddModal [name='name']").val();
                var url = $("#menuAddModal [name='url']").val();
                var ico = $("#menuAddModal [name='icon']:checked").val();
                if (!menuName) {
                    layer.msg("请填写菜单项名称！");
                    return;

                }
                if (!url) {
                    layer.msg("请填写菜单项对应的访问地址！");
                    return;
                }
                $.ajax({
                    "url": "menu/save.json",
                    "type": "post",
                    "data": {
                        "name": menuName,
                        "url": url,
                        "icon": ico,
                        "pid": window.pid
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result === "SUCCESS") {
                            layer.msg("添加成功！");
                            initWholeTree();
                        }
                        if (result === "FAILED") {
                            layer.msg(response.message);
                        }
                        clean();
                        $("#menuAddModal").modal("hide");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
            });

            $("#menuEditBtn").click(function () {
                var menuName = $("#menuEditModal [name='name']").val();
                var url = $("#menuEditModal [name='url']").val();
                var icon = $("#menuEditModal [name='icon']:checked").val();
                if (!menuName) {
                    layer.msg("请填写菜单项名称！");
                    return;

                }
                if (!url) {
                    layer.msg("请填写菜单项对应的访问地址！");
                    return;
                }
                $.ajax({
                    "url": "menu/update.json",
                    "type": "post",
                    "data": {
                        "id": window.menuId,
                        "name": menuName,
                        "url": url,
                        "icon": icon
                    },
                    "dataType": "json",
                    "success": function (response) {
                        var result = response.result;
                        if (result === "SUCCESS") {
                            layer.msg("更新成功！");
                            initWholeTree();
                        }
                        if (result === "FAILED") {
                            layer.msg(response.message);
                        }
                        clean();
                        $("#menuEditModal").modal("hide");
                    },
                    "error": function (response) {
                        layer.msg(response.message);
                    }
                });
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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表
                    <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i
                            class="glyphicon glyphicon-question-sign"></i></div>
                </div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/include-modal-menu-add.jsp" %>
<%@include file="/WEB-INF/include-modal-menu-edit.jsp" %>
</body>
</html>