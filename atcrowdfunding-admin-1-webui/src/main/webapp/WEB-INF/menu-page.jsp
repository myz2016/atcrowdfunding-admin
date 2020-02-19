<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <%@ include file="/WEB-INF/include-head.jsp" %>
    <link rel="stylesheet" href="ztree/zTreeStyle.css" type="text/css">
    <script type="text/javascript" src="ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="script/my-menu.js"></script>
    <script type="text/javascript" charset="utf-8">
        $(document).ready(function(){
            $.ajax({
               "url":"menu/get/whole/tree.json",
               "type":"post",
                "dataType":"json",
                "success": function (response) {
                    var result = response.result;
                    if (result === "SUCCESS") {
                        var setting = {
                            view: {
                                addDiyDom: showMyIcon
                            },
                            "data": {
                                "key": {
                                    "url": "notExistsProperty" // 阻止点击节点后跳转
                                }
                            }

                        };
                        var zNodes = response.data;
                        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                    }
                    if (result === "FAILED") {
                        layer.msg("加载数据失败，原因是：" + response.message);
                    }
                },
                "error": function (response) {
                   layer.msg("加载数据失败，原因是：" + response.message);
                }
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
                <div class="panel-heading"><i class="glyphicon glyphicon-th-list"></i> 权限菜单列表 <div style="float:right;cursor:pointer;" data-toggle="modal" data-target="#myModal"><i class="glyphicon glyphicon-question-sign"></i></div></div>
                <div class="panel-body">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>