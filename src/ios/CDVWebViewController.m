//
//  CDVWebViewController.m
//  MyApp
//
//  Created by zyj7815 on 16/9/28.
//
//

#import "CDVWebViewController.h"
#import "CDVAttributePlugin.h"

@interface CDVWebViewController ()

@property (nonatomic, copy)NSString *webTitle;

@end

@implementation CDVWebViewController

- (void)viewDidLoad {
    
    [super viewDidLoad];
    
    self.view.backgroundColor = [UIColor whiteColor];
    [[NSNotificationCenter defaultCenter]addObserver:self
                                            selector:@selector(getWebTitle:)
                                                name:@"webTitle"
                                              object:nil];
}


- (void)getWebTitle:(NSNotification *)notifi {
    if (_webTitle.length == 0) {
        _webTitle = notifi.userInfo[@"title"];
    }
}


- (void)viewWillAppear:(BOOL)animated {
    [super viewWillAppear:animated];

    NSMutableArray *arr = [NSMutableArray arrayWithArray:
                           self.navigationController.viewControllers];

#warning root页面是native页面需要放开注释 —— —— —— —— ——
     [arr removeObjectAtIndex:0];
    // —— —— —— —— —— —— —— —— —— ——
    // —— —— —— —— —— —— —— —— —— ——

    CordovaSingleton *singleton = [CordovaSingleton singletonClass];
    NSMutableArray *classArr = [NSMutableArray array];
    for (NSDictionary *dict in singleton.classNameArr) {
        [classArr addObject:dict[@"native"]];
    }
    
    NSMutableArray *newArr = [NSMutableArray array];
    for (int i = 0; i < arr.count; i++) {
        [newArr addObject:singleton.classNameArr[i]];
    }
    singleton.classNameArr = [NSMutableArray arrayWithArray:newArr];
}


- (void)dealloc {
    NSLog(@"dealloc");
    [[NSNotificationCenter defaultCenter]removeObserver:self];
}


@end
