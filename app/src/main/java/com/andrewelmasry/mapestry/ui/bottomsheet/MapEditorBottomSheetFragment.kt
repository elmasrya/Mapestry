package com.andrewelmasry.mapestry.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.andrewelmasry.mapestry.databinding.FragmentMapEditorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MapEditorBottomSheetFragment (): BottomSheetDialogFragment() {
    private var _binding: FragmentMapEditorBottomSheetBinding? = null
    private val binding get() = _binding!!



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)
            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id
                    .design_bottom_sheet) as FrameLayout
               // BottomSheetBehavior.from(bottomSheet).state = BottomSheetBehavior.STATE_HALF_EXPANDED
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMapEditorBottomSheetBinding.inflate(inflater, container, false)
        return binding.root

    }
}