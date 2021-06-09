package com.kanyideveloper.letsgoshopping

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ItemsAdapter(private val context: Context, private var itemList: ArrayList<Item>) :
        RecyclerView.Adapter<ItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.shopping_item_row, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        Glide.with(context)
            .load(item.itemImage)
//                .load(item.itemImage)
                .into(holder.mImage)



        holder.mName.text = item.itemName
        holder.mPrice.text = "KSh ${item.itemPrice}"

        holder.card.setOnClickListener {
            val intent = Intent(context, ItemDetailsActivity::class.java)
            intent.putExtra("ITEM_IMAGE",item.itemImage)
            intent.putExtra("ITEM_NAME",item.itemName)
            intent.putExtra("ITEM_PRICE",item.itemPrice)
            intent.putExtra("ITEM_OLD_PRICE",item.itemOldPrice)
            intent.putExtra("ITEM_VAT",item.vat)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val mImage: ImageView = view.findViewById(R.id.item_image)
        val mName: TextView = view.findViewById(R.id.item_name)
        val card: CardView = view.findViewById(R.id.shopping_card)
        val mPrice: TextView = view.findViewById(R.id.item_current_price)
    }

    fun filterList(filteredList: java.util.ArrayList<Item>) {
        itemList = filteredList
        notifyDataSetChanged()
    }
}



