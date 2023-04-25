package com.sendpost.dreamsoft.ImageEditor

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sendpost.dreamsoft.R

/**
 * Created by Ahmed Adel on 5/8/17.
 */
class FontPickerAdapter internal constructor(
    private var context: Context,
    fontnames: List<String>
) : RecyclerView.Adapter<FontPickerAdapter.ViewHolder>() {
    private var inflater: LayoutInflater
    private val fontnames: List<String>
    private var onfontselect: OnFontSelect? = null

    internal constructor(context: Context) : this(context, getDefaultColors(context)) {
        this.context = context
        inflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.font_picker_item_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textview.typeface = Typeface.createFromAsset(context.assets, fontnames[position])
        if (fontnames[position].contains("hindi"))
            holder.textview.text = "हिन्दी फॉन्ट"
        else
            holder.textview.text = "ABCD"
    }

    override fun getItemCount(): Int {
        return fontnames.size
    }

    fun setOnFontClickListener(onfontselect: OnFontSelect?) {
        this.onfontselect = onfontselect
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textview: TextView = itemView.findViewById(R.id.tv)

        init {
            itemView.setOnClickListener {
                if (onfontselect != null) onfontselect!!.onFontSelect(
                    fontnames[adapterPosition]
                )
            }
        }
    }

    interface OnFontSelect {
        fun onFontSelect(name: String)
    }

    companion object {
        fun getDefaultColors(context: Context): List<String> {
            val fontnames = ArrayList<String>()

//            fontnames.add( "hindi_.ttf")
//            fontnames.add( "hindi2.ttf")
            fontnames.add( "DroidSans.ttf")
            fontnames.add( "majalla.ttf")
            fontnames.add( "MVBOLI.TTF")
            fontnames.add( "PortLligatSans-Regular.ttf")
            fontnames.add( "ROD.TTF")
            fontnames.add( "Aspergit.otf")
            fontnames.add( "windsong.ttf")
            fontnames.add( "Walkway_Bold.ttf")
            fontnames.add( "Sofia-Regular.otf")
            fontnames.add( "segoe.ttf")
            fontnames.add( "Capture_it.ttf")
            fontnames.add( "Advertising Script Bold Trial.ttf")
            fontnames.add( "Advertising Script Monoline Trial.ttf")
            fontnames.add( "Beyond Wonderland.ttf")
            fontnames.add( "CalliGravity.ttf")
            fontnames.add( "Cosmic Love.ttf")
            fontnames.add( "lesser concern shadow.ttf")
            fontnames.add( "lesser concern.ttf")
            fontnames.add( "Queen of Heaven.ttf")
            fontnames.add( "QUIGLEYW.TTF")
            fontnames.add( "squealer embossed.ttf")
            fontnames.add( "squealer.ttf")
            fontnames.add( "still time.ttf")
            fontnames.add( "Constantia Italic.ttf")
            fontnames.add( "DejaVuSans_Bold.ttf")
            fontnames.add( "Aladin_Regular.ttf")
            fontnames.add( "Adobe Caslon Pro Italic.ttf")
            fontnames.add( "aparaji.ttf")
            fontnames.add( "ARDECODE.ttf")
            fontnames.add( "ufonts_com_ck_scratchy_box.ttf")

            return fontnames
        }
    }

    init {
        inflater = LayoutInflater.from(context)
        this.fontnames = fontnames
    }
}