var exec = require('cordova/exec');

//打开一个网页
exports.openWin = function(arg0, success, error) {
    exec(success, error, "OpenWin", "openWin", [arg0]);
};
//关闭当前网页
exports.closeWin = function( success, error) {
    exec(success, error, "OpenWin", "closeWin", []);
};
//关闭当前网页并退回到指定页面
exports.closeToWin = function(arg0, success, error) {
    exec(success, error, "OpenWin", "closeToWin", [arg0]);
};