package com.andrewelmasry.mapestry.ui

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer

class MapViewModel : ViewModel() {
    val mapboxMapLiveData = MutableLiveData<MapboxMap>()
    val lineLayerLiveData = MutableLiveData<LineLayer?>()
    private val fillLayerLiveData = MutableLiveData<FillLayer?>()
    private val circleLayerLiveData = MutableLiveData<CircleLayer?>()



    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun initializeMapPointsLayer(circleLayer: CircleLayer?) {
        circleLayerLiveData.value = circleLayer
    }

    fun initializeMapPolygonLayer(fillLayer: FillLayer?) {
        fillLayerLiveData.value = fillLayer
    }

    fun updateMapPointsColor(color: String) {
        circleLayerLiveData.value?.circleColor(color)
    }

    fun updateMapPointsOpacity(opactiy: Double) {
        circleLayerLiveData.value?.circleOpacity(opactiy)
    }
    fun updateMapPointsSize(size: Double) {
        circleLayerLiveData.value?.circleRadius(size)
    }

    fun updateMapPolygonColor(color: String) {
        fillLayerLiveData.value?.fillColor(color)
    }

    fun updateMapPolygonOpacity(opactiy: Double) {
        fillLayerLiveData.value?.fillOpacity(opactiy)
    }

}