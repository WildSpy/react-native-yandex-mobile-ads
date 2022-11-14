import React from 'react';
import { requireNativeComponent, StyleProp, ViewStyle } from 'react-native';

type AdType =
  | 'BANNER_300x250'
  | 'BANNER_300x300'
  | 'BANNER_320x50'
  | 'BANNER_320x100'
  | 'BANNER_400x240'
  | 'BANNER_728x90';

type BannerType = 'adfox' | 'ya';

interface NativeBannerViewProps {
  size: string;
  adUnitId: string;
  type: BannerType;
  ownerIdAdfox: string;
  p1Adfox: string;
  p2Adfox: string;
  onError?: Function;
  onLoad?: Function;
  onLeftApplication?: Function;
  onReturnedToApplication?: Function;
  style?: StyleProp<ViewStyle>;
}

interface BannerViewProps {
  size: AdType;
  adUnitId: string;
  type?: BannerType;
  ownerIdAdfox?: string;
  p1Adfox?: string;
  p2Adfox?: string;
  onError?: Function;
  onLoad?: Function;
  onLeftApplication?: Function;
  onReturnedToApplication?: Function;
  style?: StyleProp<ViewStyle>;
}

const RNBannerView =
  requireNativeComponent<NativeBannerViewProps>('BannerView');

const getHeight: Record<AdType, number> = {
  BANNER_300x250: 250,
  BANNER_300x300: 300,
  BANNER_320x50: 50,
  BANNER_320x100: 100,
  BANNER_400x240: 240,
  BANNER_728x90: 90,
};

const getWidth: Record<AdType, number> = {
  BANNER_300x250: 300,
  BANNER_300x300: 300,
  BANNER_320x50: 320,
  BANNER_320x100: 320,
  BANNER_400x240: 400,
  BANNER_728x90: 728,
};

const getHeightForType = (type: AdType) => getHeight[type];
const getWidthForType = (type: AdType) => getWidth[type];

const BannerView = (props: BannerViewProps) => {
  const {
    size,
    adUnitId,
    type = 'ya',
    ownerIdAdfox = '',
    p1Adfox = '',
    p2Adfox = '',
    onError,
    onLoad,
    onLeftApplication,
    onReturnedToApplication,
    style,
    ...restProps
  } = props;
  const height = getHeightForType(size);
  const width = getWidthForType(size);

  return (
    <RNBannerView
      adUnitId={adUnitId}
      size={size}
      type={type}
      ownerIdAdfox={ownerIdAdfox}
      p1Adfox={p1Adfox}
      p2Adfox={p2Adfox}
      onError={onError}
      onLoad={onLoad}
      onLeftApplication={onLeftApplication}
      onReturnedToApplication={onReturnedToApplication}
      style={[style, { height: height, width: width }]}
      {...restProps}
    />
  );
};

export default BannerView;
