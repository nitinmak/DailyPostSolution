package com.sendpost.dreamsoft.ImageEditor

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnticipateOvershootInterpolator
import android.webkit.JsResult
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import android.widget.SeekBar.GONE
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.FileProvider
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import androidx.viewpager.widget.ViewPager
import com.ainapage.vr.ImageEditor.FileSaveHelper
import com.arthenica.mobileffmpeg.Config
import com.arthenica.mobileffmpeg.FFmpeg
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.downloader.Error
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sendpost.dreamsoft.BgEraser.EraserActivity
import com.sendpost.dreamsoft.Classes.*
import com.sendpost.dreamsoft.ImageEditor.Border.BorderListener
import com.sendpost.dreamsoft.ImageEditor.Border.BorderViewAdapter
import com.sendpost.dreamsoft.ImageEditor.Frame.FrameAdapter
import com.sendpost.dreamsoft.ImageEditor.Frame.FrameListener
import com.sendpost.dreamsoft.ImageEditor.Frame.FramePagerAdapter
import com.sendpost.dreamsoft.ImageEditor.Stickers.StickerBSFragment
import com.sendpost.dreamsoft.ImageEditor.Video.AnimationAdapter
import com.sendpost.dreamsoft.ImageEditor.Video.FilterAdapter
import com.sendpost.dreamsoft.ImageEditor.filters.FilterListener
import com.sendpost.dreamsoft.ImageEditor.filters.FilterViewAdapter
import com.sendpost.dreamsoft.ImageEditor.tools.EditingToolsAdapter
import com.sendpost.dreamsoft.ImageEditor.tools.ToolType
import com.sendpost.dreamsoft.Interface.Properties
import com.sendpost.dreamsoft.NavFragment.PremiumFragment
import com.sendpost.dreamsoft.R
import com.sendpost.dreamsoft.RazorpayActivity
import com.sendpost.dreamsoft.binding.BindingAdaptet
import com.sendpost.dreamsoft.dialog.CustomeDialogFragment
import com.sendpost.dreamsoft.dialog.DialogType
import com.sendpost.dreamsoft.dialog.PickFromFragment
import com.sendpost.dreamsoft.dialog.PickedImageActionFragment
import com.sendpost.dreamsoft.model.FrameCategoryModel
import com.sendpost.dreamsoft.model.FrameModel
import com.sendpost.dreamsoft.model.PostsModel
import com.sendpost.dreamsoft.responses.FrameResponse
import com.sendpost.dreamsoft.ImageEditor.viewmodel.FrameViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import ja.burhanrashid52.photoeditor.*
import ja.burhanrashid52.photoeditor.Filter.PhotoFilter
import kotlinx.android.synthetic.main.activity_edit_image.*
import me.iwf.photopicker.PhotoPicker
import org.json.JSONObject
import java.io.*
import java.nio.channels.FileChannel


class EditImageActivity : AppCompatActivity(), View.OnClickListener,
    PropertiesBSFragment.Properties, Properties, EmojiBSFragment.EmojiListener,
    StickerBSFragment.StickerListener,EditingToolsAdapter.OnItemSelected, FilterListener, BorderListener {


    private var mPhotoEditorView: PhotoEditorView? = null
    private var mPropertiesBSFragment: PropertiesBSFragment? = null
    private var mShapeBSFragment: ShapeBSFragment? = null
    private var mDrawBSFragment: DrawBSFragment? = null
    private var mEmojiBSFragment: EmojiBSFragment? = null
    private var mStickerBSFragment: StickerBSFragment? = null
    private var mTxtCurrentTool: TextView? = null
    private var seekBar: SeekBar? = null

//  private var mWonderFont: Typeface? = null
    private var mRvTools: RecyclerView? = null
    private var mRvFilters: RecyclerView? = null
    private var mRvBorder: RecyclerView? = null
    private var rvideoItems: RecyclerView? = null
    private var pframeShowHideBtn: ImageView? = null
    private var frameLay: RelativeLayout? = null
    private var noFrameFoundTv: TextView? = null
    private var go_premium: TextView? = null

    private val mFilterViewAdapter = FilterViewAdapter(this)
    private val mBorderViewAdapter = BorderViewAdapter(this)
    private var mRootView: ConstraintLayout? = null
    private val mConstraintSet = ConstraintSet()
    private var mIsFilterVisible = false
    private var mIsBorderVisible = false
    var takePermissionUtils: PermissionUtils? = null
    var activity: Activity? = null

    @VisibleForTesting
    private var mSaveFileHelper: FileSaveHelper? = null
    var editType = ""
    var customeVideoPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)

        setContentView(R.layout.activity_edit_image)

        FrameAdapter.selected_frame_name = "person_frame1"

        VIDEO_TO_PHOTO = false
        activity = this
        initViews()

        takePermissionUtils = PermissionUtils(this, mPermissionResult)
        isPostSubsBuy = intent.getBooleanExtra("isBuyed",false)
        if (intent.getStringExtra("type").equals("PhotoVideo")) {
            postModel = PostsModel()
            VIDEO_TO_PHOTO = true
            imgSave.isActivated = false
            editType = "PhotoVideo"
            val selectPhotos: TextView = findViewById(R.id.selectPhotos)
            selectPhotos.setOnClickListener(this)
            selectPhotos.visibility = View.VISIBLE
            mPhotoEditorView?.source?.setImageDrawable(getDrawable(R.drawable.editor_bg))

        } else if (intent.getStringExtra("type").equals("CustomVideo")) {
            postModel = PostsModel()
            imgSave.isActivated = false
            editType = "CustomVideo"
            mPhotoEditorView?.initVideo()
            val selectVideo: TextView = findViewById(R.id.selectVideo)
            selectVideo.setOnClickListener(this)
            selectVideo.visibility = View.VISIBLE
            mPhotoEditorView?.source?.setImageDrawable(getDrawable(R.drawable.editor_bg))
        }

        else if (intent.getStringExtra("type").equals("CustomPhoto")) {

            postModel = PostsModel()
            imgSave.isActivated = false
            findViewById<LinearLayout>(R.id.select_custome_image_lay).visibility = View.VISIBLE
            editType = "CustomPhoto"
            mPhotoEditorView?.initImage()

            val imgCamera: ImageView = findViewById(R.id.imgCamera)
            imgCamera.setOnClickListener(this)
            imgCamera.visibility = View.VISIBLE
            val imgGallery: ImageView = findViewById(R.id.imgGallery)
            imgGallery.setOnClickListener(this)
            imgGallery.visibility = View.VISIBLE

            mPhotoEditorView?.source?.setImageDrawable(getDrawable(R.drawable.editor_bg))

        }
        else if (intent.getStringExtra("type").equals("GreetingPosts")) {
            imgSave.isActivated = false
            editType = "GreetingPosts"
            mPhotoEditorView?.initImage()

            val imgGreeting: TextView = findViewById(R.id.imgGreeting)
            imgGreeting.setOnClickListener(this)
            imgGreeting.visibility = View.VISIBLE

            mPhotoEditorView?.source?.setImageDrawable(getDrawable(R.drawable.editor_bg))
            mPhotoEditorView?.greetingFrame?.visibility = VISIBLE

            BindingAdaptet.setImageUrl(mPhotoEditorView?.greetingFrame,intent.getStringExtra("path"))

        }
        else {

            imgSave.isActivated = true

            var path = intent.getStringExtra("path")

            if (path?.contains(".mp4")!!) {

                editType = "Video"
                mPhotoEditorView?.initVideo()
                mPhotoEditorView?.source?.setImageDrawable(getDrawable(R.drawable.editor_bg))
                val proxy = App.getProxy(App.app)
                val proxyUrl = proxy.getProxyUrl(Functions.getItemBaseUrl(path))
                photoEditorView.setVideoUrl(proxyUrl)

            } else {
                editType = "Photo"
                mPhotoEditorView?.initImage()
                BindingAdaptet.setImageUrl(mPhotoEditorView?.source,path)

            }
        }


        mPropertiesBSFragment = PropertiesBSFragment()
        mEmojiBSFragment = EmojiBSFragment()

        mStickerBSFragment = StickerBSFragment()

        mShapeBSFragment = ShapeBSFragment()
        mDrawBSFragment = DrawBSFragment()

        mStickerBSFragment?.setStickerListener(this)

        mEmojiBSFragment?.setEmojiListener(this)
        mPropertiesBSFragment?.setPropertiesChangeListener(this)
        mShapeBSFragment?.setPropertiesChangeListener(this)
        mDrawBSFragment?.setPropertiesChangeListener(this)
        val llmTools = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRvTools?.layoutManager = llmTools
        mRvTools?.adapter = EditingToolsAdapter(activity!!, this)
        val llmFilters = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        mRvFilters?.layoutManager = llmFilters
        mRvFilters?.adapter = mFilterViewAdapter
        mRvBorder?.adapter = mBorderViewAdapter

        mPhotoEditorView?.run {}
        mPhotoEditorView?.drawingView.run {}
        mSaveFileHelper = FileSaveHelper(this)

        photoEditorView?.setFilterEffect(PhotoFilter.NONE)

        if (Functions.IsPremiumEnable(activity)) {
            go_premium?.visibility ?:  GONE
        }

        go_premium?.setOnClickListener {
            showPremiumFragment()
        }
    }

    private val mPermissionResult = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) {
        result ->
        var allPermissionClear = true
        val blockPermissionCheck: MutableList<String> = java.util.ArrayList()
        for (key in result.keys) {
            if (!result[key]!!) {
                allPermissionClear = false
                blockPermissionCheck.add(Functions.getPermissionStatus(activity, key))
            }
        }
        if (blockPermissionCheck.contains("blocked")) {
            Functions.showPermissionSetting(
                activity,
                getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic)
            )
        } else if (allPermissionClear) {

        }
    }

    override fun onStop() {
        super.onStop()
        if (editType == "PhotoVideo" && imgSave.isActivated) {
            photoEditorView.gLViewPause()
        }
        photoEditorView.onPause()
    }


    override fun onResume() {
        super.onResume()
        if (editType == "PhotoVideo" && imgSave.isActivated) {
            photoEditorView.gLViewResume()
        }
        photoEditorView.onResume()
    }

    override fun onPause() {
        super.onPause()
        photoEditorView.onPause()
    }

    var nameBtn: TextView? = null
    var logoBtn: TextView? = null
    var numberBtn: ImageView? = null
    private var emailBtn: ImageView? = null
    var locationBtn: ImageView? = null
    private var webBtn: ImageView? = null
    private var instaBtn: ImageView? = null
    private var youtubeBtn: ImageView? = null
    private var whatsappBtn: ImageView? = null
    private var facebookBtn: ImageView? = null
    private var frameBtn: TextView? = null

    private fun initFrameButtonsViews() {
        nameBtn = findViewById(R.id.name_btn)
        logoBtn = findViewById(R.id.logo_btn)
        numberBtn = findViewById(R.id.number_btn)
        emailBtn = findViewById(R.id.email_btn)
        locationBtn = findViewById(R.id.location_btn)
        webBtn = findViewById(R.id.web_btn)
        instaBtn = findViewById(R.id.instagram_btn)
        youtubeBtn = findViewById(R.id.youtube_btn)
        whatsappBtn = findViewById(R.id.whatsapp_btn)
        facebookBtn = findViewById(R.id.facebook_btn)
        frameBtn = findViewById(R.id.frame_btn)
        frameBtn?.isActivated = false

        logoBtn?.setOnClickListener {
            if (logoBtn!!.isActivated) {
                logoBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('logo')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('logo')")
                logoBtn?.isActivated = true
            }
        }

        nameBtn?.setOnClickListener {
            if (nameBtn!!.isActivated) {
                nameBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('name')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('name')")
                nameBtn?.isActivated = true
            }
        }

        numberBtn?.setOnClickListener {
            if (numberBtn!!.isActivated) {
                numberBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('number')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('number')")
                numberBtn?.isActivated = true
            }
        }

        emailBtn?.setOnClickListener {

            if (emailBtn!!.isActivated) {
                emailBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('email')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('email')")
                emailBtn?.isActivated = true
            }
        }

        locationBtn?.setOnClickListener {
            if (locationBtn!!.isActivated) {
                locationBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('address')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('address')")
                locationBtn?.isActivated = true
            }
        }

        webBtn?.setOnClickListener {
            if (webBtn!!.isActivated) {
                webBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('website')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('website')")
                webBtn?.isActivated = true
            }
        }

        youtubeBtn?.setOnClickListener {
            if (youtubeBtn!!.isActivated) {
                youtubeBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('youtube')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('youtube')")
                youtubeBtn?.isActivated = true
            }
        }

        instaBtn?.setOnClickListener {
            if (instaBtn!!.isActivated) {
                instaBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('instagram')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('instagram')")
                instaBtn?.isActivated = true
            }
        }

        whatsappBtn?.setOnClickListener {
            if (whatsappBtn!!.isActivated) {
                whatsappBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('whatsapp')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('whatsapp')")
                whatsappBtn?.isActivated = true
            }
        }

        facebookBtn?.setOnClickListener {
            if (facebookBtn!!.isActivated) {
                facebookBtn?.isActivated = false
                mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('facebook')")
            } else {
                mPhotoEditorView?.webView?.loadUrl("javascript:visibleItem('facebook')")
                facebookBtn?.isActivated = true
            }
        }

        frameBtn?.setOnClickListener {
            if (frameBtn!!.isActivated) {
                frameBtn?.isActivated = false
                mPhotoEditorView?.setFrameStatus(View.GONE)
                mPhotoEditorView?.setWebFrameStatus(View.GONE)
            } else {
                if (isUserFrameActive) {
                    mPhotoEditorView?.setFrameStatus(View.GONE)
                } else {
                    mPhotoEditorView?.setWebFrameStatus(View.VISIBLE)
                }
                frameBtn?.isActivated = false
            }
        }

    }

    private fun initViews() {
        mPhotoEditorView = findViewById(R.id.photoEditorView)
        mTxtCurrentTool = findViewById(R.id.txtCurrentTool)
        seekBar = findViewById(R.id.borderSizeBar)
        mRvTools = findViewById(R.id.rvConstraintTools)
        mRvFilters = findViewById(R.id.rvFilterView)
        mRvBorder = findViewById(R.id.mRvBorder)
        mRootView = findViewById(R.id.rootView)
        rvideoItems = findViewById(R.id.rvideoItems)

        pframeShowHideBtn = findViewById(R.id.pframe_show_hide_btn)
        frameLay = findViewById(R.id.frame_lay)
        noFrameFoundTv = findViewById(R.id.no_frame_found_tv)

        val imgUndo: ImageView = findViewById(R.id.imgUndo)
        imgUndo.setOnClickListener(this)
        val imgRedo: ImageView = findViewById(R.id.imgRedo)
        imgRedo.setOnClickListener(this)

        val imgSave: TextView = findViewById(R.id.imgSave)
        imgSave.setOnClickListener(this)
        val imgClose: TextView = findViewById(R.id.imgClose)
        imgClose.setOnClickListener(this)

        seekBar?.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                photoEditorView.drawingView.removeAllViews()
                progressBorder = progress
                photoEditorView.borderView.setPadding(progress, progress, progress, progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        pframeShowHideBtn?.setOnClickListener {
            if (frameLay?.visibility == View.VISIBLE) {
                frameLay?.visibility = View.GONE
                pframeShowHideBtn?.setImageDrawable(getDrawable(R.drawable.ic_right_arrow))
            } else {
                pframeShowHideBtn?.setImageDrawable(getDrawable(R.drawable.ic_arrow_left))
                frameLay?.visibility = View.VISIBLE
            }
        }

        setFrameAdapter()
    }

    var frameList: ArrayList<FrameCategoryModel> = ArrayList()
    var framViewModel: FrameViewModel? = null

    private fun setFrameAdapter() {
        framViewModel = ViewModelProvider(this).get(FrameViewModel::class.java)
        Functions.showLoader(activity)

//      frameList.add(FrameCategoryModel("0", getString(R.string.person)))
        framViewModel!!.data.observe(this, object : Observer<FrameResponse?> {
            override fun onChanged(response: FrameResponse?) {
                Functions.cancelLoader()

                Log.d("nitin","++"+response?.framecategories.toString())
                if (response != null) {
                    frameList.addAll(response.framecategories)
                    setFramesAdapter()
                    try {
                        onFrameSelect(frameList.get(0).frames.get(0))
                    } catch (e: Exception) {
                    }
                }
            }
        })

//        ServerRequest.Call_Api(
//            activity, ApiCalls.getFramesCategory, JSONObject()
//        ) { resp ->
//            Functions.cancelLoader()
//            try {
//                val gson = Gson()
//                try {
//                    val jsonObject = JSONObject(resp)
//                    val code = jsonObject.optString("code")
//                    if (code == "200") {
//                        val msg = jsonObject.optJSONArray("msg")
//                        for (i in 0 until msg.length()) {
//                            val `object` = msg.optJSONObject(i)
//                            val categoryObject = `object`.optJSONObject("FramesCategory");
//
//                            val frame_array = `object`.optJSONArray("Frames")
//                            val frameslist = java.util.ArrayList<FrameModel>()
//                            for (k in 0 until frame_array.length()) {
//                                val itemdata = frame_array.optJSONObject(k)
//                                val frameModel = gson.fromJson(itemdata.toString(), FrameModel::class.java)
//                                frameslist.add(frameModel)
//                            }
//
//                            val model = FrameCategoryModel(
//                                categoryObject.optString("id"),
//                                categoryObject.optString("name"),
//                                categoryObject.optString("status"),
//                                categoryObject.optString("created"),
//                                frameslist
//                            );
//                            frameList.add(model)
//                            if(i == 0){
//
//                            }
//                        }
//
//                    }
//                } catch (e: java.lang.Exception) {
//                    Toast.makeText(activity, "" + e.message, Toast.LENGTH_SHORT).show()
//                }
//            } catch (e: java.lang.Exception) {
//                Toast.makeText(
//                    activity,
//                    "" + e.message,
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//        }
    }
    var isUserFrameActive = false

    private fun setFramesAdapter() {
        val pager: ViewPager = findViewById(R.id.viewPager)
      val tabLayout: SmartTabLayout = findViewById(R.id.tabLayout)

        pager.adapter = FramePagerAdapter(supportFragmentManager, frameList,
            object : FrameListener {

                override fun onFrameSelected(model: FrameModel?) {
                    if (model!!.type.equals("user")) {
                        Log.d("ndcjdsndsjndjs",pager.currentItem.toString())
                        isUserFrameActive = false
                        val imageView = ImageView(activity)
                        imageView.adjustViewBounds = true
                        imageView.layoutParams = RelativeLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )

                        BindingAdaptet.setImageUrl(imageView,model.thumbnail)

                        mPhotoEditorView?.setFrameView(imageView)
                        mPhotoEditorView?.setFrameStatus(VISIBLE)
                        mPhotoEditorView?.setWebFrameStatus(GONE)

                        instaBtn?.visibility = GONE
                        facebookBtn?.visibility = GONE
                        youtubeBtn?.visibility = GONE
                        whatsappBtn?.visibility = GONE
                        locationBtn?.visibility = GONE
                        webBtn?.visibility = GONE
                        emailBtn?.visibility = GONE
                        numberBtn?.visibility = GONE
                        logoBtn?.visibility = GONE
                        nameBtn?.visibility = GONE
                        frameBtn?.isActivated = false
                    } else {
                        isUserFrameActive = false
                        mPhotoEditorView?.setFrameStatus(GONE)
                        mPhotoEditorView?.setWebFrameStatus(VISIBLE)

                        onFrameSelect(model)
                    }
                }
            })



        tabLayout.setOnTabClickListener { position ->
            Log.d("fvnjfdjfdjbfd", position.toString())
            onFrameSelect(frameList.get(position).frames.get(0))
        }

        tabLayout.setViewPager(pager)

        pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                onFrameSelect(frameList.get(position).frames.get(0))
            }

            override fun onPageSelected(position: Int) {

            }

        })

    }

    @RequiresApi(Build.VERSION_CODES.S)
    @SuppressLint("NonConstantResourceId", "MissingPermission")
    override fun onClick(view: View) {
        when (view.id) {
//          R.id.imgUndo -> mPhotoEditorView?.drawingView?.undo()
//          R.id.imgRedo -> mPhotoEditorView?.drawingView?.redo()
            R.id.imgSave -> {
                Functions.updatePostView(this, postModel.id,editType)
                mPhotoEditorView?.drawingView?.isLocked = true
                saveImage()
            }
            R.id.imgClose -> onBackPressed()
            R.id.imgCamera -> {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (pictureIntent.resolveActivity(packageManager) != null) {
                        var photoFile: File? = null
                        try {
                            photoFile = Functions.createImageFile(this)
                            imageFilePath = photoFile.absolutePath
                        } catch (ex: java.lang.Exception) {
                        }
                        if (photoFile != null) {
                            val photoURI = FileProvider.getUriForFile(
                                this, "$packageName.fileprovider",
                                photoFile
                            )
                            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        }
                    }
                    startActivityForResult(pictureIntent, CAMERA_REQUEST_CUSTOME)
                } else {
                    takePermissionUtils!!.takeStorageCameraPermission()
                }

            }
            R.id.imgGallery -> {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_REQUEST_CUSTOME
                    )
                } else {
                    takePermissionUtils!!.takeStorageCameraPermission()
                }
            }
            R.id.selectVideo -> {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    val intent = Intent()
                    intent.type = "video/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Video"),
                        PICK_REQUEST_VIDEO
                    )

                } else {
                    takePermissionUtils!!.takeStorageCameraPermission()
                }
            }
            R.id.selectPhotos -> {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    PhotoPicker.builder()
                        .setShowCamera(false)
                        .setShowGif(false)
                        .setPreviewEnabled(false)
                        .start(this, PhotoPicker.REQUEST_CODE)
                } else {
                    takePermissionUtils!!.takeStorageCameraPermission()
                }
            }
            R.id.imgGreeting -> {
                selectGreetingImage()
            }
        }
    }

    private fun selectGreetingImage() {
        PickFromFragment(getString(R.string.add_photo_), object : PickFromFragment.DialogCallback {
            override fun onCencel() {}
            override fun onGallery() {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "Select Picture"),
                        PICK_REQUEST_GREETING
                    )
                }else{
                    takePermissionUtils!!.takeStorageCameraPermission()
                }
            }

            override fun onCamera() {
                if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                    val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    if (pictureIntent.resolveActivity(packageManager) != null) {
                        var photoFile: File? = null
                        try {
                            photoFile = Functions.createImageFile(applicationContext)
                            imageFilePath = photoFile.absolutePath
                        } catch (ex: java.lang.Exception) {
                        }
                        if (photoFile != null) {
                            val photoURI = FileProvider.getUriForFile(
                                applicationContext, packageName + ".fileprovider", photoFile
                            )
                            pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                        }
                    }
                    startActivityForResult(pictureIntent, CAMERA_REQUEST_GREETING)
                }else{
                    takePermissionUtils!!.takeStorageCameraPermission()
                }
            }
        }).show(supportFragmentManager, "")
    }


    private fun saveImage() {

        if (!imgSave.isActivated) {
            return
        }
        if(!takePermissionUtils!!.isStorageCameraPermissionGranted){
            takePermissionUtils!!.takeStorageCameraPermission()
            return
        }

        try {
            removeWatermarkBtn?.visibility = GONE
        } catch (e: Exception) {
        }

        mPhotoEditorView?.saveFilter(object : OnSaveBitmap {

            override fun onBitmapReady(saveBitmap: Bitmap?) {
                val photoSaverTask = PhotoSaverTask(photoEditorView)
                photoSaverTask.setOnSaveBitmap(object : OnSaveBitmap {
                    override fun onBitmapReady(saveBitmap: Bitmap?) {
                        if (intent.getStringExtra("type").equals("CustomPhoto")) {

                            startActivity(
                                Intent(
                                    activity,
                                    PreviewActivity::class.java
                                ).putExtra("url", Functions.saveImage(saveBitmap, activity))
                            )
                            activity?.finish()

                        }
                        else if (editType == "PhotoVideo") {


                            mPhotoEditorView?.saveCustomVideo { s ->
                                VIDEO_PATH = s
                                FINAL_VIDEO_PATH = VIDEO_PATH
                                FRAME_PATH = Functions.saveImage(saveBitmap, activity)

                                CreateFinalVideo(activity!!, editType).execute()
                            }

                        } else if (editType == "CustomVideo") {

                            FINAL_VIDEO_PATH = Functions.getAppFolder(activity) + System.currentTimeMillis() + ".mp4"
                            VIDEO_PATH = customeVideoPath

                            FRAME_PATH = Functions.saveImage(saveBitmap, activity)
//
//                            Functions.showLoader(activity)
//                            Functions.dialog.findViewById<View>(R.id.tv_lay_extra).visibility = VISIBLE
//
//                            val inputCode1 = arrayOf(
//                                "-y",
//                                "-i",
//                                VIDEO_PATH,
//                                "-filter_complex",
//                                "scale=eval=frame:w=1200:h=1200:force_original_aspect_ratio=decrease,pad=1200:1200:(1200-iw)/2:(1200-ih)/2:black",
//
//                                FINAL_VIDEO_PATH
//                            )


//                            val rc = FFmpeg.execute(inputCode1)
//                            if (rc == Config.RETURN_CODE_SUCCESS) {
//                                Functions.dialog.findViewById<View>(R.id.tv_lay_extra).visibility = GONE
//                                Functions.cancelLoader()
                                CreateFinalVideo(activity!!, editType).execute()
//                                Log.i(Config.TAG, "Command execution completed successfully.")
//                            } else if (rc == Config.RETURN_CODE_CANCEL) {
//                                Log.i(Config.TAG, "Command execution cancelled by user.")
//                            } else {
//                                Log.i(
//                                    Config.TAG, String.format(
//                                        "Command execution failed with rc=%d and the output below.",
//                                        rc
//                                    )
//                                )
//                                Config.printLastCommandOutput(Log.INFO)
//                            }


                        } else if (postModel.type.equals("API")) {

                            if (postModel.item_url.contains(".mp4")) {
                                downloadAPIVideo(saveBitmap)
                            } else {
                                startActivity(
                                    Intent(
                                        activity,
                                        PreviewActivity::class.java
                                    ).putExtra("url", Functions.saveImage(saveBitmap, activity))
                                )
                                activity?.finish()
                            }

                        } else {
                            if (postModel.item_url.endsWith(".mp4")){
                                downloadAPIVideo(saveBitmap)
                            }else{
                                startActivity(
                                    Intent(
                                        activity,
                                        PreviewActivity::class.java
                                    ).putExtra("url", Functions.saveImage(saveBitmap, activity))
                                )
                                activity?.finish()
                            }
                        }
                    }

                    override fun onFailure(e: Exception?) {
                        imgSave.isActivated = true
                        Functions.showToast(activity, e?.message)
                    }
                })
                photoSaverTask.setSaveSettings(SaveSettings.Builder().build())
                photoSaverTask.saveBitmap()
            }

            override fun onFailure(e: Exception?) {
                imgSave.isActivated = true
                Functions.showToast(activity, e?.message)
            }
        })
    }

    private fun downloadAPIVideo(saveBitmap: Bitmap?) {
        var file = File(Functions.getAppFolder(this) + Variables.APP_HIDED_FOLDER + postModel.id + ".mp4")
        if (file.exists()) {
            if (saveBitmap != null) {
                VIDEO_PATH = file.absolutePath
                FINAL_VIDEO_PATH = VIDEO_PATH
                FRAME_PATH = Functions.saveImage(saveBitmap, activity)

                CreateFinalVideo(activity!!,editType).execute()
            } else {
                Functions.showToast(activity, "Bitmap null")
            }

        } else {
            Functions.showLoader(this)
            PRDownloader.download(
                Functions.getItemBaseUrl(postModel.item_url),
                Functions.getAppFolder(this) + Variables.APP_HIDED_FOLDER,
                postModel.id + ".mp4"
            )
                .build()
                .setOnStartOrResumeListener { }
                .start(object : OnDownloadListener {
                    override fun onDownloadComplete() {
                        Functions.cancelLoader()
                        if (saveBitmap != null) {
                            VIDEO_PATH = file.absolutePath
                            FINAL_VIDEO_PATH = VIDEO_PATH
                            FRAME_PATH = Functions.saveImage(saveBitmap, activity)
                            CreateFinalVideo(activity!!, editType).execute()
                        } else {
                            Functions.showToast(activity, "")
                        }
                    }

                    override fun onError(error: Error) {
                        Functions.cancelLoader()
                        Functions.showToast(activity, "" + error.connectionException)
                    }
                })

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST -> {
                    val uri = Uri.fromFile(File(imageFilePath))
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForCrop.launch(intent)
                }
                PICK_REQUEST -> {
                    val uri = data?.data
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForCrop.launch(intent)
                }
                CAMERA_REQUEST_GREETING -> {
                    findViewById<TextView>(R.id.imgGreeting).visibility = View.GONE
                    val uri = Uri.fromFile(File(imageFilePath))
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForGreeting.launch(intent)
                    imgSave.isActivated = true
                }
                PICK_REQUEST_GREETING -> {
                    findViewById<TextView>(R.id.imgGreeting).visibility = View.GONE
                    val uri = data?.data
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForGreeting.launch(intent)
                    imgSave.isActivated = true
                }
                CAMERA_REQUEST_CUSTOME -> {
                    findViewById<LinearLayout>(R.id.select_custome_image_lay).visibility = View.GONE
                    val uri = Uri.fromFile(File(imageFilePath))
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForCustomeCrop.launch(intent)
                    imgSave.isActivated = true
                }
                PICK_REQUEST_CUSTOME -> {
                    findViewById<LinearLayout>(R.id.select_custome_image_lay).visibility = View.GONE
                    val uri = data?.data
                    val intent = CropImage.activity(uri).setAspectRatio(1, 1).getIntent(this)
                    resultCallbackForCustomeCrop.launch(intent)
                    imgSave.isActivated = true
                }
                PICK_REQUEST_VIDEO -> {
                    val uri = data?.data
                    Log.d("vbhvfvfsv",Functions.getDuration(activity,FileUtils.getFileFromUri(this, uri).absolutePath).toString())
                    if (Functions.getDuration(activity,FileUtils.getFileFromUri(this, uri).absolutePath) > 60000) {
                        Functions.showToast(activity, getString(R.string.max_video_duration_error))
                    } else {

                        findViewById<TextView>(R.id.selectVideo).visibility = View.GONE
                        customeVideoPath = FileUtils.getFileFromUri(this, uri).absolutePath
                        photoEditorView.setVideoUrl(
                            FileUtils.getFileFromUri(
                                this,
                                uri
                            ).absolutePath
                        )
                        imgSave.isActivated = true
                    }
                }

                PhotoPicker.REQUEST_CODE -> {
                    findViewById<TextView>(R.id.selectPhotos).visibility = View.GONE
                    val photos = data!!.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS)
                    mPhotoEditorView?.initPhotoToVideo(photos)
                    imgSave.isActivated = true
                }
                REQUEST_MUSIC -> {

                    val uri = data!!.data

                    FINAL_AUDIO_PATH = FileUtils.getFileFromUri(this, uri).absolutePath
                    Log.d("vknjbnjdnbjnjbg", FINAL_AUDIO_PATH)
                    mPhotoEditorView?.setVideoMusic(uri)

                }
                ERASER_REQUEST -> {
                    mPhotoEditorView?.addSticker(compressBitmap(EraserActivity.bitmap, 80))
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun copy(source: File, destination: File) {
        val `in`: FileChannel = FileInputStream(source).getChannel()
        val out: FileChannel? = FileOutputStream(destination).channel
        try {
            `in`.transferTo(0, `in`.size(), out)
        } catch (e: java.lang.Exception) {
            Log.d("Exception", e.toString())
        } finally {
            if (`in` != null) `in`.close()
            if (out != null) out.close()
        }
    }

    private var resultCallbackForCustomeCrop
    = registerForActivityResult(ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val cropResult = CropImage.getActivityResult(data)
            val bitmap = MediaStore.Images.Media.getBitmap(
                contentResolver, cropResult.uri
            )
            mPhotoEditorView?.source?.setImageBitmap(bitmap)
        }
    }

    private var resultCallbackForCrop = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val cropResult = CropImage.getActivityResult(data)
            val bitmp = MediaStore.Images.Media.getBitmap(
                contentResolver, cropResult.uri
            )

            PickedImageActionFragment(bitmp) { bitmap ->
                mPhotoEditorView?.addSticker(compressBitmap(bitmap, 80))
            }.show(supportFragmentManager, "")

        }
    }

    private var resultCallbackForGreeting = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val cropResult = CropImage.getActivityResult(data)
            val bitmap = MediaStore.Images.Media.getBitmap(
                contentResolver, cropResult.uri
            )
            frameBtn?.isActivated = false
            mPhotoEditorView?.setFrameStatus(View.GONE)
            mPhotoEditorView?.setWebFrameStatus(View.GONE)
            mPhotoEditorView?.setGreetingImage(bitmap)
        }
    }


    fun getFilePath(uri: Uri): String? {
        var cursor: Cursor? = null
        val projection = arrayOf(
            MediaStore.MediaColumns.DATA
        )
        try {
            if (uri == null) return null
            var path: String? = null
            val proj = arrayOf(MediaStore.MediaColumns.DATA)
            val cursor = contentResolver.query(uri, proj, null, null, null)
            if (cursor!!.moveToFirst()) {
                val column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                path = cursor.getString(column_index)
            }
            cursor.close()
            return path
        } finally {
            cursor?.close()
        }
        return null
    }

    override fun onColorChanged(colorCode: Int) {
//        mPhotoEditor?.setShape(mShapeBuilder?.withShapeColor(colorCode))
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onOpacityChanged(opacity: Int) {
//        mPhotoEditor?.setShape(mShapeBuilder?.withShapeOpacity(opacity))
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onShapeSizeChanged(shapeSize: Int) {
//        mPhotoEditor?.setShape(mShapeBuilder?.withShapeSize(shapeSize.toFloat()))
        mTxtCurrentTool?.setText(R.string.label_brush)
    }

    override fun onShapePicked(shapeType: ShapeType?) {
//        mPhotoEditor?.setShape(mShapeBuilder?.withShapeType(shapeType))
    }

    override fun onEmojiClick(emojiUnicode: String?) {
        if (emojiUnicode != null) {
            mPhotoEditorView?.addEmoji(emojiUnicode)
            mTxtCurrentTool?.setText(R.string.label_emoji)
        }
    }


    override fun onStickerClick(bitmap: Bitmap?) {
        if (bitmap != null) {
            mPhotoEditorView?.addSticker(bitmap)
        }
    }

    @SuppressLint("MissingPermission")
    private fun showSaveDialog() {
        CustomeDialogFragment(getString(R.string.alert),
            getString(R.string.msg_save_image),
            DialogType.WARNING,
            true,
            true,
            true,
            object : CustomeDialogFragment.DialogCallback {
                override fun onCencel() {}
                override fun onSubmit() {

                    finish()
                }
                override fun onDismiss() {}
                override fun onComplete(dialog: Dialog?) {}
            }).show(supportFragmentManager, "")
    }

    override fun onFilterSelected(photoFilter: PhotoFilter?) {
        mPhotoEditorView?.setFilterEffect(photoFilter)
    }

    var progressBorder = 50

    override fun onBorderSelected(path: String?) {
        val fromAsset = getBitmapFromAsset(activity!!, path.toString())
        val drawable: Drawable = BitmapDrawable(resources, fromAsset)
        photoEditorView.borderView.setBackgroundDrawable(drawable)
        photoEditorView.borderView.setPadding(
            progressBorder,
            progressBorder,
            progressBorder,
            progressBorder
        )
    }

    private fun getBitmapFromAsset(context: Context, strName: String): Bitmap? {
        val assetManager = context.assets
        return try {
            val istr = assetManager.open(strName)
            BitmapFactory.decodeStream(istr)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    var isPostSubsBuy = false
    var resultCallbackForPayment = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            result -> if (result?.resultCode == Activity.RESULT_OK) {
            isPostSubsBuy = true
            mPhotoEditorView?.setPremiumStatus(GONE)
            Functions.showToast(activity, getString(R.string.payment_success))
            Toast.makeText(activity, "Payment Success", Toast.LENGTH_SHORT).show()
        }
    }

    var removeWatermarkBtn: TextView? = null
    fun onFrameSelect(model: FrameModel?) {
        if (!Functions.IsPremiumEnable(activity) && !isPostSubsBuy) {
            if (model?.premium == "1" || postModel.premium == "1") {
                var view = LayoutInflater.from(this).inflate(R.layout.item_premium_lock_lay, null)
                removeWatermarkBtn = view.findViewById(R.id.remove_watermark_tv)
//                removeWatermarkBtn?.setText(getString(R.string.buy_single_post))
                removeWatermarkBtn?.setOnClickListener {
                    val intent = Intent(
                        activity,
                        RazorpayActivity::class.java
                    )
                    intent.putExtra("price", Functions.getSharedPreference(activity).getString("single_post_subsciption_amount","10"))
                    resultCallbackForPayment.launch(intent)
                }
                mPhotoEditorView?.setPremiumView(view)
            } else {
                mPhotoEditorView?.setPremiumStatus(GONE)
            }
        }

        findViewById<LinearLayout>(R.id.frame_option_lay).visibility = VISIBLE
        if (!Functions.isNetworkConnected(activity)) {
            Functions.showToast(activity, "No internet connection !")
            return
        }
        initFrameButtonsViews()
        mPhotoEditorView?.webView?.loadUrl(Constants.BASE_URL + Constants.API_KEY + "/showframe/" + model?.id!!)
        mPhotoEditorView?.webView?.settings?.javaScriptEnabled = true
        mPhotoEditorView?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                Functions.showLoader(activity)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                Functions.cancelLoader()

                if (model?.type.equals("business")) {
                    mPhotoEditorView?.webView?.loadUrl(
                        "javascript:setLocation('" + Functions.getSharedPreference(
                            activity
                        ).getString(Variables.BUSSINESS_ADDRESS, "") + "')"
                    )

                    mPhotoEditorView?.webView?.loadUrl(
                        "javascript:setBusinessPic('" + Functions.getItemBaseUrl(
                            Functions.getSharedPreference(activity)
                                .getString(Variables.BUSSINESS_LOGO, "")
                        ) + "')"
                    )
                }else{
                    mPhotoEditorView?.webView?.loadUrl(
                        "javascript:setLocation('" + Functions.getSharedPreference(
                            activity
                        ).getString(Variables.P_PIC, "") + "')"
                    )

                    mPhotoEditorView?.webView?.loadUrl(
                        "javascript:setBusinessPic('" + Functions.getItemBaseUrl(
                            Functions.getSharedPreference(activity)
                                .getString(Variables.P_PIC, "")
                        ) + "')"
                    )
                }
            }
        }

        mPhotoEditorView?.webView?.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView?,
                url: String?,
                message: String?,
                result: JsResult?
            ): Boolean {
                result?.cancel()
                Log.d("onJsAlert__", message.toString())
                val jsonObject = JSONObject(message)
                if (jsonObject.has("checkButtons")) {
                    var buttonObject = jsonObject.getJSONObject("checkButtons")
                    if (buttonObject.getBoolean("name")) {
                        nameBtn?.visibility = VISIBLE
                        nameBtn?.isActivated = true

                        if (model?.type.equals("business")) {
                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('name','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.BUSSINESS_NAME,
                                    getString(R.string.demo_name)
                                ) + "')"
                            )
                        } else {
                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('name','" + Functions.getSharedPreference(
                                    activity
                                ).getString(Variables.NAME, getString(R.string.demo_name)) + "')"
                            )
                        }
                    } else {
                        nameBtn?.visibility = GONE
                    }

                    if (buttonObject.getBoolean("logo")) {
                        logoBtn?.visibility = VISIBLE
                        logoBtn?.isActivated = true
                        Log.d("modeltype",model?.type.toString())
                        if (model?.type.equals("business"))
                        {
                            Glide.with(activity!!)
                                .asBitmap()
                                .load(
                                    Functions.getItemBaseUrl(
                                        Functions.getSharedPreference(
                                            activity
                                        ).getString(Variables.BUSSINESS_LOGO, "")
                                    )
                                )
                                .into(object : SimpleTarget<Bitmap?>() {
                                    override fun onResourceReady(
                                        resource: Bitmap,
                                        transition: Transition<in Bitmap?>?
                                    ) {
                                        mPhotoEditorView?.webView?.loadUrl(
                                            "javascript:setBase64('logo','" + Functions.bitmapToBase64(
                                                resource
                                            ) + "')"
                                        )
                                    }
                                })
                        }
                        else
                        {
            var xl = Functions.getItemBaseUrl(Functions.getSharedPreference(activity).getString(Variables.P_PIC, ""))
             Log.d("fvnjfjfdbjfb",xl)
                            Glide.with(applicationContext).asBitmap().load(xl).into(object : CustomTarget<Bitmap?>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    @Nullable transition: Transition<in Bitmap?>?
                                ) {
                                    mPhotoEditorView?.webView?.loadUrl(
                                        "javascript:setBase64('logo','" + Functions.bitmapToBase64(
                                            resource
                                        ) + "')"
                                    )
                                }

                                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                            })

//                                .into(object : SimpleTarget<Bitmap?>() {
//                                    override fun onResourceReady(
//                                        resource: Bitmap,
//                                        transition: Transition<in Bitmap?>?
//                                    ) {
//                                        Log.d("NITINMAKWANA",resource.toString());
//                                        mPhotoEditorView?.webView?.loadUrl(
//                                            "javascript:setBase64('logo','" + Functions.bitmapToBase64(
//                                                resource
//                                            ) + "')"
//                                        )
//                                    }
//                                })
                        }

                    } else {
                        logoBtn?.visibility = GONE
                    }
                    if (buttonObject.getBoolean("number")) {
                        numberBtn?.visibility = VISIBLE
                        numberBtn?.isActivated = true

                        if (model?.type.equals("business")) {
                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('number','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.BUSSINESS_NUMBER,
                                    getString(R.string.demo_number)
                                ) + "')"
                            )
                        } else {
                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('number','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.NUMBER,
                                    getString(R.string.demo_number)
                                ) + "')"
                            )

                        }
                    } else {
                        mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('number')")
                        numberBtn?.isActivated = false
                        numberBtn?.visibility = GONE
                    }
                    if (buttonObject.getBoolean("email")) {
                        emailBtn?.visibility = VISIBLE
                        emailBtn?.isActivated = true

                        if (model?.type.equals("business")) {

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('email','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.BUSSINESS_EMAIL,
                                    getString(R.string.demo_email)
                                ) + "')"
                            )

                        } else {

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('email','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.U_EMAIL,
                                    getString(R.string.demo_email)
                                ) + "')"
                            )

                        }
                    } else {
                        emailBtn?.visibility = GONE
                        mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('email')")
                        emailBtn?.isActivated = false
                    }
                    if (model?.type.equals("business")) {
                        if (buttonObject.getBoolean("website")) {
                            webBtn?.visibility = VISIBLE
                            webBtn?.isActivated = true

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('website','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.WEBSITE,
                                    getString(R.string.demo_website)
                                ) + "')"
                            )
                        } else {
                            webBtn?.visibility = GONE
                            mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('website')")
                            webBtn?.isActivated = false
                        }
                    }else{
                        if (buttonObject.getBoolean("website")) {
                            webBtn?.visibility = VISIBLE
                            webBtn?.isActivated = true

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('website','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.WEBSITEP,
                                    getString(R.string.demo_website)
                                ) + "')"
                            )
                        } else {
                            webBtn?.visibility = GONE
                            mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('website')")
                            webBtn?.isActivated = false
                        }
//                        webBtn?.visibility = GONE
//                        mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('website')")
//                        webBtn?.isActivated = false
                    }
                    if (model?.type.equals("business")) {
                        if (buttonObject.getBoolean("address")) {
                            locationBtn?.visibility = VISIBLE
                            locationBtn?.isActivated = true

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('address','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.BUSSINESS_ADDRESS,
                                    getString(R.string.demo_address)
                                ) + "')"
                            )
                        } else {
                            locationBtn?.visibility = GONE
                            mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('address')")
                            locationBtn?.isActivated = false
                        }
                    }else{
                        if (buttonObject.getBoolean("address")) {
                            locationBtn?.visibility = VISIBLE
                            locationBtn?.isActivated = true

                            mPhotoEditorView?.webView?.loadUrl(
                                "javascript:changeValue('address','" + Functions.getSharedPreference(
                                    activity
                                ).getString(
                                    Variables.ADDRESSP,
                                    getString(R.string.demo_address)
                                ) + "')"
                            )

                        } else {
                            locationBtn?.visibility = GONE
                            mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('address')")
                            locationBtn?.isActivated = false
                        }
//                        locationBtn?.visibility = GONE
//                        mPhotoEditorView?.webView?.loadUrl("javascript:invisibleItem('address')")
//                        locationBtn?.isActivated = false
                    }
                    if (buttonObject.getBoolean("whatsapp")) {
                        whatsappBtn?.visibility = VISIBLE
                        whatsappBtn?.isActivated = true

                        mPhotoEditorView?.webView?.loadUrl(
                            "javascript:changeValue('whatsapp','" + Functions.getSharedPreference(
                                activity
                            ).getString(Variables.WHATSAPP, getString(R.string.demo_number)) + "')"
                        )
                    } else {
                        whatsappBtn?.visibility = GONE
                    }
                    if (buttonObject.getBoolean("youtube")) {
                        youtubeBtn?.visibility = VISIBLE
                        youtubeBtn?.isActivated = true

                        mPhotoEditorView?.webView?.loadUrl(
                            "javascript:changeValue('youtube','" + Functions.getSharedPreference(
                                activity
                            ).getString(Variables.YOUTUBE, getString(R.string.youtube)) + "')"
                        )
                    } else {
                        youtubeBtn?.visibility = GONE
                    }
                    if (buttonObject.getBoolean("facebook")) {
                        facebookBtn?.visibility = VISIBLE
                        facebookBtn?.isActivated = true

                        mPhotoEditorView?.webView?.loadUrl(
                            "javascript:changeValue('facebook','" + Functions.getSharedPreference(
                                activity
                            ).getString(Variables.FACEBOOK, getString(R.string.facebook)) + "')"
                        )
                    } else {
                        facebookBtn?.visibility = GONE
                    }
                    if (buttonObject.getBoolean("instagram")) {
                        instaBtn?.visibility = VISIBLE
                        instaBtn?.isActivated = true

                        mPhotoEditorView?.webView?.loadUrl(
                            "javascript:changeValue('instagram','" + Functions.getSharedPreference(
                                activity
                            ).getString(Variables.INSTAGRAM, getString(R.string.demo_insta)) + "')"
                        )

                    } else {
                        instaBtn?.visibility = GONE
                    }
                } else if (jsonObject.has("name")) {
//                    showFramItemChangeDialog("name", jsonObject.optString("name"))
                } else if (jsonObject.has("number")) {
//                    showFramItemChangeDialog("number", jsonObject.optString("number"))
                } else if (jsonObject.has("email")) {
//                    showFramItemChangeDialog("email", jsonObject.optString("email"))
                } else if (jsonObject.has("address")) {
//                    showFramItemChangeDialog("address", jsonObject.optString("address"))
                } else if (jsonObject.has("website")) {
//                    showFramItemChangeDialog("website", jsonObject.optString("website"))
                } else if (jsonObject.has("facebook")) {
                    showFramItemChangeDialog("facebook", jsonObject.optString("facebook"))
                } else if (jsonObject.has("whatsapp")) {
                    showFramItemChangeDialog("whatsapp", jsonObject.optString("whatsapp"))
                } else if (jsonObject.has("instagram")) {
                    showFramItemChangeDialog("instagram", jsonObject.optString("instagram"))
                } else if (jsonObject.has("youtube")) {
                    showFramItemChangeDialog("youtube", jsonObject.optString("youtube"))
                } else if (jsonObject.has("headline1")) {
                    showFramItemChangeDialog("headline1", jsonObject.optString("headline1"))
                } else if (jsonObject.has("headline2")) {
                    showFramItemChangeDialog("headline2", jsonObject.optString("headline2"))
                } else if (jsonObject.has("image")) {
                    showPickImageDialog(jsonObject.optString("image"))
                }
                return true
            }
        }
    }

    var fm: FragmentManager = supportFragmentManager
    private fun showPremiumFragment() {
        val comment_f = PremiumFragment()
        val transaction = fm.beginTransaction()
        transaction.setCustomAnimations(
            R.anim.in_from_bottom,
            R.anim.out_to_top,
            R.anim.in_from_top,
            R.anim.out_from_bottom
        )
        val args = Bundle()
        args.putString("from", "preview")
        comment_f.arguments = args
        transaction.addToBackStack(null)
        transaction.replace(android.R.id.content, comment_f).commit()
    }

    var imageFilePath: String? = null
    override fun onToolSelected(toolType: ToolType?) {
        findViewById<LinearLayout>(R.id.border_recycler_lay).visibility = View.GONE
        mIsBorderVisible = false
        when (toolType) {
            ToolType.SHAPE -> {
//                mPhotoEditor?.setBrushDrawingMode(true)
//                mShapeBuilder = ShapeBuilder()
//                mPhotoEditor?.setShape(mShapeBuilder)
                mTxtCurrentTool?.setText(R.string.label_shape)
                showBottomSheetDialogFragment(mShapeBSFragment)
            }
            ToolType.TEXT -> {
                val textEditorDialogFragment = TextEditorDialogFragment.show(this)
                textEditorDialogFragment.setOnTextEditorListener(object :
                    TextEditorDialogFragment.TextEditorListener {
                    override fun onDone(inputText: String?, colorCode: Int, fontname: String) {
                        if (inputText != null) {

                            mPhotoEditorView?.addText(
                                inputText,
                                colorCode,
                                Typeface.createFromAsset(assets, fontname)
                            )
                            mTxtCurrentTool?.setText(R.string.label_text)

                        }
                    }
                })
            }
            ToolType.ADD_PHOTO -> {
                PickFromFragment(
                    getString(R.string.take_photo),
                    object : PickFromFragment.DialogCallback {
                        override fun onCencel() {}
                        override fun onGallery() {
                            if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                                val intent = Intent()
                                intent.type = "image/*"
                                intent.action = Intent.ACTION_GET_CONTENT
                                startActivityForResult(
                                    Intent.createChooser(intent, "Select Picture"),
                                    PICK_REQUEST
                                )
                            }else{
                                takePermissionUtils!!.takeStorageCameraPermission()
                            }
                        }

                        override fun onCamera() {
                            if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                                val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                                if (pictureIntent.resolveActivity(packageManager) != null) {
                                    var photoFile: File? = null
                                    try {
                                        photoFile = Functions.createImageFile(applicationContext)
                                        imageFilePath = photoFile.absolutePath
                                    } catch (ex: java.lang.Exception) {
                                    }
                                    if (photoFile != null) {
                                        val photoURI = FileProvider.getUriForFile(
                                            applicationContext, packageName + ".fileprovider",
                                            photoFile
                                        )
                                        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                                    }
                                }
                                startActivityForResult(pictureIntent, CAMERA_REQUEST)
                            }else{
                                takePermissionUtils!!.takeStorageCameraPermission()
                            }
                        }
                    }).show(supportFragmentManager, "")
            }
            ToolType.ERASER -> {
//                mPhotoEditor?.brushEraser()
                mTxtCurrentTool?.setText(R.string.label_eraser_mode)
            }
            ToolType.FILTER -> {
                mTxtCurrentTool?.setText(R.string.label_filter)
                showFilter(true)
            }
            ToolType.BORDER -> {
                rvideoItems?.visibility = View.GONE
                mIsBorderVisible = true
                findViewById<LinearLayout>(R.id.border_recycler_lay).visibility = View.VISIBLE
                var progressBorder = 50
                val fromAsset = getBitmapFromAsset(activity!!, "border/1.png")
                val drawable: Drawable = BitmapDrawable(resources, fromAsset)
                photoEditorView.borderView.setBackgroundDrawable(drawable)
                photoEditorView.borderView.setPadding(
                    progressBorder,
                    progressBorder,
                    progressBorder,
                    progressBorder
                )
            }
            ToolType.FRAME -> {

            }
            ToolType.DRAW -> {
//                mPhotoEditor?.setBrushDrawingMode(true)
//                mShapeBuilder = ShapeBuilder()
//                mPhotoEditor?.setShape(mShapeBuilder)
                mTxtCurrentTool?.setText(R.string.label_draw)
                showBottomSheetDialogFragment(mDrawBSFragment)
            }
            ToolType.EMOJI -> showBottomSheetDialogFragment(mEmojiBSFragment)
            ToolType.STICKER -> showBottomSheetDialogFragment(mStickerBSFragment)
            ToolType.VIDEO_FILTER -> {
                findViewById<LinearLayout>(R.id.border_recycler_lay).visibility = View.GONE
                mIsBorderVisible = false
                if (imgSave.isActivated) {
                    setVideoFilterAdapter()
                }
            }
            ToolType.VIDEO_TRANSFER -> {
                findViewById<LinearLayout>(R.id.border_recycler_lay).visibility = View.GONE
                mIsBorderVisible = false
                if (imgSave.isActivated) {
                    setVideoAnimationAdapter()
                }
            }

            ToolType.MUSIC -> {
                if (imgSave.isActivated) {
                    if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                        val i = Intent()
                        i.type = "audio/*"
                        i.action = Intent.ACTION_GET_CONTENT
                        startActivityForResult(
                            i,
                            REQUEST_MUSIC
                        )
                    } else {
                        takePermissionUtils!!.showStorageCameraPermissionDailog(getString(R.string.we_need_storage_and_camera_permission_for_upload_profile_pic))
                    }
                }
            }
            else -> {}
        }
    }

    private fun setVideoAnimationAdapter() {
        rvideoItems?.visibility = View.VISIBLE
        rvideoItems?.adapter =
            AnimationAdapter(activity, mPhotoEditorView?.getAnimations()) { item ->
                mPhotoEditorView?.setVideoAnimation(item)
            }
    }

    private fun setVideoFilterAdapter() {
        rvideoItems?.visibility = View.VISIBLE
        rvideoItems?.adapter = FilterAdapter(activity) { item ->

            mPhotoEditorView?.setFilterToVideo(item)
        }
    }

    private fun showBottomSheetDialogFragment(fragment: BottomSheetDialogFragment?) {
        if (fragment == null || fragment.isAdded) {
            return
        }
        fragment.show(supportFragmentManager, fragment.tag)
    }

    private fun showFilter(isVisible: Boolean) {
        mIsFilterVisible = isVisible
        mConstraintSet.clone(mRootView)
        val rvFilterId: Int =
            mRvFilters?.id ?: throw IllegalArgumentException("RV Filter ID Expected")
        if (isVisible) {
            mConstraintSet.clear(rvFilterId, ConstraintSet.START)
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.START
            )
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.END,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
        } else {
            mConstraintSet.connect(
                rvFilterId, ConstraintSet.START,
                ConstraintSet.PARENT_ID, ConstraintSet.END
            )
            mConstraintSet.clear(rvFilterId, ConstraintSet.END)
        }
        val changeBounds = ChangeBounds()
        changeBounds.duration = 350
        changeBounds.interpolator = AnticipateOvershootInterpolator(1.0f)
        mRootView?.let { TransitionManager.beginDelayedTransition(it, changeBounds) }
        mConstraintSet.applyTo(mRootView)
    }

    override fun onBackPressed() {
        if (fm.backStackEntryCount > 0) {
            fm.popBackStack()
        }
        else if (rvideoItems?.visibility == VISIBLE) {
            rvideoItems?.visibility = GONE
        } else if (mIsFilterVisible) {
            showFilter(false)
            mTxtCurrentTool?.setText(R.string.app_name)
        } else if (mIsBorderVisible) {
            findViewById<LinearLayout>(R.id.border_recycler_lay).visibility = View.GONE
            mIsBorderVisible = false
        } else {
            showSaveDialog()
        }
    }


    private fun compressBitmap(bitmap: Bitmap, quality: Int): Bitmap {
        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()

        // Compress the bitmap with JPEG format and specified quality
        bitmap.compress(
            Bitmap.CompressFormat.PNG, quality, stream
        )

        val byteArray = stream.toByteArray()

        // Finally, return the compressed bitmap
        return BitmapFactory.decodeByteArray(
            byteArray, 0, byteArray.size
        )
    }

    private fun showFramItemChangeDialog(id: String, value: String) {
        FramItemChangeDialogFragment(value,
            Callback { text: String ->
                Log.d("onJsAlert__", id + "," + value)
                mPhotoEditorView?.webView?.loadUrl("javascript:changeValue('" + id + "','" + text + "')")
            }).show(supportFragmentManager, "")
    }

    var imageFilePath2: String? = null
    var imagewebID: String? = null
    private fun showPickImageDialog(id: String) {
        imagewebID = id
        PickFromFragment(
            getString(R.string.take_photo),
            object : PickFromFragment.DialogCallback {
                override fun onCencel() {}
                override fun onGallery() {
                    if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                        val intent = Intent()
                        intent.type = "image/*"
                        intent.action = Intent.ACTION_GET_CONTENT
                        resultCallbackForGallery.launch(intent)
                    }else{
                        takePermissionUtils!!.takeStorageCameraPermission()
                    }
                }

                override fun onCamera() {
                    if (takePermissionUtils!!.isStorageCameraPermissionGranted) {
                        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        if (pictureIntent.resolveActivity(packageManager) != null) {
                            var photoFile: File? = null
                            try {
                                photoFile = Functions.createImageFile(applicationContext)
                                imageFilePath2 = photoFile.absolutePath
                            } catch (ex: java.lang.Exception) {
                            }
                            if (photoFile != null) {
                                val photoURI = FileProvider.getUriForFile(
                                    applicationContext,
                                    packageName + ".fileprovider",
                                    photoFile
                                )
                                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                            }
                        }
                        resultCallbackForCamera.launch(pictureIntent)
                    }else{
                        takePermissionUtils!!.takeStorageCameraPermission()
                    }
                }
            }).show(supportFragmentManager, "")
    }

    var resultCallbackForGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val selectedImage = data!!.data
            val intent = CropImage.activity(selectedImage).setAspectRatio(1, 1).getIntent(this)
            resultCallbackForCrop2.launch(intent)
        }
    }

    private var resultCallbackForCrop2 = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            val cropResult = CropImage.getActivityResult(data)
            val bitmap = MediaStore.Images.Media.getBitmap(
                contentResolver, cropResult.uri
            )

            PickedImageActionFragment(bitmap) { bitmap ->
                mPhotoEditorView?.webView?.loadUrl(
                    "javascript:setBase64('" + imagewebID + "','" + Functions.bitmapToBase64(
                        bitmap
                    ) + "')"
                )
            }.show(supportFragmentManager, "")

        }
    }

    private val resultCallbackForCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val matrix = Matrix()
            try {
                val exif = ExifInterface(imageFilePath2!!)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    1
                )
                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(
                        90f
                    )
                    ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(
                        180f
                    )
                    ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(
                        270f
                    )
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
            val selectedImage = Uri.fromFile(File(imageFilePath2))
            val intent =
                CropImage.activity(selectedImage).setCropShape(CropImageView.CropShape.OVAL)
                    .setAspectRatio(1, 1).getIntent(this)
            resultCallbackForCrop2.launch(intent)
        }
    }

    class CreateFinalVideo(ctx: Activity, editType: String) : AsyncTask<Void, Void, String>() {

        var activity = ctx
        var editTypex = editType
        override fun doInBackground(vararg params: Void?): String? {
            OUT_VIDEO_PATH = Functions.getAppFolder(activity) + System.currentTimeMillis() + ".mp4"
            if (!File(Variables.APP_HIDED_FOLDER).exists()) {
                File(Variables.APP_HIDED_FOLDER).mkdir()
            }

            val retriever = MediaMetadataRetriever()
            retriever.setDataSource(VIDEO_PATH)
            val width: Int = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH))
            val height: Int = Integer.valueOf(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT))
            retriever.release()

            val b = BitmapFactory.decodeFile(FRAME_PATH)
//            val out = Bitmap.createScaledBitmap(b, width, height, false)

            val file = File(Functions.getAppFolder(activity), "resizeframe.png")
            val fOut: FileOutputStream
            try {
                fOut = FileOutputStream(file)
                b.compress(Bitmap.CompressFormat.PNG, 100, fOut)
//                out.compress(Bitmap.CompressFormat.PNG, 100, fOut)
                fOut.flush()
                fOut.close()
                b.recycle()
//              out.recycle()
            } catch (e: java.lang.Exception) {}

            Log.d("khvhsvhsbvs",FINAL_AUDIO_PATH);


            if(editTypex == "PhotoVideo"){

                val inputCode1  =  arrayOf(
                    "-i",
                    VIDEO_PATH,
                    "-i",
                    FRAME_PATH,
                    "-i",
                    FINAL_AUDIO_PATH,
                    "-filter_complex",
                    "[0]scale=1200:1200:force_original_aspect_ratio=decrease,pad=1200:1200:(ow-iw)/2:(oh-ih)/2,setsar=1[backd],[1]scale=1200X1200[scaled_image],[backd][scaled_image]overlay",
                    "-shortest",
                    OUT_VIDEO_PATH
                )

                val rc = FFmpeg.execute(inputCode1)
                if (rc == Config.RETURN_CODE_SUCCESS) {
                    file.delete()
                    File(FRAME_PATH).delete()
                    File(VIDEO_PATH).delete()
                    Log.i(Config.TAG, "Command execution completed successfully.")
                }
                else if (rc == Config.RETURN_CODE_CANCEL) {
                    Log.i(Config.TAG, "Command execution cancelled by user.")
                }
                else {
                    Log.i(
                        Config.TAG, String.format(
                            "Command execution failed with rc=%d and the output below.",
                            rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }

            }else{

                val inputCode1  =  arrayOf(
                    "-i",
                    VIDEO_PATH,
                    "-i",
                    FRAME_PATH,
                    "-filter_complex",
                    "[0]scale=1200:1200:force_original_aspect_ratio=decrease,pad=1200:1200:(ow-iw)/2:(oh-ih)/2,setsar=1[backd],[1]scale=1200X1200[scaled_image],[backd][scaled_image]overlay",
                    OUT_VIDEO_PATH
                )

                val rc = FFmpeg.execute(inputCode1)
                if (rc == Config.RETURN_CODE_SUCCESS) {
                    file.delete()
                    File(FRAME_PATH).delete()
                    File(VIDEO_PATH).delete()
                    Log.i(Config.TAG, "Command execution completed successfully.")
                }
                else if (rc == Config.RETURN_CODE_CANCEL) {
                    Log.i(Config.TAG, "Command execution cancelled by user.")
                }
                else {
                    Log.i(
                        Config.TAG, String.format(
                            "Command execution failed with rc=%d and the output below.",
                            rc
                        )
                    )
                    Config.printLastCommandOutput(Log.INFO)
                }

            }


//           val inputCode1 = arrayOf(
//                "-i",
//                VIDEO_PATH,
//                "-i",  /* "/storage/emulated/0/kiki/demopic.png",*/
//                file.absolutePath,
//                "-filter_complex",
//                "overlay=(W-w):(H-h)",
//                "-codec:a",
//                "copy",
//                OUT_VIDEO_PATH
//            )

            return null
        }

        override fun onPreExecute() {
            super.onPreExecute()
            Functions.showLoader(activity)
            Functions.dialog.findViewById<View>(R.id.tv_lay).visibility = VISIBLE
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            Functions.dialog.findViewById<View>(R.id.tv_lay).visibility = GONE
            Functions.cancelLoader()
            activity.startActivity(
                Intent(activity, PreviewActivity::class.java).putExtra(
                    "url", OUT_VIDEO_PATH
                )
            )
            activity.finish()
        }
    }


    companion object {

        private const val CAMERA_REQUEST = 52
        private const val CAMERA_REQUEST_CUSTOME = 42
        private const val PICK_REQUEST = 53
        private const val PICK_REQUEST_CUSTOME = 43
        private const val PICK_REQUEST_VIDEO = 63
        private const val REQUEST_MUSIC = 90
        const val ERASER_REQUEST = 54
        var VIDEO_TO_PHOTO = false
        var VIDEO_PATH = ""
        var FINAL_VIDEO_PATH = ""
        var FINAL_AUDIO_PATH = ""
        var FRAME_PATH = ""
        var OUT_VIDEO_PATH = ""

        private const val PICK_REQUEST_GREETING = 32
        private const val CAMERA_REQUEST_GREETING = 33
        lateinit var postModel: PostsModel
    }
}
