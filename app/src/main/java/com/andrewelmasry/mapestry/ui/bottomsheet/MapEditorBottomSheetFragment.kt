package com.andrewelmasry.mapestry.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewelmasry.mapestry.databinding.FragmentMapEditorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapEditorBottomSheetFragment (): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMapEditorBottomSheetBinding


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)

            setOnShowListener {
                val bottomSheet = findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as ConstraintLayout
                bottomSheet.setBackgroundResource(android.R.color.transparent)

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return binding.root

    }
}