package com.itextpdf.samples.book.part4.chapter15;

import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.canvas.parser.filter.IEventFilter;
import com.itextpdf.kernel.pdf.canvas.parser.listener.FilteredTextEventListener;
import com.itextpdf.kernel.pdf.canvas.parser.listener.LocationTextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.kernel.pdf.canvas.parser.listener.ITextExtractionStrategy;
import com.itextpdf.kernel.pdf.canvas.parser.filter.TextRegionEventFilter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.io.OutputStreamWriter;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import org.xml.sax.SAXException;

public class Listing_15_28_ExtractPageContentArea {
    public static final String DEST
            = "./target/book/part4/chapter15/Listing_15_28_ExtractPageContentArea.txt";
    public static final String PREFACE
            = "./src/main/resources/pdfs/preface.pdf";

    public static void main(String args[])
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        File file = new File(DEST);
        file.getParentFile().mkdirs();

        new Listing_15_28_ExtractPageContentArea().manipulatePdf(DEST);
    }

    public void manipulatePdf(String dest)
            throws IOException, SQLException, ParserConfigurationException, SAXException {
        parsePdf(PREFACE, dest);
    }

    public void parsePdf(String src, String txt) throws IOException {
        PrintWriter out = new PrintWriter(new OutputStreamWriter(new FileOutputStream(txt), "UTF-8"));
        PdfDocument pdfDoc = new PdfDocument(new PdfReader(src), new PdfWriter(new ByteArrayOutputStream()));
        Rectangle rect = new Rectangle(70, 80, 420, 500);
        IEventFilter filter = new TextRegionEventFilter(rect);
        ITextExtractionStrategy strategy;
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {
             strategy = new FilteredTextEventListener(new LocationTextExtractionStrategy(), filter);
             out.println(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i), strategy));
        }
        out.flush();
        out.close();
        pdfDoc.close();
    }
}
