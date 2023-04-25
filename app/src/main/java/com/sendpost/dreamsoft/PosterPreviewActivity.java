package com.sendpost.dreamsoft;

import static com.sendpost.dreamsoft.Classes.Constants.languageList;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.downloader.PRDownloader;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.audio.AudioAttributes;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.material.appbar.AppBarLayout;
import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.binding.BindingAdaptet;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import com.sendpost.dreamsoft.Classes.App;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Fragments.PagerPosterByLanguageFragment;
import com.sendpost.dreamsoft.ImageEditor.EditImageActivity;
import com.sendpost.dreamsoft.model.PostsModel;
import com.sendpost.dreamsoft.NavFragment.PremiumFragment;

public class PosterPreviewActivity extends AppCompatActivity implements Player.Listener{


    ImageView poster_iv;
    SmartTabLayout tabLayout;
    ViewPager viewPager;
    ShimmerFrameLayout premium_tag;
    TextView view_tv;
    String type = "";
    boolean isVideo = false;
    AppBarLayout appBarLayout;
    public static PostsModel postsModel = new PostsModel();
    PlayerView playerview;
    SimpleExoPlayer exoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_poster_preview);
        PRDownloader.initialize(getApplicationContext());

        type = getIntent().getStringExtra("type");
        isVideo = getIntent().getBooleanExtra("itemtype",false);
        poster_iv = findViewById(R.id.poster_iv);
        premium_tag = findViewById(R.id.premium_tag);
        view_tv = findViewById(R.id.view_tv);
        tabLayout = findViewById(R.id.viewpagertab);
        viewPager = findViewById(R.id.viewPager);
        appBarLayout = (AppBarLayout)findViewById(R.id.app_bar);

        playerview = findViewById(R.id.playerview);

        findViewById(R.id.remove_watermark_tv).setOnClickListener(v -> {
            Intent intent = new Intent(PosterPreviewActivity.this, RazorpayActivity.class);
            intent.putExtra("price", Functions.getSharedPreference(PosterPreviewActivity.this).getString("single_post_subsciption_amount","10"));
            resultCallbackForPayment.launch(intent);
        });

        initializePlayer();
        setData();
        setupPager();


    }

    ActivityResultLauncher<Intent> resultCallbackForPayment = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        EditImageActivity.postModel = postsModel;
                        if (Functions.isLogin(PosterPreviewActivity.this)){

                            startActivity(new Intent(PosterPreviewActivity.this, EditImageActivity.class).putExtra("isBuyed",true).putExtra("type",type).putExtra("path",Functions.getItemBaseUrl(postsModel.item_url)));
                        }else {
                            startActivity(new Intent(PosterPreviewActivity.this, MainActivity.class));
                        }
                    }
                }
            });

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initializePlayer() {
        DefaultTrackSelector trackSelector = new DefaultTrackSelector(this);
        LoadControl loadControl = new DefaultLoadControl.Builder()
                .setAllocator(new DefaultAllocator(true, 16))
                .setBufferDurationsMs(1 * 1024, 1 * 1024, 500, 1024)
                .setTargetBufferBytes(-1)
                .setPrioritizeTimeOverSizeThresholds(true)
                .build();
        try {
            exoplayer = new SimpleExoPlayer.Builder(this).
                    setTrackSelector(trackSelector)
                    .setLoadControl(loadControl)
                    .build();
            exoplayer.setThrowsWhenUsingWrongThread(false);
            exoplayer.addListener(this);
            exoplayer.setRepeatMode(Player.REPEAT_MODE_OFF);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(C.USAGE_MEDIA)
                        .setContentType(C.CONTENT_TYPE_MOVIE)
                        .build();
                exoplayer.setAudioAttributes(audioAttributes, true);
            }
        } catch (Exception e) {
            Log.d(Constants.tag,"Exception audio focus : "+e);
        }
    }

    private void setData() {
        if (exoplayer != null){
            exoplayer.setPlayWhenReady(false);
        }
        appBarLayout.setExpanded(true, true);
        if (postsModel.premium.equals("1")){
            premium_tag.setVisibility(View.VISIBLE);
            if (Functions.IsPremiumEnable(PosterPreviewActivity.this)){
                findViewById(R.id.premium_lock_lay).setVisibility(View.GONE);
            }else {
                findViewById(R.id.premium_lock_lay).setVisibility(View.VISIBLE);
            }
        }else {
            findViewById(R.id.premium_lock_lay).setVisibility(View.GONE);
            premium_tag.setVisibility(View.GONE);
        }
        view_tv.setText(postsModel.views);
        if (postsModel.item_url.contains(".mp4")){
            poster_iv.setVisibility(View.GONE);
            playerview.setVisibility(View.VISIBLE);
            findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);

            DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
            DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSourceFactory(this, httpDataSourceFactory);

            HttpProxyCacheServer proxy = App.getProxy(App.app);
            String proxyUrl = proxy.getProxyUrl(Functions.getItemBaseUrl(postsModel.item_url));

            MediaItem mediaItem = MediaItem.fromUri(proxyUrl);
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(defaultDataSourceFactory).createMediaSource(mediaItem);
            exoplayer.setMediaSource(mediaSource, true);
            exoplayer.prepare();
            exoplayer.setPlayWhenReady(true);
            exoplayer.addListener(this);
            playerview.setPlayer(exoplayer);
            Functions.updatePostView(this,postsModel.id,"video");
        }else {
            try {
                Functions.updatePostView(this,postsModel.id,type.toLowerCase());
                poster_iv.setVisibility(View.VISIBLE);
                playerview.setVisibility(View.GONE);
                BindingAdaptet.setImageUrl(poster_iv,postsModel.getItem_url());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoplayer != null){
            exoplayer.setPlayWhenReady(false);
        }
    }

    private void setupPager() {
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        viewPager.setOffscreenPageLimit(4);
        tabLayout.setViewPager(viewPager);
    }

    public void finish(View view) {
        finish();
    }

    public void use_poster(View view) {
        EditImageActivity.postModel = postsModel;
        if (Functions.isLogin(this)){

            startActivity(new Intent(PosterPreviewActivity.this, EditImageActivity.class).putExtra("type",type).putExtra("path",Functions.getItemBaseUrl(postsModel.item_url)));
        }else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void showPremiumFragment() {
        PremiumFragment comment_f = new PremiumFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.in_from_bottom, R.anim.out_to_top, R.anim.in_from_top, R.anim.out_from_bottom);
        Bundle args = new Bundle();
        args.putString("from", "preview");
        comment_f.setArguments(args);
        transaction.addToBackStack(null);
        transaction.replace(android.R.id.content, comment_f).commit();
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        }


        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            final Fragment  fragment = new PagerPosterByLanguageFragment(new PagerPosterByLanguageFragment.PostCallBack() {
                @Override
                public void onSelect(PostsModel model, String t) {
                    postsModel = model;
                    type = t;
                    setData();
                }
            });
            bundle.putString("category_id", type.equals("GreetingPosts") ? postsModel.section_id : postsModel.category_id);
            bundle.putBoolean("itemtype", isVideo);
            bundle.putString("l_code",languageList.get(position).language_code);
            bundle.putString("type",type);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return languageList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return languageList.get(position).language_name;
        }
    }

    @Override
    public void onPlaybackStateChanged(int state) {
        Player.Listener.super.onPlaybackStateChanged(state);
        switch (state){
            case Player.STATE_BUFFERING:
                findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
                break;
            case Player.STATE_READY:
                findViewById(R.id.progress_bar).setVisibility(View.GONE);
                exoplayer.setPlayWhenReady(true);
                break;
        }
    }
}