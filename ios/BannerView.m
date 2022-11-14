#import "BannerView.h"
#import "YandexMobileAds/YandexMobileAds.h"


@interface BannerView () <YMAAdViewDelegate>

@property (nonatomic, strong) YMAAdView *adView;

@end

@implementation BannerView

- (void)setSize:(NSString *)size
{
    NSLog(@"size");
    _size = size;
    [self createViewIfCan];
}

- (void)setAdUnitId:(NSString *)adUnitId
{
    _adUnitId = adUnitId;
    [self createViewIfCan];
}

- (void)createViewIfCan
{
    if (!_adUnitId || !_size) {
        return;
    }

    if (_adView) {
        [_adView removeFromSuperview];
    }

    YMAAdSize *adSize = [YMAAdSize fixedSizeWithCGSize: [self getSize: _size]];
    YMAAdView *adView = [[YMAAdView alloc] initWithAdUnitID:_adUnitId adSize:adSize];

    adView.frame = CGRectMake(0, 0, adView.bounds.size.width, adView.bounds.size.height);
    adView.delegate = self;

    if([_type isEqualToString:@"adfox"]) {
          NSMutableDictionary *parameters = [[NSMutableDictionary alloc] init];
          parameters[@"adf_ownerid"] = _ownerIdAdfox;
          parameters[@"adf_p1"] = _p1Adfox;
          parameters[@"adf_p2"] = _p2Adfox;
          parameters[@"adf_pt"] = @"b";
          parameters[@"adf_pd"] = @"";
          parameters[@"adf_pw"] = @"";
          parameters[@"adf_pv"] = @"";
          parameters[@"adf_prr"] = @"";
          parameters[@"adf_pdw"] = @"";
          parameters[@"adf_pdh"] = @"";
          parameters[@"adf_puid1"] = @"";
          parameters[@"adf_puid2"] = @"";
          parameters[@"adf_puid3"] = @"";
          parameters[@"adf_puid4"] = @"";
          parameters[@"adf_puid5"] = @"";
          parameters[@"adf_puid6"] = @"";
          parameters[@"adf_puid7"] = @"";
          YMAMutableAdRequest *request = [[YMAMutableAdRequest alloc] init];
          request.parameters = parameters;

          [adView loadAdWithRequest:request];
    } else {
          [adView loadAd];
    }

    [self addSubview:adView];

    _adView = adView;
}

- (CGSize)getSize:(NSString *)size
{
    if ([size isEqualToString:@"BANNER_300x250"]) {
        return YMAAdSizeBanner_300x250;
    } else if ([size isEqualToString:@"BANNER_300x300"]) {
        return YMAAdSizeBanner_300x300;
    }else if ([size isEqualToString:@"BANNER_320x50"]) {
        return YMAAdSizeBanner_320x50;
    } else if ([size isEqualToString:@"BANNER_320x100"]) {
        return YMAAdSizeBanner_320x100;
    } else if ([size isEqualToString:@"BANNER_300x250"]) {
        return YMAAdSizeBanner_300x250;
    } else if ([size isEqualToString:@"BANNER_400x240"]) {
        return YMAAdSizeBanner_400x240;
    } else if ([size isEqualToString:@"BANNER_728x90"]) {
        return YMAAdSizeBanner_728x90;
    }

    return YMAAdSizeBanner_240x400;
}

- (void)adViewDidFailLoading:(YMAAdView *)adView error:(NSError *)error
{
    NSLog(@"error");
    if (_onError) {
        _onError(@{ @"error": @{ @"message": [error localizedDescription] } });
    }
}

- (void)adViewDidLoad:(YMAAdView *)adView
{
    if (_onLoad) {
        _onLoad(nil);
    }
}


- (void)adViewWillLeaveApplication:(YMAAdView *)adView {
    if (_onLeftApplication) {
        _onLeftApplication(nil);
    }
}
- (void)adView:(YMAAdView *)adView willPresentScreen:(nullable UIViewController *)viewController {
    if (_onReturnedToApplication) {
        _onReturnedToApplication(nil);
    }
}

@end
