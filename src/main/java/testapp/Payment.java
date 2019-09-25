package testapp;

// Структура данных платежа
public class Payment {
    private int sender;
    private int receiver;
    private float amount;

    public Payment() {
    }

    public Payment(int sender, int receiver, float amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getReceiver() {
        return receiver;
    }

    public void setReceiver(int receiver) {
        this.receiver = receiver;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

}
