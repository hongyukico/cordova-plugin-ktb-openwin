//
//  OpenWinPlugin.h
//  MyApp
//
//  Created by zyj7815 on 16/9/28.
//
//

#import "Cordova/CDV.h"

@interface CDVOpenWinPlugin : CDVPlugin

/**
 打开一个页面
 */
- (void)openWin:(CDVInvokedUrlCommand *)command;

/**
 关闭一个页面
 */
- (void)closeWin:(CDVInvokedUrlCommand *)command;

/**
 关闭当前网页并退回到指定页面
 */
- (void)closeToWin:(CDVInvokedUrlCommand *)command;

@end
