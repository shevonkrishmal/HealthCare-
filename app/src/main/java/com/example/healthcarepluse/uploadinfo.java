package com.example.healthcarepluse;

public class uploadinfo {

        public String name;
        public String imageUrl;
        public uploadinfo(){}

        public uploadinfo(String name, String imageUrl) {
            this.name = name;
            this.imageUrl = imageUrl;
        }

        public String getname() {
            return name;
        }
        public String getimageUrl() {
            return imageUrl;
        }
    }


