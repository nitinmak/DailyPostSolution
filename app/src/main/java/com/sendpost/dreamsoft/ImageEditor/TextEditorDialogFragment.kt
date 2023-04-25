package com.sendpost.dreamsoft.ImageEditor

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sendpost.dreamsoft.R

/**
 * Created by Burhanuddin Rashid on 1/16/2018.
 */
class TextEditorDialogFragment : BottomSheetDialogFragment() {

    private var mAddTextEditText: EditText? = null
    private var mAddTextDoneTextView: TextView? = null
    private var mInputMethodManager: InputMethodManager? = null
    private var mColorCode = 0
    private var mTextEditorListener: TextEditorListener? = null
    private var fontname : String = "OpenSans_Semibold.ttf"

    interface TextEditorListener {
        fun onDone(inputText: String?, colorCode: Int,fontname : String)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener { dialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(bottomSheetDialog)
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
        val behavior: BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        // Calculate window height for fullscreen use
        val displayMetrics = DisplayMetrics()
        (context as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_text_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mAddTextEditText = view.findViewById(R.id.add_text_edit_text)
        mInputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        mAddTextDoneTextView = view.findViewById(R.id.add_text_done_tv)

        //Setup the color picker for text color
        val addTextColorPickerRecyclerView: RecyclerView = view.findViewById(R.id.add_text_color_picker_recycler_view)

        val layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        addTextColorPickerRecyclerView.layoutManager = layoutManager
        addTextColorPickerRecyclerView.setHasFixedSize(true)
        val colorPickerAdapter = ColorPickerAdapter(requireActivity())

        val addTextFontPickerRecyclerView: RecyclerView = view.findViewById(R.id.add_text_font_picker_recycler_view)
        val fontPickerAdapter = FontPickerAdapter(requireActivity())
        addTextFontPickerRecyclerView.adapter = fontPickerAdapter
        fontPickerAdapter.setOnFontClickListener(object : FontPickerAdapter.OnFontSelect{
            override fun onFontSelect(name: String) {
                mAddTextEditText!!.typeface = Typeface.createFromAsset(requireActivity().assets, name)
                fontname = name;
            }
        })

        //This listener will change the text color when clicked on any color from picker
        colorPickerAdapter.setOnColorPickerClickListener(object :
            ColorPickerAdapter.OnColorPickerClickListener {
            override fun onColorPickerClickListener(colorCode: Int) {
                mColorCode = colorCode
                mAddTextEditText!!.setTextColor(colorCode)
            }
        })
        addTextColorPickerRecyclerView.adapter = colorPickerAdapter
        mAddTextEditText!!.setText(requireArguments().getString(EXTRA_INPUT_TEXT))
        mColorCode = requireArguments().getInt(EXTRA_COLOR_CODE)
        mAddTextEditText!!.setTextColor(mColorCode)
        mInputMethodManager!!.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

        //Make a callback on activity when user is done with text editing
        mAddTextDoneTextView!!.setOnClickListener { onClickListenerView ->
            mInputMethodManager!!.hideSoftInputFromWindow(onClickListenerView.windowToken, 0)
            dismiss()
            val inputText = mAddTextEditText!!.text.toString()
            if (!TextUtils.isEmpty(inputText) && mTextEditorListener != null) {
                mTextEditorListener!!.onDone(inputText, mColorCode,fontname)
            }
        }
    }

    //Callback to listener if user is done with text editing
    fun setOnTextEditorListener(textEditorListener: TextEditorListener) {
        mTextEditorListener = textEditorListener
    }

    companion object {
        private val TAG: String = TextEditorDialogFragment::class.java.simpleName
        const val EXTRA_INPUT_TEXT = "extra_input_text"
        const val EXTRA_COLOR_CODE = "extra_color_code"

        //Show dialog with provide text and text color
        //Show dialog with default text input as empty and text color white
        @JvmOverloads
        fun show(
            appCompatActivity: AppCompatActivity,
            inputText: String = "",
            @ColorInt colorCode: Int = ContextCompat.getColor(appCompatActivity, R.color.texteditColor)
        ): TextEditorDialogFragment {
            val args = Bundle()
            args.putString(EXTRA_INPUT_TEXT, inputText)
            args.putInt(EXTRA_COLOR_CODE, colorCode)
            val fragment = TextEditorDialogFragment()
            fragment.arguments = args
            fragment.show(appCompatActivity.supportFragmentManager, TAG)
            return fragment
        }
    }
}