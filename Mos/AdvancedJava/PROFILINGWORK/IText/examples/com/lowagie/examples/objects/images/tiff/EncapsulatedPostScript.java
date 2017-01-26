/*
 * $Id: EncapsulatedPostScript.java,v 1.3 2005/05/09 11:52:45 blowagie Exp $
 * $Name:  $
 *
 * This code is part of the 'iText Tutorial'.
 * You can find the complete tutorial at the following address:
 * http://itextdocs.lowagie.com/tutorial/
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * itext-questions@lists.sourceforge.net
 */

package com.lowagie.examples.objects.images.tiff;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Adds an EPS file into a PDF document.
 * 
 * @author blowagie
 */

public class EncapsulatedPostScript {

	/**
	 * Adds an EPS into a PDF document.
	 * 
	 * @param args no arguments needed
	 */
	public static void main(String[] args) {
	    Document document = new Document(PageSize.A4, 50, 50, 50, 50);
	    System.out.println("Encapsulated PostScript");
	    try {
	      PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("eps.pdf"));
	      document.open();
	      Image img = Image.getInstance("parrot.ps");
	      img.scalePercent(50);
	      document.add(img);
	      document.newPage();
	      img = Image.getInstance("tiger.ps");
	      img.scalePercent(50);
	      document.add(img);
	      document.close();
	    }
	    catch (Exception de) {
	      de.printStackTrace();
	    }
	}
}