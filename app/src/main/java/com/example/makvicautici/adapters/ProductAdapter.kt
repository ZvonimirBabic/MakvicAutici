package com.example.makvicautici.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.makvicautici.R
import com.example.makvicautici.api.responses.products.Product
import com.example.makvicautici.databinding.RecyclerViewProductBinding
import com.example.makvicautici.fragments_viewmodels.store.StoreFragment
import java.text.SimpleDateFormat

class ProductAdapter(private val listener: StoreFragment, val noPrice: String) :
    PagingDataAdapter<Product, ProductAdapter.ProductViewHolder>(PRODUCT_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            RecyclerViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class ProductViewHolder(private val binding: RecyclerViewProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = getItem(position)
                    if (product != null) {
                        listener.onItemClick(product)
                    }
                }
            }
        }

        fun bind(product: Product) {
            binding.apply {
                if(product.featured) {constraintLayoutID.setBackgroundColor(Color.parseColor("#802ea3f2"))}
                else {constraintLayoutID.setBackgroundResource(0)}
                Glide.with(itemView)
                    .load(product.images[0].src)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(recyclerViewProductCoverPhoto)
                recyclerViewProductTitle.text = product.name


                val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                val formatter = SimpleDateFormat("dd.MM.yyyy")
                val output: String = formatter.format(parser.parse(product.date_created + 2))
                recyclerViewProductDate.text = output

                if(product.price !="" || product.regular_price != "") {
                    recyclerViewProductPrice.text = product.price + "kn"
                    recyclerViewProductPriceInEur.text =
                        "%.2f".format(product.price.toInt() / 7.5, 2) + "€"
                }
                if (product.price =="" || product.regular_price == ""){

                    recyclerViewProductPrice.text =  noPrice
                    recyclerViewProductPriceInEur.text = ""
                }

                for(attribute in product.attributes){
                    if (attribute.id==4){recyclerViewProductManufacturer.text = attribute.options[0]}
                    if (attribute.id==5){recyclerViewProductScale.text = attribute.options[0]}
                    if (attribute.id==6){}
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(product: Product)

    }

    companion object {
        private val PRODUCT_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem
        }
    }
}