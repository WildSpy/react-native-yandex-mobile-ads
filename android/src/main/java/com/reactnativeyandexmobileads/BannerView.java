package com.reactnativeyandexmobileads;

import java.util.Map;
import java.util.HashMap;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.LifecycleEventListener;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.events.RCTEventEmitter;
import com.facebook.react.views.view.ReactViewGroup;
import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;


public class BannerView extends ReactViewGroup implements BannerAdEventListener, LifecycleEventListener {
  private ReactContext mContext;
  private BannerAdView myAdView;
  private String mAdUnitId;
  private String mType;
  private String mOwnerIdAdfox;
  private String mP1Adfox;
  private String mP2Adfox;
  private AdSize mSize;
  private RCTEventEmitter mEventEmitter;

  public BannerView(ThemedReactContext context) {
    super(context);
    mContext = context;
    mContext.addLifecycleEventListener(this);
    mEventEmitter = mContext.getJSModule(RCTEventEmitter.class);
  }

  public void setAdUnitId(String adUnitId) {
    mAdUnitId = adUnitId;
    createAdViewIfCan();
  }

  public void setOwnerIdAdfox(String ownerIdAdfox) {
    mOwnerIdAdfox = ownerIdAdfox;
    createAdViewIfCan();
  }

  public void setP1Adfox(String p1Adfox) {
      mP1Adfox = p1Adfox;
      createAdViewIfCan();
  }

  public void setP2Adfox(String p2Adfox) {
      mP2Adfox = p2Adfox;
      createAdViewIfCan();
  }

  public void setType(String type) {
      mType = type;
      createAdViewIfCan();
  }

  public void setSize(AdSize size) {
    mSize = size;
    createAdViewIfCan();
  }

  private void createAdViewIfCan() {
    if (myAdView == null && mAdUnitId != null && mSize != null && mType != null && mOwnerIdAdfox != null && mP1Adfox != null && mP2Adfox != null) {
      this.myAdView = new BannerAdView(getContext());

      myAdView.setAdUnitId(mAdUnitId);
      myAdView.setAdSize(mSize);

      // Создание объекта таргетирования рекламы.
      final AdRequest adRequest;
      if (new String(mType).equals("adfox")) {
          Map<String, String> parameters = new HashMap<String, String>();
          parameters.put("adf_ownerid", mOwnerIdAdfox);
          parameters.put("adf_p1", mP1Adfox);
          parameters.put("adf_p2", mP2Adfox);
          parameters.put("adf_pt", "b");
          parameters.put("adf_pd", "");
          parameters.put("adf_pw", "");
          parameters.put("adf_pv", "");
          parameters.put("adf_prr", "");
          parameters.put("adf_pdw", "");
          parameters.put("adf_pdh", "");
          parameters.put("adf_puid1", "");
          parameters.put("adf_puid2", "");
          parameters.put("adf_puid3", "");
          parameters.put("adf_puid4", "");
          parameters.put("adf_puid5", "");
          parameters.put("adf_puid6", "");
          parameters.put("adf_puid7", "");
          adRequest = new AdRequest.Builder().setParameters(parameters).build();
      } else {
          adRequest = new AdRequest.Builder().build();
      }

      // Регистрация слушателя для отслеживания событий, происходящих в баннерной рекламе.
      myAdView.setBannerAdEventListener(this);

      // Загрузка объявления.
      myAdView.loadAd(adRequest);
    }
  }

  @Override
  public void onHostResume() {

  }

  @Override
  public void onHostPause() {

  }

  @Override
  public void onImpression(ImpressionData impressionData)  {

  }

  @Override
  public void onAdClicked() {

  }

  @Override
  public void onHostDestroy() {
    if (myAdView != null) {
      myAdView.destroy();
    }
  }

  @Override
  public void onAdLoaded() {
    this.removeAllViews();

    Resources r = mContext.getResources();
    DisplayMetrics dm = r.getDisplayMetrics();

    int pxW = mSize.getWidth() > 0 ?
      dp2px(mSize.getWidth(), dm)
      : r.getDisplayMetrics().widthPixels;
    int pxH = dp2px(mSize.getHeight(), dm);

    myAdView.measure(pxW, pxH);
    myAdView.layout(0, 0, pxW, pxH);

    addView(myAdView);

    mEventEmitter.receiveEvent(getId(), "onLoad", null);
  }

  private int dp2px(int dp, DisplayMetrics dm) {
    return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, dm));
  }


  @Override
  public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {
    WritableMap event = Arguments.createMap();

    event.putInt("errorCode", adRequestError.getCode());
    event.putString("errorMessage", adRequestError.getDescription());
    sendEvent("onError", event);

    myAdView = null;
  }

  @Override
  public void onLeftApplication() {
    sendEvent("onLeftApplication", null);
  }

  @Override
  public void onReturnedToApplication() {
    sendEvent("onReturnedToApplication", null);
  }

  private void sendEvent(String name, @Nullable WritableMap event) {
    ReactContext reactContext = (ReactContext) getContext();
    reactContext.getJSModule(RCTEventEmitter.class).receiveEvent(
      getId(),
      name,
      event);
  }
}
