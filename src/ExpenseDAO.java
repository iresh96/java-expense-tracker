import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

public class ExpenseDAO {

    private final String url = "jdbc:mysql://localhost:3306/exmanger";
    private final String uname = "root";
    private final String pass = "xxxxx";
    Connection con;

    public ExpenseDAO() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(url,uname,pass);
    }

    public Expenses getExpense() throws Exception{
        String query = "SELECT * From expense";
        Expenses ex = new Expenses();
        Statement st = con.createStatement();
        ResultSet rs = st.executeQuery(query);
        rs.next();
        int id = rs.getInt(1);
        String desription = rs.getNString(3);
        Date date = rs.getDate(2);
        int amount = rs.getInt(4);
        ex.setAmount(amount);
        ex.setDate(date);
        ex.setDescription(desription);
        ex.setEx_id(id);
        return ex;
    }

    public void setExpenses(Expenses ex){

        try {
            String query = "INSERT INTO expense values (?,?,?,?)";
            PreparedStatement st = con.prepareStatement(query);
            st = con.prepareStatement(query);
            st.setInt(1,ex.getEx_id());
            st.setDate(2,ex.getDate());
            st.setString(3,ex.getDescription());
            st.setInt(4,ex.getAmount());
            int count = st.executeUpdate();
            System.out.println(count+" row/s updated");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public int getExpenceTotal(){
        String query = "SELECT amount From expense";
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
    public void getAllexpenses(javax.swing.JTable jtable){
        String query = "SELECT * From expense";
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
    public void deleteRow(String value){
        String query = "DELETE FROM expense WHERE ex_id="+value+"";
        Statement st;
        try {
            st = con.createStatement();
            st.execute(query);
        } catch (SQLException ex) {
            Logger.getLogger(ExpenseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
