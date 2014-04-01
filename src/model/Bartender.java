package model;


public class Bartender extends Staff implements Serveable {

	private double wage;
	private String hours;
	
	
	public Bartender(){
		super();
	}
	
	public Bartender(double wage, String hours) {
		super();
		this.wage = wage;
		this.hours = hours;
	}

	public Bartender(String id, String name, String password, double wage, String hours) {
		super(id, name, password);
		this.wage = wage;
		this.hours = hours;
	}
	
	public double getWage() {
		return wage;
	}

	public void setWage(double wage) {
		this.wage = wage;
	}

	public String getHours() {
		return hours;
	}

	public void setHours(String hours) {
		this.hours = hours;
	}

	@Override
	public void serve() {
		
		
	}  
	
	
}
