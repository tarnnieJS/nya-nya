package nicetomeowyou.th.mobile.tp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import nicetomeowyou.th.mobile.tp.adapter.BannerAdapter
import nicetomeowyou.th.mobile.tp.adapter.ProductAdapter
import nicetomeowyou.th.mobile.tp.databinding.ActivityMainBinding
import nicetomeowyou.th.mobile.tp.services.api.ServiceClient
import nicetomeowyou.th.mobile.tp.services.models.ProductX


import retrofit2.HttpException


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private var listBannerImage = listOf<String>()
    private var listProduct = listOf<ProductX>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val displayName = intent.getStringExtra("displayName")
        val googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN)
        binding.textViewUserName.text = "สวัสดีคุณ ${displayName}"
        binding.buttonLogOut.setOnClickListener {
            googleSignInClient.signOut()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this@MainActivity, AuthActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(applicationContext, "something wrong", Toast.LENGTH_SHORT).show()
                    }
                }
        }


    }

    override fun onStart() {
        super.onStart()
        getProductData()
    }

    private fun initialBanner(listBannerImage: List<String>) {
        val banner = binding.viewPagerBanner
        banner.isUserInputEnabled = true
        val banners: List<String> = listBannerImage
        val adapter = BannerAdapter(banners)
        banner.adapter = adapter

        banner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setCurrentIndicator(position)
            }
        })

    }

    private fun getProductData() {
        runBlocking {
            try {
                val result = async(Dispatchers.IO) {
                    return@async ServiceClient.create().getProduct()
                }
                var response = result.await()
                if (response.products.isNotEmpty() && response.banners.isNotEmpty()) {
                    listBannerImage = response.banners
                    setupIndicators(response.banners.size)
                    setCurrentIndicator(0)
                    listProduct = response.products
                    setUpProductList(listProduct)
                    initialBanner(listBannerImage.reversed())

                } else {

                }

            } catch (ex: Exception) {

                if (ex is HttpException) {
                    Log.e("HttpException", "(${ex.code()}): $ex")
                } else {

                }
            }

        }
    }

    private fun setUpProductList(listProduct: List<ProductX>) {
        var recyclerView = binding.recycleViewProduct
        recyclerView.layoutManager = LinearLayoutManager(this)
        var adapter = ProductAdapter(listProduct, object : ProductAdapter.OnClickListener {
            override fun onClick(position: Int, model: ProductX) {
                intentActivity(model.productName)

            }

        })
        recyclerView.adapter = adapter

        val swipeRefreshLayout = binding.swipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            getProductData()
            swipeRefreshLayout.isRefreshing = false
        }

    }

    private fun intentActivity(productName: String) {
        val intent = Intent(this@MainActivity, ProductDetailsActivity::class.java)
        val productType = if (productName == "iPhone 11 Pro") {
            1
        } else if (productName == "iPad Pro") {
            2
        } else {
            3
        }
        intent.putExtra("productName", productName)
        intent.putExtra("productType", productType)
        startActivity(intent)
    }

    private fun setupIndicators(i: Int) {
        binding.indicatorContainer.removeAllViews()
        val indicators = arrayOfNulls<ImageView>(i)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(2, 8, 2, 8)
        for (x in indicators.indices) {
            indicators[x] = ImageView(applicationContext)
            indicators[x].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.permission_indictor_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            binding.indicatorContainer.addView(indicators[x])
        }
    }

    private fun setCurrentIndicator(index: Int) {
        val childCount = binding.indicatorContainer.childCount
        for (i in 0 until childCount) {
            val imageView = binding.indicatorContainer[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.permission_indictor_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.permission_indictor_inactive
                    )
                )

            }
        }
    }


}


