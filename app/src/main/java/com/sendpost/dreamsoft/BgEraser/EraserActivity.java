package com.sendpost.dreamsoft.BgEraser;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sendpost.dreamsoft.R;
import com.sendpost.dreamsoft.BgEraser.eraser.EraseSView;
import com.sendpost.dreamsoft.BgEraser.eraser.EraseView;
import com.sendpost.dreamsoft.BgEraser.eraser.MultiTouchListener;

public class EraserActivity extends AppCompatActivity implements View.OnClickListener {

    public static Bitmap bgCircleBit = null, bitmap = null ,eraserResultBmp = null;
    public static int curBgType = 1, orgBitHeight, orgBitWidth;
    public static BitmapShader patternBMPshader;
    public static Bitmap b = null;
    public ImageView auto_btn;
    public RelativeLayout auto_btn_rel, offset_seekbar_lay,rel_seek_container,
            inside_cut_lay, lasso_btn_rel, main_rel, outside_cut_lay, restore_btn_rel, zoom_btn_rel;
    public Bitmap orgBitmap;
    public EraseView dv;

    public int height/*, id*/;
    public boolean isTutOpen = true, showDialog = false;
    public Animation scale_anim;
    public ImageView tbg_img, dv1;
    public TextView save_btn,tv10, tv6, tv7, tv9, headertext, txt_desc;
    public int width;
    ImageView redo_btn;
    ImageView btn_bg;
    ImageView undo_btn;
    ImageView btn_brush;
    ImageView lasso_btn;
    ImageView restore_btn;
    ImageView zoom_btn;
    private View[] btnArr = new View[5], btnArr1 = new View[5];
    private EraseSView eraseSView;
    private LinearLayout lay_lasso_cut, lay_offset_seek, lay_threshold_seek;
    private SeekBar offset_seekbar, offset_seekbar1, offset_seekbar2, radius_seekbar, threshold_seekbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bg_eraser);

        this.scale_anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_scale_anim);
        initUI();

        this.tv6 = (TextView) findViewById(R.id.auto_txt);
        this.tv7 = (TextView) findViewById(R.id.txt_lasso);
        this.tv9 = (TextView) findViewById(R.id.restore_txt);
        this.tv10 = (TextView) findViewById(R.id.zoom_txt);
        this.isTutOpen = false;
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.heightPixels;
        this.width = displayMetrics.widthPixels;
        this.height = i - ImageUtils.dpToPx(this, 120.0f);
        curBgType = 1;
        this.main_rel.postDelayed(new Runnable() {
            public void run() {
                if (isTutOpen) {
                    tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(EraserActivity.this, R.drawable.tbg3, width, height));
                    bgCircleBit = ImageUtils.getBgCircleBit(EraserActivity.this, R.drawable.tbg3);
                } else {
                    tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(EraserActivity.this, R.drawable.tbg, width, height));
                    EraserActivity.bgCircleBit = ImageUtils.getBgCircleBit(EraserActivity.this, R.drawable.tbg);
                }
                importImageFromUri();
            }
        }, 1000);
    }

    private void initUI() {
        this.offset_seekbar_lay = (RelativeLayout) findViewById(R.id.offset_seekbar_lay);
        this.rel_seek_container = (RelativeLayout) findViewById(R.id.rel_seek_container);
        this.auto_btn_rel = (RelativeLayout) findViewById(R.id.rel_auto_btn);
        this.restore_btn_rel = (RelativeLayout) findViewById(R.id.rel_restore_btn);
        this.lasso_btn_rel = (RelativeLayout) findViewById(R.id.rel_lasso_btn);
        this.zoom_btn_rel = (RelativeLayout) findViewById(R.id.rel_zoom_btn);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.main_rel = (RelativeLayout) findViewById(R.id.main_rel);
        this.lay_threshold_seek = (LinearLayout) findViewById(R.id.lay_threshold_seek);
        this.lay_offset_seek = (LinearLayout) findViewById(R.id.lay_offset_seek);
        this.lay_lasso_cut = (LinearLayout) findViewById(R.id.lay_lasso_cut);
        this.inside_cut_lay = (RelativeLayout) findViewById(R.id.inside_cut_lay);
        this.outside_cut_lay = (RelativeLayout) findViewById(R.id.outside_cut_lay);
        this.undo_btn = (ImageView) findViewById(R.id.btn_undo);
        this.redo_btn = (ImageView) findViewById(R.id.btn_redo);

        this.auto_btn = (ImageView) findViewById(R.id.btn_auto);
        this.restore_btn = (ImageView) findViewById(R.id.btn_restore);
        this.lasso_btn = (ImageView) findViewById(R.id.btn_lasso);
        this.zoom_btn = (ImageView) findViewById(R.id.btn_zoom);
        this.save_btn = (TextView) findViewById(R.id.save_image_btn);
        this.btn_bg = (ImageView) findViewById(R.id.btn_bg);
        this.btn_brush = (ImageView) findViewById(R.id.btn_brush);
        this.tbg_img = (ImageView) findViewById(R.id.tbg_img);
        this.undo_btn.setOnClickListener(this);
        this.redo_btn.setOnClickListener(this);
        this.undo_btn.setEnabled(false);
        this.redo_btn.setEnabled(false);
        this.save_btn.setOnClickListener(this);
        this.btn_bg.setOnClickListener(this);

        this.auto_btn.setOnClickListener(this);
        this.lasso_btn.setOnClickListener(this);
        this.restore_btn.setOnClickListener(this);
        this.zoom_btn.setOnClickListener(this);
        this.inside_cut_lay.setOnClickListener(this);
        this.outside_cut_lay.setOnClickListener(this);
        this.btnArr[0] = findViewById(R.id.lay_auto_btn);
        this.btnArr[2] = findViewById(R.id.lay_restore_btn);
        this.btnArr[3] = findViewById(R.id.lay_lasso_btn);
        this.btnArr[4] = findViewById(R.id.lay_zoom_btn);
        this.offset_seekbar = (SeekBar) findViewById(R.id.offset_seekbar);
        this.offset_seekbar1 = (SeekBar) findViewById(R.id.offset_seekbar1);
        this.offset_seekbar2 = (SeekBar) findViewById(R.id.offset_seekbar2);
        this.offset_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (dv != null) {
                    dv.setOffset(i - 150);
                    dv.invalidate();
                }
            }
        });
        this.offset_seekbar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (dv != null) {
                    dv.setOffset(i - 150);
                    dv.invalidate();
                }
            }
        });
        this.offset_seekbar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (dv != null) {
                    dv.setOffset(i - 150);
                    dv.invalidate();
                }
            }
        });
        this.radius_seekbar = (SeekBar) findViewById(R.id.radius_seekbar);
        this.radius_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
                if (dv != null) {
                    dv.setRadius(i + 2);
                    dv.invalidate();
                }
            }
        });
        this.threshold_seekbar = (SeekBar) findViewById(R.id.threshold_seekbar);
        this.threshold_seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                if (dv != null) {
                    dv.setThreshold(seekBar.getProgress() + 10);
                    dv.updateThreshHold();
                }
            }
        });
    }

    @SuppressLint({"WrongConstant"})
    public void onClick(View view) {
        if (this.dv != null) {
            String str = "...";
            String str2 = "";
            switch (view.getId()) {
                case R.id.btn_auto:
                case R.id.rel_auto_btn:
                    setSelected(R.id.lay_auto_btn);
//                    this.auto_btn.setBackgroundResource(R.drawable.ic_slated_auto);
//                    this.tv1.setTextColor(getResources().getColor(R.color.select_new));
//                    this.tv6.setTextColor(getResources().getColor(R.color.select_new));
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(2);
                    this.dv.invalidate();
                    return;
                case R.id.btn_bg:
                    changeBG();
                    return;
                case R.id.btn_brush:
                    setSelected(R.id.lay_erase_btn);
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(1);
                    this.dv.invalidate();
                    if (this.isTutOpen) {
                        this.txt_desc.setText(getResources().getString(R.string.drag_color));
                    }

                    EraseView eraseView = this.dv;
                    if (eraseView != null) {
                        if (eraseView.isRectBrushEnable()) {
                            this.dv.enableRectBrush(false);
                            this.dv.invalidate();
                            this.btn_brush.setImageResource(R.drawable.ic_square);
                            return;
                        }
                        this.dv.enableRectBrush(true);
                        this.btn_brush.setImageResource(R.drawable.ic_circles);
                        this.dv.invalidate();
                        return;
                    }
                    return;
//                case R.id.btn_erase:
//                case R.id.erase_btn_lay1:
//                case R.id.rel_erase_btn:
//                    setSelected(R.id.lay_erase_btn);
//                    setClickUnSelected();
////                    this.erase_btn.setBackgroundResource(R.drawable.ic_slated_manual);
////                    this.tv3.setTextColor(getResources().getColor(R.color.select_new));
////                    this.tv8.setTextColor(getResources().getColor(R.color.select_new));
//
//                    return;
                case R.id.btn_lasso:
                case R.id.rel_lasso_btn:
                    this.offset_seekbar_lay.setVisibility(View.VISIBLE);
                    setSelected(R.id.lay_lasso_btn);
//                    this.lasso_btn.setBackgroundResource(R.drawable.ic_slated_extract);
//                    this.tv2.setTextColor(getResources().getColor(R.color.select_new));
//                    this.tv7.setTextColor(getResources().getColor(R.color.select_new));
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(3);
                    this.dv.invalidate();

                    if (this.isTutOpen) {
//                        this.rel_lasso.setVisibility(View.GONE);
//                        this.rel_lasso.clearAnimation();
                        this.txt_desc.setText(getResources().getString(R.string.msg_draw_lasso));
//                        this.rel_desc.setVisibility(View.VISIBLE);
//                        this.rel_desc.startAnimation(this.scale_anim);
                        this.lasso_btn_rel.clearAnimation();
                    }
                    return;
                case R.id.btn_redo:
                    StringBuilder sb = new StringBuilder();
                    sb.append(getString(R.string.redoing));
                    sb.append(str);
                    final ProgressDialog show = ProgressDialog.show(this, str2, sb.toString(), true);
                    show.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        dv.redoChange();
                                    }
                                });
                                Thread.sleep(500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            show.dismiss();
                        }
                    }).start();
                    return;
                case R.id.btn_restore:
                case R.id.rel_restore_btn:
                    setSelected(R.id.lay_restore_btn);
//                    this.restore_btn.setBackgroundResource(R.drawable.ic_slated_restore);
//                    this.tv4.setTextColor(getResources().getColor(R.color.select_new));
//                    this.tv9.setTextColor(getResources().getColor(R.color.select_new));
                    this.dv.enableTouchClear(true);
                    this.main_rel.setOnTouchListener(null);
                    this.dv.setMODE(4);
                    this.dv.invalidate();
                    if (this.isTutOpen) {
//                        this.rel_bw.setVisibility(View.GONE);
//                        this.rel_bw.clearAnimation();
                        this.txt_desc.setText(getResources().getString(R.string.drag_bw));
//                        this.rel_desc.setVisibility(View.VISIBLE);
//                        this.rel_desc.startAnimation(this.scale_anim);
                        this.restore_btn_rel.clearAnimation();
                    }
                    return;
                case R.id.btn_undo:
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append(getString(R.string.undoing));
                    sb2.append(str);
                    final ProgressDialog show2 = ProgressDialog.show(this, str2, sb2.toString(), true);
                    show2.setCancelable(false);
                    new Thread(new Runnable() {
                        public void run() {
                            try {
                                runOnUiThread(new Runnable() {
                                    public void run() {
                                        dv.undoChange();
                                    }
                                });
                                Thread.sleep(500);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            show2.dismiss();
                        }
                    }).start();
                    return;
                case R.id.btn_zoom:
                case R.id.rel_zoom_btn:
                    this.dv.enableTouchClear(false);
                    this.main_rel.setOnTouchListener(new MultiTouchListener());
                    setSelected(R.id.lay_zoom_btn);

//                    this.zoom_btn.setBackgroundResource(R.drawable.ic_slated_zoom);
//                    this.tv5.setTextColor(getResources().getColor(R.color.select_new));
//                    this.tv10.setTextColor(getResources().getColor(R.color.select_new));
                    this.dv.setMODE(0);
                    this.dv.invalidate();
                    return;
                case R.id.inside_cut_lay:
                    this.offset_seekbar_lay.setVisibility(View.VISIBLE);
                    this.dv.enableInsideCut(true);
                    this.inside_cut_lay.clearAnimation();
                    this.outside_cut_lay.clearAnimation();
                    return;
                case R.id.outside_cut_lay:
                    this.offset_seekbar_lay.setVisibility(View.VISIBLE);
                    this.dv.enableInsideCut(false);
                    this.inside_cut_lay.clearAnimation();
                    this.outside_cut_lay.clearAnimation();
                    return;
                case R.id.save_image_btn:
                    bitmap = this.dv.getFinalBitmap();
                    if (bitmap != null) {
                        try {
                            int dpToPx = ImageUtils.dpToPx(EraserActivity.this, 42.0f);
                            bitmap = ImageUtils.resizeBitmap(bitmap, orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx);
                            int i = dpToPx + dpToPx;
                            bitmap = Bitmap.createBitmap(bitmap, dpToPx, dpToPx, bitmap.getWidth() - i, bitmap.getHeight() - i);
                            bitmap = Bitmap.createScaledBitmap(bitmap, orgBitWidth, orgBitHeight, true);
                            bitmap = ImageUtils.bitmapmasking(orgBitmap, bitmap);

                            eraserResultBmp = bitmap;
                            setResult(RESULT_OK);
                            finish();
                        } catch (OutOfMemoryError e) {
                            e.printStackTrace();
                        }
                    } else {
                        finish();
                    }
            }
        } else {
            Toast.makeText(this, getResources().getString(R.string.import_img_warning), Toast.LENGTH_SHORT).show();
        }
    }

    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(getString(R.string.msg_save_image));
        builder.setPositiveButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.create().show();
    }

    private void changeBG() {
        int i = curBgType;
        if (i == 1) {
            curBgType = 2;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg1, this.width, this.height));
            this.btn_bg.setImageResource(R.drawable.tbg2);
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg1);
        } else if (i == 2) {
            curBgType = 3;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg2, this.width, this.height));
            this.btn_bg.setImageResource(R.drawable.tbg3);
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg2);
        } else if (i == 3) {
            curBgType = 4;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg3, this.width, this.height));
//            this.btn_bg.setImageResource(R.drawable.tbg4);
            this.btn_bg.setImageResource(R.drawable.tbg1);
            //for user view   - (image and background both are set here white so)
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg3);
        } else if (i == 4) {
            curBgType = 5;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg4, this.width, this.height));
            this.btn_bg.setImageResource(R.drawable.tbg5);
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg4);
        } else if (i == 5) {
            curBgType = 6;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg5, this.width, this.height));
            this.btn_bg.setImageResource(R.drawable.tbg);
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg5);
        } else if (i == 6) {
            curBgType = 1;
            this.tbg_img.setImageBitmap(null);
            this.tbg_img.setImageBitmap(ImageUtils.getTiledBitmap(this, R.drawable.tbg, this.width, this.height));
            this.btn_bg.setImageResource(R.drawable.tbg1);
            bgCircleBit = ImageUtils.getBgCircleBit(this, R.drawable.tbg);
        }
    }

    public void importImageFromUri() {
        this.showDialog = false;
        final ProgressDialog show = ProgressDialog.show(this, "", getResources().getString(R.string.importing_image), true);
        show.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    if (b == null) {
                        showDialog = true;
                    } else {
                        orgBitmap = b.copy(b.getConfig(), true);
                        int dpToPx = ImageUtils.dpToPx(EraserActivity.this, 42.0f);
                        EraserActivity.orgBitWidth = b.getWidth();
                        EraserActivity.orgBitHeight = b.getHeight();
                        Bitmap createBitmap = Bitmap.createBitmap(b.getWidth() + dpToPx + dpToPx, b.getHeight() + dpToPx + dpToPx, b.getConfig());
                        Canvas canvas = new Canvas(createBitmap);
                        canvas.drawColor(0);
                        float f = (float) dpToPx;
                        canvas.drawBitmap(b, f, f, null);
                        b = createBitmap;
                        if (b.getWidth() > width || b.getHeight() > height || (b.getWidth() < width && b.getHeight() < height)) {
                            b = ImageUtils.resizeBitmap(b, width, height);
                        }
                    }
                    Thread.sleep(1000);
                } catch (OutOfMemoryError e) {
                    e.printStackTrace();
                    showDialog = true;
                    show.dismiss();
                } catch (Exception e2) {
                    e2.printStackTrace();
                    showDialog = true;
                    show.dismiss();
                }
                show.dismiss();
            }
        }).start();
        show.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @SuppressLint({"WrongConstant"})
            public void onDismiss(DialogInterface dialogInterface) {
                if (showDialog) {
                    EraserActivity stickerRemoveActivity = EraserActivity.this;
                    Toast.makeText(stickerRemoveActivity, stickerRemoveActivity.getResources().getString(R.string.import_error), Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                String str = "";
                setImageBitmap();
            }
        });
    }

    @SuppressLint({"WrongConstant"})
    public void setImageBitmap() {
        this.dv = new EraseView(this);
        this.dv1 = new ImageView(this);
        this.dv.setImageBitmap(this.b);
        this.dv1.setImageBitmap(getGreenLayerBitmap(this.b));
        this.dv.enableTouchClear(false);
        this.dv.setMODE(0);
        this.dv.invalidate();
        this.offset_seekbar.setProgress(225);
        this.radius_seekbar.setProgress(18);
        this.threshold_seekbar.setProgress(20);
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.main_rel_parent);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) relativeLayout.getLayoutParams();
        this.eraseSView = new EraseSView(this);
        this.eraseSView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        relativeLayout.addView(this.eraseSView);
        this.dv.setEraseSView(this.eraseSView);
        this.main_rel.removeAllViews();
        this.main_rel.setScaleX(1.0f);
        this.main_rel.setScaleY(1.0f);
        this.main_rel.addView(this.dv1);
        this.main_rel.addView(this.dv);
        relativeLayout.setLayoutParams(layoutParams);
        this.dv.invalidate();
        this.dv1.setVisibility(View.GONE);
        this.dv.setUndoRedoListener(new EraseView.UndoRedoListener() {
            public void enableUndo(boolean z, int i) {
                if (z) {
                    EraserActivity stickerRemoveActivity = EraserActivity.this;
                    stickerRemoveActivity.setBGDrawable(undo_btn, R.drawable.ic_undo, z);
                    return;
                }
                EraserActivity stickerRemoveActivity2 = EraserActivity.this;
                stickerRemoveActivity2.setBGDrawable(undo_btn, R.drawable.ic_undo, z);
            }

            public void enableRedo(boolean z, int i) {
                if (z) {
                    EraserActivity stickerRemoveActivity = EraserActivity.this;
                    stickerRemoveActivity.setBGDrawable(redo_btn, R.drawable.ic_redo, z);
                    return;
                }
                EraserActivity stickerRemoveActivity2 = EraserActivity.this;
                stickerRemoveActivity2.setBGDrawable(redo_btn, R.drawable.ic_redo, z);
            }
        });
        this.b.recycle();
        this.dv.setActionListener(new EraseView.ActionListener() {
            public void onActionCompleted(final int i) {
                runOnUiThread(new Runnable() {
                    @SuppressLint({"WrongConstant"})
                    public void run() {
                        if (i == 5) {
                            offset_seekbar_lay.setVisibility(View.GONE);
                        }
                    }
                });
            }

            public void onAction(final int i) {
                runOnUiThread(new Runnable() {
                    @SuppressLint({"WrongConstant"})
                    public void run() {
                        if (i == 0) {
                            rel_seek_container.setVisibility(View.GONE);
                        }
                        if (i == 1) {
                            rel_seek_container.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });
    }

    public void setBGDrawable(ImageView imageView, int i2, boolean z) {
        final ImageView imageView2 = imageView;
        final int i3 = i2;
        final boolean z2 = z;
        runOnUiThread(new Runnable() {
            public void run() {
                imageView2.setImageResource(i3);
                imageView2.setEnabled(z2);
            }
        });
    }

    public Bitmap getGreenLayerBitmap(Bitmap bitmap2) {
        Paint paint = new Paint();
        paint.setColor(-16711936);
        paint.setAlpha(80);
        int dpToPx = ImageUtils.dpToPx(this, 42.0f);
        Bitmap createBitmap = Bitmap.createBitmap(orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx, bitmap2.getConfig());
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawColor(0);
        float f = (float) dpToPx;
        canvas.drawBitmap(this.orgBitmap, f, f, null);
        canvas.drawRect(f, f, (float) (orgBitWidth + dpToPx), (float) (orgBitHeight + dpToPx), paint);
        Bitmap createBitmap2 = Bitmap.createBitmap(orgBitWidth + dpToPx + dpToPx, orgBitHeight + dpToPx + dpToPx, bitmap2.getConfig());
        Canvas canvas2 = new Canvas(createBitmap2);
        canvas2.drawColor(0);
        canvas2.drawBitmap(this.orgBitmap, f, f, null);
        patternBMPshader = new BitmapShader(ImageUtils.resizeBitmap(createBitmap2, this.width, this.height), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
        return ImageUtils.resizeBitmap(createBitmap, this.width, this.height);
    }

    @SuppressLint({"WrongConstant"})
    public void setSelected(int i) {
        if (i == R.id.lay_erase_btn  ) {
            this.offset_seekbar.setProgress(this.dv.getOffset() + 150);
            this.lay_offset_seek.setVisibility(View.VISIBLE);
            this.lay_threshold_seek.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
            this.headertext.setText(getResources().getString(R.string.erase));
        }
        if (i == R.id.lay_auto_btn) {
            this.offset_seekbar1.setProgress(this.dv.getOffset() + 150);
            this.lay_offset_seek.setVisibility(View.GONE);
            this.lay_threshold_seek.setVisibility(View.VISIBLE);
            this.lay_lasso_cut.setVisibility(View.GONE);
            this.headertext.setText(getResources().getString(R.string.auto));
        }
        if (i == R.id.lay_lasso_btn) {
            this.offset_seekbar2.setProgress(this.dv.getOffset() + 150);
            this.lay_offset_seek.setVisibility(View.GONE);
            this.lay_threshold_seek.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.VISIBLE);
            this.headertext.setText(getResources().getString(R.string.extract));
        }
        if (i == R.id.lay_restore_btn) {
            this.offset_seekbar.setProgress(this.dv.getOffset() + 150);
            this.lay_offset_seek.setVisibility(View.VISIBLE);
            this.lay_threshold_seek.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
            this.headertext.setText(getResources().getString(R.string.restore));
        }
        if (i == R.id.lay_zoom_btn) {
            this.lay_offset_seek.setVisibility(View.GONE);
            this.lay_threshold_seek.setVisibility(View.GONE);
            this.lay_lasso_cut.setVisibility(View.GONE);
            this.headertext.setText(getResources().getString(R.string.zoom_in_out));
        }
        if (i == R.id.lay_restore_btn) {
            this.dv1.setVisibility(View.VISIBLE);
        } else {
            this.dv1.setVisibility(View.GONE);
        }
        if (i != R.id.lay_zoom_btn) {
            this.dv.updateOnScale(this.main_rel.getScaleX());
        }
    }


    public void onDestroy() {
        Bitmap bitmap2 = this.b;
        if (bitmap2 != null) {
            bitmap2.recycle();
            this.b = null;
        }
        try {
            if (!isFinishing() && this.dv.pd != null && this.dv.pd.isShowing()) {
                this.dv.pd.dismiss();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }

    public void finish(View view) {
        onBackPressed();
    }
}