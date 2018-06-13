function resulthandler(result,callBack) {
    if(result.success){
        $.messager.confirm("提示","保存成功",function () {
            callBack();
        });
    }else {
        $.messager.alert("提示",result.msg);
    }
}