package hu.bme.aut.android.bank.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.android.bank.R
import hu.bme.aut.android.bank.data.priceitem.PriceItem
import hu.bme.aut.android.bank.databinding.ItemPriceBinding

class BankAdapter() : RecyclerView.Adapter<BankAdapter.BankViewHolder>(){

    private val items = mutableListOf<PriceItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = BankViewHolder(
        ItemPriceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun getItemCount(): Int = items.size

    inner class BankViewHolder(val binding: ItemPriceBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: BankViewHolder, position: Int) {
        val priceItem = items[position]
        holder.binding.ivIcon.setImageResource(getImageResource(priceItem.category))
        holder.binding.itPrice.text = "${priceItem.price.toString()} Ft"
        holder.binding.itCategory.text = priceItem.category.toString()
        holder.binding.itType.text = priceItem.type
    }

    fun addItem(item: PriceItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<PriceItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }

    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }


    interface PriceItemClickListener {
        fun onItemChanged(item: PriceItem)
    }

    @DrawableRes()
    private fun getImageResource(category: PriceItem.Category): Int {
        return when (category) {
            PriceItem.Category.Bills -> R.drawable.ic_bills
            PriceItem.Category.Home -> R.drawable.ic_home
            PriceItem.Category.Transportation -> R.drawable.ic_transport_
            PriceItem.Category.Clothing -> R.drawable.ic_clothing
            PriceItem.Category.Groceries -> R.drawable.ic_groceries
            PriceItem.Category.Health -> R.drawable.ic_health
            PriceItem.Category.Entertainment -> R.drawable.ic_ent
            PriceItem.Category.Other -> R.drawable.ic_other
            PriceItem.Category.Income -> R.drawable.ic_income
        }
    }
}
