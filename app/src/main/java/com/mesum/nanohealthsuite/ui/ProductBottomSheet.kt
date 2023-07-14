package com.mesum.nanohealthsuite.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.mesum.nanohealthsuite.R
import com.mesum.nanohealthsuite.databinding.FragmentEntryDialogBinding
import com.mesum.nanohealthsuite.model.ProductsRpItem


class HalfHeightBottomSheetDialogFragment(private val product: ProductsRpItem) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val customView = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_dialog, LinearLayout(requireContext())) as LinearLayout
        // Get the root view of the BottomSheetDialogFragment
       val binding = FragmentEntryDialogBinding.inflate(inflater, container, false).root
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back press event here
                // You can perform any desired actions or navigate back
                dialog?.dismiss()
                findNavController().navigate(R.id.allProductsFragment)
            }
        })
        return  binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentEntryDialogBinding.bind(view)

            binding.ratingBar.rating = product.rating?.rate?.toFloat() ?: 2f
            binding.tvDescription.text = product.description.toString()
            binding.tvDesc.text = product.title.toString()
            binding.tvRating.text = product.rating?.rate.toString()
        binding.tvReviews.text = "Reviews (${product.rating?.count})"
        binding.ivShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, product.title.toString())

            val chooserIntent = Intent.createChooser(shareIntent, "Share via")
            if (shareIntent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(chooserIntent)
            }
        }



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



        // Inflate the custom layout



        // Add the custom layout to the root view

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



        dialog.findViewById<ImageView>(R.id.iv_state)?.setOnClickListener {
            val currentState = dialog.behavior.state
            if (currentState == BottomSheetBehavior.STATE_EXPANDED) {
                dialog.behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            } else if (currentState == BottomSheetBehavior.STATE_COLLAPSED) {
                dialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }


        // Adjust the dialog position to appear at the bottom of the screen
        // Adjust the dialog position to appear at the bottom of the screen

        return dialog
    }


    private fun getHalfScreenHeight(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels / 4
    }

    override fun getTheme() = R.style.CustomBottomSheetDialogTheme

}
