package com.example.sshop_sneakershop.Order.models

import android.util.Log
import com.example.sshop_sneakershop.Product.models.Product
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class OrderModel {
    private val db = Firebase.firestore
    private val userId = Firebase.auth.currentUser?.uid

    @Throws(Exception::class)
    suspend fun getAllOrders(): ArrayList<Order> {
        val orders = ArrayList<Order>()
        try {
            db.collection("order").whereEqualTo("userId", userId)
                .orderBy("startDate", Query.Direction.DESCENDING).get().await().forEach {
                val order = it.toObject(Order::class.java)
                orders.add(order)
            }
        } catch (e: Exception) {
            throw e
        }

        return orders
    }

    suspend fun getOrderById(id: String): Order {
        var order = Order()
        val products = ArrayList<Product>()
        var product = Product()
        try {
            db.collection("order").document(id).get().await()
                .toObject(Order::class.java)
                .let {
                    order = it!!
                }
            // name, description, price, image, quantity
            for (item in order.cart) {
                db.collection("product").document(item.id).get().await()
                    .toObject(Product::class.java)
                    .let {
                        product = it!!
                        val quantity = order.cart.find { it.id == product.id }!!.quantity
                        val price = order.cart.find { it.id == item.id }!!.price
                        Log.i("cart-value", "${quantity} - ${price}")
                        product.quantity = order.cart.find { it.id == item.id }!!.quantity
                        product.price = order.cart.find { it.id == item.id }!!.price
                        products.add(product)
                    }
            }
            order.cart.clear()
            order.cart.addAll(products)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return order
    }

    @Throws(Exception::class)
    suspend fun createOrder(order: Order): Order {
        try {
            // Add order to database and get document's id
            val id = db.collection("order").add(order).await().id
            db.collection("order").document(id).update("id", id)
            order.id = id

            // Sold product
            var stock: ArrayList<Int>
            for (product in order.cart) {
                // Update stock
                db.collection("product").document(product.id).get().await().let {
                    stock = it.data?.get("stock") as ArrayList<Int>

                    when (product.size) {
                        "US7" -> {
                            if (stock[0] > 0) {
                                val remainingStock = stock[0] - product.quantity
                                if (remainingStock < 0) {
                                    throw Exception("Remaining stock is ${stock[0]}, please reduce the quantity")
                                } else {
                                    stock[0] = remainingStock
                                }
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US8" -> {
                            if (stock[1] > 0) {
                                val remainingStock = stock[1] - product.quantity
                                if (remainingStock < 0) {
                                    throw Exception("Remaining stock is ${stock[1]}, please reduce the quantity")
                                } else {
                                    stock[1] = remainingStock
                                }
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US9" -> {
                            if (stock[2] > 0) {
                                val remainingStock = stock[2] - product.quantity
                                if (remainingStock < 0) {
                                    throw Exception("Remaining stock is ${stock[2]}, please reduce the quantity")
                                } else {
                                    stock[2] = remainingStock
                                }
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        "US10" -> {
                            if (stock[3] > 0) {
                                val remainingStock = stock[3] - product.quantity
                                if (remainingStock < 0) {
                                    throw Exception("Remaining stock is ${stock[3]}, please reduce the quantity")
                                } else {
                                    stock[3] = remainingStock
                                }
                            } else {
                                throw Exception("Out of stock")
                            }
                        }
                        else -> {
                            throw Exception("Size not found")
                        }
                    }
                }

                // Decrease product's stock
                db.collection("product").document(product.id).update("stock", stock)

                // Create sold product
                db.collection("sold_product").whereEqualTo("productId", product.id).get().await().let {
                    if (it.isEmpty) {
                        db.collection("sold_product").add(mapOf(
                            "createdAt" to Timestamp.now(),
                            "productId" to product.id,
                            "quantity" to product.quantity,
                            "total" to product.price,
                            "brand" to product.brand,
                        ))
                    } else {
                        db.collection("sold_product").document(it.documents[0].id).update(
                            "quantity",
                            FieldValue.increment(product.quantity.toLong()),
                            "total",
                            FieldValue.increment(product.price.toLong() * product.quantity.toLong())
                        )
                    }
                }
            }

            // Empty product list in cart when order is created
            db.collection("cart").whereEqualTo("userId", userId).get().await().forEach {
                it.reference.update("productList", ArrayList<Product>()).await()
            }
        } catch (e: Exception) {
            throw e
        }

        return order
    }
}