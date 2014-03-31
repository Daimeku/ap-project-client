package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import model.Drink;
import network.Client;

public class Listening implements ActionListener {



		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			// get input values
			String drinkName = tfName.getText().trim();
			String drinkTypeString = comboBoxType.getSelectedItem().toString().trim();
			Double drinkPrice = Double.parseDouble(spinnerPrice.getValue().toString());
			// set drinkType integer
			int drinkType = (drinkTypeString.equals(Drink.TYPES[0]))?1:2;
			
			// if everything is A-OK create Drink object and attempt to save
			if(drinkName.length() > 2 && drinkPrice >= Drink.MIN_PRICE){
				Drink aDrink = new Drink(drinkName, drinkType, drinkPrice);
				
				try {
					
					boolean added = this.addDrink(client,aDrink);
					client.sendChoice("add drink");
					if(client.recieveResponse()){
						
					}
					if(aDrink.save()){
						JOptionPane.showMessageDialog(null, drinkName+" drink added!", "Drink Added",
							    JOptionPane.INFORMATION_MESSAGE);
						clearFields(); // or dispose();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}else
				JOptionPane.showMessageDialog(null, "The drink information you entered is invalid, please try again.","Invalid Drink",
					    JOptionPane.ERROR_MESSAGE);
		}

		private boolean addDrink(Client client,Drink drink){		// button action to send the drink to the server
			boolean added = false;
			client.sendChoice("add drink");
			
			if(client.recieveResponse()){
				client.sendDrink(drink);
			}
			
			return added;
		}
		
	};
	



