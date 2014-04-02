package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableModel;

import model.Drink;
import network.Client;

public class GuestView extends JFrame {

	private JPanel contentPane;
	private JPanel toolBarPanel;
	private static JTable table;
	private JMenuItem mntmExit;
	private JMenuItem mntmAbout;
	private JButton btnOrder;
	private JPanel orderPane;
	private JList orderList;
	private DefaultListModel<Object> lm;
	private ArrayList<Drink> drinks;

	/**
	 * Create the frame.
	 */
	public GuestView(Client client) {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(ManagerView.class.getResource("/resources/drink.png")));
		setTitle("AP-Project v0.1.1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 720, 576);
		
		
		drinks = new ArrayList<Drink>();
		lm = new DefaultListModel();
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent aEvent) {
				System.exit(EXIT_ON_CLOSE);
			}
		});
		mntmExit.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/exit.png")));
		mnFile.add(mntmExit);
		
		JMenu mnHelp = new JMenu("Help");
		menuBar.add(mnHelp);
		
		mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				About abt = new About();
				
				JOptionPane.showMessageDialog(GuestView.this, abt.getEditorPane(),"About AP-Project", JOptionPane.PLAIN_MESSAGE);
			}
		});
		mntmAbout.setIcon(new ImageIcon(ManagerView.class.getResource("/resources/about.png")));
		mnHelp.add(mntmAbout);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(46, 139, 87));
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		
		toolBarPanel = new JPanel();
		toolBarPanel.setBackground(new Color(139, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		
		orderPane = new JPanel();
		
		JButton button = new JButton(">>");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addOrder();
			}
		});
		
		JButton button_1 = new JButton("<<");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				removeDrink();
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(toolBarPanel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 474, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(button)
						.addComponent(button_1))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(orderPane, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(orderPane, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(185)
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(toolBarPanel, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
		);
		
		orderList = new JList();
		orderList.setSelectionBackground(new Color(46, 139, 87));
		orderPane.add(orderList);
		toolBarPanel.setLayout(null);
		
		btnOrder = new JButton("Submit Order");
		btnOrder.setBounds(274, 21, 150, 35);
		toolBarPanel.add(btnOrder);
		btnOrder.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnOrder.setFocusable(false);
		btnOrder.setForeground(Color.BLACK);
		btnOrder.setBackground(new Color(139, 0, 0));
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JOptionPane.showConfirmDialog(null, "Your Order:", "Confirm Order", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		table = new JTable();
		table.setAutoscrolls(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setSelectionBackground(new Color(46, 139, 87));
		table.setRowMargin(0);
		table.setFont(new Font("Serif", Font.PLAIN, 12));
		table.setRowHeight(27);
		table.setGridColor(new Color(0, 0, 0));
		table.setBackground(new Color(211, 211, 211));
		client.sendChoice("drink table");
		table.setModel(client.recieveTableModel());
		
		scrollPane.setViewportView(table);
		contentPane.setLayout(gl_contentPane);
	}
	
	public static JTable getTable(){
		return table;
	}
	
	public void addOrder(){
		Drink drink = new Drink();
		int c;
		//Order order = new Order();
		
		int selRow = table.getSelectedRow();
		TableModel tm = table.getModel();
		lm.addElement(tm.getValueAt(selRow, 0));
		orderList.setModel(lm);
		
		for(c=0; c>=2;c++){
			switch(c){
			case 0:
				drink.setName((String) tm.getValueAt(selRow, c));
				break;
			case 1:
				drink.setPrice((double) tm.getValueAt(selRow, c));
				break;
			case 2:
				if(tm.getValueAt(selRow, c).equals("Alcoholic"))
					drink.setType(1);
				else
					drink.setType(2);
			}
		}
		
		drinks.add(drink);
	}
	
	public void removeDrink(){
		
		lm.remove(orderList.getSelectedIndex());
		
		for(Drink d:drinks){
			if(d.getName() == orderList.getSelectedValue())
				drinks.remove(d);
		}
	}
}
