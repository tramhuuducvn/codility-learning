package org.example.design_pattern.structural.adapter.translation;

import org.example.util.Logger;

public class TranslatorAdapter implements VietnameseTarget {

    private JapaneseAdaptee adaptee;

    public TranslatorAdapter(JapaneseAdaptee adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public void send(String words) {
        Logger.log("Reading words...");
        Logger.log(words);
        String vietnameseWords = translate(words);
        adaptee.receive(vietnameseWords);
    }

    private String translate(String vietnameseWords) {
        Logger.log("Translated");
        return "こんにちは";
    }
}
