package com.kurotkin.ajetpack

import androidx.annotation.NonNull
import io.reactivex.Scheduler
import io.reactivex.observers.DisposableObserver

class OrderListPresenter(var orderRepository: OrderRepository?, var workScheduler: Scheduler?, var resultScheduler: Scheduler?) :
    OrderListContract.Presenter, BasePresenter<OrderListContract.View>() {

    override fun refresh() {
        if (getView() == null) return
        getView()?.showProgress()
        orderRepository!!.getOrders()
            .subscribeOn(workScheduler)
            .observeOn(resultScheduler)
            .subscribe(object : DisposableObserver<List<Order?>?>() {
                override fun onNext(@NonNull orders: List<Order?>) {
                    if (getView() == null) return
                    getView()?.hideProgress()
                    getView()?.showOrders(orders)
                }

                override fun onError(@NonNull e: Throwable) {
                    if (getView() == null) return
                    getView()?.hideProgress()
                    getView()?.showError(e.message)
                }

                override fun onComplete() {}
            })
    }

}