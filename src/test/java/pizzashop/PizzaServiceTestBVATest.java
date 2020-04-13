package pizzashop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import static org.junit.jupiter.api.Assertions.*;

class PizzaServiceTestBVATest {
    private PizzaService service;
    @BeforeEach
    void setUp() {
        MenuRepository repoMenu=new MenuRepository();
        PaymentRepository payRepo= new PaymentRepository(Constants.getPaymentFile());
        service = new PizzaService(repoMenu, payRepo);
    }

    @Test
    @Order(0)
    void testBVA_TC1(){
        Exception exception = assertThrows(Exception.class, () -> service.addPayment(0, PaymentType.Cash,50));
    }
    @Test
    @Order(1)
    void testBVA_TC2(){
        try {
            service.addPayment(3, PaymentType.Cash, 50);
        }catch (Exception e){
            fail("Should not have thrown any exception");
        }
    }
    @Test
    @Order(2)
    void testBVA_TC3(){
        Exception exception = assertThrows(Exception.class, () -> service.addPayment(9, PaymentType.Cash,50));

    }

    @Test
    @Order(3)
    void testBVA_TC4(){
        Exception exception = assertThrows(Exception.class, () -> service.addPayment(1, PaymentType.valueOf("Test"),50));

    }
    @Test
    @Order(4)
    void testBVA_TC5(){
        try {
            service.addPayment(1, PaymentType.valueOf("Cash"), 50);
        }catch (Exception e){
            fail("Should not have thrown any exception");
        }

    }

    @Test
    @Order(5)
    void testBVA_T6(){
        Exception exception = assertThrows(Exception.class, () -> service.addPayment(1, PaymentType.Cash,-100));

    }

    @Test
    @Order(6)
    void testBVA_TC7(){
        try {
            service.addPayment(1, PaymentType.Cash, 100);
        }catch (Exception e) {
            fail("Should not have thrown any exception");
        }
    }



    @AfterEach
    void tearDown() {
        System.out.println("Test finished...");
    }
}
