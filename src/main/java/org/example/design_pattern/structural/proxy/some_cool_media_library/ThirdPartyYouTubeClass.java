package org.example.design_pattern.structural.proxy.some_cool_media_library;

import java.util.HashMap;

import org.example.util.Logger;

public class ThirdPartyYouTubeClass implements ThirdPartyYouTubeLib {

    @Override
    public HashMap<String, Video> popularVideos() {
        this.connectToServer("http://www.youtube.com");

        return getRandomVideos();
    }

    @Override
    public Video getVideo(String videoId) {
        connectToServer("http://www.youtube.com/" + videoId);
        return getSomeVideo(videoId);
    }

    // -----------------------------------------------------------------------
    // Fake methods to simulate network activity. They as slow as a real life.

    private void connectToServer(String server) {
        Logger.log("Connect to " + server + "...");
        experienceNetworkLatency();
        Logger.log("Connected!");
    }

    private void experienceNetworkLatency() {
        int randomLatency = random(5, 10);
        for (int i = 0; i < randomLatency; ++i) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Logger.printStackTrace(e);
            }
        }
    }

    private int random(int min, int max) {
        return min + (int) (Math.random() * (max - min + 1));
    }

    private HashMap<String, Video> getRandomVideos() {
        Logger.log("Downloading popular videos...");
        HashMap<String, Video> hmap = new HashMap<>();

        experienceNetworkLatency();
        hmap.put("catzzzzzzzzz", new Video("sadgahasgdas", "Catzzzz.avi"));
        hmap.put("mkafksangasj", new Video("mkafksangasj", "Dog play with ball.mp4"));
        hmap.put("dancesvideoo", new Video("asdfas3ffasd", "Dancing video.mpq"));
        hmap.put("dlsdk5jfslaf", new Video("dlsdk5jfslaf", "Barcelona vs RealM.mov"));
        hmap.put("3sdfgsd1j333", new Video("3sdfgsd1j333", "Programing lesson#1.avi"));

        Logger.log("Done!");
        return hmap;
    }

    private Video getSomeVideo(String videoId) {
        Logger.log("Downloading video...");
        experienceNetworkLatency();
        Video video = new Video(videoId, "Some video title");
        Logger.log("Done!");
        return video;
    }
}
