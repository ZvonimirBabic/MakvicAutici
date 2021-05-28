package com.makvic.makvicautici.fragments_viewmodels.dialog_fragments
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.observe
import androidx.navigation.navGraphViewModels
import com.makvic.makvicautici.R
import com.makvic.makvicautici.api.responses.makes.Make
import com.makvic.makvicautici.api.responses.manufacturers.Manufacturer
import com.makvic.makvicautici.api.responses.scales.Scale
import com.makvic.makvicautici.fragments_viewmodels.store.StoreViewModel
import kotlinx.android.synthetic.main.product_filter.*

class FilterDialogFragment(
    val anyManufacturer: String,
    val anyMake: String,
    val anyScale: String,
    val selectedManufacturer: Int,
    val selectedMake: Int,
    val selectedScale: Int
) :
    DialogFragment() {

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
            R.layout.product_filter,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupClickListeners(view)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(null)

    }

    private fun setupView(view: View) {
        val firstManufacturer = Manufacturer(null, anyManufacturer, null)
        val firstMake = Make(null, anyMake, null)
        val firstScale = Scale(null, anyScale, null)


        viewModel.manufacturersList.observe(viewLifecycleOwner) {
            if (it[0].name != firstManufacturer.name) {
                it.add(0, firstManufacturer)
            }
            val adapter = activity?.let { it1 ->
                ArrayAdapter(
                    it1,
                    R.layout.support_simple_spinner_dropdown_item,
                    it
                )
            }
            spinnerManufacturer.adapter = adapter
            spinnerManufacturer.setSelection(selectedManufacturer)
        }
        viewModel.makesList.observe(viewLifecycleOwner) {
            if (it[0].name != firstMake.name) {
                it.add(0, firstMake)
            }
            val adapter = activity?.let { it1 ->
                ArrayAdapter(
                    it1,
                    R.layout.support_simple_spinner_dropdown_item,
                    it
                )
            }
            spinnerMake.adapter = adapter
            spinnerMake.setSelection(selectedMake)
        }
        viewModel.scalesList.observe(viewLifecycleOwner) {
            if (it[0].name != firstScale.name) {
                it.add(0, firstScale)
            }
            val adapter = activity?.let { it1 ->
                ArrayAdapter(
                    it1,
                    R.layout.support_simple_spinner_dropdown_item,
                    it
                )
            }
            spinnerScale.adapter = adapter
            spinnerScale.setSelection(selectedScale)
        }

    }

    private fun setupClickListeners(view: View) {
        buttonReset.setOnClickListener {
            spinnerManufacturer.setSelection(0)
            spinnerMake.setSelection(0)
            spinnerScale.setSelection(0)
        }
        buttonSearch.setOnClickListener {
            var selectedManufacturer = (spinnerManufacturer.selectedItem as Manufacturer).slug
            var selectedMake = (spinnerMake.selectedItem as Make).slug
            var selectedScale = (spinnerScale.selectedItem as Scale).slug
            viewModel.filter(
                selectedManufacturerSlug = selectedManufacturer,
                selectedMakeSlug = selectedMake,
                selectedScaleSlug = selectedScale,
                manufacturerPosition = spinnerManufacturer.selectedItemPosition,
                makePosition = spinnerMake.selectedItemPosition,
                scalePosition = spinnerScale.selectedItemPosition
            )
            dismiss()
        }
    }
}

