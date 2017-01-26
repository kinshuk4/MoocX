package applets;

import java.awt.*;
import java.awt.event.*;
import java.applet.Applet;
public class mouselisten extends 
Applet implements MouseListener
{
	String message=new String();
	MouseEvent x;
	public void init()
	{
		addMouseListener(this);
	}
	public void mouseClicked(MouseEvent me)
	{
		message="mouse clicked";
		x=me;
		repaint();
	}
	public void mousePressed(MouseEvent me)
	{
		message="mouse pressed";
		x=me;		
		repaint();
	}
	public void mouseReleased(MouseEvent me)
	{
		message="mouse released";
		x=me;		
		repaint();
	}
	public void mouseEntered(MouseEvent me)
	{
		message="mouse Entered";
		x=me;		
		repaint();
	}
	public void mouseExited(MouseEvent me)
	{
		message="mouse exited";
		x=me;		
		repaint();
	}
	public void paint(Graphics g)
	{
		Dimension d=size();
		g.setFont(new Font("Arial",1,20));
		g.drawString(message,
		20,d.height-20);
		String s=
		String.valueOf(x.getX()+","+x.getY());
		g.drawString(s,x.getX(),x.getY());
	}
}