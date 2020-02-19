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
}