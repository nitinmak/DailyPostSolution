package com.sendpost.dreamsoft.BgEraser;

import android.content.Context;
import android.graphics.Bitmap;


public interface DeeplabInterface {
    int getInputSize();

    boolean initialize(Context context);

    boolean isInitialized();

    Bitmap segment(Bitmap bitmap);
}
