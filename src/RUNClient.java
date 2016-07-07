import java.sql.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Ankur Mishra
 */
public class RUNClient extends JFrame implements ActionListener {
    protected int i=0;
    JFrame frame=new JFrame();
    JButton b1=new JButton("OK");
    JTextField f1=new JTextField("localhost");
    JTextField f2=new JTextField("username");
    JPasswordField pwd=new JPasswordField();
    JLabel lab1=new JLabel("IP of Server :");
    JLabel lab2=new JLabel("Enter Username :");
    JLabel lab3=new JLabel("Enter Password :");

    private RUNClient(){
        frame.setLayout(null);
        b1.setBounds(150,100,100,20);
        frame.add(b1);
        b1.addActionListener(this);
        f1.setBounds(200,25,100,20);
        frame.add(f1);
        f2.setBounds(200,50,100,20);
        frame.add(f2);
        pwd.setEchoChar('*');
        pwd.setBounds(200,75,100,20);
        frame.add(pwd);
        lab1.setBounds(75,25,100,20);
        frame.add(lab1);
        lab2.setBounds(75,50,100,20);
        frame.add(lab2);
        lab3.setBounds(75,75,100,20);
        frame.add(lab3);
        frame.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        f1.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                f1.selectAll();
            }
        });
        f2.addMouseListener(new java.awt.event.MouseAdapter(){
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e){
                f2.selectAll();
            }
        });
        frame.setSize(400,200);
        frame.setLocation(400,300);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e){
        String host=f1.getText();
        String name=f2.getText();
        String pswd=pwd.getText();
        if(host.equals("")){
            frame.setTitle("Server IP Never Left Blank");
            return;
        }
        if(name.equals("")||pswd.equals("")){
            JOptionPane.showMessageDialog(this,"Username AND Password never left blank","Warning!",JOptionPane.WARNING_MESSAGE);
            return;
        }
        try{
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection c=DriverManager.getConnection("Jdbc:Odbc:Report");
            Statement s=c.createStatement();
            ResultSet r=s.executeQuery("select * from admin");
            while(r.next()){
                if(name.equals(r.getString("Username"))&&pswd.equals(r.getString("Password"))){
                    new ReportClient(host,name).setVisible(true);
                    frame.setVisible(false);
                }
            }
        } catch(Exception ee) {
            ee.printStackTrace(System.out);
        }
        JOptionPane.showMessageDialog(this,"Invalid User name & Password","OOPS!!!",JOptionPane.ERROR_MESSAGE);
        f2.setText("");
        pwd.setText("");
        f2.requestFocus();
    }

    public static void main(String args[]){
        new RUNClient();
    }
}