package com.lemma.plugins.lemmasdk;

import android.Manifest;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.getcapacitor.JSObject;
import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;

import lemma.lemmavideosdk.vast.listeners.AdManagerCallback;
import lemma.lemmavideosdk.vast.manager.LMAdRequest;
import lemma.lemmavideosdk.vast.manager.LMVideoAdManager;

@NativePlugin(
        permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.INTERNET
        }
)
public class LemmaSDK extends Plugin {

    private LMVideoAdManager mVAdManager = null;
    private ViewGroup mViewGroup;
    RelativeLayout mAdViewLayout;

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.success(ret);
    }

    // Initialize AdMob with appId
    @PluginMethod()
    public void initialize(final PluginCall call) {
        Log.d("lm-initialize",call.toString());
        /* Sample AdMob App ID: ca-app-pub-3940256099942544~3347511713 */
        String appId = call.getString("appId", "ca-app-pub-3940256099942544~3347511713");
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                try {
                    mViewGroup = (ViewGroup) ((ViewGroup) getActivity().findViewById(android.R.id.content)).getChildAt(0);

                    call.success();

                }catch (Exception ex) {
                    call.error(ex.getLocalizedMessage(), ex);
                }

            }
        });


    }

    @PluginMethod()
    public void showVideoAd(final PluginCall call) {

        final String adUnitId  = call.getString("adUnitId");
        final String pubId = call.getString("pubId");
        final int height = call.getInt("height", 250);
        final int width = call.getInt("width",RelativeLayout.LayoutParams.MATCH_PARENT);
        final int bottomMargin = call.getInt("bottomMargin",0);
        final String baseServerURL = call.getString("baseServerURL");

        if (adUnitId == null || pubId == null) {
            call.error("adUnitId & pubId no passed", new Exception("adUnitId & pubId no passed"));
            return;
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mAdViewLayout = new RelativeLayout(getContext());
                mAdViewLayout.setHorizontalGravity(Gravity.CENTER_HORIZONTAL);
                mAdViewLayout.setGravity(Gravity.BOTTOM);

                float density = getContext().getResources().getDisplayMetrics().density;
                int finalWidth = width;

                if (finalWidth != RelativeLayout.LayoutParams.MATCH_PARENT ){
                    finalWidth = (int)(finalWidth * density);
                }
                final CoordinatorLayout.LayoutParams mAdViewLayoutParams = new CoordinatorLayout.LayoutParams(
                        finalWidth,
                        (int)(height * density)
                );
                mAdViewLayoutParams.gravity = Gravity.BOTTOM | Gravity.CENTER;
                mAdViewLayoutParams.bottomMargin  = (int)(bottomMargin * density);
                mAdViewLayout.setLayoutParams(mAdViewLayoutParams);
                mViewGroup.addView(mAdViewLayout);


                Log.d("lm-showVideoAd",call.toString());
                LMAdRequest adRequest = new LMAdRequest(pubId,adUnitId);
                if (baseServerURL !=null && baseServerURL.length() > 0){
                    adRequest.setAdServerBaseURL(baseServerURL);
                }
                mVAdManager = new LMVideoAdManager(getActivity(), adRequest, new AdManagerCallback() {
                    @Override
                    public void onAdError(LMVideoAdManager adManager, Error error) {
                        notifyListeners("onAdError", new JSObject().put("value", true).put("error",error.toString()));
                    }

                    @Override
                    public void onAdEvent(AdManagerCallback.AD_EVENT event) {
                        notifyListeners("onAdEvent", new JSObject().put("value", true).put("event",event.toString()));

                        switch (event) {
                            case AD_LOADED:
                                mVAdManager.startAd();
                                break;
                            default:
                                break;
                        }
                    }
                });
                mVAdManager.init(mAdViewLayout);

                call.success(new JSObject().put("value", true));
            }
        });


    }
    @PluginMethod()
    public void removeVideoAd(PluginCall call) {
        try {
            if (mAdViewLayout != null) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mAdViewLayout != null) {
                            mViewGroup.removeView(mAdViewLayout);
                            if(mVAdManager != null ) {
                                mVAdManager.destroy();
                                mVAdManager = null;
                                Log.d(getLogTag(), "Video ad Removed");
                            }
                        }
                    }
                });
            }

            call.success(new JSObject().put("value", true));

        }catch (Exception ex) {
            call.error(ex.getLocalizedMessage(), ex);
        }
    }
}