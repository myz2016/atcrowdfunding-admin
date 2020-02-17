/**
 *  初始化分页参数
 */
function initGlobalVariable() {
    window.pageNum = 1;
    window.pageSize = 5;
    window.keyword = "";
}

/**
 * 给服务器发送请求获取分页数据（pageInfo），并在页面上显示分页效果（主体、页码导航条）
 */
function showPage() {
    // 给服务器发送请求获取分页数据 PageInfo
    var pageInfo = getPageInfo();
    generateTableBody(pageInfo);
    initPagination(pageInfo);
}

/**
 * 获取分页数据
 */
function getPageInfo() {
    var resultEntity = $.ajax({
        "url": "role/search/by/keyword.json",
        "type": "post",
        "data": {
            "pageNum": window.pageNum || 1,
            "pageSize": window.pageSize || 5,
            "keyword": window.keyword || ""
        },
        "dataType": "json",
        "async": false
    });
    var result = resultEntity["responseJSON"];
    if (result.result === "SUCCESS") {
        return result.data;
    }
    if (result.result === "FAILED") {
        layer.msg(result.message);
    }
    return null;
}

/**
 * 生成表体
 * @param pageInfo
 */
function generateTableBody(pageInfo) {
    var roleTableBody = $("#roleTableBody");
    roleTableBody.empty();
    var list = pageInfo.list;
    var checkBtn = "<button type='button' class='btn btn-success btn-xs'><i class=' glyphicon glyphicon-check'></i></button>";
    var pencilBtn = "<button type='button' class='btn btn-primary btn-xs'><i class=' glyphicon glyphicon-pencil'></i></button>";
    var removeBtn = "<button type='button' class='btn btn-danger btn-xs'><i class=' glyphicon glyphicon-remove'></i></button>";
    if (!list) {
        roleTableBody.append("<tr><td style='text-align: center' colspan='6'>抱歉！没有符合您要求的查询结果！</td></tr>");
        return;
    }
    list.forEach(function (data, index) {
        var rowNumTd = "<td>" + index + "</td>";
        var checkBoxTd = "<td><input roleId='" + data.id + "'type='checkbox'></td>";
        var roleNameTd = "<td>" + data.name + "</td>";
        var btnTd = "<td>" + checkBtn + " " + pencilBtn + " " + removeBtn + "</td>";
        var tr = "<tr>" + rowNumTd + checkBoxTd + roleNameTd + btnTd + "</tr>";
        roleTableBody.append(tr);
    });
}

// 声明函数封装导航条初始化操作
function initPagination(pageInfo) {
    // 声明变量存储分页导航条显示时的属性设置
    var paginationProperties = {
        num_edge_entries: 3,			//边缘页数
        num_display_entries: 5,		    //主体页数
        callback: pageSelectCallback,	//回调函数
        items_per_page: window.pageSize,	//每页显示数据数量，就是pageSize
        current_page: (window.pageNum - 1), //当前页页码
        prev_text: "上一页",			    //上一页文本
        next_text: "下一页"			    //下一页文本
    };

    // 显示分页导航条
    $("#Pagination").pagination(pageInfo.total, paginationProperties);
}

// 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
function pageSelectCallback(pageIndex, jq) {
    // 将全局变量中的pageNum修改为最新值
    window.pageNum = pageIndex + 1;
    showPage();
    return false;
}