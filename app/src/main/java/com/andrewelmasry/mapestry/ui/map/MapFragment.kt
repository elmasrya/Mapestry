
package com.andrewelmasry.mapestry.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.andrewelmasry.mapestry.R
import com.andrewelmasry.mapestry.databinding.FragmentMapBinding
import com.andrewelmasry.mapestry.ui.bottomsheet.MapEditorBottomSheetFragment
import com.andrewelmasry.mapestry.ui.map.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mapbox.maps.extension.style.style

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val mapViewModel: MapViewModel by viewModels()
    private lateinit var bottomSheetFragment: MapEditorBottomSheetFragment



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.fab.setOnClickListener {
            //bottomSheetFragment = MapEditorBottomSheetFragment()
            //bottomSheetFragment.show(suppo, "BSDialogFragment")

        }
        binding.mapView.getMapboxMap().loadStyle(
            styleExtension = style(getString(R.string.mapbox_style_url)) {

            }
        )
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}