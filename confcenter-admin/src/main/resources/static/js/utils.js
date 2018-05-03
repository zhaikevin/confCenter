var conf = conf || {};

/**
 * 创建命名空间
 */
conf.createNS = function (namespaceString) {
    var parts = namespaceString.split('.'),
        parent = window,
        currentPart = '';

    for (var i = 0, length = parts.length; i < length; i++) {
        currentPart = parts[i];
        parent[currentPart] = parent[currentPart] || {};
        parent = parent[currentPart];
    }

    return parent;
}

String.prototype.trim = function () {
    var regEx = /\s+/g;
    return this.replace(regEx, ' ');
};

String.prototype.endWith = function (str) {
    return this.lastIndexOf(str) == (this.length - str.length);
};

String.prototype.startWith = function (str) {
    return this.indexOf(str) == 0;
};

String.prototype.contains = function (str) {
    return this.indexOf(str) != -1;
};

String.prototype.replaceAll = function (s1, s2) {
    return this.replace(new RegExp(s1, "gm"), s2);
}

String.prototype.isNotEmpty = function () {
    return this && this != null && this != 'null' && this != '';
};

String.prototype.isEmpty = function () {
    return !this.isNotEmpty();
};

Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1,
        "d+": this.getDate(),
        "h+": this.getHours(),
        "m+": this.getMinutes(),
        "s+": this.getSeconds(),
        "q+": Math.floor((this.getMonth() + 3) / 3),
        "S": this.getMilliseconds()
    }
    if (/(y+)/.test(format)) {
        format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(format)) {
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
        }
    }
    return format;
};

Array.prototype.contains = function (obj) {
    var i = this.length;
    while (i--) {
        if (this[i] === obj) {
            return true;
        }
    }
    return false;
};


conf.utils = {
    // 系统全局的ajax队列
    ajax_queue: [],
    context: function () {
        var contextPath = "/";
        return contextPath + conf.utils.getPathVariables()[1];
    },
    getPathVariables: function () {
        var url = document.location.href;
        if (url.indexOf("http://") > -1 || url.indexOf("https://") > -1) {
            url = url.substring(url.indexOf("//") + 2, url.length);
            var pathIndex = url.indexOf("?");
            if (pathIndex != -1) {
                url = url.substring(0, pathIndex);
            }
            return url.split("/");
        }
        return [];
    },
    getParams: function () {
        var url = document.location.href;
        var params = [];
        if (url.indexOf("http://") > -1 || url.indexOf("https://") > -1) {
            url = url.substring(url.indexOf("//") + 2, url.length);
            var pathIndex = url.indexOf("?");
            if (pathIndex != -1) {
                url = url.substring(pathIndex + 1, url.length);
                paramArray = url.split("&");
                for (var i = 0; i < paramArray.length; i++) {
                    var num = paramArray[i].indexOf("=");
                    if (num > 0) {
                        var key = paramArray[i].substring(0, num);
                        var value = paramArray[i].substr(num + 1);
                        params[key] = value;
                    }
                }
            }
        }
        return params;
    },
    formatDate: function (time, format) {
        if (!format || format == null) {
            format = 'yyyy-MM-dd hh:mm:ss';
        }
        if (time == null || time == 0 || time == '' || time <= 0 || time > 9000000000000000000) {
            return '';
        }
        return new Date(time).format(format);
    },
    currDate: function () {
        var date = new Date();
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        var day = date.getDate();
        return year + '-' + (month < 10 ? ('0' + month) : month) + '-' + (day < 10 ? ('0' + day) : day);
    },
    dateDayDiff: function (start, end) {
        return Math.floor((end.getTime() - start.getTime()) / (24 * 3600 * 1000));
    },
    showSingleButton: function () {
        $('.btn-single-detail').removeAttr('disabled');
    },
    clear: function (id) {
        $('#' + id).val('');
    },
    open: function (title, url, iWidth, iHeight, callback) {
        if (url.contains('?')) {
            if (url.contains('=')) {
                url = url + '&';
            }
            url = url + 'v=' + conf.utils.currTime();
        } else {
            url = url + '?';
            if (url.contains('=')) {
                url = url + '&';
            }
            url = url + 'v=' + conf.utils.currTime();
        }
        if (!iWidth) {
            iWidth = window.screen.width * 0.9;
        }
        if (!iHeight) {
            iHeight = window.screen.height * 0.8;
        }
        var iTop = (window.screen.height - 30 - iHeight) / 2;  //获得窗口的垂直位置;
        var iLeft = (window.screen.width - 10 - iWidth) / 2;   //获得窗口的水平位置;
        var child = window.open(url, title, 'height=' + iHeight + ',,innerHeight=' + iHeight
            + ',width=' + iWidth + ',innerWidth=' + iWidth
            + ',top=' + iTop + ',left=' + iLeft
            + ',toolbar=no,menubar=no,scrollbars=yes,location=no,status=no,resizable=yes');
        if (callback) {
            child.onunload = callback;
        }
    },
    ajax: function (options) {
        if (options.lu_ajax_id) {
            // 检查之前的req是否已经完成
            if (conf.utils.ajax_queue.contains(options.lu_ajax_id)) {
                alert('不要频繁重复操作,请稍后再试.');
                return;
            }
            // complete回调，用于移除之前的ajax queue中的请求标记
            options.complete = function (jqXHR, textStatus) {
                var index = conf.utils.ajax_queue.indexOf(options.lu_ajax_id);
                if (index > -1) {
                    conf.utils.ajax_queue.splice(index, 1);
                }
            }

            conf.utils.ajax_queue.push(options.lu_ajax_id);
        }

        if (!options.dataType) {
            options.dataType = "json";
        }

        if (!options.timeout) {
            options.timeout = 1000 * 60 * 60;
        }

        if (!options.timeout) {
            options.timeout = 1000 * 60 * 3;
        }

        if (options.url.indexOf('?') > -1) {//加入时间戳
            options.url += '&' + new Date().getTime();
        } else {
            options.url += '?' + new Date().getTime();
        }

        if (!options.success) { //没有加入自定义的success回调函数，则调用默认回调函数
            options.success = function (res, textStatus, jqXHR) {
                if (res.status != 0) { //如果返回结果消息状态码非零则表示失败,弹出错误信息
                    if (res.status == 1) {
                        window.location.href = "/user/login";
                    } else {
                        if (options.fail) {
                            options.fail.call(this, res, textStatus, jqXHR);
                            return;
                        }
                        alert(res.statusInfo);
                        return;
                    }
                }

                if (options.ok) { // 如果有自定义ok回调，则在结果码为成功时回调
                    options.ok.call(this, res, textStatus, jqXHR);
                }
            }
        }
        if (!options.error) { // 没有加入自定义的error回调函数，则指定默认回调
            options.error = function (res, textStatus, jqXHR) {
                alert("ERROR:" + jqXHR);
            }
        }

        return $.ajax(options);
    },
    populate: function (form) {
        return form.serialize();
    },
    currTime: function () {
        return new Date().getTime();
    }
};

