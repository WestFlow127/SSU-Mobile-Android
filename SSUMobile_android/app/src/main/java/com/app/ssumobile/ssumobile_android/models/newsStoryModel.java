package com.app.ssumobile.ssumobile_android.models;

import java.util.Comparator;

/**
 * Created by ben on 11/12/15.
 * Modified by Weston on 11/30/17
 */
public class newsStoryModel {
    public String id;
    public String published;
    public String link;
    public String content;
    public String summary;
    public String image_url;
    public String title;
    public String category;

    public static Comparator<newsStoryModel> COMPARE_BY_PUBLISHED = new Comparator<newsStoryModel>() {
        public int compare(newsStoryModel one, newsStoryModel other) {
            return other.published.compareTo(one.published);
        }
    };

}
