package pizzashop;

import org.junit.jupiter.api.*;
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
        System.out.println("Set up...");
        MenuRepository repoMenu=new MenuRepository();
        PaymentRepository payRepo= new PaymentRepository(Constants.getPaymentFile());
        service = new PizzaService(repoMenu, payRepo);
    }

    @AfterEach
    void tearDown() {
        System.out.println("Clearning");
    }
    @BeforeAll
    public static void init() {
        System.out.println("Initializing tests...");
    }

    @AfterAll
    public static void post() {
        System.out.println("Finished test!");
    }

    @Test
    void assertNormal() {
        service.addPayment(1, PaymentType.Cash, 200);
    }

    @Test
    @DisplayName("Test with non-existant table")
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
}
