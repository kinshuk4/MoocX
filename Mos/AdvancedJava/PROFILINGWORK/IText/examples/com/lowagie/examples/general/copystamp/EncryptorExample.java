/*
 * $Id: EncryptorExample.java,v 1.2 2005/05/09 11:52:51 blowagie Exp $
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
package com.lowagie.examples.general.copystamp;

import java.io.FileOutputStream;

import com.lowagie.text.pdf.PdfEncryptor;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Encrypts an existing PDF file.
 */
public class EncryptorExample {

	/**
     * Reads and encrypts an existing PDF file.
     * @param args no arguments needed
     */
	public static void main(String[] args) {
		System.out.println("Encryptor example");
		try {
			PdfReader reader = new PdfReader("ChapterSection.pdf");
			PdfEncryptor.encrypt(reader,
					new FileOutputStream("encrypted.pdf"),
					"Hello".getBytes(),
					"World".getBytes(),
					PdfWriter.AllowPrinting | PdfWriter.AllowCopy,
					false);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
