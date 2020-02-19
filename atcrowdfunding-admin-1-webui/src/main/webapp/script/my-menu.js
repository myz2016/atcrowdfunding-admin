/**
 * 由setting.view.addDiyDom属性引用，负责显示自定义图标
 * @param treeId <ul id="treeDemo" class="ztree"></ul>的id属性值
 * @param treeNode 对应每一个树形节点
 */
function showMyIcon(treeId, treeNode) {
    var spanId = treeNode.tId + "_ico";
    var newClass = treeNode.icon;
    $("#" + spanId).removeClass().addClass(newClass).css("background", "");
}

function initWholeTree() {
    $.ajax({
        "url": "menu/get/whole/tree.json",
        "type": "post",
        "dataType": "json",
        "success": function (response) {
            var result = response.result;
            if (result === "SUCCESS") {
                var setting = {
                    view: {
                        "addDiyDom": showMyIcon,
                        "addHoverDom": addHoverDom,
                        "removeHoverDom": removeHoverDom
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
}

// 专门生成按钮组的函数
function generateBtnGrp(treeNode) {

    // 获取当前节点的id（HTML中li标签的id）
    var treeNodeId = treeNode.tId;

    // 获取当前节点在数据库中的id值
    // Menu实体类中的属性，都可以通过treeNode以“.属性名”的方式直接访问
    var menuId = treeNode.id;

    // 组装按钮组所在的span的id
    var btnGrpSpanId = treeNodeId + "_btnGrp";

    // 生成span标签对应的jQuery对象
    var $span = $("<span id='" + btnGrpSpanId + "'></span>");

    // 获取当前节点的level值
    var level = treeNode.level;

    // 声明三种按钮
    var addBtn = "<a onclick='showAddModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='添加子节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-plus rbg'></i></a>";
    var editBtn = "<a onclick='showEditModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='编辑节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-edit rbg'></i></a>";
    var removeBtn = "<a onclick='showConfirmModal(this)' id='" + menuId + "' class='btn btn-info dropdown-toggle btn-xs' style='margin-left:10px;padding-top:0px;' title='删除节点'>&nbsp;&nbsp;<i class='fa fa-fw fa-times rbg'></i></a>";

    // 根据level进行判断
    if (level == 0) {
        $span.append(addBtn);
    }

    if (level == 1) {

        if (treeNode.children.length > 0) {

            $span.append(addBtn + " " + editBtn);

        } else {

            $span.append(addBtn + " " + editBtn + " " + removeBtn);

        }

    }

    if (level == 2) {

        $span.append(editBtn + " " + removeBtn);

    }

    return $span;
}

// 在鼠标移入节点范围时添加自定义控件
function addHoverDom(treeId, treeNode) {

    // 在执行添加前，先进行判断，如果已经添加过，就停止执行
    // 组装按钮组所在的span标签的id
    var btnGrpSpanId = treeNode.tId + "_btnGrp";

    var btnGrpSpanLength = $("#" + btnGrpSpanId).length;

    if (btnGrpSpanLength > 0) {
        return;
    }

    // 由于按钮组是放在当前节点中的超链接（a标签）后面，所以先定位到a标签
    // 按id生成规则组装a标签的id
    var anchorId = treeNode.tId + "_a";

    // 调用已封装函数生成按钮组
    var $btnGrpSpan = generateBtnGrp(treeNode);

    // 在a标签后面追加按钮组
    $("#" + anchorId).after($btnGrpSpan);

}

// 在鼠标移出节点范围时删除自定义控件
function removeHoverDom(treeId, treeNode) {

    // 组装按钮组所在的span标签的id
    var btnGrpSpanId = treeNode.tId + "_btnGrp";

    // 删除对应的元素
    $("#" + btnGrpSpanId).remove();
}
