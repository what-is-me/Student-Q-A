(function () {

    var factory = function (exports) {

        var pluginName = "file-dialog";

        exports.fn.fileDialog = function () {

            var _this = this;
            var cm = this.cm;
            var lang = this.lang;
            var editor = this.editor;
            var settings = this.settings;
            var cursor = cm.getCursor();
            var selection = cm.getSelection();
            var fileLang = lang.dialog.file;
            var classPrefix = this.classPrefix;
            var iframeName = classPrefix + "file-iframe";
            var dialogName = classPrefix + pluginName, dialog;

            cm.focus();

            var loading = function (show) {
                var _loading = dialog.find("." + classPrefix + "dialog-mask");
                _loading[(show) ? "show" : "hide"]();
            };

            if (editor.find("." + dialogName).length < 1) {
                var guid = (new Date).getTime();
                var action = settings.fileUploadURL + (settings.fileUploadURL.indexOf("?") >= 0 ? "&" : "?") + "guid=" + guid;

                var dialogContent = ("<form action=\"" + action + "\" target=\"" + iframeName + "\" method=\"post\" enctype=\"multipart/form-data\" class=\"" + classPrefix + "form\">") +
                    ("<iframe name=\"" + iframeName + "\" id=\"" + iframeName + "\" guid=\"" + guid + "\"></iframe>") +
                    "<label>" + fileLang.url + "</label>" +
                    "<input type=\"text\" data-url />" + (function () {
                        return "<div class=\"" + classPrefix + "file-input\">" +
                            "<input type=\"file\" name=\"" + classPrefix + "file-file\"/>" +
                            "<input type=\"submit\" value=\"" + fileLang.uploadButton + "\" />" +
                            "</div>";
                    })() +
                    "<br/>" +
                    "<label>" + fileLang.alt + "</label>" +
                    "<input type=\"text\" value=\"" + (selection == "" ? "附件" : selection) + "\" data-alt />" +
                    "<br/>" +
                    ("</form>");

                //var fileFooterHTML = "<button class=\"" + classPrefix + "btn " + classPrefix + "file-manager-btn\" style=\"float:left;\">" + fileLang.managerButton + "</button>";

                dialog = this.createDialog({
                    title: fileLang.title,
                    width: 465,
                    height: 254,
                    name: dialogName,
                    content: dialogContent,
                    mask: settings.dialogShowMask,
                    drag: settings.dialogDraggable,
                    lockScreen: settings.dialogLockScreen,
                    maskStyle: {
                        opacity: settings.dialogMaskOpacity,
                        backgroundColor: settings.dialogMaskBgColor
                    },
                    buttons: {
                        enter: [lang.buttons.enter, function () {
                            var url = this.find("[data-url]").val();
                            var alt = this.find("[data-alt]").val();

                            if (url === "") {
                                alert(fileLang.fileURLEmpty);
                                return false;
                            }

                            var altAttr = (alt !== "") ? " \"" + alt + "\"" : "";
                            if (alt == "")
                                cm.replaceSelection("\n [" + alt + "](" + url + altAttr + ")  \n");
                            else
                                cm.replaceSelection("\n [附件](" + url + altAttr + ")  \n");

                            if (alt === "") {
                                cm.setCursor(cursor.line, cursor.ch + 2);
                            }

                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }],

                        cancel: [lang.buttons.cancel, function () {
                            this.hide().lockScreen(false).hideMask();

                            return false;
                        }]
                    }
                });

                dialog.attr("id", classPrefix + "file-dialog-" + guid);

                var fileInput = dialog.find("[name=\"" + classPrefix + "file-file\"]");

                fileInput.bind("change", function () {
                    var fileName = fileInput.val();
                    if (fileName === "") {
                        alert(fileLang.uploadFileEmpty);
                        return false;
                    }

                    loading(true);

                    var submitHandler = function () {

                        var uploadIframe = document.getElementById(iframeName);

                        uploadIframe.onload = function () {

                            loading(false);

                            var body = (uploadIframe.contentWindow ? uploadIframe.contentWindow : uploadIframe.contentDocument).document.body;
                            var json = (body.innerText) ? body.innerText : ((body.textContent) ? body.textContent : null);

                            json = (typeof JSON.parse !== "undefined") ? JSON.parse(json) : eval("(" + json + ")");

                            if (json.success === 1) {
                                dialog.find("[data-url]").val(json.url);
                            } else {
                                alert(json.message);
                            }
                            return false;
                        };
                    };

                    dialog.find("[type=\"submit\"]").bind("click", submitHandler).trigger("click");
                });
            }

            dialog = editor.find("." + dialogName);
            dialog.find("[type=\"text\"]").val("附件");
            dialog.find("[type=\"file\"]").val("");

            this.dialogShowMask(dialog);
            this.dialogLockScreen();
            dialog.show();

        };

    };

    // CommonJS/Node.js
    if (typeof require === "function" && typeof exports === "object" && typeof module === "object") {
        module.exports = factory;
    } else if (typeof define === "function")  // AMD/CMD/Sea.js
    {
        if (define.amd) { // for Require.js

            define(["editormd"], function (editormd) {
                factory(editormd);
            });

        } else { // for Sea.js
            define(function (require) {
                var editormd = require("./../../editormd");
                factory(editormd);
            });
        }
    } else {
        factory(window.editormd);
    }

})();
