package pizzashop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.PaymentRepository;
import pizzashop.repository.PaymentValidator;

import java.io.File;
import java.util.Date;

import static org.mockito.Mockito.*;

public class PizzaRepositoryTest {
    private PaymentRepository paymentRepository;
    private PaymentValidator validator;

    @BeforeEach
    public void setUp(){
        //File file = new File(String.format("./test/payment-%d.txt", new Date().getTime()));
        PaymentRepository repo = new PaymentRepository();
        paymentRepository = spy(repo);
        validator = spy(PaymentValidator.class);

    }
    @Test
    public void testAdd() {
        doNothing().when(paymentRepository).writeAll();
        Payment p1 = new Payment(1, PaymentType.Cash,234);
        paymentRepository.add(p1);
        assert paymentRepository.getAll().get(0).getTableNumber() == p1.getTableNumber();
    }
    @Test
    public void testValidate() {
        doNothing().when(paymentRepository).writeAll();
        assert validator.validate(new Payment(1, PaymentType.Cash, 234));
        paymentRepository.add(new Payment(1, PaymentType.Cash,234));
        assert !validator.validate(new Payment(-1, PaymentType.Cash, 234));
        assert paymentRepository.getAll().size() == 1;
    }
}
