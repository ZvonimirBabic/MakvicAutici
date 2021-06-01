package com.makvic.makvicautici.fragments_viewmodels.store


import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.makvic.makvicautici.R
import com.makvic.makvicautici.adapters.ProductAdapter
import com.makvic.makvicautici.adapters.WordpressProductLoadStateAdapter
import com.makvic.makvicautici.api.responses.products.Product
import com.makvic.makvicautici.databinding.StoreFragmentLayoutBinding
import com.makvic.makvicautici.fragments_viewmodels.dialog_fragments.FilterDialogFragment
import com.makvic.makvicautici.fragments_viewmodels.dialog_fragments.SortDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.store_fragment_layout.*

@AndroidEntryPoint
open class StoreFragment : Fragment(R.layout.store_fragment_layout),
    ProductAdapter.OnItemClickListener {


    private val viewModel: StoreViewModel by
    navGraphViewModels(R.id.fragment_navigation_xml) {
        defaultViewModelProviderFactory
    }
    private var _binding: StoreFragmentLayoutBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = StoreFragmentLayoutBinding.bind(view)
        val adapter = ProductAdapter(this, resources.getString(R.string.no_price))
        adapter.stateRestorationPolicy =
            RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY

        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)

        }
        viewModel.makesListPage1.observe(viewLifecycleOwner) {
            viewModel.makesList.value = it
        }
        viewModel.makesListPage2.observe(viewLifecycleOwner) {

            viewModel.makesList.value?.addAll(it)
        }
        var isforceRefreshRegistered: Boolean = false
        viewModel.forceRefresh.observe(viewLifecycleOwner) {

            if (isforceRefreshRegistered) {
                binding.productRecyclerView.scrollToPosition(0)
                Log.i("forceRefresh", "ForceRefresh")
            }
            isforceRefreshRegistered = true


        }





        binding.apply {
            productRecyclerView.adapter = adapter.withLoadStateHeaderAndFooter(
                header = WordpressProductLoadStateAdapter { adapter.retry() },
                footer = WordpressProductLoadStateAdapter { adapter.retry() }
            )
            storeRetryButton.setOnClickListener {
                adapter.retry()
            }
        }

        adapter.addLoadStateListener { loadState ->
            binding.apply {
                productRecyclerView.itemAnimator = null
                storeProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                productRecyclerView.isVisible = loadState.source.refresh is LoadState.NotLoading
                storeRetryButton.isVisible = loadState.source.refresh is LoadState.Error
                storeTextViewRetry.isVisible = loadState.source.refresh is LoadState.Error
                //Empty
                if (loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    adapter.itemCount < 1
                ) {
                    productRecyclerView.isVisible = false
                    textViewEmpty.isVisible = true
                } else {
                    textViewEmpty.isVisible = false
                }
            }
        }



        store_button_filter.setOnClickListener {
            val dialogFragment = FilterDialogFragment(
                resources.getString(R.string.any_manufacturer),
                resources.getString(R.string.any_make),
                resources.getString(R.string.any_scale),
                viewModel.manufacturerPositionVM,
                viewModel.makePositionVM,
                viewModel.scalePositionVM
            )
            setTargetFragment(dialogFragment, 0)
            dialogFragment.show(
                childFragmentManager, ""

            )

        }
        store_button_sort.setOnClickListener {
            val dialogFragment = SortDialogFragment()
            setTargetFragment(dialogFragment, 0)
            dialogFragment.show(
                childFragmentManager, ""
            )
        }

    }


    override fun onItemClick(product: Product) {
        val action = StoreFragmentDirections.actionStoreFragmentToStoreSingleFragment(product)
        findNavController().navigate(action)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
