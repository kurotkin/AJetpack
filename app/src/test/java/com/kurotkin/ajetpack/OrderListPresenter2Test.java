package com.kurotkin.ajetpack;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class OrderListPresenter2Test {

    OrderListPresenter presenter;

    @Mock
    OrderRepository mockOrderRepository;

    @Mock
    OrderListContract.View mockView;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        presenter = new OrderListPresenter(mockOrderRepository, Schedulers.trampoline(), Schedulers.trampoline());
        presenter.attachView(mockView);
    }

    @Test
    public void refreshSuccess() throws Exception {
        List<Order> fakeOrders = getFakeOrderList();
        when(mockOrderRepository.getOrders()).thenReturn(Observable.<List<Order>> just(fakeOrders));

        presenter.refresh();

        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showOrders(fakeOrders);
        verify(mockView, never()).showError(anyString());
    }

    @Test
    public void refreshFailed() throws Exception {
        String error = "Network error";

        when(mockOrderRepository.getOrders()).thenReturn(Observable.<List<Order>>error(new Exception(error)));

        presenter.refresh();

        verify(mockView).showProgress();
        verify(mockView).hideProgress();
        verify(mockView).showError(error);
        verify(mockView, never()).showOrders(ArgumentMatchers.<Order>anyList());
    }

    @Test
    public void refreshWithoutView() throws Exception {
        presenter.detachView();
        presenter.refresh();

        verify(mockOrderRepository, never()).getOrders();
        verify(mockView, never()).showProgress();
        verify(mockView, never()).showOrders(ArgumentMatchers.<Order>anyList());
    }

    private List<Order> getFakeOrderList() {
        List<Order> orders = new LinkedList<>();
        orders.add(new Order(1, 100, "Order 1"));
        orders.add(new Order(2, 200, "Order 2"));
        return orders;
    }
}
