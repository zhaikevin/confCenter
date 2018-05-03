var conf = conf || {};

conf.loaded = true;

conf.pagination = function (url, form, successCallback, errorCallback) {
    return function (data, callback, settings) {
        if (!lucky.app.loaded) {
            return;
        }
        var params = {};
        params.page = (data.start / data.length) + 1;
        params.rows = data.length;
        if (data.order && data.order.length > 0) {
            params.sort = data.columns[data.order[0].column].data;
            params.order = data.order[0].dir;
        } else if (settings.aaSorting.length > 0) {
            if (settings.aaSorting[0] instanceof Array) {
                var index = settings.aaSorting[0][0];
                params.sort = data.columns[index].data;
                params.order = settings.aaSorting[0][1];
            } else {
                var index = settings.aaSorting[0];
                params.sort = data.columns[index].data;
                params.order = settings.aaSorting[1];
            }
        } else {
            params.sort = data.columns[0].data;
            params.order = "asc";
        }
        params.params = {};
        if (form) {
            $('input,select', $(form)).each(function () {
                if ($(this).attr('name') && $(this).val() && $(this).val() != '') {
                    params.params[$(this).attr('name')] = $(this).val().trim().replaceAll("'", '').replaceAll('"', '').replaceAll('<', '').replaceAll('>', '');
                }
            });
        }
        $.ajax({
            type: "POST",
            url: url,
            data: params,  //传入组装的参数
            dataType: "json",
            success: function (result) {
                if (result.status == 0) {
                    var returnData = {};
                    returnData.draw = data.draw;
                    returnData.recordsTotal = result.data.total;
                    returnData.recordsFiltered = result.data.total;
                    returnData.data = result.data.rows;
                    $('.btn-single-detail').attr('disabled', 'disabled');
                    callback(returnData);
                    if (successCallback) {
                        successCallback.call();
                    }
                } else if (result.status == 1) {
                    window.location.href = "/user/login";
                } else {
                    if (errorCallback) {
                        errorCallback.call();
                    }
                    $.fn.alert(result.statusInfo);
                }
            }
        });
    };
}