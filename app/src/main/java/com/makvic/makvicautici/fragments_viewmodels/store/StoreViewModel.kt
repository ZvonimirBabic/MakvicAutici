package com.makvic.makvicautici.fragments_viewmodels.store

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.makvic.makvicautici.R
import com.makvic.makvicautici.api.repository.WordpressRepository
import com.makvic.makvicautici.api.responses.makes.Makes
import com.makvic.makvicautici.api.responses.products.Product
import com.makvic.makvicautici.api.responses.products.Products
import dagger.hilt.android.qualifiers.ApplicationContext
import io.paperdb.Paper

class StoreViewModel @ViewModelInject constructor(
    private val repository: WordpressRepository,
    @ApplicationContext application: Context
) :
    ViewModel() {
    // TODO: Implement the ViewModel

    companion object {
        private val DEFAULT_VALUE = null
    }

    val wishlist = Paper.book().read("wishlist", Products())
    val wishlistID = Paper.book().read("wishlistID", ArrayList<Int>())

    val currentQuery: MutableLiveData<String?> = MutableLiveData(DEFAULT_VALUE)
    var currentManufacturer: String? = DEFAULT_VALUE
    var currentMake: String? = DEFAULT_VALUE
    var currentScale: String? = DEFAULT_VALUE
    var currentOrder: String? = DEFAULT_VALUE
    var currentOrderBy: String? = DEFAULT_VALUE
    var currentRadioButtonId: Int = R.id.radioButtonNewest

    // Manufacturers / Makes / Scales data
    var manufacturersList = repository.fillManufacturers()
    var makesListPage1 = repository.fillMakes(1)
    var makesListPage2 = repository.fillMakes(2)
    var scalesList = repository.fillScales()
    var makesList = MutableLiveData<Makes>()


    // Force refresh RecyclerView data
    var forceRefresh: MutableLiveData<String> = MutableLiveData("")
    private fun forceRefresh() {
        forceRefresh.value = forceRefresh.value
    }

    // UNTESTED
    var app = application
    var manufacturerPositionVM: Int = 0
    var makePositionVM: Int = 0
    var scalePositionVM: Int = 0

    // RecyclerView data
    val data = forceRefresh.switchMap {
        repository.getProductsResults(
            //query = currentQuery,
            manufacturer = currentManufacturer,
            make = currentMake,
            scale = currentScale,
            order = currentOrder,
            orderBy = currentOrderBy
        ).cachedIn(viewModelScope)
    }

    fun sort(selectedRadioButtonId: Int) {
        currentRadioButtonId = selectedRadioButtonId
        when (selectedRadioButtonId) {
            R.id.radioButtonMostExpensive -> {
                currentOrderBy = "price"
                currentOrder = "desc"
            }
            R.id.radioButtonLeastExpensive -> {
                currentOrderBy = "price"
                currentOrder = "asc"
            }
            R.id.radioButtonNewest -> {
                currentOrderBy = "date"
                currentOrder = "desc"
            }
            R.id.radioButtonOldest -> {
                currentOrderBy = "date"
                currentOrder = "asc"
            }
        }
        forceRefresh()
    }

    fun filter(
        selectedManufacturerSlug: String?,
        selectedMakeSlug: String?,
        selectedScaleSlug: String?,
        manufacturerPosition: Int,
        makePosition: Int,
        scalePosition: Int
    ) {
        currentManufacturer = selectedManufacturerSlug
        currentMake = selectedMakeSlug
        currentScale = selectedScaleSlug
        manufacturerPositionVM = manufacturerPosition
        makePositionVM = makePosition
        scalePositionVM = scalePosition
        currentRadioButtonId = R.id.radioButtonNewest
        currentOrderBy = "date"
        currentOrder = "desc"
        forceRefresh()
    }


    fun addToWishlist(product: Product) {
        wishlist.add(product)
        wishlistID.add(product.id)
        Paper.book().write("wishlist", wishlist)
        Paper.book().write("wishlistID", wishlistID)
    }

    fun removeFromWishlist(product: Product) {

        wishlist.removeAll { it.id == product.id }
        wishlistID.remove(product.id)
        Paper.book().write("wishlist", wishlist)
        Paper.book().write("wishlistID", wishlistID)

    }

}