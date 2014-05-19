package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.Toolkit;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.DefaultComboBoxModel;

import network.Client;
import model.Drink;


import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class deleteDrinkView extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPanel;
	private JPanel buttonPane;	//panel to hold 'OK' and 'CANCEL' buttons
	private JLabel lblName;
	private JTextField tfName;
	private JButton okButton;
	private JButton cancelButton;
	private GroupLayout gl_contentPanel;
	
	private Client client;
	
	/**
	 * Create the dialog.
	 */
	public deleteDrinkView() {
		this.initializeComponents();
		this.setLayouts();
		this.addComponentsToPanels();
		this.registerListeners();
		this.setComponentProperties();
		this.addComponentsToWindow();
		this.setWindowProperties();
	}
	
	// Primary Constructor
	public deleteDrinkView(Client client) {
		this.client = client;
		this.initializeComponents();
		this.setLayouts();
		this.addComponentsToPanels();
		this.registerListeners();
		this.setComponentProperties();
		this.addComponentsToWindow();
		this.setWindowProperties();
	}
	
	// clear all fields
	private void clearFields(){
		this.tfName.setText(null);
	}
	
	public void initializeComponents(){
		contentPanel = new JPanel();
		buttonPane = new JPanel();
		
		lblName = new JLabel("Name:");
		tfName = new JTextField(10);
			
		okButton = new JButton("OK");
		cancelButton = new JButton("Cancel");
		
		gl_contentPanel = new GroupLayout(contentPanel);
	}
	
	public void setLayouts(){
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(gl_contentPanel);
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
	}
	
	public void addComponentsToPanels(){
		buttonPane.add(okButton);
		buttonPane.add(cancelButton);
	}
	
	public void registerListeners(){
		// OK BUTTON
		okButton.addActionListener(this); // END OK BUTTON LISTENER
		
		
		//CANCEL BUTTON LISTENER
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				dispose();
			}
		}); // END CANCEL BUTTON LISTENER
		
	}
	
	public void setComponentProperties(){
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		//SET HORIZONTAL GROUP
		gl_contentPanel.setHorizontalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addComponent(lblName)
								.addGap(10)
								.addComponent(tfName, GroupLayout.PREFERRED_SIZE, 279, GroupLayout.PREFERRED_SIZE))		)
						.addContainerGap(102, Short.MAX_VALUE))
		);
		
		//SET VERTICAL GROUP
		gl_contentPanel.setVerticalGroup(
				gl_contentPanel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_contentPanel.createSequentialGroup()
						.addGap(41)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(3)
								.addComponent(lblName))
							.addComponent(tfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(35)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE))
						.addGap(38)
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_contentPanel.createSequentialGroup()
								.addGap(6)))
						.addContainerGap(49, Short.MAX_VALUE))
		);
		
		//OK BUTTON PROPERTIES
		okButton.setFocusable(false);
		okButton.setIcon(new ImageIcon(deleteDrinkView.class.getResource("/resources/accept.gif")));
		okButton.setActionCommand("OK");
		
		//CANCEL BUTTON PROPERTIES
		cancelButton.setFocusable(false);
		cancelButton.setIcon(new ImageIcon(deleteDrinkView.class.getResource("/resources/cancel.png")));
		cancelButton.setActionCommand("Cancel");
		
	}
	
	public void addComponentsToWindow(){
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
	}
	
	public void setWindowProperties()
	{
		setIconImage(Toolkit.getDefaultToolkit().getImage(deleteDrinkView.class.getResource("/resources/rem_drink.png")));
		setTitle("Remove Drink");
		setBounds(100, 100, 450, 300);
		getRootPane().setDefaultButton(okButton);
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == okButton){
			
			String drinkName = tfName.getText().trim();

			
			// set drinkType integer

			
			// if everything is A-OK create Drink object and attempt to save
			if(drinkName.length() > 2 ){
				Drink aDrink = new Drink(drinkName, 1, 0.0);
				
				try {
					
					client.sendChoice("delete drink");
					
					if(client.recieveResponse()){		//if server approves
						client.sendObject(aDrink);
						boolean res = client.recieveResponse();
						if(res == true){		// IF DRINK IS removed
						JOptionPane.showMessageDialog(null, drinkName+"Drink removed", "drink deleted",
							    JOptionPane.INFORMATION_MESSAGE);
						}
						else{// IF DRINK ISNT removed
							JOptionPane.showMessageDialog(null, drinkName+" NO DRINK DELETED", "Drink not deleted",
								    JOptionPane.ERROR_MESSAGE);
						}
						client.sendChoice("drink table");
						ManagerView.getTable().setModel(client.recieveTableModel());
						clearFields(); // or dispose();
						
						
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				this.dispose();
			}
			else
				JOptionPane.showMessageDialog(null, "The drink information you entered is invalid, please try again.","Invalid Drink",
					    JOptionPane.ERROR_MESSAGE);
		}
		
	}
		
}
	

