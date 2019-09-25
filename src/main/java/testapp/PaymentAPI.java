package testapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Класс, реализующий работу с таблицей платежей
public class PaymentAPI {

    private final String URL_1="jdbc:postgresql://localhost:5432/User";
    private final String URL_2="jdbc:postgresql://localhost:5435/User";
    private final String URL_3="jdbc:postgresql://localhost:5439/User";
    private final String USER_1="test_user";
    private final String USER_2="test_user";
    private final String USER_3="test_user";
    private final String PASS_1="0000";
    private final String PASS_2="0000";
    private final String PASS_3="0000";

    // Метод для добавления списка платежей
    public void addPayments(List<Payment> p) {
        List<Payment> p1=new ArrayList<>();
        List<Payment> p2=new ArrayList<>();
        List<Payment> p3=new ArrayList<>();
        for(int i=0;i<p.size();i++){
            switch (p.get(i).getSender()%3){
                case 0:
                    p1.add(p.get(i));
                    break;
                case 1:
                    p2.add(p.get(i));
                    break;
                case 2:
                    p3.add(p.get(i));
                    break;
            }
        }
        System.out.println(p1.size() + " " + p2.size() + " " + p3.size());
        String queryString1=buildQuery(p1);
        String queryString2=buildQuery(p2);
        String queryString3=buildQuery(p3);
        try (Connection connection1 = DriverManager.getConnection(URL_1, USER_1, PASS_1);
             Connection connection2 = DriverManager.getConnection(URL_2, USER_2, PASS_2);
             Connection connection3 = DriverManager.getConnection(URL_3, USER_3, PASS_3);
             Statement statement1 = connection1.createStatement();
             Statement statement2 = connection2.createStatement();
             Statement statement3 = connection3.createStatement();) {
            System.out.println("Connections established");
            connection1.setAutoCommit(false);
            connection2.setAutoCommit(false);
            connection3.setAutoCommit(false);
            if(queryString1!=null) {
                statement1.executeUpdate(queryString1);
            }
            if(queryString2!=null) {
                statement2.executeUpdate(queryString2);
            }
            if(queryString3!=null) {
                statement3.executeUpdate(queryString3);
            }
            if(queryString1!=null) {
                connection1.commit();
            }
            if(queryString2!=null) {
                connection2.commit();
            }
            if(queryString3!=null) {
                connection3.commit();
            }
        }
        catch( SQLException ex){
            ex.printStackTrace();
        }
    }

    private String buildQuery(List<Payment> lp){
        if(lp.size()>0) {
            StringBuilder sb = new StringBuilder("INSERT INTO payment (sender, receiver, amount) VALUES (");
            for (int i = 0; i < lp.size(); i++) {
                if (i != 0) {
                    sb.append(", (");
                }
                sb.append(lp.get(i).getSender()).append(", ").append(lp.get(i).getReceiver()).append(", ").append(lp.get(i).getAmount()).append(")");
            }
            return sb.toString();
        }
        else
            return null;
    }

    // Метод для подсчёта общей суммы потраченных средств по отправителю
    public float getSummaryAmountForSender(int senderId) {
        float result=-1;
        String url;
        String pass;
        String userId;
        switch (senderId % 3) {
            case 0:
                url = URL_1;
                pass = PASS_1;
                userId = USER_1;
                break;
            case 1:
                url = URL_2;
                pass = PASS_2;
                userId = USER_2;
                break;
            case 2:
                url = URL_3;
                pass = PASS_3;
                userId = USER_3;
                break;
            default:
                return result;

        }
        StringBuilder  queryStringBuilder= new StringBuilder("select SUM(amount::numeric) as avgAmount  from payment");
        queryStringBuilder.append(" where sender=").append(senderId);
        String queryString=queryStringBuilder.toString();
        try (Connection connection = DriverManager.getConnection(url, userId, pass);
             Statement statement = connection.createStatement();
             ResultSet resultSet= statement.executeQuery(queryString);) {
             if(resultSet.next()) {
                result=resultSet.getFloat("avgAmount");
             }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return result;
    }
}
