package com.kanyideveloper.letsgoshopping


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private lateinit var adapter: ItemsAdapter


    var itemList: ArrayList<Item>? = null

    private val sharedPrefFile = "kotlinsharedpreference"
    private lateinit var sharedPreferences: SharedPreferences
    private var sharedIdValue: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        login.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            //start the signup activity
            startActivity(intent)
        }



//        shopping_cart.setOnClickListener {
//            val intent = Intent(this, CartItem::class.java)
//            //start the cart activity
//            startActivity(intent)
//        }


//        fruitbowl.setOnClickListener {
//            val intent = Intent(this, CheckoutActivity::class.java)
//            //start the signup activity
//            startActivity(intent)
//        }
//    }
//}


//        mFirebaseDatabase = FirebaseDatabase.getInstance()
//        mRecyclerView = findViewById(R.id.recyclerView)
//
//
        sharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        checkCounter()

        shopping_cart.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }

        fruitbowl.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
        spoonset.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
        storagebasket.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
        cupset.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
        calender.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
        airfryer.setOnClickListener {
            startActivity(Intent(applicationContext, CheckoutActivity::class.java))
        }
    }


    //
//        search_item_bar.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
//            override fun afterTextChanged(editable: Editable) {
//                filter(editable.toString())
//            }
//        })
//
//        itemList = arrayListOf()
//        reference = mFirebaseDatabase.getReference("trena-collection-default-rtdb")
////        reference = mFirebaseDatabase.getReference("items")
//
//        reference.addValueEventListener(object : ValueEventListener {
//            override fun onCancelled(p0: DatabaseError) {
//            }
//
//            override fun onDataChange(p0: DataSnapshot) {
//                if (p0.exists()) {
//                    for (i in p0.children) {
//                        val itm = i.getValue(Item::class.java)
//                        itemList!!.add(itm!!)
//                        shimmerFrameLayout.visibility = View.GONE
//                        recyclerView.visibility = View.VISIBLE
//                    }
//
//                    val adapter = ItemsAdapter(applicationContext, itemList!!)
//                    mRecyclerView.adapter = adapter
//                }
//            }
//        })
//    }

    private fun checkCounter() {
        val sharedIdValue = sharedPreferences.getInt(Utils.counter.toString(), 0)
        if (sharedIdValue == 0) {
            cart_badge.visibility = View.INVISIBLE
        } else if (sharedIdValue >= 1) {
            cart_badge.text = sharedIdValue.toString()
            cart_badge.visibility = View.VISIBLE
        }
    }

    fun filter(e: String) {
        val filteredList = ArrayList<Item>()
        for (item in itemList!!) {
            if (item.itemName?.toLowerCase()?.contains(e.toLowerCase())!!) {
                filteredList.add(item)
            }
        }
        adapter = ItemsAdapter(applicationContext, filteredList!!)
        mRecyclerView.adapter = adapter
    }
}

//    override fun onPause() {
//        super.onPause()
//        shimmerFrameLayout.stopShimmerAnimation()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        shimmerFrameLayout.startShimmerAnimation()
//        checkCounter()
//    }
