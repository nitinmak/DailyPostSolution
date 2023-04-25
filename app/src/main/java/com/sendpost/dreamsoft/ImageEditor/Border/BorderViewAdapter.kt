package com.sendpost.dreamsoft.ImageEditor.Border

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.sendpost.dreamsoft.R
import java.io.IOException
import java.util.ArrayList

/**
 * @author [Burhanuddin Rashid](https://github.com/burhanrashid52)
 * @version 0.1.2
 * @since 5/23/2018
 */

class BorderViewAdapter(private val mBorderListner: BorderListener) : RecyclerView.Adapter<BorderViewAdapter.ViewHolder>() {
    private val mPairList: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_border_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fromAsset = getBitmapFromAsset(holder.itemView.context,  mPairList[position])
        holder.mImageFilterView.setImageBitmap(fromAsset)
    }

    override fun getItemCount(): Int {
        return mPairList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mImageFilterView: ImageView = itemView.findViewById(R.id.imgBorder)

        init {
            itemView.setOnClickListener{
                mBorderListner.onBorderSelected(mPairList[position])
            }
        }
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

    private fun setupFilters() {
        mPairList.add("border/1.png")
        mPairList.add("border/2.png")
        mPairList.add("border/3.png")
        mPairList.add("border/4.png")
        mPairList.add("border/5.png")
        mPairList.add("border/6.png")
        mPairList.add("border/7.png")
        mPairList.add("border/8.png")
        mPairList.add("border/9.png")
        mPairList.add("border/10.png")
        mPairList.add("border/11.png")
        mPairList.add("border/12.png")
        mPairList.add("border/13.png")
        mPairList.add("border/14.png")
//        mPairList.add("border/border_1.jpg")
//        mPairList.add("border/border_2.jpg")
//        mPairList.add("border/border_3.jpg")
//        mPairList.add("border/border_4.jpg")
//        mPairList.add("border/border_5.jpg")
//        mPairList.add("border/border_6.jpg")
//        mPairList.add("border/border_7.jpg")
    }

    init {
        setupFilters()
    }
}