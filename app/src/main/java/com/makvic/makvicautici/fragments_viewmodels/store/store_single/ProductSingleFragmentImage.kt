package com.makvic.makvicautici.fragments_viewmodels.store.store_single

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.makvic.makvicautici.R
import kotlinx.android.synthetic.main.product_single_image_fragment.*

class ProductSingleFragmentImage(private val imageSource: String) : DialogFragment() {


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
        val dialog: Dialog = Dialog(requireContext())
        if (getDialog() != null) {
            super.setShowsDialog(false)
        }
        dialog.setContentView(R.layout.product_single_image_fragment)
        dialog.setTitle("Custom Dialog")

        Glide.with(this)
            .load(imageSource)
            .error(R.drawable.ic_baseline_error_outline_24)
            .into(dialog.imageViewPopup)

        return dialog
    }


}