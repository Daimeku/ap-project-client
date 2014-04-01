import javax.swing.UIManager;

import view.LoginView;
import model.Drink;
import model.Manager;
import network.Client;


public class Driver {

	public static void main(String[] args) throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
        Client cl = new Client();
        
       
        /*
        Drink d = new Drink();
        d.setName("thisdrink");
       // System.out.println("Sending choice");
       // System.out.println("Sending choice");
        String c = "add drink";
        cl.sendChoice(c);
        System.out.println("response: "+cl.recieveResponse());
        cl.sendDrink(d);
        System.out.println("response drink: "+cl.recieveResponse());
        */
        LoginView lv = new LoginView(cl);
        lv.setVisible(true);
      //  cl.closeConnections();
    }
}
