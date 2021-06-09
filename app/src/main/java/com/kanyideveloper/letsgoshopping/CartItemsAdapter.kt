package com.kanyideveloper.letsgoshopping

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CartItemsAdapter(private val context : Context, private val itmList : ArrayList<CartItem>, private val itemClickListener : ItemClickListener)
    : RecyclerView.Adapter<CartItemsAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent : ViewGroup, viewType : Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.cart_items_row, parent, false))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder : ViewHolder, position : Int) {
        val itm = itmList[position]

        Glide.with(context)
                .load(itm.itemImage)
                .into(holder.itmImage)
        holder.itmName.text = itm.itemName

        holder.itmPrice.text = "KSh. ${itm.itemPrice}"

        holder.itemNumber.text = itm.counter.toString()

        holder.deleteItem.setOnClickListener {
            itemClickListener.deleteFromCart(itmList[position], position)
        }

        holder.addItem.setOnClickListener {
            itemClickListener.increaseToCart(itmList[position], position)
        }

        holder.minusItem.setOnClickListener {

            itemClickListener.decreaseFromCart(itmList[position], position)
        }

    }

    override fun getItemCount(): Int {
        return itmList.size
    }

    fun clear() {
        val size : Int = itmList.size
        itmList.clear()
        notifyItemRangeRemoved(0, size)
    }

    class ViewHolder(view : View) : RecyclerView.ViewHolder(view){
        val itmImage: ImageView = view.findViewById(R.id.cart_item_image)
        val itmName: TextView = view.findViewById(R.id.cart_item_name)
        val itmPrice: TextView = view.findViewById(R.id.cart_item_price)
        val deleteItem: ImageView = view.findViewById(R.id.delete_cart_item)
        val addItem: ImageView = view.findViewById(R.id.add_cart_item)
        val minusItem: ImageView = view.findViewById(R.id.minus_cart_item)
        val itemNumber: TextView = view.findViewById(R.id.items_num)
    }
}