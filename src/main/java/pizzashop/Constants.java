package pizzashop;

import pizzashop.repository.PaymentRepository;

import java.io.File;

public class Constants {
    public static final String PAYMENT_FILE_PATH = "data/payments.txt";

    public static File getPaymentFile() {
        ClassLoader classLoader = PaymentRepository.class.getClassLoader();
        return new File(classLoader.getResource(PAYMENT_FILE_PATH).getFile());
    }
}
