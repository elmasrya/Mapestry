
package com.andrewelmasry.mapestry.ui.map

import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.andrewelmasry.mapestry.R
import com.andrewelmasry.mapestry.databinding.AnnotationViewBinding
import com.andrewelmasry.mapestry.databinding.FragmentMapBinding
import com.andrewelmasry.mapestry.helpers.LocationPermissionHelper
import com.andrewelmasry.mapestry.ui.MapViewModel
import com.andrewelmasry.mapestry.ui.bottomsheet.MapEditorBottomSheetFragment

import com.mapbox.android.gestures.MoveGestureDetector
import com.mapbox.bindgen.Value
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.Style
import com.mapbox.maps.extension.style.expressions.dsl.generated.interpolate
import com.mapbox.geojson.Point
import com.mapbox.maps.RenderedQueryGeometry
import com.mapbox.maps.RenderedQueryOptions
import com.mapbox.maps.ScreenCoordinate
import com.mapbox.maps.extension.observable.eventdata.MapLoadingErrorEventData
import com.mapbox.maps.extension.style.expressions.generated.Expression
import com.mapbox.maps.extension.style.layers.generated.CircleLayer
import com.mapbox.maps.extension.style.layers.generated.FillLayer
import com.mapbox.maps.extension.style.layers.generated.LineLayer
import com.mapbox.maps.extension.style.layers.generated.SymbolLayer
import com.mapbox.maps.extension.style.layers.generated.circleLayer
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.layers.getLayerAs
import com.mapbox.maps.extension.style.layers.properties.generated.IconTextFit
import com.mapbox.maps.extension.style.sources.generated.VectorSource
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style
import com.mapbox.maps.plugin.LocationPuck2D
import com.mapbox.maps.plugin.delegates.listeners.OnMapLoadErrorListener
import com.mapbox.maps.plugin.gestures.OnMoveListener
import com.mapbox.maps.plugin.gestures.gestures
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorBearingChangedListener
import com.mapbox.maps.plugin.locationcomponent.OnIndicatorPositionChangedListener
import com.mapbox.maps.plugin.locationcomponent.location
import com.mapbox.maps.viewannotation.ViewAnnotationManager
import com.mapbox.maps.viewannotation.viewAnnotationOptions
import java.lang.ref.WeakReference

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel: MapViewModel by activityViewModels()
    private lateinit var bottomSheetFragment: MapEditorBottomSheetFragment
    private lateinit var locationPermissionHelper: LocationPermissionHelper
    private lateinit var locationManager: LocationManager
    private lateinit var currentLocation: Point


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.ivEditMap.setOnClickListener {
            bottomSheetFragment = MapEditorBottomSheetFragment()
            activity?.let { it1 -> bottomSheetFragment.show(it1.supportFragmentManager, "BSDialogFragment") }

        }

        locationPermissionHelper = LocationPermissionHelper(WeakReference(this.activity))
        locationPermissionHelper.checkPermissions {
            onMapReady()
        }

        binding.ivCurrentLocation.setOnClickListener {
            binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(currentLocation).zoom(14.0)
                .build())
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.mapView.location.removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapView.location.removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.gestures.removeOnMoveListener(onMoveListener)

        _binding = null
    }

    private val onMoveListener = object : OnMoveListener {
        override fun onMoveBegin(detector: MoveGestureDetector) {
            onCameraTrackingDismissed()
        }

        override fun onMove(detector: MoveGestureDetector): Boolean {
            return false
        }

        override fun onMoveEnd(detector: MoveGestureDetector) {}
    }

    private val onIndicatorBearingChangedListener = OnIndicatorBearingChangedListener {
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().bearing(it).build())
    }

    private val onIndicatorPositionChangedListener = OnIndicatorPositionChangedListener {
        currentLocation = it
        binding.mapView.getMapboxMap().setCamera(CameraOptions.Builder().center(it).build())
        binding.mapView.gestures.focalPoint = binding.mapView.getMapboxMap().pixelForCoordinate(it)
    }

    private fun onMapReady() {
        binding.mapView.getMapboxMap().setCamera(
            CameraOptions.Builder()
                .zoom(14.0)
                .build()
        )
        binding.mapView.getMapboxMap().loadStyleUri(MAP_BOX_URL) { style ->
            initLocationComponent()
            setupGesturesListener()
            //val fillLayer = style.getLayerAs<FillLayer>("tampa-parking-tileset")
            println("Layers: ${style.styleLayers}")
            val symbolLayer = style.getLayerAs<SymbolLayer>("tampa-parking-symbol-layer")
            val circleLayer = style.getLayerAs<CircleLayer>("alternative-parking-points")
            var data = circleLayer?.let { geoJsonSource(it.layerId) }
            //val lineLayer = style.getLayerAs<LineLayer>("tampa-parking-lines-tileset")
            mapViewModel.initializeMapSymbolLayer(symbolLayer = symbolLayer)
            val layerList = mutableListOf<String>()
            layerList.add(0,"alternative-parking-points")

            val options = RenderedQueryOptions(layerList, Value.nullValue())
            val screenCoordinate = ScreenCoordinate(-82.541185, 28.17246)


            binding.mapView.getMapboxMap().queryRenderedFeatures(screenCoordinate, options) { expectedFeatures ->
                if (expectedFeatures.value?.isNotEmpty() == true) {
                    println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%")
                }

                println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")

            }
            symbolLayer?.iconAllowOverlap(true)
            symbolLayer?.iconImage("rectangle-blue-4")
            symbolLayer?.iconTextFit(IconTextFit.BOTH)
            symbolLayer?.iconTextFitPadding(listOf(10.0,10.0,10.0,10.0))


            println("---------##################### Circle Layer: $circleLayer")

        }

        object : OnMapLoadErrorListener {
            override fun onMapLoadError(eventData: MapLoadingErrorEventData) {
                println("------------- Error")
                println(eventData.message)
            }
        }



    }

    private fun setupGesturesListener() {
        binding.mapView.gestures.addOnMoveListener(onMoveListener)
    }

    private fun getLayers() {
        println("------------------------")
        binding.mapView.getMapboxMap().getStyle { style ->
            // Get an existing layer by referencing its
            // unique layer ID (LAYER_ID)
            val layer = style.getLayerAs<FillLayer>("2")
            // Update layer properties
            println(layer)
            layer?.fillOpacity(0.7)
        }
    }

    private fun initLocationComponent() {
        val locationComponentPlugin = binding.mapView.location
        locationComponentPlugin.updateSettings {
            this.enabled = true
            pulsingEnabled = true
            this.locationPuck = LocationPuck2D(
                bearingImage = this@MapFragment.activity?.let {
                    AppCompatResources.getDrawable(
                        it.applicationContext,
                        R.drawable.mapbox_user_puck_icon,
                    )
                },
                shadowImage = this@MapFragment.activity?.let {
                    AppCompatResources.getDrawable(
                        it.applicationContext,
                        R.drawable.mapbox_user_icon_shadow,
                    )
                },
                scaleExpression = interpolate {
                    linear()
                    zoom()
                    stop {
                        literal(0.0)
                        literal(0.6)
                    }
                    stop {
                        literal(20.0)
                        literal(1.0)
                    }
                }.toJson()
            )
        }
        locationComponentPlugin.addOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        locationComponentPlugin.addOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
    }

    private fun onCameraTrackingDismissed() {
        Toast.makeText(this.activity, "onCameraTrackingDismissed", Toast.LENGTH_SHORT).show()
        binding.mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    private fun addViewAnnotation(point: Point) {
        // Define the view annotation
        val viewAnnotation = binding.mapView.viewAnnotationManager.addViewAnnotation(
            // Specify the layout resource id
            resId = R.layout.annotation_view,
            // Set any view annotation options
            options = viewAnnotationOptions {
                geometry(point)
            }
        )
        AnnotationViewBinding.bind(viewAnnotation)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.location
            .removeOnIndicatorBearingChangedListener(onIndicatorBearingChangedListener)
        binding.mapView.location
            .removeOnIndicatorPositionChangedListener(onIndicatorPositionChangedListener)
        binding.mapView.gestures.removeOnMoveListener(onMoveListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        locationPermissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}
