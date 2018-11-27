package com.books.noteh5servers.utils;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.JobPriority;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.List;

public class PrinterServiceUtil implements Printable {
	
	
	
	Image img;
	String price;
	 public PrinterServiceUtil(Image img1,String price) {
		 	img = img1;
		    
		 this.price=price;
		  }
	 
    public List<String> getPrinters(){

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);

        List<String> printerList = new ArrayList<String>();
        for(PrintService printerService: printServices){
            printerList.add( printerService.getName());
        }

        return printerList;
    }

    @Override
    public int print(Graphics g, PageFormat pf, int page)
            throws PrinterException {
        if (page > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        /*
         * User (0,0) is typically outside the imageable area, so we must
         * translate by the X and Y values in the PageFormat to avoid clipping
         */
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(pf.getImageableX(), pf.getImageableY());
        g2d.drawImage(img, 16, -5, 80, 60, null);
               /* Now we perform our rendering */

        //g.setFont(new Font("Roman", 0, 8));
        
        
      if(price!=null) {
        g.drawString(" MRP: ₹"+price, 25, 70);
      }
        return PAGE_EXISTS;
    }

    public void printString(String printerName, String text) {

        // find the printService of name printerName
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        DocPrintJob job = service.createPrintJob();

        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);


            job.print(doc, null);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.GIF;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
		
        PrintService printService[] = PrintServiceLookup.lookupPrintServices(
                flavor, pras);
        PrintService service = findPrintService(printerName, printService);

        
        DocPrintJob job = service.createPrintJob();

        try {
        	PrintRequestAttributeSet requestAttributeSet = new HashPrintRequestAttributeSet();
    		requestAttributeSet.add(new JobPriority(1));
    		//requestAttributeSet.add(new Copies(1));
    		//requestAttributeSet.add(MediaSizeName.ISO_A5);
    		//requestAttributeSet.add(new MediaPrintableArea(34, 2, 35, 28, MediaSize.MM));
    		requestAttributeSet.add(new MediaPrintableArea(34, 2, 35, 10, MediaSize.MM));
    		
    		
            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, requestAttributeSet);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PrintService findPrintService(String printerName,
            PrintService[] services) {
        for (PrintService service : services) {
            if (service.getName().equalsIgnoreCase(printerName)) {
                return service;
            }
        }

        return null;
    }
}