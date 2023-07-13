package com.mesum.nanohealthsuite.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentEntryDialogBinding
import com.mesum.nanohealthsuite.model.ProductsRpItem


class HalfHeightBottomSheetDialogFragment(val product: ProductsRpItem) : BottomSheetDialogFragment() {
    private val viewModel : ProductsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return  FragmentEntryDialogBinding.inflate(inflater, container, false).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)

            binding.ratingBar.rating = product.rating?.rate?.toFloat() ?: 2f
            binding.tvDescription.text = product.description.toString()
            binding.tvName.text = product.title.toString()
            binding.tvRating.text = product.rating?.rate.toString()



    }

    @SuppressLint("ResourceType")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        dialog.setCanceledOnTouchOutside(false)
        dialog.setOnKeyListener { _, keyCode, event ->
            keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_UP
        }

        val contentView = layoutInflater.inflate(R.layout.fragment_entry_dialog, null)
        dialog.setContentView(contentView)

        // Set the peek height (initial visible height) of the bottom sheet
        dialog.behavior.peekHeight = getHalfScreenHeight()
        dialog.setCancelable(false)  // Disable canceling the dialog by tapping outside
        // Set the expanded height of the bottom sheet
        val params = dialog.window?.attributes
        dialog.window?.setGravity(Gravity.BOTTOM)
        params?.height = getHalfScreenHeight()

        // Enable swipe-up behavior to expand the bottom sheet fully
        dialog.behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                if (newState == BottomSheetBehavior.STATE_EXPANDED) {
                    dialog.behavior.peekHeight = 0  // Set peek height to 0 for expanded state
                    dialog.behavior.isHideable = false  // Disable hiding the bottom sheet
                    dialog.findViewById<ImageView>(R.id.iv_state)?.setImageDrawable(requireContext().getDrawable(R.drawable.lessdetails))
                } else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                    dialog.behavior.peekHeight = getHalfScreenHeight()  // Set peek height for collapsed state
                    dialog.behavior.isHideable = true  // Enable hiding the bottom sheet
                    dialog.findViewById<ImageView>(R.id.iv_state)?.setImageDrawable(requireContext().getDrawable(R.drawable.moredetails))

                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Optional: Handle the slide offset if needed
            }
        })




        // Adjust the dialog position to appear at the bottom of the screen
        // Adjust the dialog position to appear at the bottom of the screen

        return dialog
    }


    private fun getHalfScreenHeight(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels / 4
    }
}
