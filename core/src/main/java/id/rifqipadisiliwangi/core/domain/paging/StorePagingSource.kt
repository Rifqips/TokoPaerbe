package id.rifqipadisiliwangi.core.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseItemDataStore
import id.rifqipadisiliwangi.core.domain.usecase.StoreInteractor

class StorePagingSource(
    private val storeInteractor: StoreInteractor,
) : PagingSource<Int, ResponseItemDataStore>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ResponseItemDataStore> {
        try {
            val currentPage = params.key ?: 1

            val response = storeInteractor.productStore(
                searchItem = "",
                brandItem = "",
                sortItem = "",
                limitItem = params.loadSize,
                pageItem = currentPage
            )
            if (response.code == 200) {
                val storeResponse = response.data
                storeResponse.let {
                    val store = it.items
                    val prevKey = if (currentPage == 1) null else currentPage - 1
                    val nextKey = if (currentPage == it.totalPages) null else currentPage + 1
                    return LoadResult.Page(store, prevKey, nextKey)
                }
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
        return LoadResult.Error(Exception("Unknown error"))

    }
    override fun getRefreshKey(state: PagingState<Int, ResponseItemDataStore>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}