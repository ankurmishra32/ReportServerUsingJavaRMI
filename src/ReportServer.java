import java.sql.*;
import java.rmi.*;
import java.awt.*;
import java.awt.event.*;
import java.rmi.server.*;
import java.rmi.registry.*;
public  class ReportServer extends UnicastRemoteObject implements ReportInt
{
    public final TextArea textArea=new TextArea();
    public ReportServer() throws RemoteException {}
    @Override
    public String getText() throws RemoteException {
        return textArea.getText();
    }
    @Override
    public void appendText1(String text1,String text2,String text3,String text4) throws RemoteException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection c=DriverManager.getConnection("Jdbc:Odbc:Report");
            Statement s=c.createStatement();
            ResultSet r=s.executeQuery("select * from Server");
            r.next();
            textArea.appendText(" ");
            textArea.appendText(text1+"\t"+text2+"\t"+text3+"\t"+text4+"\n");
            s.executeUpdate("insert into Server(Field1,Field2,Field3,Field4) values('"+text1+"','"+text2+"','"+text3+"','"+text4+"')");
        }
        catch(Exception ee) {
            ee.printStackTrace(System.out);
        }
    }
    @Override
    public void appendText2(String text1,String text2,String text3,String text4) throws RemoteException {
        try {
            Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            Connection c=DriverManager.getConnection("Jdbc:Odbc:Report");
            Statement s=c.createStatement();
            ResultSet r=s.executeQuery("select * from Server");
            textArea.appendText(text1+"\t\t"+text2+"\t\t"+text3+"\t\t\t"+text4+"\n");
            textArea.appendText("-------------------------------------------------");
            s.executeUpdate("insert into Server(Value1,Value2,Value3,Value4) values('"+text1+"','"+text2+"','"+text3+"','"+text4+"')");
        }
        catch(Exception ee) {
            ee.printStackTrace(System.out);
        }
    }

    public static void main(String args[]) {
        try {
            LocateRegistry.createRegistry(1099);
            ReportServer server=new ReportServer();
            Naming.rebind("ReportServer",server);
            Frame f=new Frame("Report Box");
            f.add("Center",server.textArea);
            f.pack();
            f.setVisible(true);
            f.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent we) {
                    System.exit(0);
                }
            });
        }
        catch(Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}