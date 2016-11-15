//
//  CDVOpenWinPlugin.m
//  MyApp
//
//  Created by zyj7815 on 16/9/28.
//

#import "CDVOpenWinPlugin.h"
#import <objc/runtime.h>
#import "CDVWebViewController.h"
#import "CDVAttributePlugin.h"

@implementation CDVOpenWinPlugin

#pragma mark - 打开一个页面
- (void)openWin:(CDVInvokedUrlCommand *)command {
    
    NSDictionary *dict = command.arguments[0];
    NSString *url = dict[@"url"];
    NSString *pageParam = dict[@"pageParam"];
    
    CordovaSingleton *singleton = [CordovaSingleton singletonClass];
    singleton.pageParam = pageParam;
    
    [self pushWeb:url];
    
}


- (void)pushWeb:(NSString *)url {
    
    CDVWebViewController *web = [[CDVWebViewController alloc] init];
    web.startPage = url;
    [self.viewController.navigationController pushViewController:web animated:YES];
    
    
    CordovaSingleton *singleton = [CordovaSingleton singletonClass];
    [singleton.classNameArr addObject:@{@"class":web,
                                        @"url":url,
                                        @"native":self.viewController}];
    
}

#pragma mark - 关闭一个页面
- (void)closeWin:(CDVInvokedUrlCommand *)command {
    [self.viewController.navigationController popViewControllerAnimated:YES];
}

#pragma mark - 关闭当前网页并退回到指定页面
- (void)closeToWin:(CDVInvokedUrlCommand *)command {
    
    CordovaSingleton *singleton = [CordovaSingleton singletonClass];
    NSString *url = command.arguments[0][@"url"];
    
    NSMutableArray *classArr = [NSMutableArray array];
    for (NSDictionary *dict in singleton.classNameArr) {
        [classArr addObject:dict[@"url"]];
    }
    
    // 如果栈中存在需要跳转的url
    if ([classArr containsObject:url]) {
        NSInteger index = 0;
        NSArray *sortArr = [NSArray arrayWithArray:singleton.classNameArr];
        sortArr = [[sortArr reverseObjectEnumerator] allObjects];
        // 返回页面，在栈中的下标
        for (int i = 0; i < sortArr.count; i++) {
            if ([sortArr[i][@"url"] isEqualToString:url]) {
                NSLog(@"%d-%@",i,sortArr[i]);
                index = i;
                break;
            }
        }
        
        NSDictionary *dict = sortArr[index];
        [self.viewController.navigationController popToViewController:dict[@"class"]
                                                             animated:YES];
    }
    else {
        NSInteger webCount = singleton.classNameArr.count;
        NSInteger navigationCount = self.viewController.navigationController.viewControllers.count;
        NSInteger index = navigationCount - webCount;
        
        [self.viewController.navigationController popToViewController:self.viewController.navigationController.viewControllers[index]
                                                             animated:YES];
    }
    
}



@end



