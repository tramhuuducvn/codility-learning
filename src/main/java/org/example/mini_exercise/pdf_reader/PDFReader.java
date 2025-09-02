package org.example.mini_exercise.pdf_reader;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.util.Logger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PDFReader {

    public static void main(String[] args) throws IOException {
        File file = new File("./src/main/java/org/example/mini_exercise/pdf_reader/PDFReader.java");
        Logger.log("The file exist: " + file.exists());
        PDDocument document = Loader.loadPDF(file);
        PDFTextStripper stripper = new PDFTextStripper();
        String text = stripper.getText(document);
        Logger.log(text);
        document.close();
    }
}
