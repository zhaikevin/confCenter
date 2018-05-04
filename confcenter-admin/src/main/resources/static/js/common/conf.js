!function () {
    window.console || (window.console = function (e) {
    })
}(), function (e) {
    e.fn.cofTable = function (t) {
        var a = this, n = e(this), i = {
            data: [],
            columns: {},
            operations: {displayed: !1, title: "操作", btn: []},
            selectable: !1,
            multipleSelect: !1,
            pager: !1,
            selectBackFn: function (e) {
            },
            sortBackFn: function (e) {
            },
            onInit: function () {
            },
            afterRender: function () {
            }
        };
        this.options = e.extend(i, t || {}), this.checkedItems = "";
        var s = function () {
            a.options.onInit(), r(), a.options.afterRender()
        }, r = function () {
            n.empty();
            var t = a.options.columns, i = a.options.operations, s = document.createElement("thead"),
                r = document.createElement("tr");
            if (r.className = "", a.tdCount = 0, e.each(t, function (t, n) {
                    var i = document.createElement("th");
                    if (i.className = "text-center", "string" == typeof n) i.innerHTML = n; else if (i.innerHTML = n.title, n && n.titleClass && (i.className += " " + n.titleClass), n && n.sortable) {
                        i.className += " cof-pointer js_sortTd", i.setAttribute("data-sign", "0");
                        var s = n.sortBy ? n.sortBy : t;
                        i.setAttribute("data-sortby", s);
                        var o = document.createElement("i");
                        o.className = "glyphicon glyphicon-chevron-up cof-ml5 hidden js_sortSign", o.setAttribute("data-sign", "1");
                        var l = document.createElement("i");
                        l.setAttribute("data-sign", "2"), l.className = "glyphicon glyphicon-chevron-down cof-ml5 hidden js_sortSign", i.appendChild(o), i.appendChild(l), i.onclick = function () {
                            var t = this.getAttribute("data-sortby"), n = this.getAttribute("data-sign"), i = e(this);
                            i.parent().find(".js_sortSign").addClass("hidden"), i.parent().find(".js_sortTd").attr("data-sign", "0");
                            var s = {sortBy: t};
                            switch (n) {
                                case"0":
                                    this.setAttribute("data-sign", "1"), i.find("[data-sign='1']").removeClass("hidden"), s.sign = "asc";
                                    break;
                                case"1":
                                    this.setAttribute("data-sign", "2"), i.find("[data-sign='2']").removeClass("hidden"), s.sign = "desc";
                                    break;
                                case"2":
                                    this.setAttribute("data-sign", "0"), s.sign = ""
                            }
                            a.options && a.options.sortBackFn(s)
                        }
                    }
                    r.appendChild(i), a.tdCount++
                }), i.displayed) {
                var d = document.createElement("th");
                d.className = "text-center", d.innerHTML = i.title, r.appendChild(d), a.tdCount++
            }
            s.appendChild(r), n.append(e(s));
            var c = o();
            n.append(e(c)), l()
        }, o = function () {
            var t = a.options.data, i = a.options.columns, s = a.options.operations,
                r = document.createElement("tbody");
            if (t && t.length > 0) e.each(t, function (t, o) {
                var l = document.createElement("tr"), p = 0;
                if (e.each(i, function (t, i) {
                        var s = null === o[t] || "undefined" == typeof o[t] ? "" : o[t];
                        if (i.type) {
                            var r = i.type.toLowerCase();
                            switch (r) {
                                case"datetime":
                                    s = s ? c(new Date(s), "yyyy-MM-dd HH:mm:ss") : "";
                                    break;
                                case"date":
                                    s = s ? c(new Date(s), "yyyy-MM-dd") : "", s = s ? c(new Date(s), "yyyy-MM-dd") : ""
                            }
                        }
                        i.format && e.each(i.format, function (e, t) {
                            s = s.toString().replace(new RegExp(e, "gm"), t)
                        });
                        var u = document.createElement("td");
                        if (u.className = "text-center", i.align && (u.className = "text-" + i.align), u.className += i.cellClass ? " " + i.cellClass : "", a.options.selectable && 0 == p) {
                            var h = document.createElement("input");
                            h.name = t, h.setAttribute("data-value", s), h.className = "cof-pointer js_selectInput", a.options.multipleSelect ? h.type = "checkbox" : h.type = "radio", h.onclick = function () {
                                a.options.multipleSelect ? (a.checkedItems = [], n.find(".js_selectInput").each(function (e, t) {
                                    t.checked && a.checkedItems.push(t.getAttribute("data-value"))
                                })) : a.checkedItems = this.getAttribute("data-value"), a.options && a.options.selectBackFn(this)
                            }, u.appendChild(h), l.appendChild(u)
                        } else if (i && i.href) {
                            var g = document.createElement("a");
                            g.innerHTML = s, g.href = d(i.href, o), g.setAttribute("target", "_blank"), u.appendChild(g)
                        } else {
                            var v = document.createElement("span");
                            v.innerHTML = s, u.appendChild(v)
                        }
                        var f = i && i.cellAttributes;
                        f && e.each(f, function (e, t) {
                            var a = d(t, o);
                            u.setAttribute(e, a)
                        });
                        var m = i && i.cellStyle;
                        m && e.each(m, function (t, a) {
                            e(u).css(t, a)
                        }), l.appendChild(u), p++
                    }), s.displayed) {
                    var u = document.createElement("td");
                    u.className = "text-center", e.each(s.btn, function (t, a) {
                        var n = document.createElement("a");
                        if (n.innerHTML = a.title ? a.title : "", a.className && (n.className = a.className), n.setAttribute("href", "javascript:void(0)"), a.attr && e.each(a.attr, function (e, t) {
                                var a = d(t, o);
                                n.setAttribute(e, a)
                            }), n.onclick = function () {
                                a.fn && a.fn(this)
                            }, u.appendChild(n), t < s.btn.length - 1) {
                            var i = document.createElement("span");
                            i.innerHTML = "&nbsp;&nbsp;|&nbsp;&nbsp;", i.className = "gray", u.appendChild(i)
                        }
                    }), l.appendChild(u)
                }
                r.appendChild(l)
            }); else {
                var o = document.createElement("tr");
                o.className = "text-center gray";
                var l = document.createElement("td");
                l.setAttribute("colspan", a.tdCount.toString()), l.innerHTML = "暂无记录", o.appendChild(l), r.appendChild(o)
            }
            return r
        }, l = function () {
            var t = n.attr("id"), i = e("div[data-table='" + t + "']");
            a.options.pager ? "function" == typeof i.pagination ? i.pagination(a.options.pager) : console.log("not require pagination.") : i.empty()
        }, d = function (e, t) {
            if (e.indexOf("{{:") > -1) for (var a = 0; a < 1; a++) {
                var n = e.indexOf("{{:") + 3, i = e.indexOf("}}"), s = e.substring(n, i),
                    r = "undefined" == typeof t[s] ? "" : t[s], o = "{{:" + s + "}}";
                e = e.replace(o, r), e.indexOf("{{:") > -1 && a--
            }
            return e
        }, c = function (e, t) {
            function a(e, a) {
                t = t.replace(e, a)
            }

            function n(e, t, a) {
                var n = a || "0";
                return n = new Array(t).join(n), e = n + e, e.slice(-t)
            }

            if ("string" != typeof t) return e;
            if ("string" == typeof e) return e;
            var i = e.getFullYear(), s = e.getMonth() + 1, r = e.getDate(), o = e.getHours(), l = e.getMinutes(),
                d = e.getSeconds();
            return a(/yyyy/g, n(i, 4)), a(/yy/g, n(parseInt(i.toString().slice(2), 10), 2)), a(/MM/g, n(s, 2)), a(/M/g, s), a(/dd/g, n(r, 2)), a(/d/g, r), a(/HH/g, n(o, 2)), a(/H/g, o), a(/hh/g, n(o % 12, 2)), a(/h/g, o % 12), a(/mm/g, n(l, 2)), a(/m/g, l), a(/ss/g, n(d, 2)), a(/s/g, d), t
        };
        return this.destroy = function () {
            n.empty()
        }, this.refresh = function () {
            r()
        }, this.getChecked = function () {
            return a.checkedItems
        }, this.reload = function (t) {
            a.options.data = t.data, a.options.pager = e.extend(a.options.pager, t.pager || !1);
            var i = o();
            n.find("tbody").html(i.childNodes), l()
        }, s(), this
    }
}(jQuery, window), function (e) {
    function t(t) {
        var a = [];
        a.push('<div class="modal fade" id="' + t.id + '" tabindex="-1" role="dialog" data-backdrop="static">'), a.push('\t<div class="modal-dialog">'), a.push('\t\t<div class="modal-content">'), a.push('\t\t\t<div class="modal-header">'), a.push('\t\t\t\t<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>'), a.push('\t\t\t\t<h4 class="modal-title" id="myModalLabel">' + t.title + "</h4>"), a.push("\t\t\t</div>"), a.push('\t\t\t<div class="modal-body">' + t.content + "</div>"), a.push('\t\t\t<div class="modal-footer">'), t.close && a.push('\t\t\t\t<button type="button" class="btn btn-primary" data-dismiss="modal">' + t.close + "</button>"), t.save && a.push('\t\t\t\t<button type="button" class="btn btn-success">' + t.save + "</button>"), a.push("\t\t\t</div>"), a.push("\t\t</div>"), a.push("\t</div>"), a.push("</div>"), e("body").append(a.join("")), t.width && (e("#" + t.id + " .modal-dialog").css({width: t.width}), e("#" + t.id + " .modal-body").css({width: t.width})), t.height && e("#" + t.id + " .modal-body").css({height: t.height})
    }

    function a(a) {
        t({
            id: a.id,
            title: a.title,
            content: a.content,
            close: a.close,
            save: a.save,
            width: a.width,
            height: a.height
        }), e("#" + a.id).modal({backdrop: "static", keyboard: !1, show: !0, remote: !1});
        var i = a.closeCallback;
        e("#" + a.id).on("hidden.bs.modal", function () {
            n(e(this).attr("id")), i && "function" == typeof i && i(), e("body").css({"padding-right": "0"})
        });
        var s = a.saveCallback, r = !1;
        s && "function" == typeof s && e("#" + a.id + " .btn-success").on("click", function () {
            s && "function" == typeof s ? (r = s(a.id), (void 0 === r || r) && e("#" + a.id).modal("hide")) : e("#" + a.id).modal("hide")
        })
    }

    function n(t) {
        e("#" + t).remove()
    }

    function i(e) {
        alert(e)
    }

    e.fn.alert = function (e) {
        var t = {};
        if (t.id = "alert_" + Date.now(), !e) return void i("请传递Alert弹窗提示内容");
        if ("string" == typeof e) t.title = "ConfigCenter", t.content = e, t.close = "关闭"; else {
            if (e.title ? t.title = e.title : t.title = "ConfigCenter", !e.content) return void i("请传递提示内容content属性");
            t.content = e.content, e.close ? t.close = e.close : t.close = "关闭", e.closeCallback && (t.closeCallback = e.closeCallback)
        }
        a(t)
    }, e.fn.confirm = function (e) {
        var t = {};
        return t.id = "confirm_" + Date.now(), e ? "string" == typeof e ? void i("Confirm弹窗参数格式为对象") : e.saveCallback && "function" == typeof e.saveCallback ? (e.title ? t.title = e.title : t.title = "ConfigCenter", e.content ? (t.content = e.content, e.close ? t.close = e.close : t.close = "关闭", e.save ? t.save = e.save : t.save = "保存", e.closeCallback && (t.closeCallback = e.closeCallback), e.saveCallback && (t.saveCallback = e.saveCallback), void a(t)) : void i("请传递Pop弹窗显示的html内容")) : void i("请传递确定按钮点击之后的回调函数saveCallback函数") : void i("请传递Confirm弹窗参数")
    }, e.fn.pop = function (e) {
        var t = {};
        return t.id = "pop_" + Date.now(), e ? "string" == typeof e ? void console.error("Pop弹窗参数格式为对象") : e.saveCallback && "function" == typeof e.saveCallback ? (e.title ? t.title = e.title : t.title = "ConfigCenter", e.content ? (t.content = e.content, e.close ? t.close = e.close : t.close = "关闭", e.save ? t.save = e.save : t.save = "保存", e.closeCallback && (t.closeCallback = e.closeCallback), e.saveCallback && (t.saveCallback = e.saveCallback), e.width ? t.width = e.width : t.width = 800, e.height ? t.height = e.height : t.height = 600, void a(t)) : void console.error("请传递确认内容")) : void console.error("请传递确定按钮点击之后的回调函数saveCallback函数") : void console.error("请传递pop弹窗参数")
    }, e.fn.popHide = function (t) {
        e("#" + t).modal("hide")
    }
}(jQuery), function (e) {
    e(document).ready(function () {
        e("select").each(function () {
            var t = e(this).attr("data-select");
            t = t ? t : e(this).attr("data-default"), t && e(this).val(t)
        }), e("input").blur(function () {
            e(this).hasClass("notrim") || e(this).val(e.trim(e(this).val()))
        }), e("[data-uvalid-pattern]").uvalid({
            validation_events: ["blur", "keyup", "input"],
            on: {
                valid: function (t, a) {
                    e(a).removeClass("err"), e(a).parent().removeClass("has-error"), e(a).data("errmsg", "")
                }, invalid: function (t, a, n) {
                    e(a).addClass("err"), e(a).parent().addClass("has-error");
                    var i = [], s = e(a).attr("title");
                    i.push(s);
                    for (var r = 0, o = n.length; r < o; r++) i.push(n[r].message), i.push("\n");
                    e(a).data("errmsg", i.join(""))
                }
            }
        }), e("body").on("mouseover", "[data-uvalid-pattern]", function () {
            e(this).hasClass("err") && (e.showTips(this), e(this).one("mouseout", function () {
                e.hideTips()
            }))
        }), e(".datePicker, .datePickerGroup").each(function () {
            this.setAttribute("readonly", "readonly");
            var t = this.getAttribute("data-emptybtn"), a = this.getAttribute("data-emptybtn-text");
            a = a ? a : '<i class="glyphicon glyphicon-trash"></i>', t && e(this).after('<a title="清空" onclick="$(this).prev().val(\'\');" style="margin-left: 5px">' + a + "</a>")
        }), e(document).on("focus", ".datePicker", function () {
            var e = this.getAttribute("data-datepicker-maxdate"), i = "now" === e ? "%y-%M-%d" : e,
                s = this.getAttribute("data-datepicker-mindate"), r = "now" === s ? "%y-%M-%d" : s,
                o = this.getAttribute("data-datepicker-datefmt"), l = this.getAttribute("data-datepicker-range"),
                d = l ? l.substr(l.length - 1, 1) : "", c = l ? l.substr(0, l.length - 1) : "";
            if (l) {
                var p = t(d, c);
                if (l.indexOf("+") > -1) i = p; else if (l.indexOf("-") > -1) r = p; else {
                    var u = t(d, "+" + c);
                    i = u;
                    var h = t(d, "-" + c);
                    r = h
                }
            }
            i = i ? i : "", r = r ? r : "";
            var g = this.getAttribute("data-datepicker-maxdategroup"),
                v = this.getAttribute("data-datepicker-mindategroup"),
                f = this.getAttribute("data-datepicker-grouprange"), m = f ? f.substr(f.length - 1, 1) : "",
                y = f ? f.substr(0, f.length - 1) : "";
            if (g && g.startWith("$")) {
                var b = g.substring(1, g.length);
                i = "#F{$dp.$D('" + b + "')||'" + i + "'}";
                var C = n(m, y);
                if (r && $dp.cal) {
                    var $ = $dp.$D(b, C.dateObj), w = "object" == typeof $ ? $.y + "-" + $.M + "-" + $.d : $,
                        k = new Date(Date.parse(w)), S = new Date(Date.parse("now" === s ? new Date : r));
                    if ("Invalid Date" == S && $dp.cal && l) {
                        var P = a(d, c);
                        S = new Date(P.y + "-" + P.M + "-" + P.d)
                    }
                    $ && !k.compare(S) && (l || r) && C.dateObj && (r = w)
                } else r = C.date ? "#F{$dp.$D('" + b + "' , " + C.date + ")||'" + r + "';}" : r
            }
            if (v && v.startWith("$")) {
                var x = v.substring(1, v.length);
                r = "#F{$dp.$D('" + x + "')||'" + r + "'}";
                var A = n(m, y);
                if (i && $dp.cal) {
                    var D = $dp.$D(x, A.dateObj), T = D.y + "-" + D.M + "-" + D.d, _ = new Date(Date.parse(T)),
                        E = new Date(Date.parse("now" === e ? new Date : i));
                    if ("Invalid Date" == E && $dp.cal && l) {
                        var I = a(d, c);
                        E = new Date(I.y + "-" + I.M + "-" + I.d)
                    }
                    D && !_.compare(E) && (l || i) && A.dateObj && (i = T)
                } else i = A.date ? "#F{$dp.$D('" + x + "' , " + A.date + ")||'" + i + "';}" : i
            }
            WdatePicker({dateFmt: o ? o : "yyyy-MM-dd", maxDate: i, minDate: r})
        });
        var t = function (e, t) {
            var a = "";
            switch (e) {
                case"y":
                    a = "{%y" + t + "}-%M-%d";
                    break;
                case"M":
                    a = "%y-{%M" + t + "}-%d";
                    break;
                case"d":
                    a = "%y-%M-{%d" + t + "}"
            }
            return a
        }, a = function (t, a) {
            var n = "", i = e.getToday();
            switch (a = parseInt(a), t) {
                case"y":
                    n = $dp.$DV(i, {y: a});
                    break;
                case"M":
                    n = $dp.$DV(i, {M: a});
                    break;
                case"d":
                    n = $dp.$DV(i, {d: a})
            }
            return n
        }, n = function (e, t) {
            var a = {};
            switch (t.startWith("+") && (t = t.substring(1, t.length)), t = parseInt(t), e) {
                case"y":
                    a.date = "{y:" + t + "}", a.dateObj = {y: t};
                    break;
                case"M":
                    a.date = "{M:" + t + "}", a.dateObj = {M: t};
                    break;
                case"d":
                    a.date = "{d:" + t + "}", a.dateObj = {d: t}
            }
            return a
        };
        String.prototype.startWith = function (e) {
            return !(null == e || "" == e || 0 == this.length || e.length > this.length) && this.substr(0, e.length) == e
        }, Date.prototype.compare = function (e) {
            var t = this.getTime(), a = e.getTime(), n = (a - t) / 864e5;
            return n < 0
        }, e.getToday = function () {
            var e = new Date, t = "-", a = e.getMonth() + 1, n = e.getDate();
            a >= 1 && a <= 9 && (a = "0" + a), n >= 0 && n <= 9 && (n = "0" + n);
            var i = e.getFullYear() + t + a + t + n;
            return i
        }
    })
}($);
var conf = conf || {};
conf.loaded = !0, conf.string = {
    trim: function (e) {
        return e.replace(/(^\s*)|(\s*$)/g, "")
    }, replaceAll: function (e, t, a) {
        return e.replace(new RegExp(t, "gm"), a)
    }
}, conf.list = {
    autoResize: function () {
        $(".dataTable").each(function () {
            var e = this;
            $(".row", $(e).parent()).each(function () {
                $(this).css("width", $(e).css("width"))
            }), $(".table-header").css("width", $(e).css("width"));
            var t = $(e).css("width");
            t.indexOf("px") != -1 && (t = t.substring(0, t.length - 2), t = new Number(t) + 40, t += "px"), $(".breadcrumbs").css("width", t), $(".dataTable").next().children().each(function (e) {
                $(this).removeClass("col-xs-6"), 0 === e ? $(this).css({
                    display: "inline-block",
                    padding: "5px 0"
                }) : 1 === e && $(this).css({"float": "left"})
            })
        })
    }
}, conf.pagination = function (e, t, a, n) {
    return function (i, s, r) {
        if (conf.list.autoResize(), conf.loaded) {
            conf.list.defaultSort || (conf.list.defaultSort = r.aLastSort[0]);
            var o = {};
            if (o.page = i.start / i.length + 1, o.rows = i.length, i.order && i.order.length > 0) {
                var l = i.columns[i.order[0].column];
                l.name ? o.sort = l.name : o.sort = l.data, o.order = i.order[0].dir
            } else if (r.aaSorting.length > 0) if (r.aaSorting[0] instanceof Array) {
                var d = r.aaSorting[0][0];
                i.columns[d].name ? o.sort = i.columns[d].name : o.sort = i.columns[d].data, o.order = r.aaSorting[0][1]
            } else {
                var d = r.aaSorting[0];
                i.columns[d].name ? o.sort = i.columns[d].name : o.sort = i.columns[d].data, o.order = r.aaSorting[1]
            } else o.sort = i.columns[0].data, o.order = "asc";
            if (0 == o.order || "false" == o.order) {
                var c = conf.list.defaultSort, l = i.columns[c.col];
                o.sort = l.name ? l.name : l.data, o.order = c.dir
            }
            o.params = {}, t && $("input,select", $(t)).each(function () {
                if ($(this).attr("name") && $(this).val() && "" != $(this).val()) {
                    var e = conf.string.trim($(this).val());
                    e = conf.string.replaceAll(e, "'", ""), e = conf.string.replaceAll(e, '"', ""), e = conf.string.replaceAll(e, "<", ""), e = conf.string.replaceAll(e, ">", ""), o.params[$(this).attr("name")] = e
                }
            }), $.ajax({
                type: "POST", url: e, data: o, dataType: "json", success: function (e) {
                    if (0 == e.status) {
                        var t = {};
                        t.draw = i.draw, t.recordsTotal = e.data.total, t.recordsFiltered = e.data.total, t.data = e.data.rows, $(".btn-single-detail").attr("disabled", "disabled"), s(t), conf.list.autoResize(), a && a.call()
                    }else if (1 == e.status) {
                        window.location.href = "/user/login";
                    }
                    else conf.list.autoResize(), n && n.call(), $.fn.alert(e.statusInfo)
                }
            })
        }
    }
}, $.open = function (e, t, a, n) {
    if ("undefined" == typeof e) return alert("请输入正确链接地址"), !1;
    var i = e;
    "string" == typeof i ? (t = t ? t : 800, a = a ? a : 600, n = "") : (e = i.url, t = i.width ? i.width : 800, a = i.height ? i.height : 600, n = i.name);
    var s = (window.screen.availHeight - 30 - a) / 2, r = (window.screen.availWidth - 10 - t) / 2;
    return window.open(e, n, "height=" + a + ",,innerHeight=" + a + ",width=" + t + ",innerWidth=" + t + ",top=" + s + ",left=" + r + ",status=no,toolbar=no,menubar=no,location=no,resizable=yes,scrollbars=yes,titlebar=no")
}, function (e) {
    e.fn.pagination = function (t) {
        var a = this, n = e(this), i = {
            pageSize: 10,
            dataCount: 0,
            current: 1,
            listLength: 1,
            displayedPages: 5,
            ajaxRefresh: !0,
            textAlign: "left",
            prevPageText: "&laquo;",
            nextPageText: " &raquo;",
            firstPageText: "首页",
            lastPageText: "尾页",
            displayedHomeLast: !0,
            displayedGo2Page: !1,
            selectPageSize: !1,
            disabled: !1,
            showPagerDetail: !0,
            backFn: function (e) {
            },
            onInit: function () {
            }
        }, s = function () {
            var s = e.extend(i, t || {});
            if (a.options = s, s.pageCount = Math.ceil(s.dataCount / s.pageSize), 0 == s.listLength && s.ajaxRefresh) {
                if (1 == s.current && 0 == s.listLength) return a.destroy(), !1;
                if ("function" == typeof s.backFn) {
                    var o = n.find("span.current").text();
                    s.current - 1 <= n.find("span.current").text() ? s.backFn(s.current - 1) : (s.backFn(o), alert("跳转页数已超过最大值"))
                }
            } else r(), s.onInit()
        };
        this.prevPage = function () {
            var e = a.options;
            e.current > 1 && o(e.current - 1)
        }, this.nextPage = function () {
            var e = a.options;
            (parseInt(e.current) + 1 <= e.pageCount || 0 == e.pageCount && e.pageSize == e.listLength) && o(parseInt(e.current) + 1)
        }, this.firstPage = function () {
            o(1)
        }, this.lastPage = function () {
            var e = a.options;
            o(e.pageCount)
        }, this.getPagesCount = function () {
            return a.options.pageCount
        }, this.setPagesCount = function (e) {
            a.options.pageCount = e
        }, this.getCurrentPage = function () {
            return a.options ? a.options.current : 1
        }, this.destroy = function () {
            n.empty()
        }, this.refresh = function () {
            r()
        }, this.disable = function () {
            a.options.disabled = !0, r()
        }, this.enable = function () {
            a.options.disabled = !1, r()
        }, this.updatePageSize = function (e) {
            e != a.options.pageSize && (a.options.pageSize = e, o(1))
        }, this.getPageSize = function () {
            return a.options ? a.options.pageSize : 10
        };
        var r = function () {
            a.destroy();
            var t = a.options;
            if (t.pageCount < 2 && 0 != t.pageCount) return !1;
            var i = document.createElement("nav");
            i.setAttribute("aria-label", "Page navigation");
            var s = document.createElement("ul");
            if (s.className = "pagination", e(s).css("margin-bottom", "0px"), t.displayedHomeLast && t.pageCount > t.displayedPages) {
                var r = document.createElement("a");
                r.innerHTML = t.firstPageText, r.className = "js_go2FirstPage", r.onclick = function () {
                    a.firstPage()
                };
                var l = document.createElement("li");
                l.appendChild(r), s.appendChild(l)
            }
            var d = document.createElement("li"), c = document.createElement("a");
            c.setAttribute("aria-label", "Previous"), c.onclick = function () {
                a.prevPage()
            };
            var p = document.createElement("span");
            p.setAttribute("aria-hidden", "true"), p.innerHTML = t.prevPageText, c.appendChild(p), d.appendChild(c), s.appendChild(d);
            var u = parseInt(t.current);
            if (0 != t.pageCount) for (var h = parseInt(t.displayedPages / 2), g = t.pageCount <= t.displayedPages ? 1 : u - h > 0 ? u + h > t.pageCount ? t.pageCount - t.displayedPages + 1 : u - h : 1, v = g + t.displayedPages > t.pageCount ? t.pageCount : g + t.displayedPages - 1; g <= v;) {
                var f = document.createElement("li"), m = document.createElement("a");
                m.innerHTML = g, m.onclick = function () {
                    var t = parseInt(e(this).text());
                    o(t)
                }, g == t.current && (f.className = "active"), f.appendChild(m), s.appendChild(f), g++
            } else {
                var f = document.createElement("li"), m = document.createElement("a");
                m.innerHTML = u, f.appendChild(m), s.appendChild(f)
            }
            var y = document.createElement("li"), b = document.createElement("a");
            b.setAttribute("aria-label", "Next");
            var C = document.createElement("span");
            if (C.setAttribute("aria-hidden", "true"), C.innerHTML = t.nextPageText, b.appendChild(C), b.onclick = function () {
                    a.nextPage()
                }, y.appendChild(b), s.appendChild(y), t.displayedHomeLast && t.pageCount > t.displayedPages) {
                var $ = document.createElement("a");
                $.innerHTML = t.lastPageText, $.className = "js_go2LastPage", $.onclick = function () {
                    a.lastPage()
                };
                var w = document.createElement("li");
                w.appendChild($), s.appendChild(w)
            }
            if (i.appendChild(s), n.append(i), t.showPagerDetail) {
                var k = (1 * t.current - 1) * t.pageSize + 1, S = k + t.listLength - 1;
                n.append("<div style='margin-bottom: 0px'><small class='gray'>共 " + t.dataCount + " 条记录，当前第 " + k + " 条到第 " + S + " 条。</small></div>"), n.css("text-align", t.textAlign)
            }
        }, o = function (e, t) {
            if (!(e > a.options.pageCount && a.options.pageCount > 0)) return a.options.current = e, a.options.ajaxRefresh || r(), a.options.backFn(e, t)
        };
        return s(), this
    }
}(jQuery, window), function (e) {
    function t(e) {
        return this.componentClass = "pop-select", this.arrowClass = "glyphicon", this.arrowDownClass = "glyphicon-triangle-bottom", this.arrowUpClass = "glyphicon-triangle-top", this.popSelectDivIdPrefix = "pop_select_", this.popSelectReplaceInputIdPrefix = "pop_input_", this.popSelectDiv = null, this.popSelectInput = null, this.popSelectArrow = null, this.popSelectUl = null, this.popSelectPageChange = null, this.popSelectPageStatistic = null, this.id = null, this.select = e, this.pageSize = 6, this.pageNo = 1, this.pageSum = 0, this.data = null, this.pageChangeClass = "pop-select-page", this.pagePreClass = this.pageChangeClass + " pre", this.pageNextClass = this.pageChangeClass + " next", this.pagePreText = "上一页", this.pageNextText = "下一页", this.isReadonly = !1, this.searchResult = [], this
    }

    t.prototype.popSelectInit = function (a) {
        if (a) {
            var n = new t(e(a));
            n.handleSelect()
        } else e("select." + this.componentClass).each(function () {
            var a = new t(e(this));
            a.handleSelect()
        });
        setTimeout(function () {
            e(window).trigger("resize")
        }, 300)
    }, t.prototype.handleSelect = function () {
        var t = this;
        t.id = Date.now() + "_" + parseInt(1e11 * Math.random()), t.handlerSelectParam(), e(t.select).wrap("<span></span>");
        var a = e(t.select).parent(), n = t.createSelectDiv(), i = t.createReplaceInput(), s = t.createArrow();
        a.append(i), a.append(s), t.bindReplaceInputEvent(), e(t.select).hide(), t.listenSelectChange(), a.append(n), t.bindPopSelectDivEvent(), t.bindPopSelectLiEvent(), t.bindPageChangeEvent(), t.togglePageChange()
    }, t.prototype.handlerSelectParam = function () {
        var e = this;
        e.select && e.select.attr("pageSize") && (e.pageSize = parseInt(e.select.attr("pageSize"))), e.select && e.select.attr("readonly") && (e.isReadonly = !0)
    }, t.prototype.convertOptionsToJson = function () {
        for (var t = this, a = e(t.select).children(), n = [], i = 0; i < a.length; i++) n.push({
            text: e(a[i]).text(),
            value: e(a[i]).attr("value")
        });
        t.data = n, t.pageSum = parseInt(t.data.length / t.pageSize), t.data.length % t.pageSize === 0 ? "" : t.pageSum += 1
    }, t.prototype.listenSelectChange = function () {
        var t = this;
        e(t.select).on("change", function () {
            var a = e(this).find("option:selected").text();
            e(t.popSelectInput).val(a)
        })
    }, t.prototype.createSelectDiv = function () {
        var t = this, a = e("<div/>");
        a.attr("id", t.popSelectDivIdPrefix + t.id), a.css({
            position: "absolute",
            border: "1px solid #ddd",
            background: "#fff",
            "box-shadow": "0 1px 10px #ddd",
            "border-bottom-right-radius": "5px",
            "border-bottom-left-radius": "5px",
            "z-index": "99"
        }), t.convertOptionsToJson();
        var n = t.createSelectUlList();
        return t.createSelectLiAryList(t.getPageData()).forEach(function (e) {
            n.append(e)
        }), a.append(n), a.append(t.createPageChange()), a.hide(), t.popSelectDiv = a, a
    }, t.prototype.createSelectUlList = function () {
        var t = this, a = e("<ul/>");
        return a.css({"list-style": "none", padding: "0", margin: "0"}), t.popSelectUl = a, a
    }, t.prototype.createSelectLiAryList = function (t) {
        for (var a = [], n = null, i = 0; i < t.length; i++) n = e("<li/>"), n.css({
            padding: 10,
            "border-bottom": "1px solid #ddd",
            cursor: "pointer",
            "word-wrap": "break-word",
            "white-space": "normal"
        }), n.attr("data-value", t[i].value), n.text(t[i].text), a.push(n);
        return a
    }, t.prototype.createReplaceInput = function () {
        var t = this, a = e("<input/>");
        a.attr("id", t.popSelectReplaceInputIdPrefix + t.id);
        var n = e(t.select).attr("class");
        return n && (n = n.replace(t.componentClass, ""), a.addClass(n)), a.attr("placeholder", t.data[0].text), t.isReadonly && a.attr("readonly", "readonly"), a.css({
            display: "inline-block",
            width: e(this.select).outerWidth()
        }), e(t.select).val() && a.val(e(t.select).find("option:selected").text()), t.popSelectInput = a, a
    }, t.prototype.createArrow = function () {
        var t = this, a = e("<i/>");
        return a.addClass(t.arrowClass), a.addClass(t.arrowDownClass), a.css({
            position: "relative",
            color: "#888",
            "font-size": "12px",
            right: "17px"
        }), t.popSelectArrow = a, a
    }, t.prototype.createPageChange = function () {
        var t = this, a = e("<div/>"), n = e("<a/>"), i = e("<a/>"), s = e("<span/>"),
            r = {width: "100%", "text-align": "center", display: "none"}, o = {
                display: "inline-block",
                padding: "10px 0",
                width: "35%",
                "text-decoration": "none",
                color: "#888",
                "font-size": "13px"
            }, l = {
                display: "inline-block",
                padding: "10px 0",
                width: "28%",
                color: "#888",
                "border-left": "1px solid #eee",
                "border-right": "1px solid #eee"
            };
        return a.addClass(t.pageChangeClass), a.css(r), n.addClass(t.pagePreClass), n.html(t.pagePreText), n.css(o), i.addClass(t.pageNextClass), i.html(t.pageNextText), i.css(o), s.css(l), s.html(t.pageNo + " / " + t.pageSum), a.append(n), a.append(s), a.append(i), t.popSelectPageChange = a, t.popSelectPageStatistic = s, a
    }, t.prototype.getPageData = function () {
        var e = this, t = [], a = (e.pageNo - 1) * e.pageSize;
        a = 0 === a ? 0 : a;
        var n = a + e.pageSize;
        n = n >= e.data.length ? e.data.length : n;
        for (var i = a; i < n; i++) t.push(e.data[i]);
        return t
    }, t.prototype.togglePageChange = function () {
        var t = this;
        t.pageSum > 1 ? e(t.popSelectPageChange).show() : e(t.popSelectPageChange).hide()
    }, t.prototype.refreshPageData = function (e) {
        var t = this;
        if (t.popSelectUl.empty(), e) {
            if (0 === t.searchResult.length) return void t.popSelectDiv.hide();
            t.createSelectLiAryList(t.searchResult).forEach(function (e) {
                t.popSelectUl.append(e)
            }), t.bindPopSelectLiEvent(), t.popSelectPageChange.hide()
        } else t.createSelectLiAryList(t.getPageData()).forEach(function (e) {
            t.popSelectUl.append(e)
        }), t.togglePageChange(), t.bindPopSelectLiEvent(), t.popSelectPageStatistic.html(t.pageNo + " / " + t.pageSum);
        t.popSelectDiv.show()
    }, t.prototype.search = function (e) {
        for (var t = this, a = 0; a < t.data.length; a++) t.data[a].text && t.data[a].text.indexOf(e) > -1 && t.searchResult.push(t.data[a]);
        t.refreshPageData(!0)
    }, t.prototype.locationPopDiv = function () {
        var t = this, a = e(t.popSelectInput).outerWidth(), n = e(t.popSelectInput).outerHeight(),
            i = e(t.popSelectInput).position().top, s = e(t.popSelectInput).position().left;
        t.popSelectDiv.css({width: a, height: "auto", top: i + n, left: s})
    }, t.prototype.bindPopSelectDivEvent = function () {
        var t = this;
        e(window).resize(function () {
            t.locationPopDiv()
        })
    }, t.prototype.bindPopSelectLiEvent = function () {
        var t = this;
        e(t.popSelectDiv).find("li").on("mouseenter", function () {
            e(this).css({background: "#eee"})
        }).on("mouseleave", function () {
            e(this).css({background: "#fff"})
        }).on("mousedown", function () {
            var a = e(this).text(), n = e(this).attr("data-value");
            e(t.popSelectDiv).hide(), e(t.popSelectInput).val(a), e(t.select).val(n), e(t.select).trigger("change")
        })
    }, t.prototype.bindReplaceInputEvent = function () {
        var t = this;
        e(t.popSelectInput).on("focus", function () {
            e(t.popSelectDiv).show(), e(t.popSelectArrow).removeClass(t.arrowDownClass).addClass(t.arrowUpClass), t.refreshPageData()
        }).on("blur", function () {
            t.searchResult.splice(0, t.searchResult.length), e(t.popSelectDiv).hide(), e(t.popSelectArrow).removeClass(t.arrowUpClass).addClass(t.arrowDownClass);
            var a = e.trim(e(this).val()), n = !1;
            if (a) {
                for (var i = 0; i < t.data.length; i++) if (a === t.data[i].text) {
                    n = !0, e(t.select).val(t.data[i].value);
                    break
                }
                n || (e(t.select).val(""), e(this).val(""))
            } else e(t.select).val(""), e(t.select).trigger("change")
        }).on("keyup", function (a) {
            t.searchResult.splice(0, t.searchResult.length);
            var n = e.trim(e(this).val());
            if (!n) return e(t.popSelectDiv).show(), e(t.popSelectArrow).removeClass(t.arrowDownClass).addClass(t.arrowUpClass), t.refreshPageData(), void e(t.select).val("");
            if (27 === a.keyCode) return e(t.popSelectDiv).hide(), void e(this).blur();
            var i = !1;
            if (13 === a.keyCode) {
                if (n) {
                    for (var s = 0; s < t.data.length; s++) if (n === t.data[s].text) {
                        i = !0, e(t.select).val(t.data[s].value);
                        break
                    }
                    i || (e(t.select).val(""), e(this).val(""))
                } else e(t.select).val("");
                t.searchResult.splice(0, t.searchResult.length), e(t.popSelectDiv).hide(), e(t.popSelectArrow).removeClass(t.arrowUpClass).addClass(t.arrowDownClass)
            } else t.search(n)
        })
    }, t.prototype.bindPageChangeEvent = function () {
        var t = this;
        e(t.popSelectDiv).find("a." + t.pageChangeClass).on("mouseenter", function () {
            e(this).css({background: "#eee"})
        }).on("mouseleave", function () {
            e(this).css({background: "#fff"})
        }).on("mousedown", function (a) {
            a.preventDefault();
            var n = e(this).hasClass("pre");
            if (n) {
                if (1 === t.pageNo) return;
                t.pageNo = t.pageNo - 1, t.refreshPageData()
            } else {
                if (t.pageNo === t.pageSum) return;
                t.pageNo = t.pageNo + 1, t.refreshPageData()
            }
            return !1
        })
    }, window.onload = function () {
        (new t).popSelectInit()
    }, e.fn.popSelectInit = function () {
        (new t).popSelectInit(e(this))
    }
}(jQuery), function ($) {
    $.fn.uvalid = function (e) {
        e = $.extend({}, e || {});
        var t = new uvalidGroup(e);
        return t.append(this), t
    }, $.fn.validAll = function (e) {
        setting = {
            validation_events: ["blur", "keyup", "input"], on: {
                valid: function (e, t) {
                    $(t).removeClass("err"), $(t).parent().removeClass("has-error"), $(t).data("errmsg", "")
                }, invalid: function (e, t, a) {
                    $(t).addClass("err"), $(t).parent().addClass("has-error");
                    var n = [], i = $(t).attr("title");
                    n.push(i);
                    for (var s = 0, r = a.length; s < r; s++) n.push(a[s].message), n.push("\n");
                    $(t).data("errmsg", n.join(""))
                }
            }
        };
        var t = new uvalidGroup(setting);
        t.append(this), t.validateAll(e)
    }, $.extend({
        uvalid: {
            version: "0.2.3", PATTERN: {}, LOG: [], addPattern: function (e) {
                e.name && ($.uvalid.PATTERN[e.name] = e)
            }, log: function (e) {
                $.uvalid.LOG.push(e)
            }
        }
    });
    var AsyncRequest = function () {
        this.reqs = [], this.status = 0
    };
    AsyncRequest.prototype.addRequest = function (e) {
        0 == this.status && this.reqs.push(e)
    }, AsyncRequest.prototype.go = function () {
        if (0 == this.status) {
            this.status = 1;
            for (var e = this, t = this.reqs, a = this.reqs.length, n = 0; n < t.length; n++) {
                var i = t[n];
                if (0 == this.status) return;
                i(function () {
                    a--, 0 == a && e.finish()
                })
            }
        }
    }, AsyncRequest.prototype.finish = function (e) {
        this.status = 0, this.onfinished && this.onfinished(e)
    }, AsyncRequest.prototype.clear = function () {
        0 == this.status && (this.reqs = [])
    };
    var uvalidGroup = function (e) {
        this.setting = e, this._jvs = [], this._selector = null, this.async = new AsyncRequest
    };
    uvalidGroup.prototype.refresh = function () {
        if (this._selector) {
            var e = this;
            this._jvs = [], $(this._selector).each(function () {
                var t = $(this).data("uvalid");
                t || (t = new uvalid(this, e.setting), $(this).data("uvalid", t)), e._jvs.push(t)
            })
        }
    }, uvalidGroup.prototype.append = function (e) {
        e && (this._selector = this._selector ? this._selector.add(e) : e, this.refresh())
    }, uvalidGroup.prototype.remove = function (e) {
        e && this._selector && (this._selector = this._selector.not(e), this.refresh())
    }, uvalidGroup.prototype.validateAll = function (e) {
        var t = this._jvs, a = this.async, n = !0;
        a.clear(), a.onfinished = function () {
            e && e(n)
        };
        for (var i = !0, s = 0; s < t.length; s++) {
            var r = t[s];
            r && r.exists() && (i = !1)
        }
        if (i) e && e(n); else {
            for (var s = 0; s < t.length; s++) {
                var o = t[s];
                o && o.exists() && !function (e) {
                    a.addRequest(function (t) {
                        e.check(function (e) {
                            e || (n = e), t()
                        })
                    })
                }(o)
            }
            a.go()
        }
    };
    var uvalid = function (e, t) {
        var a = this;
        this.invalid_pattern = [], this.el = e, this.setting = t, this.async = new AsyncRequest;
        var n = this.setting.validation_events || [];
        $.each(n, function (t, n) {
            $(e).bind(n, function () {
                a.check()
            })
        });
        var i = this.setting.on || {};
        $.each(i, function (e, t) {
            $(a).bind(e, t)
        }), $(e).blur(function () {
            $(a).trigger("blur")
        })
    };
    uvalid.prototype.exists = function () {
        return jQuery(this.el).closest("body").length > 0 && $(this.el).is(":visible")
    }, uvalid.prototype._checkPattern = function (resultTable) {
        var $el = $(this.el), pattern = $el.attr("data-uvalid-pattern") || "",
            code = pattern.replace(/ /g, "").replace(/\|/g, "||").replace(/\&/g, "&&");
        return code = code.replace(/([^|&\(\)]+)/g, function (e, t) {
            return "undefined" != typeof resultTable[t] ? resultTable[t] : "true"
        }), eval(code)
    }, uvalid.prototype.check = function (e) {
        var t = this, a = $(this.el), n = this.async,
            i = (a.attr("data-uvalid-pattern") || "").replace(/\(/g, "").replace(/\)/g, "").replace(/\|/g, ",").replace(/\&/g, ","),
            s = i ? i.split(",") : [], r = a.val(), o = {};
        this.invalid_pattern = [], n.clear(), n.onfinished = function (a) {
            var n = t._checkPattern(o);
            e && e(n), t.after_check(n)
        };
        for (var l = 0; l < s.length; l++) {
            var d = $.trim(s[l]), c = $.uvalid.PATTERN[d];
            c ? !function (e, a) {
                n.addRequest(function (n) {
                    e.validate.call(t.el, r, function (i, s) {
                        i || (e.message = s || e.message, t.invalid_pattern.push(e)), o[a] = i, n()
                    })
                })
            }(c, d) : $.uvalid.log("找不到模式[" + d + "]")
        }
        n.go()
    }, uvalid.prototype.after_check = function (e) {
        e ? $(this).trigger("valid", [this.el]) : $(this).trigger("invalid", [this.el, this.invalid_pattern])
    }, $.uvalid.addPattern({
        name: "notEmpty", message: "不能为空", validate: function (e, t) {
            t($.trim(e).length > 0)
        }
    }), $.uvalid.addPattern({
        name: "lt100", message: "请输入在100个字以内", validate: function (e, t) {
            t($.trim(e).length <= 100)
        }
    }), $.uvalid.addPattern({
        name: "lt200", message: "请输入在200个字以内", validate: function (e, t) {
            t($.trim(e).length <= 200)
        }
    }), $.uvalid.addPattern({
        name: "2_10", message: "请输入在2-10个字以内", validate: function (e, t) {
            t($.trim(e).length >= 2 && $.trim(e).length <= 10)
        }
    }), $.uvalid.addPattern({
        name: "str_lt10", message: "仅可以为字母或汉字，长度不超过10位字符", validate: function (e, t) {
            var a = /^[A-Za-z\u4e00-\u9fa5]+$/g;
            t(a.test($.trim(e)) && $.trim(e).length <= 10)
        }
    }), $.uvalid.addPattern({
        name: "lt500", message: "不能含有特殊字符且长度在500个字符以内", validate: function (e, t) {
            var a = /^(([^<>#%&'?$:@!~\\\/])*)$/;
            t(a.test($.trim(e)) && $.trim(e).length <= 500)
        }
    }), $.uvalid.addPattern({
        name: "lt15", message: "请输入在15个字符以内", validate: function (e, t) {
            t($.trim(e).length <= 15)
        }
    }), $.uvalid.addPattern({
        name: "lt20", message: "请输入在20个字符以内", validate: function (e, t) {
            t($.trim(e).length <= 20)
        }
    }), $.uvalid.addPattern({
        name: "number", message: "只能输入数字", validate: function (e, t) {
            var a = /^([+-]?)\d*\.?\d+$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "decimalNumber", message: "支持最多保留两位小数",
        validate: function (e, t) {
            var a = /^\d+(?:\.\d{1,2})?$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "empty", message: "", validate: function (e, t) {
            var a = /^\s*$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "mobile", message: "格式不正确", validate: function (e, t) {
            t(/^1[3|4|5|6|7|8|9][0-9]\d{8}$/.test(e))
        }
    }), $.uvalid.addPattern({
        name: "numAndWord", message: "只支持数字和字母", validate: function (e, t) {
            t(/^[A-Za-z0-9]+$/.test(e))
        }
    }), $.uvalid.addPattern({
        name: "email", message: "格式不正确", validate: function (e, t) {
            var a = "" == $.trim(e);
            t(/^([a-zA-Z0-9-_\\.])+@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$/.test(e) || a)
        }
    }), $.uvalid.addPattern({
        name: "username_exist", message: "the username is exist", validate: function (e, t) {
            function a(e) {
                var a;
                a = !parseInt(e.data, 10), t(a)
            }

            var n = $.trim(e);
            "" != n ? $.ajax({
                url: "http://xxx.com/webApi/validate",
                data: {username: n},
                dataType: "jsonp",
                success: a
            }) : t(!0)
        }
    }), $.uvalid.addPattern({
        name: "normalChar", message: "只允许输入汉字、数字和字母", validate: function (e, t) {
            if ("" === $.trim(e)) return void t(!0);
            var a = /^[A-Za-z0-9\u4e00-\u9fa5\s]+$/g;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "identifier", message: "请输入字母和数字组合", validate: function (e, t) {
            if ("" === $.trim(e)) return void t(!0);
            var a = /^(?=.*?[a-zA-Z])(?=.*?[0-9])[a-zA-Z0-9]+$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "noBadLetter", message: "不支持特殊字符", validate: function (e, t) {
            if ("" === $.trim(e)) return void t(!0);
            var a = /^[^\|"'<>]*$/g;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "less100", message: "必须为小于100的正整数!", validate: function (e, t) {
            if ("" === $.trim(e)) return void t(!0);
            var a = /^[1-9][0-9]{0,1}$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "0-19", message: "输入0-19之间的数字", validate: function (e, t) {
            var a = /^([0-9]|(1[0-9]))$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "0-10", message: "请输入0-10之间的数字且最多保留一位小数", validate: function (e, t) {
            var a = /^(\d(\.\d{1})?)$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "positiveInteger", message: "请输入正整数", validate: function (e, t) {
            var a = /^[1-9]\d*$/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "nonNegativeInteger", message: "请输入大于或等于0的整数", validate: function (e, t) {
            var a = /(^[1-9]+\d*$)|(^0$)/;
            t(a.test($.trim(e)))
        }
    }), $.uvalid.addPattern({
        name: "URL", message: "请输入合法的链接地址", validate: function (e, t) {
            var a = /^([hH][tT]{2}[pP]:\/\/|[hH][tT]{2}[pP][sS]:\/\/)(([A-Za-z0-9-~]+)\.)+([A-Za-z0-9-~\/])+$/;
            t(!!a.test($.trim(e)))
        }
    }), $.extend({
        showTips: function (e, t) {
            if ($(".js_valid_errmsg").length < 1) {
                var a = document.createElement("div");
                a.className = "valid-tips js_valid_errmsg", $("body").append(a)
            }
            var n = $(".js_valid_errmsg");
            n.html($(e).data("errmsg"));
            var i = $(e).offset();
            n.css({
                "min-width": $(e).outerWidth() - 2,
                top: i.top + e.clientHeight + 2 + "px",
                left: i.left + "px"
            }), n.show()
        }, hideTips: function () {
            var e = $(".js_valid_errmsg");
            e.text("").css({top: 0, left: 0}), e.hide()
        }
    })
}(jQuery, window);