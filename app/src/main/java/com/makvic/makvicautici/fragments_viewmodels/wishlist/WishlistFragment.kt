package com.makvic.makvicautici.fragments_viewmodels.wishlist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.makvic.makvicautici.R
import com.makvic.makvicautici.adapters.WishlistAdapter
import com.makvic.makvicautici.api.responses.products.Product
import com.makvic.makvicautici.databinding.WishlistFragmentBinding
import com.makvic.makvicautici.fragments_viewmodels.store.StoreViewModel

class WishlistFragment : Fragment(R.layout.wishlist_fragment), WishlistAdapter.OnItemClickListener{


    private val viewModel: StoreViewModel by
    navGraphViewModels(R.id.fragment_navigation_xml) {
        defaultViewModelProviderFactory
    }
    private var _binding: WishlistFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = WishlistFragmentBinding.bind(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
        if(viewModel.wishlist.isNotEmpty()){
            binding.noItems.visibility = View.GONE

            val recyclerView = binding.recyclerView
            val adapter = WishlistAdapter(viewModel.wishlist, this)
            recyclerView.layoutManager = LinearLayoutManager(context)
            recyclerView.adapter = adapter
        }



    }

    override fun onItemClick(product: Product) {
        val action = WishlistFragmentDirections.actionWishlistFragmentToStoreSingleFragment(product)
        findNavController().navigate(action)}

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }


}