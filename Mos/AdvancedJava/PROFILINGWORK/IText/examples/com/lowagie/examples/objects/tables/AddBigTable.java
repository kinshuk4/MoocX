/*
 * $Id: AddBigTable.java,v 1.3 2005/05/09 11:52:47 blowagie Exp $
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
package com.lowagie.examples.objects.tables;

import java.io.FileOutputStream;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;

/**
 * Add a big table to a PDF with document.add().
 */
public class AddBigTable {

    /**
     * Big PdfPTable with document.add().
     *
     * @param args
     *            no arguments needed
     */
    public void test() {


        System.out.println("document.add(BigTable)");
        // step1
        Document document = new Document(PageSize.A4.rotate(), 10, 10, 10, 10);
        try {
            // step2
            PdfWriter writer = PdfWriter.getInstance(document,
                    new ByteArrayOutputStream(120000));
            // step3
            document.open();
            // step4
            String[] bogusData = {"M0065920", "SL", "FR86000P", "PCGOLD",
                "119000", "96 06", "2001-08-13", "4350", "6011648299",
                "FLFLMTGP", "153", "119000.00"};
            int NumColumns = 12;

            PdfPTable datatable = new PdfPTable(NumColumns);
            int headerwidths[] = {9, 4, 8, 10, 8, 11, 9, 7, 9, 10, 4, 10}; // percentage
            datatable.setWidths(headerwidths);
            datatable.setWidthPercentage(100); // percentage
            datatable.getDefaultCell().setPadding(3);
            datatable.getDefaultCell().setBorderWidth(2);
            datatable.getDefaultCell().setHorizontalAlignment(
                    Element.ALIGN_CENTER);
            datatable.addCell("Clock #");
            datatable.addCell("Trans Type");
            datatable.addCell("Cusip");
            datatable.addCell("Long Name");
            datatable.addCell("Quantity");
            datatable.addCell("Fraction Price");
            datatable.addCell("Settle Date");
            datatable.addCell("Portfolio");
            datatable.addCell("ADP Number");
            datatable.addCell("Account ID");
            datatable.addCell("Reg Rep ID");
            datatable.addCell("Amt To Go ");

            datatable.setHeaderRows(1); // this is the end of the table header

            datatable.getDefaultCell().setBorderWidth(1);
            for (int i = 1; i < 750; i++) {
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(0.9f);
                }
                for (int x = 0; x < NumColumns; x++) {
                    datatable.addCell(bogusData[x]);
                }
                if (i % 2 == 1) {
                    datatable.getDefaultCell().setGrayFill(0.0f);
                }
            }
            document.add(datatable);
        } catch (Exception de) {
            de.printStackTrace();
        }
        // step5
        document.close();

    }

    public static void main(String[] args) {
        int times = Integer.parseInt(args[0]);
        for (int i = 0; i < times; ++i) {
            long t = System.currentTimeMillis();
            new AddBigTable().test();
            t = System.currentTimeMillis() - t;
            System.out.println(t);
        }
    }
}