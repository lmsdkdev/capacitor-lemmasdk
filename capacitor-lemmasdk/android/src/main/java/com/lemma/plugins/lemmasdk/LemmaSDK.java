package com.lemma.plugins.lemmasdk;

import android.Manifest;
import android.view.ViewGroup;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import lemma.lemmavideosdk.vast.listeners.AdManagerCallback;
import lemma.lemmavideosdk.vast.manager.LMAdRequest;
import lemma.lemmavideosdk.vast.manager.LMConfig;
import lemma.lemmavideosdk.vast.manager.LMVideoAdManager;
//import lemma.lemmavideosdk.LMBannerView;

@NativePlugin(
        permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
        }
)
public class LemmaSDK extends Plugin {

    private LMVideoAdManager mVAdManager = null;
    private ViewGroup mViewGroup;

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    // Initialize AdMob with appId
    @PluginMethod()
    public void initialize(PluginCall call) {
        /* Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713 */
        String appId = call.getString("appId", "ca-app-pub-3940256099942544~3347511713");

        try {
            mViewGroup = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);

            call.success();

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }

    @PluginMethod()
    public void showBanner(PluginCall call) {

        LMAdRequest adRequest = new LMAdRequest("1", "3100");
        mVAdManager = new LMVideoAdManager(getActivity(), adRequest, new AdManagerCallback() {
            @Override
            public void onAdError(LMVideoAdManager adManager, Error error) {

            }

            @Override
            public void onAdEvent(AdManagerCallback.AD_EVENT event) {

                switch (event) {
                    case AD_LOADED:
                        mVAdManager.startAd();
                        break;
                    default:
                        break;
                }
            }
        });
        mVAdManager.init(mViewGroup);
        call.success(new JSObject().put("value", true));

    }
}