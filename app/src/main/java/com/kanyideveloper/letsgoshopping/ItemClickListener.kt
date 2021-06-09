package com.kanyideveloper.letsgoshopping

interface ItemClickListener {
        fun increaseToCart(item: CartItem, position: Int)
        fun decreaseFromCart(item : CartItem, position : Int)
        fun deleteFromCart(item : CartItem, position : Int)
}
