package com.sendpost.dreamsoft.ImageEditor.Stickers

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sendpost.dreamsoft.R

class StickerBSFragment : BottomSheetDialogFragment() {

    private var mStickerListener: StickerListener? = null
    var list : ArrayList<com.sendpost.dreamsoft.ImageEditor.Stickers.StickerModelCategory> = ArrayList()

    fun setStickerListener(stickerListener: StickerListener?) {
        mStickerListener = stickerListener
    }

    interface StickerListener {
        fun onStickerClick(bitmap: Bitmap?)
    }

    private val mBottomSheetBehaviorCallback: BottomSheetCallback = object : BottomSheetCallback() {

        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss()
            }
        }
        override fun onSlide(bottomSheet: View, slideOffset: Float) {}
    }

    var framViewModel: com.sendpost.dreamsoft.viewmodel.FrameViewModel? = null
    @SuppressLint("RestrictedApi")
    override fun setupDialog(dialog: Dialog, style: Int) {
        super.setupDialog(dialog, style)
        val contentView = View.inflate(context, R.layout.fragment_bottom_sticker_dialog, null)
        dialog.setContentView(contentView)
        val params = (contentView.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior
        if (behavior != null && behavior is BottomSheetBehavior<*>) {
            behavior.setBottomSheetCallback(mBottomSheetBehaviorCallback)
        }
        list.clear()
        framViewModel = ViewModelProvider(this).get(com.sendpost.dreamsoft.viewmodel.FrameViewModel::class.java)
        framViewModel!!.getStickers().observe(this, object : Observer<com.sendpost.dreamsoft.responses.FrameResponse?> {
            override fun onChanged(response: com.sendpost.dreamsoft.responses.FrameResponse?) {
                com.sendpost.dreamsoft.Classes.Functions.cancelLoader()
                if (response != null){
                    list.addAll(response.stickercategory)
                    setupAdapter(contentView)
                }
            }
        })
    }

    private fun setupAdapter(contentView: View) {
        val pager: ViewPager = contentView.findViewById(R.id.viewPager)
        val tabLayout: SmartTabLayout = contentView.findViewById(R.id.tabLayout)
        pager.adapter =
            StickerPagerAdapter(
                childFragmentManager,
                list,
                object : StickerListener {
                    override fun onStickerClick(bitmap: Bitmap?) {
                        mStickerListener!!.onStickerClick(bitmap)
                        dismiss()
                    }
                }
            )
        tabLayout.setViewPager(pager)
    }

}