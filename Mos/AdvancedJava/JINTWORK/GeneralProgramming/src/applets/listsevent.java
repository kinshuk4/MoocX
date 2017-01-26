package applets;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class listsevent extends 
Applet implements ItemListener,
ActionListener
{
	String message=new String();
	List c;
	public void init()
	{
		c=new List(4);
		c.add("ddlj");
		c.add("josh");
		c.add("hum");
		c.add("knp");
		c.add("Om");
		c.add("xyz");
		c.add("abc");
		add(c);
		c.select(4);
		c.addActionListener(this);
		c.addItemListener(this);
	}
	public void actionPerformed(
	ActionEvent ae)
	{
		message="This is an ACTION event and you selected "+
		c.getSelectedItem();
		repaint();
	}
	public void itemStateChanged(
	ItemEvent ie)
	{
		message="This is an ITEM state change event and you selected"+c.getSelectedItem();
		repaint();
	}
	public void paint(Graphics g)
	{
		g.setFont(new Font("arial",1,20));
		g.drawString(message,1,100);
	}
}