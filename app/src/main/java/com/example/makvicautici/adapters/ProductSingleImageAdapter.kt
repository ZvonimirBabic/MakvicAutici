package com.example.makvicautici.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.makvicautici.R
import com.example.makvicautici.api.responses.products.Image
import com.example.makvicautici.fragments_viewmodels.store.store_single.StoreSingleFragment
import com.github.islamkhsh.CardSliderAdapter
import kotlinx.android.synthetic.main.product_single_image.view.*

class ProductSingleImageAdapter(
    private val images: List<Image>,
    private val listener: StoreSingleFragment
) :
    CardSliderAdapter<ProductSingleImageAdapter.ImageViewHolder>() {


    override fun bindVH(holder: ImageViewHolder, position: Int) {

        val image = images[position]

        holder.itemView.run {
            Glide.with(this)
                .load(image.src)
                .centerCrop()
                .transition(DrawableTransitionOptions.withCrossFade())
                .error(R.drawable.ic_baseline_error_outline_24)
                .into(imageViewSingle)
            imageViewSingle.setOnClickListener {
                listener.onItemClick(image)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.product_single_image, parent, false)

        return ImageViewHolder(view)
    }


    override fun getItemCount() = images.size


    class ImageViewHolder(view: View) : RecyclerView.ViewHolder(view)

    interface OnItemClickListener {
        fun onItemClick(image: Image)
    }
}