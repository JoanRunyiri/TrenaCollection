package com.kanyideveloper.letsgoshopping

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_checkout.shopping_cart
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.cart_items_row.*

class CheckoutActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var mDatabase: FirebaseDatabase
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mReference: DatabaseReference
    private var cartList: ArrayList<CartItem>? = null
    private lateinit var adapter: CartItemsAdapter
    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)



        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        mDatabase = FirebaseDatabase.getInstance()
        mReference = mDatabase.getReference("cart_items")
        cartList = arrayListOf()

        mReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (i in snapshot.children) {
                        val item = i.getValue(CartItem::class.java)
                        cartList!!.add(item!!)
                    }
                    var sum = 0.0
                    var vat = 0.0
                    var total = 0.0
                    for (itm: CartItem in cartList!!) {
                        sum += itm.itemPrice.toDouble() * itm.counter
                        vat += itm.vat.toDouble()
                        total = sum + vat
                    }
                    subtotal_value.text = sum.toString()
                    vat_value.text = vat.toString()
                    total_value.text = total.toString()

                    initialiseRecycler()
                } else {
                    subtotal_value.text = "0.0"
                    vat_value.text = "0.0"
                    total_value.text = "0.0"
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun increaseToCart(item: CartItem, position: Int) {
        item.counter += 1
        val mQuery: Query = mReference.orderByChild("itemName").equalTo(item.itemName)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (mSnapshot in snapshot.children) {
                    mSnapshot.child("counter").ref.setValue(item.counter)
                    adapter.clear()
                    incrementCounter()
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun decreaseFromCart(item: CartItem, position: Int) {
        if (item.counter == 1) {
            minus_cart_item.isClickable = false
        }
        item.counter -= 1
        val mQuery: Query = mReference.orderByChild("itemName").equalTo(item.itemName)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (mSnapshot in snapshot.children) {
                    mSnapshot.child("counter").ref.setValue(item.counter)
                    adapter.clear()
                    decrementCounter(1)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    override fun deleteFromCart(item: CartItem, position: Int) {
        val mQuery: Query = mReference.orderByChild("itemName").equalTo(item.itemName)
        mQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (mSnapshot in snapshot.children) {
                        val itmCount = item.counter
                        mSnapshot.ref.removeValue()
                        removeItem(position)
                        adapter.clear()
                        decrementCounter(itmCount)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun incrementCounter() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(
            Utils.counter.toString(),
            sharedPreferences.getInt(Utils.counter.toString(), 0) + 1
        )
        editor.apply()
        editor.commit()
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        items_num.text = sharedIdValue.toString()
    }

    private fun decrementCounter(num: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(
            Utils.counter.toString(),
            sharedPreferences.getInt(Utils.counter.toString(), 0) - num
        )
        editor.apply()
        editor.commit()
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        items_num.text = sharedIdValue.toString()
    }

    private fun removeItem(position: Int) {
        cartList?.removeAt(position)
        adapter.notifyItemRemoved(position)
    }

    private fun initialiseRecycler() {
        mRecyclerView = findViewById(R.id.cart_recycler)
        mRecyclerView.hasFixedSize()
        mRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        adapter = CartItemsAdapter(applicationContext, cartList!!, this@CheckoutActivity)
        mRecyclerView.adapter = adapter


        btn_checkout.setOnClickListener {

            val simToolKitLaunchIntent = this@CheckoutActivity.getPackageManager()
                .getLaunchIntentForPackage("com.android.stk");
            if (simToolKitLaunchIntent != null) {
                startActivity(simToolKitLaunchIntent);

            }


        }
    }
}
