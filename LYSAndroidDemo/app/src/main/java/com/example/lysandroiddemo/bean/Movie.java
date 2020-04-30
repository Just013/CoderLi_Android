package com.example.lysandroiddemo.bean;

import java.util.List;

public class Movie {
//    public Movie(){}
    private Integer count;
    private Integer total;

    private List<Subjects> subjects;

    public List<Subjects> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subjects> subjects) {
        this.subjects = subjects;
    }


    public class Subjects{
        private Images images;

        public Images getImages() {
            return images;
        }

        public void setImages(Images images) {
            this.images = images;
        }

    }

    public class Images{
        private String small;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }


}
