package org.example.mini_exercise.pdf_reader;

import java.io.IOException;

import org.example.util.Logger;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;

public class ITextPDFTest {
    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader("./src/main/java/org/example/mini_exercise/pdf_reader/Factory Method.pdf");
        int pages = reader.getNumberOfPages();
        StringBuilder text = new StringBuilder();
        for (int i = 1; i <= pages; i++) {
            String contentPage = PdfTextExtractor.getTextFromPage(reader, i);
            text.append(contentPage);
            Logger.print(String.format("========== Page %d ============\n", i));
            Logger.log(contentPage);
        }
        // Logger.log(text);
        reader.close();

    }
}
