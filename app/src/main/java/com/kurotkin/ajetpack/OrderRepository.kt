package com.kurotkin.ajetpack

import io.reactivex.Observable

interface OrderRepository {
    fun getOrders(): Observable<List<Order?>>
}