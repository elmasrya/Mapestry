package com.andrewelmasry.mapestry.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mapbox.maps.MapboxMap
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer

class MapViewModel : ViewModel() {
    val mapboxMapLiveData = MutableLiveData<MapboxMap>()
    private val symbolLayerLiveData = MutableLiveData<SymbolLayer?>()



    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    fun initializeMapSymbolLayer(symbolLayer: SymbolLayer?) {
        symbolLayerLiveData.value = symbolLayer
    }

}