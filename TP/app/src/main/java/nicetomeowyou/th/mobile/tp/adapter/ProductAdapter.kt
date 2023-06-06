package nicetomeowyou.th.mobile.tp.adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import nicetomeowyou.th.mobile.tp.databinding.ItemProductBinding
import nicetomeowyou.th.mobile.tp.services.models.ProductX

class ProductAdapter(private val productList: List<ProductX> , private var onClickListener: OnClickListener?)
    : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductBinding.inflate(inflater, parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return productList.size
    }
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int, model: ProductX)
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: ProductX) {
            Picasso.get().load(product.productImage).into( binding.imageViewProduct)
            binding.textViewProductName.text = product.productName
            binding.textViewProductDescription.text = product.productTopic
            binding.itemLayout.setOnClickListener{
                onClickListener?.onClick(position,product)
            }


        }

    }
}
