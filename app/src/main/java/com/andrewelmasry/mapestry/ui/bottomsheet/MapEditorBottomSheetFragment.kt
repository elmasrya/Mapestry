package com.andrewelmasry.mapestry.ui.bottomsheet

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.SeekBar
import androidx.compose.material.TextField
import androidx.fragment.app.activityViewModels
import codes.side.andcolorpicker.group.PickerGroup
import codes.side.andcolorpicker.group.registerPickers
import codes.side.andcolorpicker.hsl.HSLColorPickerSeekBar
import codes.side.andcolorpicker.model.IntegerHSLColor
import codes.side.andcolorpicker.view.picker.ColorSeekBar
import com.andrewelmasry.mapestry.databinding.FragmentMapEditorBottomSheetBinding
import com.andrewelmasry.mapestry.ui.MapViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class MapEditorBottomSheetFragment (): BottomSheetDialogFragment() {
    private var _binding: FragmentMapEditorBottomSheetBinding? = null
    private val mapViewModel: MapViewModel by activityViewModels()

    private val binding get() = _binding!!



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.setDimAmount(0.4f)
            setOnShowListener {

            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMapEditorBottomSheetBinding.inflate(inflater, container, false)
        var index = 0;
        val colorSet = listOf("#00FF00","#FF0000","#87CEEB","#FFA500","#800080","#000000");
        //observeViewModel()


        binding.btnPointColorPicker.setOnClickListener{
            if (index == 5 ) {
                index = 0
            }
            val colorHEX = colorSet[index];
            //mapViewModel.(colorHEX)
            binding.pointsColorPresenter.setBackgroundColor(Color.parseColor(colorHEX))
            index += 1

        }

        binding.btnPolyColorPicker.setOnClickListener{
            if (index == 5 ) {
                index = 0
            }
            val colorHEX = colorSet[index];
            //mapViewModel.updateMapPolygonColor(colorHEX)
            binding.polyColorPresenter.setBackgroundColor(Color.parseColor(colorSet[index]))
            index += 1

        }

        binding.sbPointsSize.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val actualProgress = progress + 1
               // mapViewModel.updateMapPointsSize(actualProgress.toDouble())
                binding.tvPointsSizeValue.text = actualProgress.toString() + " px"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user starts interacting with the seek bar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user stops interacting with the seek bar
            }
        })

        binding.sbPointsOpacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val opacityValue = progress.toFloat() / 255f
                //mapViewModel.updateMapPointsOpacity(opacityValue.toDouble())
                binding.tvPointsOpacityValue.text = opacityValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user starts interacting with the seek bar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user stops interacting with the seek bar
            }
        })


        binding.sbPolyOpacity.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val opacityValue = progress.toFloat() / 255f
                //mapViewModel.updateMapPolygonOpacity(opacityValue.toDouble())
                binding.tvPolyOpacityValue.text = opacityValue.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user starts interacting with the seek bar
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // This method will be called when the user stops interacting with the seek bar
            }
        })






         return binding.root

    }
}