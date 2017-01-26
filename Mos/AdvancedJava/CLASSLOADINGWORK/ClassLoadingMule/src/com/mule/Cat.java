package com.mule;

public class Cat extends Animal implements Pet {
	private String Name="MAXI";
	public Cat(String name) {
		super();
		Name = name;
	}
	public void makeSound() {
		System.out.println("MEOOOEW");
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	static {
		System.out.println("Staic Clause :::: Class CAT getting Loaded");
	}
}
