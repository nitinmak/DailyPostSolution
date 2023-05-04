package com.sendpost.dreamsoft.ImageEditor.Stickers

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Bitmap
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.sendpost.dreamsoft.Classes.Functions
import com.sendpost.dreamsoft.R
import com.sendpost.dreamsoft.viewmodel.FrameViewModel
import java.util.Objects

class StickerBSFragment : BottomSheetDialogFragment() {

    private var mStickerListener: StickerListener? = null
    var list : ArrayList<StickerModelCategory> = ArrayList()

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

    var framViewModel: FrameViewModel ? = null

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
        framViewModel = ViewModelProvider(this)[FrameViewModel::class.java]
        framViewModel!!.stickers.observe(this)
        { response -> Functions.cancelLoader()
            if (response != null) {
                list.addAll(response.stickercategory)
                setupAdapter(contentView)
            }
        }
    }

    private fun setupAdapter(contentView: View) {
        val pager: ViewPager = contentView.findViewById(R.id.viewPager)
        val tabLayout: SmartTabLayout = contentView.findViewById(R.id.tabLayout)



        pager.adapter = StickerPagerAdapter(childFragmentManager, list, object : StickerListener {

                    override fun onStickerClick(bitmap: Bitmap?) {

                        mStickerListener!!.onStickerClick(bitmap)
                        dismiss()

                    }
                }
            )
        tabLayout.setViewPager(pager)
    }

}