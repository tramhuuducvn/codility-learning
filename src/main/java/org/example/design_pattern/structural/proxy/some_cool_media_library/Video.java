package org.example.design_pattern.structural.proxy.some_cool_media_library;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Video {
    private String id;
    private String title;
    private String data;

    public Video(String id, String title) {
        this.id = id;
        this.title = title;
        this.data = "Random video.";
    }
}
