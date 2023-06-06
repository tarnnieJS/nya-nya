package nicetomeowyou.th.mobile.tp.services.models


import com.squareup.moshi.Json

data class Product(
    @Json(name = "banners")
    val banners: List<String>,
    @Json(name = "products")
    val products: List<ProductX>
)