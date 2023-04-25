package com.sendpost.dreamsoft.Interface

import ja.burhanrashid52.photoeditor.ShapeType


interface Properties {
        fun onColorChanged(colorCode: Int)
        fun onOpacityChanged(opacity: Int)
        fun onShapeSizeChanged(shapeSize: Int)
        fun onShapePicked(shapeType: ShapeType?)
    }
