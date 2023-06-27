package com.sendpost.dreamsoft;

import static com.sendpost.dreamsoft.Classes.Constants.SUCCESS;
import static com.sendpost.dreamsoft.Classes.Constants.languageList;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.multidex.BuildConfig;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import com.sendpost.dreamsoft.Account.EditProfileFragment;
import com.sendpost.dreamsoft.Account.LoginFragment;
import com.sendpost.dreamsoft.Classes.App;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment;
import com.sendpost.dreamsoft.Fragments.SplashFragment;
import com.sendpost.dreamsoft.model.LanguageModel;
import com.sendpost.dreamsoft.View.NonSwipeableViewPager;
import com.sendpost.dreamsoft.dialog.DialogType;
import com.sendpost.dreamsoft.viewmodel.LanguageViewModel;
import com.sendpost.dreamsoft.viewmodel.SettingsViewModel;
import com.sendpost.dreamsoft.viewmodel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    NonSwipeableViewPager viewPager;
    private SettingsViewModel settingsViewModel;
    private LanguageViewModel languageViewModel;
    private UserViewModel userViewModel;
    CardView cardview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Functions.setLocale(Functions.getSharedPreference(MainActivity.this).getString(Variables.APP_LANGUAGE_CODE, Variables.DEFAULT_LANGUAGE_CODE), this, MainActivity.class, false);
        setContentView(R.layout.activity_main);

        App.app.onCreate();
        sharedPreferences = getSharedPreferences(Variables.PREF_NAME, MODE_PRIVATE);
        viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(new Adapter(getSupportFragmentManager()));
        if (getIntent().hasExtra("type") && getIntent().getStringExtra("type").equals("login")) {
            viewPager.setCurrentItem(1);
        }

        cardview = findViewById(R.id.cardviewx);
        getSetting();

//        HashKey For Facebook Login
//        Functions.PrintHashKey(this);

    }

    private void getSetting() {
        settingsViewModel = new ViewModelProvider(this).get(SettingsViewModel.class);
        settingsViewModel.getAllSetting().observe(this, settingModels -> {
            if (settingModels != null) {
                for (int i = 0; i < settingModels.size(); i++) {
                    sharedPreferences.edit().putString(settingModels.get(i).field, settingModels.get(i).value).apply();
                }
                cheakUpdate();
                getLangauges();
            } else {
                new CustomeDialogFragment(
                        getString(R.string.api_error),
                        getString(R.string.please_config_api),
                        DialogType.ERROR, true,true,true,
                        new CustomeDialogFragment.DialogCallback() {
                            @Override
                            public void onCencel() {}
                            @Override
                            public void onSubmit() {
                                getSetting();
                            }
                            @Override
                            public void onComplete(Dialog dialog) {}
                            @Override
                            public void onDismiss() {}
                        }).show(getSupportFragmentManager(), "");
            }
        });
    }

    private void getLangauges() {
        languageViewModel = new ViewModelProvider(this).get(LanguageViewModel.class);
        languageViewModel.getData().observe(this, new Observer<List<LanguageModel>>() {
            @Override
            public void onChanged(List<LanguageModel> languageModels) {
                if (languageModels != null) {
                    languageList.clear();
                    languageList.addAll(languageModels);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void cheakUpdate() {
        if (sharedPreferences.getString(Variables.SHOW_UPDATE_DIALOG,"").equals("true")) {
            if (!sharedPreferences.getString(Variables.APP_VERSION_CODE,""+ BuildConfig.VERSION_CODE).equals(""+BuildConfig.VERSION_CODE)){
                new CustomeDialogFragment(
                        getString(R.string.app_update),
                        getString(R.string.please_update_app),
                        DialogType.WARNING, true, sharedPreferences.getString(Variables.FORCE_UPDATE, "").equals("false"),true,
                        new CustomeDialogFragment.DialogCallback() {
                            @Override
                            public void onCencel() {}
                            @Override
                            public void onSubmit() {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(sharedPreferences.getString(Variables.APP_LINK, ""))));
                            }
                            @Override
                            public void onComplete(Dialog dialog) {}
                            @Override
                            public void onDismiss() {
                                if (Functions.getSharedPreference(MainActivity.this).getBoolean(Variables.IS_LOGIN, false)) {
                                    getUserProfileData();
                                } else {
                                    startTimer();
                                }
                            }
                        }).show(getSupportFragmentManager(), "");
            }else{
                if (Functions.getSharedPreference(MainActivity.this).getBoolean(Variables.IS_LOGIN, false)) {
                    getUserProfileData();
                } else {
                    startTimer();
                }
            }
        } else {
            if (Functions.getSharedPreference(MainActivity.this).getBoolean(Variables.IS_LOGIN, false)) {
                getUserProfileData();
            } else {
                startTimer();
            }
        }
    }

    private void getUserProfileData() {

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.getUserProfile(Functions.getUID(this)).observe(this, userResponse -> {
            if (userResponse != null){
                if (userResponse.code == SUCCESS){
                    Functions.saveUserData(userResponse.getUserModel(), MainActivity.this);
                    startTimer();
                }else {
                    new CustomeDialogFragment(
                            getString(R.string.alert),
                            userResponse.message,
                            DialogType.ERROR,
                            false,
                            false,
                            false,
                            new CustomeDialogFragment.DialogCallback() {
                                @Override
                                public void onCencel() {
                                }
                                @Override
                                public void onSubmit() {
                                }
                                @Override
                                public void onDismiss() {
                                }
                                @Override
                                public void onComplete(Dialog dialog) {
                                }
                            }
                    ).show(getSupportFragmentManager(),"");
                }
            }
        });
    }

    private void startTimer() {
        cardview.setVisibility(View.GONE);
        if (Functions.getSharedPreference(MainActivity.this).getBoolean(Variables.IS_LOGIN, false)) {
            if (Functions.getSharedPreference(MainActivity.this).getString(Variables.NAME, "").equals("") || Functions.getSharedPreference(MainActivity.this).getString(Variables.NAME, "").equals("null")) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
                transaction.addToBackStack(null);
                transaction.replace(R.id.main_activity, new EditProfileFragment()).commit();
            } else if (Functions.getSharedPreference(MainActivity.this).getBoolean(Variables.IS_FIRST_TIME, true)) {
                startActivity(new Intent(MainActivity.this, IntroActivity.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, HomeActivity.class));
                finish();
            }
        } else {
            viewPager.setCurrentItem(1);
        }
    }

    public class Adapter extends FragmentPagerAdapter {

        public Adapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SplashFragment();
                case 1:
                    return new LoginFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

    }

}
