import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class IncomeDAO {
    private final String url = "jdbc:mysql://localhost:3306/exmanger";
    private final String uname = "root";
    private final String pass = "xxxx";
    Connection con;

    public IncomeDAO() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,uname,pass);
    }

    public Incomes getExpense() throws Exception{
        String query = "SELECT * From income";
        Incomes in = new Incomes();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int id = rs.getInt(1);
        String desription = rs.getNString(3);
        Date date = rs.getDate(2);
        int amount = rs.getInt(4);
        in.setAmount(amount);
        in.setDate(date);
        in.setDescription(desription);
        in.setEx_id(id);
        return in;
    }
    public int getIncomeTotal(){
        String query = "SELECT amount From income";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int amount = 0;
            while(rs.next()){
                amount += rs.getInt(1);
            }
            return amount;

        } catch (SQLException ex1) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
        return 0;
    
    
    }

    public void setIncome(Incomes in){

        try {
            String query = "INSERT INTO income values (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st = con.prepareStatement(query);
            st.setInt(1,in.getEx_id());
            st.setDate(2,in.getDate());
            st.setString(3,in.getDescription());
            st.setInt(4,in.getAmount());
            int count = st.executeUpdate();
            System.out.println(count+" row/s updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public void deleteRow(String value){
        String query = "DELETE FROM income WHERE in_id="+value+"";
        Statement st;
        try {
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public void getAllIncomes(javax.swing.JTable jtable){
        String query = "SELECT * From income";
        Statement st;
        try {
            st = con.createStatement();
            ResultSet rs = st.executeQuery(query);
            int id = 0;
            String description = "";
            String amount = "";
            java.sql.Date date;
            DefaultTableModel model = new DefaultTableModel(new String[]{"No", "Date", "Description", "Amount"}, 0);

            while(rs.next()){
                id = rs.getInt(1);
                date = rs.getDate(2);
                description = rs.getNString(3);
                amount = new DecimalFormat("#,###.00").format(rs.getInt(4));
                model.addRow(new Object[]{id, date, description,amount});
            }
            jtable.setModel(model);
        } catch (SQLException ex1) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex1);
        }
    }
}
