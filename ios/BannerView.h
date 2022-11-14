#import <React/RCTView.h>
#import <React/RCTComponent.h>

@interface BannerView : RCTView

@property (nonatomic, copy) RCTBubblingEventBlock onError;
@property (nonatomic, copy) RCTBubblingEventBlock onLoad;
@property (nonatomic, copy) RCTBubblingEventBlock onLeftApplication;
@property (nonatomic, copy) RCTBubblingEventBlock onReturnedToApplication;

@property (nonatomic, strong) NSString *size;
@property (nonatomic, strong) NSString *adUnitId;
@property (nonatomic, strong) NSString *type;
@property (nonatomic, strong) NSString *ownerIdAdfox;
@property (nonatomic, strong) NSString *p1Adfox;
@property (nonatomic, strong) NSString *p2Adfox;

@end
