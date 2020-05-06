package pizzashop.repository;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import pizzashop.model.Payment;
import pizzashop.model.PaymentType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class PaymentRepository {
    private static final Logger logger = Logger.getLogger(PaymentRepository.class);
    private File file;
    private List<Payment> paymentList;
    private PaymentValidator validator = null;

    public PaymentRepository(File file){
        this.paymentList = new ArrayList<>();
        this.file = file;
        readPayments();
    }
    public PaymentRepository(PaymentValidator validator){
        this.paymentList = new ArrayList<>();
        this.validator = validator;
        //readPayments();
    }


    private void readPayments(){
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while((line=br.readLine())!=null){
                Payment payment=getPayment(line);
                paymentList.add(payment);
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }

    private Payment getPayment(String line){
        Payment item=null;
        if (line==null|| line.equals("")) return null;
        StringTokenizer st=new StringTokenizer(line, ",");
        int tableNumber= Integer.parseInt(st.nextToken());
        String type= st.nextToken();
        double amount = Double.parseDouble(st.nextToken());
        item = new Payment(tableNumber, PaymentType.valueOf(type), amount);
        return item;
    }

    public void add(Payment payment){
        if(validator != null) {
            if (validator.validate(payment))
                paymentList.add(payment);
        }
        else{
            paymentList.add(payment);
        }
        writeAll();

    }

    public List<Payment> getAll(){
        return paymentList;
    }

    public void writeAll(){

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))){
            for (Payment p : paymentList) {
                logger.log(Level.INFO, p.toString());
                bw.write(p.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            logger.log(Level.ERROR, e);
        }
    }

}
