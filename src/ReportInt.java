import java.rmi.*;

public interface ReportInt extends Remote {
    public String getText() throws RemoteException;
    public void appendText1(String text1,String text2,String text3,String text4) throws RemoteException;
    public void appendText2(String text1,String text2,String text3,String text4) throws RemoteException;
}
