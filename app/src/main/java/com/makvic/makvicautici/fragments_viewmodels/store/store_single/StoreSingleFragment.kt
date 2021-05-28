package com.makvic.makvicautici.fragments_viewmodels.store.store_single

import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.makvic.makvicautici.R
import com.makvic.makvicautici.adapters.ProductSingleImageAdapter
import com.makvic.makvicautici.api.responses.products.Image
import com.makvic.makvicautici.databinding.StoreSingleFragmentLayoutBinding
import com.makvic.makvicautici.fragments_viewmodels.store.StoreViewModel

class StoreSingleFragment : Fragment(R.layout.store_single_fragment_layout)
    , ProductSingleImageAdapter.OnItemClickListener {

    private val args by navArgs<StoreSingleFragmentArgs>()
    private val viewModel: StoreViewModel by
    navGraphViewModels(R.id.fragment_navigation_xml) {
        defaultViewModelProviderFactory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = StoreSingleFragmentLayoutBinding.bind(view)




        binding.apply {
            val product = args.product

            if ((viewModel.wishlist.contains(product) || viewModel.wishlistID.contains(product.id))) {
                wishlistButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_24))
            }
            wishlistButton.setOnClickListener {
                if ((viewModel.wishlist.contains(product) || viewModel.wishlistID.contains(product.id))) {
                    viewModel.removeFromWishlist(product)
                    wishlistButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_border_24))
                    Toast.makeText(context, getString(R.string.wishlistRemove), Toast.LENGTH_SHORT)
                        .show()
                } else {
                    viewModel.addToWishlist(product)
                    wishlistButton.setImageDrawable(resources.getDrawable(R.drawable.ic_baseline_star_24))
                    Toast.makeText(context, getString(R.string.wishlistAdd), Toast.LENGTH_SHORT)
                        .show()
                }

            }
            viewPager.adapter =
                ProductSingleImageAdapter(product.images, this@StoreSingleFragment)



            textViewTitleSingle.text = product.name
            textViewDescription.text = Html.fromHtml(product.description).toString()
            for (attribute in product.attributes) {
                if (attribute.id == 4) {
                    textViewManufacturerSingle.text = attribute.options[0]

                }
                if (attribute.id == 5) {
                    textViewScaleSingle.text = attribute.options[0]
                }
                if (attribute.id == 6) {
                    textViewMakeSingle.text = attribute.options[0]
                }
            }
            if (product.price != "" || product.regular_price != "") {
                textViewPriceSingle.text = product.price + "kn"
                textViewPriceSingleEur.text =
                    "%.2f".format(product.price.toInt() / 7.5, 2) + "€"
            }
            if (product.price == "" || product.regular_price == "") {

                textViewPriceSingle.text = ""
                textViewPriceSingleEur.text = ""
            }

        }


    }

    override fun onItemClick(image: Image) {
        val imageSource = image.src
        ProductSingleFragmentImage(imageSource).show(childFragmentManager, "a")


    }
}