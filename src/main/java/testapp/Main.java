package testapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {

    public static void main(String[] args){

        PaymentAPI p =new PaymentAPI();
//        List<Payment> lp;
//        lp=generateRandomPayments(200);
//        p.addPayments(lp);
        System.out.println(p.getSummaryAmountForSender(4));
    }

    // Генерация списка с платежами для тестирования
    private static List<Payment> generateRandomPayments(int count){
        List<Payment> p=new ArrayList<>();
        Random r=new Random();
        Payment payment;
        for(int i=0;i< count; i++){
           payment=new Payment(r.nextInt(count/3)+1, r.nextInt(count/3)+1, r.nextInt(1000));
           p.add(payment);
        }
        return p;
    }
}
