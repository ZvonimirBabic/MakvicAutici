package com.example.makvicautici.fragments_viewmodels.store


import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.paging.LoadState
import com.example.makvicautici.R
import com.example.makvicautici.adapters.ProductAdapter
import com.example.makvicautici.adapters.WordpressProductLoadStateAdapter
import com.example.makvicautici.api.responses.products.Product
import com.example.makvicautici.databinding.StoreFragmentLayoutBinding
import com.example.makvicautici.fragments_viewmodels.dialog_fragments.FilterDialogFragment
import com.example.makvicautici.fragments_viewmodels.dialog_fragments.SortDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.store_fragment_layout.*

@AndroidEntryPoint
class StoreFragment : Fragment(R.layout.store_fragment_layout), ProductAdapter.OnItemClickListener {


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


        viewModel.data.observe(viewLifecycleOwner) {
            adapter.submitData(viewLifecycleOwner.lifecycle, it)
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
