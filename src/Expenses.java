import java.util.Date;

public class Expenses {
    private int ex_id;
    private int amount;
    private String description;
    private Date date;

    public Expenses() {
    }

    public Expenses(Date date, String description, int amount) {
        this.amount = amount;
        this.description = description;
        this.date = date;
    }

    public int getEx_id() {
        return ex_id;
    }

    public void setEx_id(int ex_id) {
        this.ex_id = ex_id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public java.sql.Date getDate() {
        return (java.sql.Date) date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
