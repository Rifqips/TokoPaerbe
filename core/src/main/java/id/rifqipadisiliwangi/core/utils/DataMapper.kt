package id.rifqipadisiliwangi.core.utils

import id.rifqipadisiliwangi.core.data.network.api.model.detail.ResponseDetailStore
import id.rifqipadisiliwangi.core.data.network.api.model.fulfillment.FulfillmentItem
import id.rifqipadisiliwangi.core.data.network.api.model.history.HistoryData
import id.rifqipadisiliwangi.core.data.network.api.model.login.ResponseLoginItem
import id.rifqipadisiliwangi.core.data.network.api.model.profile.DataResponseRegisterProfile
import id.rifqipadisiliwangi.core.data.network.api.model.rating.ResponseRating
import id.rifqipadisiliwangi.core.data.network.api.model.refresh.ResponseRefreshToken
import id.rifqipadisiliwangi.core.data.network.api.model.register.ResponseRegisterItem
import id.rifqipadisiliwangi.core.data.network.api.model.review.DataResponseReviewItem
import id.rifqipadisiliwangi.core.data.network.api.model.search.ResponseSearch
import id.rifqipadisiliwangi.core.data.network.api.model.store.ResponseStore
import id.rifqipadisiliwangi.core.domain.model.detail.StoreDetailData
import id.rifqipadisiliwangi.core.domain.model.detail.toProductVarianList
import id.rifqipadisiliwangi.core.domain.model.fulfillment.ItemFulfillment
import id.rifqipadisiliwangi.core.domain.model.history.DataHistory
import id.rifqipadisiliwangi.core.domain.model.history.toItemsHistoryList
import id.rifqipadisiliwangi.core.domain.model.login.LoginResponse
import id.rifqipadisiliwangi.core.domain.model.profile.RequestRegisterProfileItem
import id.rifqipadisiliwangi.core.domain.model.rating.RatingResponse
import id.rifqipadisiliwangi.core.domain.model.refresh.TokenRefreshResponse
import id.rifqipadisiliwangi.core.domain.model.register.RegisterResponseItem
import id.rifqipadisiliwangi.core.domain.model.review.DataResponseReviewItemDataResponse
import id.rifqipadisiliwangi.core.domain.model.search.SearchResponse
import id.rifqipadisiliwangi.core.domain.model.store.ResponseStoreItem

object DataMapper {

    fun ResponseLoginItem.toLoginResponse() = LoginResponse(
        code = this.code,
        data = this.data,
        message = this.message,
    )

    fun DataResponseRegisterProfile.toRegisterProfileResponse() = RequestRegisterProfileItem(
        userName = this.userName.orEmpty(),
        userImage = this.userImage.orEmpty()
    )

    fun ResponseRegisterItem.toRegisterResponse() = RegisterResponseItem(
        code = this.code,
        data = this.data,
        message = this.message
    )

    fun ResponseRefreshToken.toRefreshToken() = TokenRefreshResponse(
        code = this.code,
        data = this.data,
        message = this.message
    )


    fun ResponseStore.toResponseStore() = ResponseStoreItem(
        code = this.code,
        data = this.data,
        message = this.message
    )

    fun ResponseDetailStore.DataResponseDetail.toDetailStoreMapping() = StoreDetailData(
        brand = this.brand,
        description = this.description,
        image = this.image,
        productId = this.productId,
        productName = this.productName,
        productPrice = this.productPrice,
        productRating = this.productRating,
        productVariant = this.productVariant.toProductVarianList(),
        sale = this.sale,
        stock = this.stock,
        store = this.store,
        totalRating = this.totalRating,
        totalReview = this.totalReview,
        totalSatisfaction = this.totalSatisfaction,
    )
    fun ResponseSearch.toSearchResponse() = SearchResponse(
        code = this.code,
        data = this.data.toSearchList(),
        message = this.message
    )

    fun List<String>.toSearchList() = this.map { it }
    fun DataResponseReviewItem.toReviewProductMapper() = DataResponseReviewItemDataResponse(
        userImage = this.userImage,
        userName = this.userName,
        userRating = this.userRating,
        userReview = this.userReview
    )

    fun Collection<DataResponseReviewItem>.toReviewList() = this.map { it.toReviewProductMapper() }

    fun HistoryData.toHistoryMapper() = DataHistory(
        date = this.date,
        image = this.image,
        invoiceId = this.invoiceId,
        items = this.items.toItemsHistoryList(),
        name = this.name,
        payment = this.payment,
        rating = this.rating ?: 0,
        review = this.review.orEmpty(),
        status = this.status,
        time = this.time,
        total = this.total
    )

    fun Collection<HistoryData>.toHistoryList() = this.map { it.toHistoryMapper() }




    fun FulfillmentItem.toFulfillmentMapper() = ItemFulfillment(
        productId = this.productId,
        variantName = this.variantName,
        quantity = this.quantity,
    )

    fun Collection<FulfillmentItem>.toFullfilmentList() = this.map { it.toFulfillmentMapper() }

    fun ResponseRating.toRatingMapper() = RatingResponse(
        code = this.code,
        message = this.message
    )
}