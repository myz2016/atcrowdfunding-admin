/**
 * 批量删除
 * @param adminIdArr 被删除的数据的 id
 */
function doBatchRemove(adminIdArr) {
    var adminIds = JSON.stringify(adminIdArr);
    $.ajax({
        // 服务器端接收请求的 url 地址
        "url": "/admin/batch/remove.json",
        // 设置请求方式为 post
        "type": "post",
        // 设置请求体内容类型，告诉服务器当前请求体发送的是JSON数据
        "contentType": "application/json;charset=UTF-8",
        // 请求体真正要发送给服务器的数据
        "data": adminIds,
        // 把服务器端返回的数据当做 json 格式解析
        "dataType": "json",
        // 服务器处理请求成功后执行的函数，响应体以参数形式传入当前函数
        "success": function (resp) {
            if (resp.result === "SUCCESS") {
                window.location.href = "admin/query/for/search.html?pageNum=" + window.pageNum + "&keyword=" + window.keyword;
            } else {
                alert(resp.message);
            }
        },
        // 服务器处理请求失败后执行的函数，响应体以参数形式传入当前函数
        "error": function (resp) {
            alert(resp.message);
        }
    });
}

// 声明函数封装导航条初始化操作
function initPagination() {
    // 声明变量存储总记录数
    var totalRecord = window.total;
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
    $("#Pagination").pagination(totalRecord, paginationProperties);
}

// 在每一次点击“上一页”、“下一页”、“页码”时执行这个函数跳转页面
function pageSelectCallback(pageIndex, jq) {
    // 将全局变量中的pageNum修改为最新值
    window.pageNum = pageIndex + 1;

    // pageIndex从0开始，pageNum从1开始
    // 跳转页面
    window.location.href = "admin/query/for/search.html?pageNum=" + (pageIndex + 1) + "&keyword=" + window.keyword;
    return false;
}