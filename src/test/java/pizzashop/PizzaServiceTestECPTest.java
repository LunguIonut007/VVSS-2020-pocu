package pizzashop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;
import pizzashop.service.exceptions.InvalidSumOfMoneyException;
import pizzashop.service.exceptions.TableNotExistentException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

class PizzaServiceTestECPTest {
    private PizzaService service;
    @BeforeEach
    void setUp() {
        MenuRepository repoMenu=new MenuRepository();
        PaymentRepository payRepo= new PaymentRepository();
        service = new PizzaService(repoMenu, payRepo);
    }

    @Test
    void assertNormal() {
        service.addPayment(1, PaymentType.Cash, 200);
    }

    @Test
    void voidUpperInexistentTable() {
        assertThrows(TableNotExistentException.class, () -> service.addPayment(30,PaymentType.Card, 10));
    }

    @Test
    void voidLowerInexistentTable() {
        assertThrows(TableNotExistentException.class, () -> service.addPayment(-2,PaymentType.Cash, 10));
    }

    @Test
    void voidInexistentCashFlow() {
        assertThrows(InvalidSumOfMoneyException.class, () -> service.addPayment(3,PaymentType.Card, -10));
    }

    @AfterEach
    void tearDown() {

    }
}
