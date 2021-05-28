package com.example.makvicautici.api.pagingSource

import androidx.paging.PagingSource
import com.example.makvicautici.api.WordpressApi
import com.example.makvicautici.api.responses.products.Product
import retrofit2.HttpException
import java.io.IOException

private const val WORDPRESS_STARTING_PAGE_INDEX = 1

class WordpressPagingSource(
    private val wordpressApi: WordpressApi,
    private val query: String? = null,
    private val manufacturer: String? = null,
    private val make: String? = null,
    private val scale: String? = null,

    private val orderby: String? = null,
    private val order: String? = null
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        val position = params.key ?: WORDPRESS_STARTING_PAGE_INDEX

        return try {
            val response = wordpressApi.getProducts(
                query = query,
                manufacturer = manufacturer,
                make = make,
                scale = scale,
                orderBy = orderby,
                order = order,
                Page = position,
                perPage = params.loadSize
            )
            LoadResult.Page(
                data = response,
                prevKey = if (position == WORDPRESS_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (response.isEmpty()) null else position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }
}