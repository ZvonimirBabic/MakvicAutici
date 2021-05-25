package com.example.makvicautici.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.makvicautici.R
import com.example.makvicautici.api.responses.products.Product
import com.example.makvicautici.api.responses.products.Products
import com.example.makvicautici.databinding.RecyclerViewProductBinding
import com.example.makvicautici.fragments_viewmodels.wishlist.WishlistFragment
import java.text.SimpleDateFormat

class WishlistAdapter(private val products: Products, private val listener: WishlistFragment) :
    RecyclerView.Adapter<WishlistAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerViewProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val getItem = products[position]
        if (getItem != null) {
            holder.bind(getItem
            )
        }

    }



    inner class ViewHolder(private val binding: RecyclerViewProductBinding) : RecyclerView.ViewHolder(binding.root) {


        init {
            binding.root.setOnClickListener {
                val position = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val product = products[position]
                    if (product != null) {
                        listener.onItemClick(product)
                    }
                }
            }
        }
        fun bind(product: Product){
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

                    recyclerViewProductPrice.text =  "a"
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
}