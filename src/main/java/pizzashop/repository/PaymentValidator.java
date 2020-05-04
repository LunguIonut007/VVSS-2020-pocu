package pizzashop.repository;

import pizzashop.model.Payment;

import java.util.ArrayList;
import java.util.List;

public class PaymentValidator {
    public boolean validate(Payment payment){
        if(payment.getTableNumber() > 0)
            return true;
        else
            return false;
    }
}
