package com.books.noteadminribbon.utils;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSize;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JComponent;

import org.springframework.beans.factory.annotation.Value;

public class ImagePrint extends JComponent {

    @Value("${image.upload.path}")
    private String fileUploadPath;

    private final static String UPLOADED_FOLDER = "F://temp//";

    public void imageMain(String... args) throws IOException, PrinterException {

        String imageurl = null;
        String price = null;
        String fileUploadPath = null;

        if (args.length > 2) {

            imageurl = args[0];
            price = args[1];
            fileUploadPath = args[2];

        } else {
            imageurl = args[0];
            fileUploadPath = args[1];
        }

        String url = "https://barcode.tec-it.com/barcode.ashx?data=" + imageurl;

        URL obj = new URL(url);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        String fpath = fileUploadPath + "/Barcode/";

        File fi1 = new File(fpath);
        if (!fi1.exists())
            fi1.mkdirs();
        fi1 = new File(fpath + imageurl + ".gif");
        FileOutputStream fileOS = new FileOutputStream(fi1);

        try (InputStream inputStream = obj.openStream()) {
            int n = 0;
            byte[] buffer = new byte[1024];
            while (-1 != (n = inputStream.read(buffer))) {
                output.write(buffer, 0, n);
                fileOS.write(buffer);
            }
        }

        fileOS.flush();
        fileOS.close();

        BufferedImage image = new BufferedImage(40, 30, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();

        Image image2 = Toolkit.getDefaultToolkit().getImage(fpath + imageurl + ".gif");

        PrinterServiceUtil ps = new PrinterServiceUtil(image2, price);

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(OrientationRequested.PORTRAIT);

        aset.add(new MediaPrintableArea(24, 3, 55, 48, MediaSize.MM));

        PrinterJob oJob = PrinterJob.getPrinterJob();
        PageFormat pf = oJob.defaultPage();
        oJob.setPrintable(ps, pf);

        try {
            oJob.print(aset);
        } catch (PrinterException ex) {
            ex.printStackTrace();
            System.out.println(" fdklajsdfjas " + ex);
        }

        PageFormat format = new PageFormat();
        Paper paper = new Paper();
        double margin = 5;
        paper.setImageableArea(34, 2, 35, 28);

        format.setPaper(paper);

        System.out.println(ps.getPrinters());
        // ps.printBytes("Gprinter GP-1324D", fileContent);
        ps.print(graphics2D, format, 1);
    }

}
