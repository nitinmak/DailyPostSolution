package ja.burhanrashid52.photoeditor

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.text.Layout
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.ImageView
import android.widget.RelativeLayout
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultAllocator
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource
import com.hw.photomovie.render.GLTextureView
import ja.burhanrashid52.photoeditor.Filter.FilterImageView
import ja.burhanrashid52.photoeditor.Filter.FilterImageView.OnImageChangedListener
import ja.burhanrashid52.photoeditor.Filter.PhotoFilter
import ja.burhanrashid52.photoeditor.Movie.DemoPresenter
import ja.burhanrashid52.photoeditor.Movie.IDemoView
import ja.burhanrashid52.photoeditor.Movie.widget.FilterItem
import ja.burhanrashid52.photoeditor.Movie.widget.SaveVideoResponse
import ja.burhanrashid52.photoeditor.Movie.widget.TransferItem
import ja.burhanrashid52.photoeditor.Sticker.DrawableSticker
import ja.burhanrashid52.photoeditor.Sticker.Sticker
import ja.burhanrashid52.photoeditor.Sticker.StickerView
import ja.burhanrashid52.photoeditor.Sticker.TextSticker


class PhotoEditorView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : RelativeLayout(context, attrs, defStyle), IDemoView, StickerView.OnStickerOperationListener {
    private var mImgSource: FilterImageView = FilterImageView(context)
    var drawingView: StickerView
    private set
    private var mImageFilterView: ImageFilterView
    var frameView: RelativeLayout
    var premiumView: RelativeLayout
    private var playerView: PlayerView
    private var graphicView: RelativeLayout
    var borderView: RelativeLayout
    private var greetingPic: ImageView
    private var greetingPicLay: ZoomLayout
    var greetingFrame: ImageView
    var glTextureView: GLTextureView
    private var clipSourceImage = false
    var exoplayer: SimpleExoPlayer? = null
    var webView: WebView? = null
    val sourceParam = setupImageSource(attrs)

    init {
        //Setup GLSurface attributes
        borderView = RelativeLayout(context)
        drawingView = StickerView(context)
        mImageFilterView = ImageFilterView(context)
        frameView = RelativeLayout(context)
        premiumView = RelativeLayout(context)
        playerView = PlayerView(context)
        graphicView = RelativeLayout(context)
        greetingPic = ImageView(context)
        greetingPicLay = ZoomLayout(context)
        greetingFrame = ImageView(context)
        glTextureView = GLTextureView(context)
        webView = WebView(context)
        webView?.setBackgroundColor(Color.TRANSPARENT)

        Log.d("initCanvas", "setupImageSource")
        mImgSource.setOnImageChangedListener(object : OnImageChangedListener {
            override fun onBitmapLoaded(sourceBitmap: Bitmap?) {
                mImageFilterView.setFilterEffect(PhotoFilter.NONE)
                mImageFilterView.setSourceBitmap(sourceBitmap)

            }
        })

        //Border View
        addView(borderView, setupBorderView())

        //Add image source
        borderView.addView(mImgSource, sourceParam)

        //FilterLay
        val filterParam = setupFilterView()
        borderView.addView(mImageFilterView, filterParam)
    }

    fun initImage() {
        borderView.addView(greetingPicLay, setupGreetingView())
        borderView.addView(greetingFrame, setupGreetingFrameView())
        addMultiView();
    }

    private val mDemoPresenter: DemoPresenter = DemoPresenter()

    fun initPhotoToVideo(photos: ArrayList<String>?) {
        borderView.addView(glTextureView, setupGlTextureView())
        addMultiView();
        mDemoPresenter.attachView(this)
        mDemoPresenter.onPhotoPick(photos)
    }

    fun setFilterToVideo(item: FilterItem?) {
        mDemoPresenter.onFilterSelect(item)
    }

    fun setVideoAnimation(item: TransferItem?) {
        mDemoPresenter.onTransferSelect(item)
    }

    fun setVideoMusic(uri: Uri?) {
        mDemoPresenter.setMusic(uri)
    }

    fun getAnimations(): List<TransferItem?>? {
        return mDemoPresenter.transfers
    }

    fun saveCustomVideo(param: SaveVideoResponse) {
        mDemoPresenter.saveVideo { s -> param.onVideoSAve(s) }
    }

    fun initVideo() {
        //ExoPlayer
        borderView.addView(playerView, setupPlayerView())
        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FIT
        playerView.useController = false

        initializePlayer()
        addMultiView();
    }

    private var mViews: ArrayList<View>? = null
    private fun addMultiView() {
        mViews = ArrayList()

        //Graphic View
        borderView.addView(graphicView, setupFrameView())

        borderView.addView(webView, setupFrameView())

        //Frame View
        borderView.addView(frameView, setupFrameView())

        borderView.addView(premiumView, setupPremiumView())

        val brushParam = setupDrawingView()
        borderView.addView(drawingView, brushParam)

        drawingView.isLocked = false
        drawingView.isConstrained = true
        drawingView.onStickerOperationListener = this
        this.drawingView.setOnTouchListener { v, event ->
            drawingView.invalidate()
            v?.onTouchEvent(event) ?: true
        }
    }

    private fun initializePlayer() {
        val trackSelector = DefaultTrackSelector(context)
        val loadControl: LoadControl = DefaultLoadControl.Builder()
            .setAllocator(DefaultAllocator(true, 16))
            .setBufferDurationsMs(1 * 1024, 1 * 1024, 500, 1024)
            .setTargetBufferBytes(-1)
            .setPrioritizeTimeOverSizeThresholds(true)
            .build()
        try {
            exoplayer = SimpleExoPlayer.Builder(context).setTrackSelector(trackSelector)
                .setLoadControl(loadControl)
                .build()
            exoplayer!!.setThrowsWhenUsingWrongThread(false)
            exoplayer!!.setRepeatMode(Player.REPEAT_MODE_ALL)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val audioAttributes = AudioAttributes.Builder()
                    .setUsage(C.USAGE_MEDIA)
                    .setContentType(C.CONTENT_TYPE_MOVIE)
                    .build()
                exoplayer!!.setAudioAttributes(audioAttributes, true)
            }
        } catch (e: java.lang.Exception) {

        }
    }


    fun setVideoUrl(url: String) {
        setBackgroundColor(Color.TRANSPARENT)
        playerView.visibility = View.VISIBLE
        val httpDataSourceFactory =
            DefaultHttpDataSource.Factory().setAllowCrossProtocolRedirects(true)
        val defaultDataSourceFactory: DataSource.Factory =
            DefaultDataSourceFactory(context, httpDataSourceFactory)
        val mediaItem: MediaItem = MediaItem.fromUri(url)
        val mediaSource: MediaSource =
            ProgressiveMediaSource.Factory(defaultDataSourceFactory).createMediaSource(mediaItem)
        exoplayer!!.setMediaSource(mediaSource, true)
        exoplayer!!.prepare()
        exoplayer!!.playWhenReady = true
        playerView.player = exoplayer
    }

    @SuppressLint("Recycle")
    private fun setupImageSource(attrs: AttributeSet?): LayoutParams {
        mImgSource.id = imgSrcId
        mImgSource.adjustViewBounds = true
        mImgSource.scaleType = ImageView.ScaleType.CENTER_INSIDE

        attrs?.let {
            val a = context.obtainStyledAttributes(it, R.styleable.PhotoEditorView)
            val imgSrcDrawable = a.getDrawable(R.styleable.PhotoEditorView_photo_src)
            if (imgSrcDrawable != null) {
                mImgSource.setImageDrawable(imgSrcDrawable)
            }
        }

        var widthParam = ViewGroup.LayoutParams.MATCH_PARENT
        if (clipSourceImage) {
            widthParam = ViewGroup.LayoutParams.WRAP_CONTENT
        }
        val params = LayoutParams(
            widthParam, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        return params
    }

    private fun setupDrawingView(): LayoutParams {
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, imgSrcId)
        params.addRule(ALIGN_BOTTOM, imgSrcId)
        params.addRule(ALIGN_LEFT, imgSrcId)
        params.addRule(ALIGN_RIGHT, imgSrcId)
        return params
    }

    private fun setupFrameView(): LayoutParams {
        frameView.id = frameId
        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, imgSrcId)
        params.addRule(ALIGN_BOTTOM, imgSrcId)
        return params
    }

    private fun setupPremiumView(): LayoutParams {
        frameView.id = premiumId
        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, imgSrcId)
        params.addRule(ALIGN_BOTTOM, imgSrcId)
        return params
    }

    private fun setupBorderView(): LayoutParams {
        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        return params
    }

    private fun setupGreetingView(): LayoutParams {
        greetingPic.visibility = GONE
        greetingPic.id = greetingId

        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, imgSrcId)
        params.addRule(ALIGN_BOTTOM, imgSrcId)
        return params
    }

    private fun setupPlayerView(): LayoutParams {
        playerView.visibility = GONE
        playerView.id = playerViewID

        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, glFilterId)
        params.addRule(ALIGN_BOTTOM, glFilterId)
        return params
    }

    private fun setupGlTextureView(): LayoutParams {
        playerView.visibility = GONE
        playerView.id = playerViewID

        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, glFilterId)
        params.addRule(ALIGN_BOTTOM, glFilterId)
        return params
    }

    private fun setupGreetingFrameView(): LayoutParams {
        greetingFrame.visibility = GONE
        greetingFrame.id = greetingFrameId

        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        params.addRule(ALIGN_TOP, glFilterId)
        params.addRule(ALIGN_BOTTOM, glFilterId)
        params.addRule(CENTER_IN_PARENT, TRUE)
        return params
    }

    private fun setupFilterView(): LayoutParams {
        mImageFilterView.visibility = GONE
        mImageFilterView.id = glFilterId

        //Align brush to the size of image view
        val params = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.addRule(CENTER_IN_PARENT, TRUE)
        params.addRule(ALIGN_TOP, imgSrcId)
        params.addRule(ALIGN_BOTTOM, imgSrcId)
        return params
    }

    /**
     * Source image which you want to edit
     *
     * @return source ImageView
     */
    val source: ImageView get() = mImgSource

    fun saveFilter(onSaveBitmap: OnSaveBitmap) {
        if (exoplayer == null) {
            mImageFilterView.saveBitmap(object : OnSaveBitmap {
                override fun onBitmapReady(saveBitmap: Bitmap?) {
                    saveBitmap?.let {
                        mImgSource.setImageBitmap(it)
                    }
                    mImageFilterView.visibility = GONE
                    onSaveBitmap.onBitmapReady(saveBitmap)
                }

                override fun onFailure(e: Exception?) {
                    onSaveBitmap.onFailure(e)
                }
            })
        } else {
            Log.e(TAG, "saveFilter: GONE")
            onSaveBitmap.onBitmapReady(mImgSource.bitmap)
        }
    }

    fun setFilterEffect(filterType: PhotoFilter?) {
        mImageFilterView.visibility = VISIBLE
        mImageFilterView.setSourceBitmap(mImgSource.bitmap)
        mImageFilterView.setFilterEffect(filterType)
    }


    fun setFrameStatus(visible: Int) {
        if (visible == INVISIBLE || visible == GONE) {
            frameView.removeAllViews()
        }
    }

    fun setWebFrameStatus(visible: Int) {
        webView!!.visibility = visible
    }

    fun setFrameView(view: View?) {
        frameView.visibility = VISIBLE
        frameView.removeAllViews()
        frameView.addView(view)
    }

    fun setPremiumView(view: View?) {
        premiumView.visibility = VISIBLE
        premiumView.removeAllViews()
        premiumView.addView(view)
    }

    fun setPremiumStatus(visible: Int) {
        premiumView.visibility = visible
    }

    fun setGreetingImage(view: Bitmap?) {
        greetingPic.visibility = VISIBLE
        greetingPic.setImageBitmap(view)
        greetingPicLay.addView(greetingPic)

    }

    val STEP = 200f
    var mRatio = 1.0f
    var mBaseDist = 0
    var mBaseRatio = 0f
    var fontsize = 13f

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.getPointerCount() === 2) {
            val action: Int = event.getAction()
            val pureaction = action and MotionEvent.ACTION_MASK
            if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                mBaseDist = getDistance(event)
                mBaseRatio = mRatio
            } else {
                val delta: Float = (getDistance(event) - mBaseDist) / STEP
                val multi = Math.pow(2.0, delta.toDouble()).toFloat()
                mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi))
            }
        }
        return true
    }
    fun getDistance(event: MotionEvent): Int {
        val dx = (event.getX(0) - event.getX(1)).toInt()
        val dy = (event.getY(0) - event.getY(1)).toInt()
        return Math.sqrt((dx * dx + dy * dy).toDouble()).toInt()
    }
    
    fun setClipSourceImage(clip: Boolean) {
        clipSourceImage = clip
        val param = setupImageSource(null)
        mImgSource.layoutParams = param
    } // endregion

    companion object {
        private const val TAG = "PhotoEditorView"
        private const val imgSrcId = 1
        private const val shapeSrcId = 2
        private const val glFilterId = 3
        private const val frameId = 4
        private const val premiumId = 10
        private const val greetingId = 5
        private const val greetingFrameId = 6
        private const val borderId = 7
        private const val playerViewID = 8
    }

    override fun getGLView(): GLTextureView {
        return glTextureView
    }

    override fun getActivity(): Context {
        return context
    }

    fun gLViewPause() {
        glView.onPause()
        mDemoPresenter.onPause()
    }

    fun gLViewResume() {
        glView.onResume()
        mDemoPresenter.onResume()
    }

    fun onResume() {
        if (exoplayer != null) {
            exoplayer!!.playWhenReady = true
        }
    }

    fun onPause() {
        if (exoplayer != null) {
            exoplayer!!.playWhenReady = false
        }
    }

    fun addSticker(bitmap: Bitmap?) {
        val d: Drawable = BitmapDrawable(resources, bitmap)
        drawingView.addSticker(DrawableSticker(d))
    }

    fun addText(string: String?, fontColor: Int, typeface: Typeface) {
        var text = TextSticker(context);
        text.text = string
        text.setTextColor(fontColor)
        text.setTypeface(typeface)
        text.setTextAlign(Layout.Alignment.ALIGN_CENTER)
        text.resizeText()

        drawingView.addSticker(text)
    }

    fun addEmoji(emoji: String?) {
        var text = TextSticker(context);
        text.text = emoji
        text.setTextAlign(Layout.Alignment.ALIGN_CENTER)
        text.resizeText()

        drawingView.addSticker(text)
    }

    override fun onStickerAdded(sticker: Sticker) {

    }

    private val textSticker: TextSticker? = null
    override fun onStickerClicked(sticker: Sticker) {
        if (sticker is TextSticker) {
//            (sticker as TextSticker).setTextColor(Color.RED)
            drawingView.replace(sticker)
            drawingView.invalidate()
        }
    }

    override fun onStickerDeleted(sticker: Sticker) {

    }

    override fun onStickerDragFinished(sticker: Sticker) {

    }

    override fun onStickerTouchedDown(sticker: Sticker) {

    }

    override fun onStickerZoomFinished(sticker: Sticker) {

    }

    override fun onStickerFlipped(sticker: Sticker) {

    }

    override fun onStickerDoubleTapped(sticker: Sticker) {

    }

}