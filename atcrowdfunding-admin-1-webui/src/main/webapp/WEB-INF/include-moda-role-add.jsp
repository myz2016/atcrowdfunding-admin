<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="addModal" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <form role="form">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title">尚筹网系统弹窗</h4>
                </div>
                <div class="modal-body">
                    <input id="roleNameInput" type="text" class="form-control" placeholder="请输入角色名称...">
                </div>
                <div class="modal-footer">
                    <button id="addModalBtn" type="button" class="btn btn-success"><i class="glyphicon glyphicon-plus"></i> 保存</button>
                    <button type="reset" class="btn btn-primary"><i class="glyphicon glyphicon-refresh"></i> 重置</button>
                </div>
            </div>
        </form>
    </div>
</div>