package com.sendpost.dreamsoft.ImageEditor.filters

import ja.burhanrashid52.photoeditor.Filter.PhotoFilter


interface FilterListener {
    fun onFilterSelected(photoFilter: PhotoFilter?)
}