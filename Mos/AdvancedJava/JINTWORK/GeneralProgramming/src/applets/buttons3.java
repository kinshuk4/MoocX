package applets;

import java.awt.*;
import java.awt.event.*;
import java.applet.*;
public class buttons3 extends Applet implements ActionListener
{
   Button b1,b2,b3;
   String s=" ";
   public void init()
   {
      b1=new Button("Java");
      b2=new Button("Java");
      b3=new Button("Java");
      add(b1);
      add(b2);
      add(b3);
      b1.addActionListener(this);
      b2.addActionListener(this);
      b3.addActionListener(this);
  }
  public void actionPerformed(ActionEvent ae)
  {
   if(ae.getSource()==b1)
   s="button I";
   else if(ae.getSource()==b2)
   s="button II";
   else
   s="button III";
   repaint();
  }
  public void paint(Graphics g)
  {
   g.drawString("you clicked on "+s,100,100);
  }
}


