package nicetomeowyou.th.mobile.tp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import nicetomeowyou.th.mobile.tp.databinding.ActivityMainBinding
import nicetomeowyou.th.mobile.tp.databinding.ActivityProductDetailsBinding
import nicetomeowyou.th.mobile.tp.services.api.ServiceClient
import nicetomeowyou.th.mobile.tp.services.models.Request
import retrofit2.HttpException

class ProductDetailsActivity : AppCompatActivity() {
    private val binding: ActivityProductDetailsBinding by lazy {
        ActivityProductDetailsBinding.inflate(layoutInflater)
    }
    private var type: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val productName = intent.getStringExtra("productName")
        val productType = intent.getIntExtra("productType", 0)
        type = productType
        binding.ProductNameTitle.text = productName
        binding.buttonBack.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        getProductDetails()

    }

    private fun getProductDetails() {
        runBlocking {
            try {

                if (type == 1 ){
                    val result = async(Dispatchers.IO) {
                        return@async ServiceClient.create().getIphoneDetails(Request(""))
                    }
                    var response = result.await()
                    binding.textViewProductDescription.text = response.productDetail
                    Picasso.get().load(response.productImage).into( binding.imageViewProductDetail)
                } else if(type == 2) {
                    val result = async(Dispatchers.IO) {
                        return@async ServiceClient.create().getIpadDetails(Request(""))
                    }
                    var response = result.await()
                    binding.textViewProductDescription.text = response.productDetail
                    Picasso.get().load(response.productImage).into( binding.imageViewProductDetail)

                } else {
                    val result = async(Dispatchers.IO) {
                        return@async ServiceClient.create().getAppleWatchDetails(Request(""))
                    }
                    var response = result.await()
                    binding.textViewProductDescription.text = response.productDetail
                    Picasso.get().load(response.productImage).into( binding.imageViewProductDetail)

                }


            } catch (ex: Exception) {
                if (ex is HttpException) {
                    Log.e("HttpException", "(${ex.code()}): $ex")
                } else {
                    Log.e("HttpException", ex.toString())
                }
            }

        }

    }
}