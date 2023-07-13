package com.mesum.nanohealthsuite.ui

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.core.widget.doOnTextChanged
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesum.nanohealthsuite.databinding.FragmentEntryDialogBinding


class HalfHeightBottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  FragmentEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)

        binding.name.doOnTextChanged { text, start, before, count ->
            binding.saveButton.isEnabled = (start + count) > 0
        }

        binding.colorSpinner.onItemLongClickListener = object : AdapterView.OnItemSelectedListener,
            AdapterView.OnItemLongClickListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selected = parent?.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemLongClick(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ): Boolean {
                return false
            }


        }
        binding.cancelButton.setOnClickListener { dismiss() }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        // Set the peek height (initial visible height) of the bottom sheet
        dialog.behavior.peekHeight = getHalfScreenHeight()

        // Set the expanded height of the bottom sheet
        val params = dialog.window?.attributes
        params?.height = getHalfScreenHeight()

        // Enable swipe-up behavior to expand the bottom sheet fully
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    params?.height = ViewGroup.LayoutParams.MATCH_PARENT
                    dialog.window?.attributes = params
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    params?.height = getHalfScreenHeight()
                    dialog.window?.attributes = params
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Optional: Handle the slide offset if needed
            }
        })

        return dialog
    }

    private fun getHalfScreenHeight(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels / 4
    }
}
