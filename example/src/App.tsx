import React, { Component } from 'react';
import { Button, StyleSheet, View } from 'react-native';
import {
  MobileAds,
  BannerView,
  InterstitialAdManager,
  RewardedAdManager,
} from 'react-native-yandex-mobile-ads';

MobileAds.initialize({
  userConsent: true,
  locationConsent: true,
  enableDebugErrorIndicator: true,
  enableLogging: true,
});

export default class ExampleAd extends Component {
  render() {
    return (
      <View style={styles.container}>
        <View style={styles.bannerContainer}>
          <BannerView
            adUnitId={'demo-banner-yandex'}
            size={'BANNER_300x300'}
            onLoad={() => console.log('onLoad')}
            onLeftApplication={() => console.log('onLeftApplication')}
            onReturnedToApplication={() =>
              console.log('onReturnedToApplication')
            }
            onError={(err: any) => console.log('error', err)}
          />
        </View>
        <View style={styles.button}>
          <Button
            title={'Interstitial Ad'}
            onPress={() => {
              InterstitialAdManager.showAd('R-M-DEMO-interstitial')
                .then((didClick: boolean) => {
                  console.log('clicked: ' + didClick);
                })
                .catch((error: any) => {
                  console.log('error: ' + error);
                });
            }}
          />
        </View>
        <View style={styles.button}>
          <Button
            title={'Rewarded Ad'}
            onPress={() => {
              RewardedAdManager.showAd('R-M-DEMO-rewarded-client-side-rtb')
                .then((resp) => {
                  const amount = resp.amount;
                  const type = resp.type;
                  const clicked = resp.click;
                  console.log(
                    `amount: ${amount}, type: ${type}, clicked: ${clicked}`
                  );
                })
                .catch((error: any) => {
                  console.log('error: ' + error);
                });
            }}
          />
        </View>
      </View>
    );
  }
}

let styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    marginTop: 50,
  },
  bannerContainer: {
    marginVertical: 10,
    alignItems: 'center',
  },
  button: {
    margin: 10,
    borderWidth: 1,
  },
});
