# Cordova OpenWin plugin

for iOS and Android, by [ktb](https://github.com/hongyukico)

## Description
打开网页、返回到上一页页面、返回到指定的页面，支持android、ios平台


* 1.0.0 Works with Cordova 3.x
* 1.0.1+ Works with Cordova >= 4.0

## Installation

```
cordova plugin add cordova-plugin-ktb-openwin
```


## Usage

```javascript
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

```
