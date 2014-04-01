import view.LoginView;
import model.Drink;
import model.Manager;
import network.Client;


public class Driver {

	public static void main(String[] args) throws Exception {
        // TODO code application logic here
		
        Client cl = new Client();
        
       
        /*
        Drink d = new Drink();
        d.setName("thisdrink");
<<<<<<< HEAD
       // System.out.println("Sending choice");\\
=======
       // System.out.println("Sending choice");
>>>>>>> 93a707fe61ad9522358182ef9004959a01c526d2
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
