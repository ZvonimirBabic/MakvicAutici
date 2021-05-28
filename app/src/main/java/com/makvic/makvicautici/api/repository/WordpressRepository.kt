package com.makvic.makvicautici.api.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.makvic.makvicautici.api.WordpressApi
import com.makvic.makvicautici.api.pagingSource.WordpressPagingSource
import com.makvic.makvicautici.api.responses.makes.Makes
import com.makvic.makvicautici.api.responses.manufacturers.Manufacturers
import com.makvic.makvicautici.api.responses.scales.Scales
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WordpressRepository @Inject constructor(private val wordpressApi: WordpressApi) {

    fun getProductsResults(
        query: String? = null,
        manufacturer: String? = null,
        make: String? = null,
        scale: String? = null,
        orderBy: String? = null,
        order: String? = null
    ) = Pager(
        config = PagingConfig(
            pageSize = 35,
            maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
            initialLoadSize = 35,
            enablePlaceholders = true
        ),
        pagingSourceFactory = {
            WordpressPagingSource(wordpressApi, query, manufacturer, make, scale, orderBy, order)
        }
    ).liveData

    fun fillManufacturers(): MutableLiveData<Manufacturers> {
        val call = wordpressApi.getManufacturers()
        var data = MutableLiveData<Manufacturers>()
        call.enqueue(object : Callback<Manufacturers> {
            override fun onFailure(call: Call<Manufacturers>?, t: Throwable?) {
                Log.v("Repository", "fillManufacturers() -> onFailure")
            }

            override fun onResponse(
                call: Call<Manufacturers>?,
                response: Response<Manufacturers>?
            ) {
                data.value = response!!.body()!!
                Log.v("Repository", "fillManufacturers() -> onResponse")
            }
        })
        return data
    }

    fun fillMakes(page: Int?): MutableLiveData<Makes> {
        val call = wordpressApi.getMakes(Page = page)
        var data = MutableLiveData<Makes>()
        call.enqueue(object : Callback<Makes> {
            override fun onFailure(call: Call<Makes>?, t: Throwable?) {
                Log.v("Repository", "fillMakes() -> onFailure")
            }

            override fun onResponse(call: Call<Makes>?, response: Response<Makes>?) {
                data.value = response!!.body()!!
                Log.v("Repository", "fillMakes() -> onResponse")
            }
        })
        return data
    }

    fun fillScales(): MutableLiveData<Scales> {
        val call = wordpressApi.getScales()
        var data = MutableLiveData<Scales>()
        call.enqueue(object : Callback<Scales> {
            override fun onFailure(call: Call<Scales>?, t: Throwable?) {
                Log.v("Repository", "fillScales() -> onFailure")
            }

            override fun onResponse(call: Call<Scales>?, response: Response<Scales>?) {
                data.value = response!!.body()!!
                Log.v("Repository", "fillScales() -> onResponse")
            }
        })
        return data
    }
}
