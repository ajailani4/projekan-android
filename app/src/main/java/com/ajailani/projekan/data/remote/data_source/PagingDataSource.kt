package com.ajailani.projekan.data.remote.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ajailani.projekan.data.remote.dto.response.BaseResponse
import retrofit2.Response

class PagingDataSource<T: Any>(
    private inline val serviceMethod: suspend (page: Int, size: Int) -> Response<BaseResponse<List<T>>>
) : PagingSource<Int, T>() {
    override suspend fun load(params: LoadParams<Int>) =
        try {
            val currentPage = params.key ?: 1
            val response = serviceMethod(currentPage, 10)
            val data = response.body()?.data ?: emptyList()

            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (data.isNotEmpty()) currentPage.plus(1) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    override fun getRefreshKey(state: PagingState<Int, T>) = state.anchorPosition
}