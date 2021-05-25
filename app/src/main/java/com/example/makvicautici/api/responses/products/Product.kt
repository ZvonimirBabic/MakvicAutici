package com.example.makvicautici.api.responses.products

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    val attributes: List<Attribute>,
    val categories: List<Category>,
    val date_created: String,
    val date_modified: String,
    val date_on_sale_from: String,
    val date_on_sale_to: String,
    val description: String,
    val dimensions: Dimensions,
    val download_expiry: Int,
    val download_limit: Int,
    val download_type: String,
    val downloadable: Boolean,
    val external_url: String,
    val featured: Boolean,
    val id: Int,
    val images: List<Image>,
    val in_stock: Boolean,
    val manage_stock: Boolean,
    val menu_order: Int,
    val name: String,
    val on_sale: Boolean,
    val parent_id: Int,
    val permalink: String,
    val price: String,
    val price_html: String,
    val purchasable: Boolean,
    val purchase_note: String,
    val rating_count: Int,
    val regular_price: String,
    val related_ids: List<Int>,
    val reviews_allowed: Boolean,
    val sale_price: String,
    val shipping_class: String,
    val shipping_class_id: Int,
    val shipping_required: Boolean,
    val shipping_taxable: Boolean,
    val short_description: String,
    val sku: String,
    val slug: String,
    val sold_individually: Boolean,
    val status: String,
    val stock_quantity: Int,
    val tags: List<Tag>,
    val tax_class: String,
    val tax_status: String,
    val total_sales: Int,
    val type: String,
    val upsell_ids: List<Int>,
    val virtual: Boolean,
    val weight: String,
    val yoast_head: String
) : Parcelable