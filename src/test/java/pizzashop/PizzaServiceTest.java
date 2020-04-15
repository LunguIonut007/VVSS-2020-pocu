package pizzashop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;
import pizzashop.service.exceptions.TableNotExistentException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PizzaServiceTest {
    private PizzaService service;

    @BeforeEach
    void setUp() throws IOException {
        MenuRepository repoMenu = new MenuRepository();
        File directory = new File("./test");
        if(!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(String.format("./test/payment-%d.txt", new Date().getTime()));
        if(!file.exists()){
            file.createNewFile();
        }

        PaymentRepository payRepo = new PaymentRepository(file);
        payRepo.add(new Payment(1, PaymentType.Card, 3));
        payRepo.add(new Payment(4, PaymentType.Cash, 13));
        payRepo.add(new Payment(1, PaymentType.Card, 74));
        service = new PizzaService(repoMenu, payRepo);
    }

    @Test
    void testAmoutCash() {
        assertEquals(service.getTotalAmount(PaymentType.Cash), 13);
    }

    @Test
    void testAmoutCard() {
        assertEquals(service.getTotalAmount(PaymentType.Card), 77);
    }

    @Test
    void testAmountTC_02() {
        File emptyPayFile = new File("./test/empty.txt");
        if(!emptyPayFile.exists()){
            assert false;
        }
        else{
            service.setPayRepo(new PaymentRepository(emptyPayFile));
            assertEquals(service.getTotalAmount(PaymentType.Card), 0);
        }



    }

    @Test
    void testAmoutCardTC_03() {

        assertEquals(service.getTotalAmount(PaymentType.Card), 77);
    }

}
