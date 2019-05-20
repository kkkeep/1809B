package com.jy.small.training.repository.entity;

import java.util.ArrayList;

/*
 * created by taofu on 2019-05-20
 **/
public class ZhihuDailyData {

    private String date;

    private ArrayList<Daily> stories;


    private ArrayList<TopBanner> top_stories;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ArrayList<Daily> getStories() {
        return stories;
    }

    public void setStories(ArrayList<Daily> stories) {
        this.stories = stories;
    }

    public ArrayList<TopBanner> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(ArrayList<TopBanner> top_stories) {
        this.top_stories = top_stories;
    }

    public class TopBanner{
        private String image;
        private String ga_prefix;
        private String title;
        private int type;
        private int id;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }


    public class Daily{
        private ArrayList<String> images;
        private String ga_prefix;
        private String title;
        private int type;
        private int id;
        private  boolean multipic;

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }
    }
}
