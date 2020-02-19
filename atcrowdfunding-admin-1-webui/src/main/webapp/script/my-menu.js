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