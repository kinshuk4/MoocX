/*
 * $Id: ReadEncrypted.java,v 1.2 2005/05/09 11:52:49 blowagie Exp $
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
package com.lowagie.examples.general.read;

import java.io.BufferedWriter;
import java.io.FileWriter;

import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfWriter;

/**
 * Reading an encrypted PDF file (you need the owner password to do this).
 */
public class ReadEncrypted {

	/**
	 * Reads an encrypted PDF document.
	 * 
	 * @param args
	 *            no arguments needed
	 */
	public static void main(String[] args) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(
					"info_encrypted.txt"));
			PdfReader r = new PdfReader("HelloEncrypted.pdf", "Hello"
						.getBytes());
			out.write(r.getInfo().toString());
			out.write("\r\n");
			int permissions = r.getPermissions();
			out.write("Printing allowed: " + ((PdfWriter.AllowPrinting & permissions) > 0) );
			out.write("\r\n");
			out.write("Modifying contents allowed: " + ((PdfWriter.AllowModifyContents & permissions) > 0) );
			out.write("\r\n");
			out.write("Copying allowed: " + ((PdfWriter.AllowCopy & permissions) > 0) );
			out.write("\r\n");
			out.write("Modifying annotations allowed: " + ((PdfWriter.AllowModifyAnnotations & permissions) > 0) );
			out.write("\r\n");
			out.write("Fill in allowed: " + ((PdfWriter.AllowFillIn & permissions) > 0) );
			out.write("\r\n");
			out.write("Screen Readers allowed: " + ((PdfWriter.AllowScreenReaders & permissions) > 0) );
			out.write("\r\n");
			out.write("Assembly allowed: " + ((PdfWriter.AllowAssembly & permissions) > 0) );
			out.write("\r\n");
			out.write("Degraded printing allowed: " + ((PdfWriter.AllowDegradedPrinting & permissions) > 0) );
			out.write("\r\n");
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}