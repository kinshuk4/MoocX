package applets;

import java.awt.*;
import java.applet.*;
import java.awt.event.*;
public class choicesevent extends 
Applet implements ItemListener
{
	Choice c=new Choice();
	public void init()
	{
  	c.add("ddlj");
		c.add("josh");
		c.add("hum");
		c.add("knp");
		c.add("Om");
		add(c);
		c.insert("a",10);
		c.addItemListener(this);
	}
	public void itemStateChanged(
	ItemEvent ie)
	{
		Graphics g=getGraphics();
		update(g);
		g.drawString("you selected "+
		c.getSelectedItem(),100,100);
		g.drawString("index was "+
		String.valueOf(
		c.getSelectedIndex()),
		100,120);
	}
}