package com.sendpost.dreamsoft.ImageEditor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
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
import com.sendpost.dreamsoft.responses.UserResponse;
import com.sendpost.dreamsoft.ImageEditor.viewmodel.UserViewModel;
import com.sendpost.dreamsoft.Classes.Constants;
import com.sendpost.dreamsoft.Classes.Functions;
import com.sendpost.dreamsoft.Classes.Variables;
import com.sendpost.dreamsoft.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class PreviewActivity extends AppCompatActivity {

    ImageView poster_iv;
    String path = "", file_name;
    PlayerView playerview;
    SimpleExoPlayer exoplayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);

        path = getIntent().getStringExtra("url");

        poster_iv = findViewById(R.id.poster_iv);
        playerview = findViewById(R.id.playerview);


        if (path.endsWith(".mp4")) {

            file_name = System.currentTimeMillis() + ".mp4";
            initializePlayer();
            playerview.setVisibility(View.VISIBLE);
            poster_iv.setVisibility(View.GONE);
            DefaultHttpDataSource.Factory httpDataSourceFactory = new DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true);
            DefaultDataSource.Factory defaultDataSourceFactory = new DefaultDataSourceFactory(this, httpDataSourceFactory);

            MediaItem mediaItem = MediaItem.fromUri(Uri.parse(path));
            MediaSource mediaSource = new ProgressiveMediaSource.Factory(defaultDataSourceFactory).createMediaSource(mediaItem);
            exoplayer.setMediaSource(mediaSource, true);

            exoplayer.prepare();
            exoplayer.setPlayWhenReady(true);
            playerview.setPlayer(exoplayer);

        } else {

            file_name = System.currentTimeMillis() + ".jpg";
            playerview.setVisibility(View.GONE);
            poster_iv.setVisibility(View.VISIBLE);
            try {
                Glide.with(this).load(path).placeholder(R.drawable.placeholder).into(poster_iv);
            } catch (Exception e) {}

        }

        findViewById(R.id.whatsapp_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("whatsapp");
            }
        });

        findViewById(R.id.facebook_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("facebook");
            }
        });

        findViewById(R.id.telegram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("telegram");
            }
        });

        findViewById(R.id.instagram).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("instagram");
            }
        });

        findViewById(R.id.more_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("more");
            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download("save");
            }
        });

//        findViewById(R.id.progress_lay).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });

        if (!path.startsWith("http")) {
            uploadPost();
        } else {
//            findViewById(R.id.progress_lay).setVisibility(View.GONE);
//            isUploading = false;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    boolean isUploading = true;
    UserViewModel userViewModel;

    private void uploadPost() {
//        findViewById(R.id.progress_lay).setVisibility(View.VISIBLE);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        userViewModel.uploadUserPost(Functions.getUID(this), path).observe(this, new Observer<UserResponse>() {
            @Override
            public void onChanged(UserResponse userResponse) {
                isUploading = false;
//                findViewById(R.id.progress_lay).setVisibility(View.GONE);
                Functions.cancelLoader();
                if (userResponse != null) {
                    if (userResponse.code != Constants.SUCCESS) {
                        Toast.makeText(PreviewActivity.this, "Post not backup !", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(PreviewActivity.this, "Post not backup", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (exoplayer != null) {
            exoplayer.setPlayWhenReady(false);
        }
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
            exoplayer.setRepeatMode(Player.REPEAT_MODE_ALL);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setUsage(C.USAGE_MEDIA)
                        .setContentType(C.CONTENT_TYPE_MOVIE)
                        .build();
                exoplayer.setAudioAttributes(audioAttributes, true);
            }
        } catch (Exception e) {
            Log.d(Constants.tag, "Exception audio focus : " + e);
        }
    }

    @Override
    public void onBackPressed() {
        if (isUploading) {
            Functions.showToast(this, getString(R.string.please_wait_post_is_uploading));
        } else {
            super.onBackPressed();
        }
    }

    public void finish(View view) {
        finish();
    }

    private void download(String name) {

        if (path.startsWith("http")) {
            downloadFromServer(name);
        } else {
            File file = new File(path);
            if (file.exists()) {
                if (name.equals("save")) {

                    File outfile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
                    try {
                        moveFile(file, outfile);
                    } catch (IOException e) {
                        Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("image/*");
                    Uri photoURI = FileProvider.getUriForFile(PreviewActivity.this, getPackageName() + ".fileprovider", file);
                    intent.putExtra(Intent.EXTRA_STREAM, photoURI);
                    if (name.equals("whatsapp")) {
                        intent.setPackage("com.whatsapp");
                    } else if (name.equals("facebook")) {
                        intent.setPackage("com.facebook.katana");
                    } else if (name.equals("instagram")) {
                        intent.setPackage("com.instagram.android");
                    } else if (name.equals("telegram")) {
                        intent.setPackage("org.telegram.messenger");
                    }
                    try {
                        startActivity(Intent.createChooser(intent, "Share Image Via"));
                    } catch (Exception e) {
                        Toast.makeText(PreviewActivity.this, name + " Not Installed ", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                Functions.showToast(this, "File not found");
            }
        }

    }


    private void downloadFromServer(String name) {
        File appfolder = null;
        File file = null;
        if (name.equals("save")) {
            appfolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath());
        } else {
            appfolder = new File(Functions.getAppFolder(this) + Variables.APP_HIDED_FOLDER);
        }
        if (!appfolder.exists()) {
            appfolder.mkdir();
            appfolder.mkdirs();
        }
        file = new File(appfolder.getAbsolutePath() + "/" + file_name);
        if (file.exists()) {
            if (!name.equals("save")) {
                shareFile(file, name);
            } else {
                Functions.showToast(PreviewActivity.this, getString(R.string.saved));
            }
        } else {
            Functions.showLoader(PreviewActivity.this);
            File finalFile = file;
            PRDownloader.download(Functions.getItemBaseUrl(getIntent().getStringExtra("url")), appfolder.getAbsolutePath(), file_name)
                    .build()
                    .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                        @Override
                        public void onStartOrResume() {

                        }
                    })
                    .setOnPauseListener(new OnPauseListener() {
                        @Override
                        public void onPause() {

                        }
                    })
                    .setOnCancelListener(new OnCancelListener() {
                        @Override
                        public void onCancel() {
                            Functions.cancelLoader();
                        }
                    })
                    .setOnProgressListener(new OnProgressListener() {
                        @Override
                        public void onProgress(Progress progress) {

                        }
                    })
                    .start(new OnDownloadListener() {
                        @Override
                        public void onDownloadComplete() {
                            Functions.cancelLoader();
                            if (!name.equals("save")) {
                                shareFile(finalFile, name);
                            } else {
                                Functions.showToast(PreviewActivity.this, getString(R.string.saved));
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            Functions.cancelLoader();
                            Toast.makeText(PreviewActivity.this, "" + error.getServerErrorMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void shareFile(File file, String name) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        Uri photoURI = FileProvider.getUriForFile(PreviewActivity.this, getPackageName() + ".fileprovider", file);
        intent.putExtra(Intent.EXTRA_STREAM, photoURI);
        intent.putExtra(Intent.EXTRA_TEXT, Functions.getSharedPreference(this).getString("share_text", ""));
        if (name.equals("whatsapp")) {
            intent.setPackage("com.whatsapp");
        } else if (name.equals("facebook")) {
            intent.setPackage("com.facebook.katana");
        }
        try {
            startActivity(Intent.createChooser(intent, "Share Image Via"));
        } catch (Exception e) {
            Toast.makeText(this, name + " Not Installed ", Toast.LENGTH_SHORT).show();
        }
    }


    public void copyFile(File source, File destination) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                FileUtils.copy(new FileInputStream(source), new FileOutputStream(destination));
                source.delete();
                Functions.showToast(this, getString(R.string.saved));
            } catch (IOException e) {
                Functions.showToast(this, e.getMessage());
            }
        }
    }

    boolean isDownload = false;

    private void moveFile(File file, File dir) throws IOException {
        if (isDownload) {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            return;
        }
        File newFile = new File(dir, file.getName());
        path = newFile.getAbsolutePath();
        FileChannel outputChannel = null;
        FileChannel inputChannel = null;
        try {
            outputChannel = new FileOutputStream(newFile).getChannel();
            inputChannel = new FileInputStream(file).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputChannel.close();
            file.delete();
            isDownload = true;
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();

            MediaScannerConnection.scanFile(this, new String[]{newFile.getAbsolutePath()}, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String path, Uri uri) {

                        }
                    });
        } finally {
            if (inputChannel != null) inputChannel.close();
            if (outputChannel != null) outputChannel.close();
        }

    }

}