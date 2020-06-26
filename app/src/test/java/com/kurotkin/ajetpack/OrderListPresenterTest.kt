package com.kurotkin.ajetpack

import io.reactivex.Observable.just
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import java.util.*


@RunWith(MockitoJUnitRunner::class)
class OrderListPresenterTest {

    lateinit var presenter: OrderListPresenter

    @Mock
    lateinit var mockOrderRepository: OrderRepository

    @Mock
    lateinit var mockView: OrderListContract.View

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = OrderListPresenter(mockOrderRepository, Schedulers.trampoline(), Schedulers.trampoline())
        presenter.attachView(mockView)
    }

    @Test
    fun refresh() {

    }

    @Test
    fun refreshSuccess() {
        val fakeOrders = getFakeOrderList()
        `when`(mockOrderRepository.getOrders()).thenReturn(just(fakeOrders))

        presenter.refresh()
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).showOrders(fakeOrders)
        verify(mockView, never()).showError(anyString())
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun refreshFailed() {
        val error = "Network error"
        `when`(mockOrderRepository.getOrders()).thenReturn(error(Exception(error)))
        presenter.refresh()
        verify(mockView).showProgress()
        verify(mockView).hideProgress()
        verify(mockView).showError(error)
        verify(mockView, never()).showOrders(ArgumentMatchers.anyList<Order?>())
    }

    @Test
    @Throws(java.lang.Exception::class)
    fun refreshWithoutView() {
        presenter.detachView()
        presenter.refresh()
        verify(mockOrderRepository, never()).getOrders()
        verify(mockView, never()).showProgress()
        verify(mockView, never()).showOrders(ArgumentMatchers.anyList())
    }

    private fun getFakeOrderList(): List<Order> {
        val orders: MutableList<Order> = LinkedList()
        orders.add(Order(1, 100F, "Order 1"))
        orders.add(Order(2, 200F, "Order 2"))
        return orders
    }
}