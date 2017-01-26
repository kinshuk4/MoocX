package applets;

import java.awt.*;
import java.applet.Applet;
public class drawing extends Applet
{
	public void paint(Graphics g)
	{
		g.drawOval(20,20,50,50);
		g.drawRect(50,50,50,50);
		g.setColor(Color.red);
		g.drawLine(50,50,100,100);
		int x[]={10,30,100};
		int y[]={32,56,68};
		g.drawPolyline(x,y,3);
		g.setColor(Color.green);
		g.fillRect(100,100,30,30);
	}
}