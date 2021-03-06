package pizzashop.service;

import pizzashop.model.MenuDataModel;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;
import pizzashop.repository.MenuRepository;
import pizzashop.repository.PaymentRepository;
import pizzashop.service.exceptions.InvalidSumOfMoneyException;
import pizzashop.service.exceptions.TableNotExistentException;

import java.util.List;

public class PizzaService {
    public void setPayRepo(PaymentRepository payRepo) {
        this.payRepo = payRepo;
    }

    private MenuRepository menuRepo;
    private PaymentRepository payRepo;

    public PizzaService(MenuRepository menuRepo, PaymentRepository payRepo){
        this.menuRepo=menuRepo;
        this.payRepo=payRepo;
    }

    public List<MenuDataModel> getMenuData(){return menuRepo.getMenu();}

    public List<Payment> getPayments(){return payRepo.getAll(); }


    public void addPayment(int table, PaymentType type, double amount){
        if(table <= 0 || table >= 9) {
            throw new TableNotExistentException("No such table with an id: " + table);
        }

        if(amount < 0) {
            throw new InvalidSumOfMoneyException("Sum of money must be greated or lesser than 0!");
        }

        Payment payment= new Payment(table, type, amount);
        payRepo.add(payment);
    }

    public double getTotalAmount(PaymentType type){
        double total=0.0f;
        if(type == null) return total;
        List<Payment> l=getPayments();
        if ((l==null) || l.isEmpty())
            return total;
        for (Payment p:l){
            if (p.getType().equals(type))
                total+=p.getAmount();
        }
        return total;
    }

}
