package pizzashop;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.PizzaService;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IntegrationTestE {

    private PizzaService service;

    private PaymentRepository repoPay;

    private MenuRepository repoMenu;

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
        repoPay = payRepo;
    }

    @Test
    public void testAddPay(){
        service.addPayment(2,PaymentType.Cash,22);
        assert repoPay.getAll().get(repoPay.getAll().size()-1).getTableNumber() == 2;
    }

    @Test
    void testAmoutCash() {
        assertEquals(service.getTotalAmount(PaymentType.Cash), 13);
    }

    @Test
    void testAmoutCard() {
        assertEquals(service.getTotalAmount(PaymentType.Card), 77);
    }
}
