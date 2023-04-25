package com.sendpost.dreamsoft.ImageEditor.Frame

import com.sendpost.dreamsoft.model.FrameModel


interface FrameListener {
    fun onFrameSelected(frameModel: FrameModel?)
}