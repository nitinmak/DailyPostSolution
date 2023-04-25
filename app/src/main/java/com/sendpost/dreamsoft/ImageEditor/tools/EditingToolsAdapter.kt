package com.sendpost.dreamsoft.ImageEditor.tools

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sendpost.dreamsoft.ImageEditor.EditImageActivity.Companion.VIDEO_TO_PHOTO
import com.sendpost.dreamsoft.R
import java.util.ArrayList

/**
 * @author [Burhanuddin Rashid](https://github.com/burhanrashid52)
 * @version 0.1.2
 * @since 5/23/2018
 */
class EditingToolsAdapter(context :Context,val mOnItemSelected: OnItemSelected) :
    RecyclerView.Adapter<EditingToolsAdapter.ViewHolder>() {
    private val mToolList: MutableList<ToolModel> = ArrayList()

    interface OnItemSelected {
        fun onToolSelected(toolType: ToolType?)
    }

    internal inner class ToolModel(
        val mToolName: String,
        val mToolIcon: Int,
        val mToolType: ToolType
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_editing_tools, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mToolList[position]
        holder.txtTool.text = item.mToolName
        holder.imgToolIcon.setImageResource(item.mToolIcon)
    }

    override fun getItemCount(): Int {
        return mToolList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgToolIcon: ImageView = itemView.findViewById(R.id.imgToolIcon)
        val txtTool: TextView = itemView.findViewById(R.id.txtTool)

        init {
            itemView.setOnClickListener { _: View? ->
                mOnItemSelected.onToolSelected(
                    mToolList[layoutPosition].mToolType
                )
            }
        }
    }

    init {
        if (VIDEO_TO_PHOTO){
            mToolList.add(ToolModel(context.getString(R.string.label_filter), R.drawable.ic_movie_filter, ToolType.VIDEO_FILTER))
            mToolList.add(ToolModel(context.getString(R.string.label_animation), R.drawable.ic_movie_transfer, ToolType.VIDEO_TRANSFER))
            mToolList.add(ToolModel(context.getString(R.string.label_music), R.drawable.ic_movie_music, ToolType.MUSIC))
        }
        mToolList.add(ToolModel(context.getString(R.string.label_frame), R.drawable.ic_frame, ToolType.FRAME))
        mToolList.add(ToolModel(context.getString(R.string.label_border), R.drawable.ic_border, ToolType.BORDER))
        mToolList.add(ToolModel(context.getString(R.string.label_text), R.drawable.ic_text, ToolType.TEXT))
        mToolList.add(ToolModel(context.getString(R.string.add_photo_), R.drawable.ic_gallery, ToolType.ADD_PHOTO))
        mToolList.add(ToolModel(context.getString(R.string.label_sticker), R.drawable.ic_sticker, ToolType.STICKER))
        mToolList.add(ToolModel(context.getString(R.string.label_emoji), R.drawable.ic_insert_emoticon, ToolType.EMOJI))
        if (!VIDEO_TO_PHOTO){
            mToolList.add(ToolModel(context.getString(R.string.label_filter), R.drawable.ic_filter, ToolType.FILTER))
        }
//        mToolList.add(ToolModel(context.getString(R.string.label_shape), R.drawable.ic_shapes, ToolType.SHAPE))
//        mToolList.add(ToolModel(context.getString(R.string.label_draw), R.drawable.ic_brush, ToolType.DRAW))
//        mToolList.add(ToolModel(context.getString(R.string.label_eraser), R.drawable.ic_eraser, ToolType.ERASER))
    }
}