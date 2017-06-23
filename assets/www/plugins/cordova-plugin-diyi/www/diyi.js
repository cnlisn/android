cordova.define("cordova-plugin-diyi.DiyiPlugin", function(require, exports, module) {
var exec = require('cordova/exec');

var DiyiPlugin = function () { };

DiyiPlugin.prototype.diyiciFangFaMing= function (ceshi, successCallback, errorCallback) {
// 第三个参数是插件的名称，必须与文件中的feature.name保持一致
  exec(successCallback, null, 'DiyiPlugin', 'diyiciFangFaMing', [ceshi]);
};
var diyiPlugin = new DiyiPlugin();
module.exports = diyiPlugin;

});
