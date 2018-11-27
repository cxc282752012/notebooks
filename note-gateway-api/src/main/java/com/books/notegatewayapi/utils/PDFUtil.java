package com.books.notegatewayapi.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;

import org.slf4j.LoggerFactory;
import org.xhtmlrenderer.pdf.ITextRenderer;

public class PDFUtil {
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(PDFUtil.class);

    /**
     * html filepath to pdf file
     * 
     * @Description
     * @author wangyuhai
     * @param htmlFile
     * @param pdfFile
     */
    public static void htmlFile2pdf(String htmlFile, String pdfFile) {
        // step 1
        String url;
        OutputStream os = null;
        try {
            url = new File(htmlFile).toURI().toURL().toString();
            os = new FileOutputStream(pdfFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocument(url);
            // renderer.setDocumentFromString(htmlFile);
            // 解决中文不显示问题
            // ITextFontResolver fontResolver = renderer.getFontResolver();
            // fontResolver.addFont(chineseFontPath, BaseFont.IDENTITY_H,
            // BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        } catch (MalformedURLException e) {
            LOGGER.warn(e.toString(), e);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e.toString(), e);
        } catch (com.lowagie.text.DocumentException e) {
            LOGGER.warn(e.toString(), e);
        } catch (IOException e) {
            LOGGER.warn(e.toString(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LOGGER.warn(e.toString(), e);
                }
            }
        }
    }

    /**
     * html file content to pdf
     * 
     * @Description
     * @author wangyuhai
     * @param htmlFile
     * @param pdfFile
     */
    public static void htmlContent2pdf(String content, String pdfFile) {
        // step 1
        String url;
        OutputStream os = null;
        try {
            os = new FileOutputStream(pdfFile);
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(content);
            // 解决中文不显示问题
            // ITextFontResolver fontResolver = renderer.getFontResolver();
            // fontResolver.addFont(chineseFontPath, BaseFont.IDENTITY_H,
            // BaseFont.NOT_EMBEDDED);

            renderer.layout();
            renderer.createPDF(os);
        } catch (FileNotFoundException e) {
            LOGGER.warn(e.toString(), e);
        } catch (com.lowagie.text.DocumentException e) {
            LOGGER.warn(e.toString(), e);
        } catch (IOException e) {
            LOGGER.warn(e.toString(), e);
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    LOGGER.warn(e.toString(), e);
                }
            }
        }
    }

    public static void main(String[] args) {
        try {
            String htmlFilePath = "D:\\MM-201806-001-000175220180615024540804.html";
            String htmlFileContent = "<html><head></head><body>123</body></html>";
            // 中文字体存储路径
            // String chineseFontPath =
            // "E:\\projects\\learn\\spring-boot\\springboot-learn\\springboot-learn\\springboot-poi\\src\\main\\resources\\Fonts\\simsun.ttc";
            // html转pdf
            htmlFile2pdf(htmlFilePath, "D:\\MM-201806-001-000175220180615024540804.pdf");
            // htmlContent2pdf(htmlFileContent,
            // "D:\\MM-201806-001-000175220180615024540804.pdf");
            System.out.println("convert successful！");
        } catch (Exception e) {
            LOGGER.error("html convert 2 pdf failure", e);
        }
    }
}
