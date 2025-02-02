package org.example.design_pattern.structural.adapter.translation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class VietnameseClient {

    public static void main(String[] args) {
        VietnameseTarget target = new TranslatorAdapter(new JapaneseAdaptee());
        target.send("Hello World!");
    }

    private void example() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter your name: ");
        try {
            String s = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
