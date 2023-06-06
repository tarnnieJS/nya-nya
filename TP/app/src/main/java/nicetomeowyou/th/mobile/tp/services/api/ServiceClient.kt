package nicetomeowyou.th.mobile.tp.services.api

import nicetomeowyou.th.mobile.tp.services.api.endpoint.ServiceAPI
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ServiceClient {
    companion object {


        @JvmStatic
        fun createWithToken(token: String): ServiceAPI {

            val url = "https://private-18a32-iosadv.apiary-mock.com/"

            val logging = HttpInterceptor()
            logging.apply {
                level = HttpInterceptor.Level.BODY
            }

            val header =
                Interceptor { chain ->
                    chain.proceed(
                        if (token != null) {
                            chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .addHeader("Authorization", "$token")
                                .build()
                        } else {
                            chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .build()
                        }
                    )
                }

            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(header)
                .addInterceptor(logging)
                .connectTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)


            val retrofit = Retrofit.Builder()
                .addConverterFactory(
                    MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()))
                .client(okHttpBuilder.build())
                .baseUrl(url)
                .build()

            return retrofit.create(ServiceAPI::class.java)
        }

        @JvmStatic
        fun create(): ServiceAPI {
            val url = "https://private-18a32-iosadv.apiary-mock.com/"

            val logging = HttpInterceptor()
            logging.apply {
                level = HttpInterceptor.Level.BODY
            }

            val header =
                Interceptor { chain ->
                    chain.proceed(
                        chain.request().newBuilder()
                            .addHeader("Content-Type", "application/json")
                            .build()



                    )
                }

            val okHttpBuilder = OkHttpClient.Builder()
                .addInterceptor(header)
                .addInterceptor(logging)
                .connectTimeout(200, TimeUnit.SECONDS)
                .writeTimeout(200, TimeUnit.SECONDS)
                .readTimeout(200, TimeUnit.SECONDS)

            val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(
                    Moshi.Builder().add(
                        KotlinJsonAdapterFactory()
                    ).build()))
                .client(okHttpBuilder.build())
                .baseUrl(url)
                .build()


            return retrofit.create(ServiceAPI::class.java)
        }
    }
}
