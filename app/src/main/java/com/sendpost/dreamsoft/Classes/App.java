package com.sendpost.dreamsoft.Classes;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatDelegate;

import com.danikula.videocache.HttpProxyCacheServer;
import com.facebook.FacebookSdk;
import com.onesignal.OneSignal;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class App extends Application {

    public static App app;
    private static final String ONESIGNAL_APP_ID = "6694d321-ed16-46ac-a834-d2dc3f7f3981";

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        if (Functions.getSharedPreference(this).getBoolean(Variables.NIGHT_MODE,false)){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        FacebookSdk.sdkInitialize(this);

        // Enable verbose OneSignal logging to debug issues if needed.
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // OneSignal Initialization
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // promptForPushNotifications will show the native Android notification permission prompt.
        // We recommend removing the following code and instead using an In-App Message to prompt for notification permission (See step 7)

        // OneSignal.promptForPushNotifications();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.sendpost.dreamsoft", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:",
                        Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    private HttpProxyCacheServer proxy;
    public static HttpProxyCacheServer getProxy(Context context) {
        App app = (App) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)
                .maxCacheFilesCount(20)
                .cacheDirectory(new File(Functions.getAppFolder(this)+"videoCache"))
                .build();
    }
}
