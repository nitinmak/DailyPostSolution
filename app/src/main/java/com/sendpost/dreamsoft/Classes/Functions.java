package com.sendpost.dreamsoft.Classes;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.model.UserModel;
import com.sendpost.dreamsoft.network.ApiClient;
import com.sendpost.dreamsoft.network.ApiService;
import com.sendpost.dreamsoft.responses.SimpleResponse;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.DownloadListener;
import com.sendpost.dreamsoft.BuildConfig;
import com.sendpost.dreamsoft.MainActivity;
import com.sendpost.dreamsoft.model.BussinessModel;
import com.sendpost.dreamsoft.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

import retrofit2.Call;


public class Functions {

    public static Dialog dialog;

    public static void showLoader(Context context) {
        try {
            if (dialog != null) {
                cancelLoader();
                dialog = null;
            }{
                dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.item_dialog_loading_view);
                dialog.getWindow().setBackgroundDrawable(ContextCompat.getDrawable(context,R.drawable.login_btn));
                dialog.setCanceledOnTouchOutside(false);
                dialog.setCancelable(false);
                dialog.show();
            }

        } catch (Exception e) {
            Log.d(Constants.tag, "Exception : " + e);
        }
    }

    public static void cancelLoader() {
        try {
            if (dialog != null || dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception e) {
            Log.d(Constants.tag, "Exception : " + e);
        }
    }

    public static void PrintHashKey(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                final MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                final String hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d(Constants.tag, "KeyHash : " + hashKey);
            }
        } catch (Exception e) {
        }
    }

    public static boolean isNetworkConnected(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static HashMap<String, String> getHeaders(Context context) {
        HashMap<String, String> headers = new HashMap<String, String>();
        headers.put("User-Id", getSharedPreference(context).getString(Variables.U_ID, null));
        headers.put("Auth-Token", getSharedPreference(context).getString(Variables.AUTH_TOKEN, null));
        headers.put("device", "android");
        headers.put("version", BuildConfig.VERSION_NAME);
        headers.put("ip", getSharedPreference(context).getString(Variables.DEVICE_IP, null));
        headers.put("device-token", getSharedPreference(context).getString(Variables.DEVICE_TOKEN, null));
        return headers;
    }

    public static SharedPreferences getSharedPreference(Context context) {
        if (Variables.sharedPreferences == null)
            Variables.sharedPreferences = context.getSharedPreferences(Variables.PREF_NAME, Context.MODE_PRIVATE);
        return Variables.sharedPreferences;
    }

    public static String getUID(Context ctx) {
        return getSharedPreference(ctx).getString(Variables.U_ID,"0");
    }

    public static String getItemBaseUrl(String url) {
        if (!url.contains(Variables.http)) {
            url = Constants.BASE_URL + url;
        }
        return url;
    }

    public static String removeSpecialChar(String s) {
        return s.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, "" + msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP | Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static String getPermissionStatus(Activity activity, String androidPermissionName) {
        if (ContextCompat.checkSelfPermission(activity, androidPermissionName) != PackageManager.PERMISSION_GRANTED) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, androidPermissionName)) {
                return "blocked";
            }
            return "denied";
        }
        return "granted";
    }

    public static File createImageFile(Context context) throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.ENGLISH).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        return image;
    }

    public static void showPermissionSetting(Activity context,String message) {
        new CustomeDialogFragment(context.getString(R.string.permission_alert),
                message,
                DialogType.WARNING,
                true,
                false,
                true,
                new CustomeDialogFragment.DialogCallback() {
                    @Override
                    public void onCencel() {
                    }
                    @Override
                    public void onSubmit() {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package",context.getPackageName(), null);
                        intent.setData(uri);
                        context.startActivity(intent);
                    }
                    @Override
                    public void onDismiss() {
                    }
                    @Override
                    public void onComplete(Dialog dialog) {

                    }
                });
    }

    public static String bitmapToBase64(Bitmap imagebitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        imagebitmap.compress(Bitmap.CompressFormat.PNG, 99, baos);
        byte[] byteArray = baos.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    public static void setLocale(String lang, Activity context, Class<?> className,boolean isRefresh) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        if (Build.VERSION.SDK_INT >= 24) {
            config.setLocale(locale);
            context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
        } else {
            config.locale = locale;
            context.getApplicationContext().createConfigurationContext(config);
        }

//        Locale myLocale = new Locale(lang);
//        Resources res = context.getResources();
//        DisplayMetrics dm = res.getDisplayMetrics();
//        Configuration conf = new Configuration();
//        conf.locale = myLocale;
//        res.updateConfiguration(conf, dm);
//        context.onConfigurationChanged(conf);
        if (isRefresh) {
            updateActivity(context,className);
        }
    }

    public static void updateActivity(Activity context, Class<?> className) {
        Intent intent = new Intent(context,className);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static String getAppFolder(Context activity) {
        return activity.getExternalFilesDir(null).getPath()+"/";
    }

    public static boolean IsPremiumEnable(Context context) {
        return getSharedPreference(context).getString(Variables.SUB_NAME,"0").length() > 6;
    }

    public static void updatePostView(Context context, String id,String type) {
        Log.d("ININININ",id+"----"+type);
        ApiClient.getRetrofit().create(ApiService.class).updatePostViews(Constants.API_KEY,id,type).enqueue(new retrofit2.Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, retrofit2.Response<SimpleResponse> response) {

            }
            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {

            }
        });
    }

    public static boolean isLogin(Context context) {
        return getSharedPreference(context).getBoolean(Variables.IS_LOGIN,false);
    }

    public static void saveUserData(UserModel userObject, Context context) {
        getSharedPreference(context).edit().putBoolean(Variables.IS_LOGIN,true).apply();
        getSharedPreference(context).edit().putString(Variables.U_ID,userObject.getId()).apply();
        getSharedPreference(context).edit().putString(Variables.NAME,userObject.getName()).apply();
        getSharedPreference(context).edit().putString(Variables.P_PIC,userObject.getProfile_pic()).apply();
        getSharedPreference(context).edit().putString(Variables.U_EMAIL,userObject.getEmail()).apply();
        getSharedPreference(context).edit().putString(Variables.NUMBER,userObject.getNumber()).apply();
        getSharedPreference(context).edit().putString(Variables.ADDRESSP,userObject.getAddress()).apply();
        getSharedPreference(context).edit().putString(Variables.REFER_ID,userObject.getMember_id()).apply();
        getSharedPreference(context).edit().putString(Variables.REFERRAL,userObject.getParent_id()).apply();
        getSharedPreference(context).edit().putString(Variables.WEBSITEP,userObject.getWebsite()).apply();
        getSharedPreference(context).edit().putString(Variables.SUB_PRICE,userObject.getSubscription_price()).apply();
        getSharedPreference(context).edit().putString(Variables.SUB_NAME,userObject.getSubscription_name()).apply();
        getSharedPreference(context).edit().putString(Variables.SUB_DATE,userObject.getSubscription_date()).apply();
        getSharedPreference(context).edit().putString(Variables.SUB_END_DATE,userObject.getSubscription_end_date()).apply();
        getSharedPreference(context).edit().putString(Variables.U_SOCIAL_ID,userObject.getSocial_id()).apply();
        getSharedPreference(context).edit().putString(Variables.SOCIAL,userObject.getSocial()).apply();
        getSharedPreference(context).edit().putString(Variables.AUTH_TOKEN,userObject.getAuth_token()).apply();
        getSharedPreference(context).edit().putString(Variables.DEVICE_TOKEN,userObject.getDevice_token()).apply();
        getSharedPreference(context).edit().putString(Variables.CREATED,userObject.getCreated_at()).apply();
    }


    public static void saveBussinessData(BussinessModel bussiness, Context context) {
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_ID,bussiness.getId()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_NAME,bussiness.getName()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_EMAIL,bussiness.getEmail()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_NUMBER,bussiness.getNumber()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_ADDRESS,bussiness.getAddress()).apply();
        getSharedPreference(context).edit().putString(Variables.WEBSITE,bussiness.getWebsite()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_LOGO,bussiness.getImage()).apply();
        getSharedPreference(context).edit().putString(Variables.BUSSINESS_ABOUT,bussiness.getAbout()).apply();
        getSharedPreference(context).edit().putString(Variables.INSTAGRAM,bussiness.getInstagram()).apply();
        getSharedPreference(context).edit().putString(Variables.YOUTUBE,bussiness.getYoutube()).apply();
        getSharedPreference(context).edit().putString(Variables.FACEBOOK,bussiness.getFacebook()).apply();
        getSharedPreference(context).edit().putString(Variables.WHATSAPP,bussiness.getWhatsapp()).apply();
    }

    public static void shareApp(Context context,String lang) {


    if(Objects.equals(lang, "english")){
        Functions.showLoader(context);
        File directory = new File(getAppFolder(context)+Variables.APP_HIDED_FOLDER);
        String filename = System.currentTimeMillis()+".jpg";
        AndroidNetworking.download(getItemBaseUrl(getSharedPreference(context).getString("share_image_url","")), directory.getPath(), filename).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Functions.cancelLoader();
                String shareText = getSharedPreference(context).getString("share_text","").replace("REFER_CODE",getSharedPreference(context).getString(Variables.REFER_ID,""));
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(directory.getPath()+"/"+filename)));
                intent.putExtra(Intent.EXTRA_TEXT,shareText);
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                context.startActivity(intent);
            }

            public void onError(ANError aNError) {
                Functions.cancelLoader();
            }
        });


    } else if (Objects.equals(lang, "hi")) {
        Functions.showLoader(context);
        File directory = new File(getAppFolder(context)+Variables.APP_HIDED_FOLDER);
        String filename = System.currentTimeMillis()+".jpg";
        AndroidNetworking.download(getItemBaseUrl(getSharedPreference(context).getString("share_image_url","")), directory.getPath(), filename).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Functions.cancelLoader();

                String shareText = "नमस्ते! मुझे भरोसा है कि SENDPOST ऐप हमारे व्यवसाय को बढ़ाने, हमारे व्यवसाय को अलग करने, ग्राहकों के साथ संबंध बनाने, व्यवसायों को अधिक उचाइयो प् ले जाने के लिए है। साथ ही sendpost  का उद्देश्य हमारे ग्राहकों, दोस्तों, परिवार और निकट और प्रिय लोगों के बीच हमारी उपस्थिति और व्यक्तित्व को बढ़ाना है।\n" +
                        "यदि आप अगले 48 घंटों में मेरे रेफरल कोड  " + getSharedPreference(context).getString(Variables.REFER_ID,"") + "  के साथ मेरे लिंक का उपयोग करके sendpost ऐप डाउनलोड करते हैं तो आप मुफ्त 200 रजिस्ट्रेशन बोनस पॉइंट भी जीत सकते हैं।\n" +
                        "https://play.google.com/store/apps/details?id=com.sendpost.dreamsoft";

                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(directory.getPath()+"/"+filename)));
                intent.putExtra(Intent.EXTRA_TEXT,shareText);
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                context.startActivity(intent);
            }

            public void onError(ANError aNError) {
                Functions.cancelLoader();
            }
        });


    }else {
        Functions.showLoader(context);
        File directory = new File(getAppFolder(context)+Variables.APP_HIDED_FOLDER);
        String filename = System.currentTimeMillis()+".jpg";
        AndroidNetworking.download(getItemBaseUrl(getSharedPreference(context).getString("share_image_url","")), directory.getPath(), filename).build().startDownload(new DownloadListener() {
            public void onDownloadComplete() {
                Functions.cancelLoader();
                String shareText = "હાય! અમારા વ્યવસાયને વધારવામાં, અમારા વ્યવસાયને અલગ પાડવામાં, ગ્રાહક સાથે સંબંધો બનાવવા," +
                        " વ્યવસાયોને વધુ એક્સપોઝર આપવા માટે મને SENDPOST એપ્લિકેશન પર વિશ્વાસ છે. તેમજ Sendpost નો હેતુ અમારા ગ્રાહકો, મિત્રો, કુટુંબીજનો અને નજીકના અને પ્રિયજનોમાં અમારી હાજરી અને વ્યક્તિત્વ વધારવાનો છે.\n" +
                        "જો તમે આગામી 48 કલાકમાં મારા રેફરલ કોડ " + getSharedPreference(context).getString(Variables.REFER_ID,"") + " સાથેની મારી લિંકનો ઉપયોગ કરીને Sendpost એપ ડાઉનલોડ કરશો તો તમને મફત 200 રજીસ્ટ્રેશન બોનસ પોઈન્ટ પણ જીતી શકો છો..\n" +
                        "https://play.google.com/store/apps/details?id=com.sendpost.dreamsoft";
//getSharedPreference(context).getString(Variables.REFER_ID,"");
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_STREAM, FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileprovider", new File(directory.getPath()+"/"+filename)));
                intent.putExtra(Intent.EXTRA_TEXT,shareText);
                intent.putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
                context.startActivity(intent);
            }

            public void onError(ANError aNError) {
                Functions.cancelLoader();
            }
        });
    }


    }

//
//    public static void shareApp(Context context) {
//        Functions.showLoader(context);
//        new OmegaIntentBuilder(context)
//                .share()
//                .filesUrls(getItemBaseUrl(getSharedPreference(context).getString("share_image_url","")))
//                .download(new DownloadCallback() {
//                    @Override
//                    public void onDownloaded(boolean success, @NotNull ContextIntentHandler contextIntentHandler) {
//                        Functions.cancelLoader();
//                        contextIntentHandler.getIntent().putExtra(Intent.EXTRA_TEXT,getSharedPreference(context).getString("share_text",""));
//                        contextIntentHandler.getIntent().putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
//                        contextIntentHandler.startActivity();
//                    }
//                });
//    }

    public static void rateApp(Context context) {
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://play.google.com/store/apps/details?id="+context.getPackageName()));
        context.startActivity(intent);
    }

    public static void logOut(Activity context) {
        new AlertDialog.Builder(context)
                .setMessage("Do you really want to logout ?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreference(context).edit().clear().apply();
                        context.startActivity(new Intent(context, MainActivity.class));
                        context.finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static String getFormatedDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd MMM yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static long getDuration(Context context,String path) {
        MediaPlayer mMediaPlayer = null;
        long duration = 0;
        try {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setDataSource(context, Uri.parse(path));
            mMediaPlayer.prepare();
            duration = mMediaPlayer.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mMediaPlayer != null) {
                mMediaPlayer.reset();
                mMediaPlayer.release();
            }
        }
        return duration;
    }

    public static String saveImage(Bitmap paramBitmap,Context context) {
        File directory = new File(getAppFolder(context)+Variables.APP_HIDED_FOLDER);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, System.currentTimeMillis()+".png");
        if (file.exists()) {
            file.delete();
        }

        try {
            OutputStream outputStream = new FileOutputStream(file);
            paramBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStream.close();
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            return file.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }

    public static int getSubsIntervel(Date startDate, Date endDate) {
        long totalDuration = endDate.getTime() - startDate.getTime();
        Date currentTime = new Date();
        long currentDuration = currentTime.getTime() - startDate.getTime();

        final double percentage = currentDuration / ((double) totalDuration);
        int finalPr = (int) (percentage * 100);

        return finalPr;
    }

}
