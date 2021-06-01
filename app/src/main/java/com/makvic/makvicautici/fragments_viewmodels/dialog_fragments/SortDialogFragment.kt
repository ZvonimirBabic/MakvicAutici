package com.makvic.makvicautici.fragments_viewmodels.dialog_fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import com.makvic.makvicautici.R
import com.makvic.makvicautici.fragments_viewmodels.store.StoreFragment
import com.makvic.makvicautici.fragments_viewmodels.store.StoreViewModel
import kotlinx.android.synthetic.main.product_sort.*

class SortDialogFragment() : DialogFragment() {

    private val viewModel: StoreViewModel by
    navGraphViewModels(R.id.fragment_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.product_sort,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
        radioGroupSort.check(viewModel.currentRadioButtonId)

    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(null)


    }

    private fun setupView(view: View) {


    }


    private fun setupClickListeners(view: View) {
        buttonSort.setOnClickListener {
            viewModel.sort(radioGroupSort.checkedRadioButtonId)
            val Parent = parentFragment as StoreFragment
            dismiss()
        }
    }


}

