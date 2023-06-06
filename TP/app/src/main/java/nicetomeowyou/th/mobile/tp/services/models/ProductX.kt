package nicetomeowyou.th.mobile.tp.services.models


import com.squareup.moshi.Json

data class ProductX(
    @Json(name = "product_image")
    val productImage: String,
    @Json(name = "product_name")
    val productName: String,
    @Json(name = "product_topic")
    val productTopic: String,
    @Json(name = "product_url")
    val productUrl: String
)