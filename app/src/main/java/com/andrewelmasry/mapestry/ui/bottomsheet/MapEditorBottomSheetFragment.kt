package com.andrewelmasry.mapestry.ui.bottomsheet

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.andrewelmasry.mapestry.databinding.FragmentMapEditorBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MapEditorBottomSheetFragment (listener: MapEditorListener): BottomSheetDialogFragment() {

    private lateinit var binding: FragmentMapEditorBottomSheetBinding
    private var mBottomSheetListener: MapEditorListener? = null

    init {
        this.mBottomSheetListener = listener
    }



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f) /** Set dim amount here (the dimming factor of the parent fragment) */

            /** IMPORTANT! Here we set transparency to dialog layer */
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        /** attach listener from parent fragment */
        try {
            mBottomSheetListener = context as MapEditorListener?
        }
        catch (_: ClassCastException){
        }
    }


    interface MapEditorListener:BottomSheetBehavior() {
    }
}