package pizzashop;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;
import pizzashop.service.exceptions.TableNotExistentException;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PizzaServiceMockTest {
    @Mock
    private PaymentRepository repo;
    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    private PizzaService service;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_getTotalAmount(){

        Payment p1 = new Payment(2,PaymentType.Cash,15.2);
        Payment p2 = new Payment(5,PaymentType.Card,12.2);

        Mockito.when(service.getPayments()).thenReturn(Arrays.asList(p1,p2));

        //Mockito.when(service.getTotalAmount(PaymentType.Card)).thenReturn(12.2);

        // System.out.println(service.getTotalAmount(PaymentType.Cash));
        Assertions.assertEquals(service.getTotalAmount(PaymentType.Card),12.2);
        Assertions.assertEquals(service.getTotalAmount(null),0.0f);
    }

    @Test(expected = TableNotExistentException.class)
    public void test_addInvalidPayment(){

        Mockito.doThrow(TableNotExistentException.class).when(repo);

        service.addPayment(-4,PaymentType.Cash,-23);

        assert false;
    }



}
