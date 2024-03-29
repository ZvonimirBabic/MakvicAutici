package com.makvic.makvicautici.api

import com.makvic.makvicautici.api.responses.makes.Makes
import com.makvic.makvicautici.api.responses.manufacturers.Manufacturers
import com.makvic.makvicautici.api.responses.products.Products
import com.makvic.makvicautici.api.responses.scales.Scales
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WordpressApi {

    companion object {
        const val BASE_URL = "https://makvic-autici.hr/wp-json/"
    }

    @GET("wco/v1/products")
    suspend fun getProducts(
        @Query("search") query: String? = null,
        @Query("filter[pa_proizvodac]") manufacturer: String? = null,
        @Query("filter[pa_marka]") make: String? = null,
        @Query("filter[pa_mjerilo]") scale: String? = null,

        @Query("orderby") orderBy: String? = null,
        @Query("order") order: String? = null,
        @Query("page") Page: Int? = null,
        @Query("per_page") perPage: Int? = null,
        @Query("status") published: String? = "publish"
    ): Products

    @GET("wco/v1/manufacturers")
    fun getManufacturers(
        @Query("page") Page: Int? = 1,
        @Query("per_page") perPage: Int? = 100,
        @Query("hide_empty") empty: String= "true"
    ): Call<Manufacturers>

    @GET("wco/v1/makes")
    fun getMakes(
        @Query("page") Page: Int? = 1,
        @Query("per_page") perPage: Int? = 100,
        @Query("hide_empty") empty: String= "true"
    ): Call<Makes>

    @GET("wco/v1/scales")
    fun getScales(
        @Query("page") Page: Int? = 1,
        @Query("per_page") perPage: Int? = 100,
        @Query("hide_empty") empty: String= "true"
    ): Call<Scales>


}
