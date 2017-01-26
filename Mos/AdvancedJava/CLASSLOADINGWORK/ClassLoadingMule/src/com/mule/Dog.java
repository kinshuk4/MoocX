package com.mule;

public class Dog extends Animal implements Pet{
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dog(String name) {
		super();
		this.name = name;
	}
	public Dog() {
		super();
		this.name = "SNOWY";
	}
	static {
		System.out.println("Staic Clause :::: Class DOG getting Loaded");
	}
	public void makeSound() {
		System.out.println("WHOOOAAAA");
		
	}
}
