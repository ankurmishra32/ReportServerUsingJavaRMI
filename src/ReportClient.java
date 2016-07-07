import java.sql.*;
import java.rmi.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 *
 * @author Ankur Mishra
 */
public class ReportClient extends JFrame{
    public ReportClient(String host,String name){
        try{
            final ReportInt ServerReportArea=(ReportInt)Naming.lookup("rmi://"+host+"/ReportServer");
            JFrame jf=new JFrame("Branch Report");
            jf.setLayout(new BorderLayout());
            JPanel ptext= new JPanel();	ptext.setLayout(new GridLayout(5,1));
            JPanel pbutton=new JPanel();	pbutton.setLayout(new BorderLayout());
            final JLabel cliLbBr=new JLabel("Branch Name:");		ptext.add(cliLbBr);
            final JTextField cliTfBr=new JTextField(7);			ptext.add(cliTfBr);
            final JLabel cliLbStud=new JLabel("Course Name:");		ptext.add(cliLbStud);
            final JTextField cliTfStud=new JTextField(7);			ptext.add(cliTfStud);
            final JLabel cliLbEx=new JLabel("Number of joined:");		ptext.add(cliLbEx);
            final JTextField cliTfEx=new JTextField(7);			ptext.add(cliTfEx);
            final JLabel cliLbPro=new JLabel("Revenue From The Course:");	ptext.add(cliLbPro);
            final JTextField cliTfPro=new JTextField(7);			ptext.add(cliTfPro);
            Button apply=new Button("Send to Head Office");			pbutton.add("South",apply);
            apply.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent ae){
                    try{
                        String branch=cliTfBr.getText();
                        String course=cliTfStud.getText();
                        String student=cliTfEx.getText();
                        String revenue=cliTfPro.getText();
                        ServerReportArea.appendText1("Branch Name:","Course Name:","No. of Students joined:","Revenue from the course:") ;
                        ServerReportArea.appendText2(branch,course,student,revenue);
                        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
                        Connection c=DriverManager.getConnection("Jdbc:Odbc:Report");
                        Statement s=c.createStatement();
                        ResultSet r=s.executeQuery("select * from Client");
                        r.next();
                        s.executeUpdate("insert into Client(Branch,Course,Student,Revenue) values('"+branch+"','"+course+"','"+student+"','"+revenue+"')");
                    } catch(Exception ee){
                        ee.printStackTrace(System.out);
                    }
                }
            });
            
            jf.add("North",ptext);
            jf.add("South",pbutton);
            jf.pack();
            jf.setVisible(true);
            jf.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent we){
                    System.exit(0);
                }
            });
        } catch(Exception ee) {
            ee.printStackTrace();
            System.exit(0);
        }
    }
}