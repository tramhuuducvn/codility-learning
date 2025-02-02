package org.example.design_pattern.structural.adapter.translation;

import org.example.util.Logger;

public class JapaneseAdaptee {

    public void receive(String words) {
        Logger.log("Retrieving words from adapter...");
        Logger.log(words);
    }
}
