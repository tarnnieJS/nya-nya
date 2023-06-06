package nicetomeowyou.th.mobile.tp.services.api.endpoint


import nicetomeowyou.th.mobile.tp.services.models.Product
import nicetomeowyou.th.mobile.tp.services.models.ProductDetails
import nicetomeowyou.th.mobile.tp.services.models.Request
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceAPI {

    @GET("products")
    suspend fun getProduct(): Product

    @POST("iphone")
    suspend fun getIphoneDetails(@Body data: Request): ProductDetails

    @POST("ipad")
    suspend fun getIpadDetails(@Body data: Request): ProductDetails

    @POST("applewatch")
    suspend fun getAppleWatchDetails(@Body data: Request): ProductDetails



}
