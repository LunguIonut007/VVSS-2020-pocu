package pizzashop.service;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.apache.log4j.Logger;
import pizzashop.model.PaymentType;

import java.util.Optional;

public class PaymentAlert implements PaymentOperation {
    private static final String SEPARATOR = "--------------------------";
    private static final Logger logger = Logger.getLogger(PaymentAlert.class);
    private PizzaService service;

    public PaymentAlert(PizzaService service){
        this.service=service;
    }

    @Override
    public void cardPayment() {
        logger.info(SEPARATOR);
        logger.info("Paying by card...");
        logger.info("Please insert your card!");
        logger.info(SEPARATOR);
    }
    @Override
    public void cashPayment() {
        logger.info(SEPARATOR);
        logger.info("Paying cash...");
        logger.info("Please show the cash...!");
        logger.info(SEPARATOR);
    }
    @Override
    public void cancelPayment() {
        logger.info(SEPARATOR);
        logger.info("Payment choice needed...");
        logger.info(SEPARATOR);
    }
      public void showPaymentAlert(int tableNumber, double totalAmount ) {
        Alert paymentAlert = new Alert(Alert.AlertType.CONFIRMATION);
        paymentAlert.setTitle("Payment for Table "+tableNumber);
        paymentAlert.setHeaderText("Total amount: " + totalAmount);
        paymentAlert.setContentText("Please choose payment option");
        ButtonType cardPayment = new ButtonType("Pay by Card");
        ButtonType cashPayment = new ButtonType("Pay Cash");
        ButtonType cancel = new ButtonType("Cancel");
        paymentAlert.getButtonTypes().setAll(cardPayment, cashPayment, cancel);
        Optional<ButtonType> result = paymentAlert.showAndWait();
        if(!result.isPresent()) {
            logger.error("No result!");
            return;
        }
        if (result.get() == cardPayment) {
            cardPayment();
            service.addPayment(tableNumber, PaymentType.Card,totalAmount);
        } else if (result.get() == cashPayment) {
            cashPayment();
            service.addPayment(tableNumber, PaymentType.Cash,totalAmount);
        } else if (result.get() == cancel) {
             cancelPayment();
        } else {
            cancelPayment();
        }
    }
}
