package com.example.prakriti.myapplication.Pojo;

import java.io.Serializable;

/**
 * Created by Prakriti on 9/1/2017.
 */

public class MyData implements Serializable {
       Integer id;
        String  name, description, image,price;

        public MyData(Integer id, String name, String price, String description, String image) {
                this.id = id;
                this.name = name;
                this.price = price;
                this.description = description;
                this.image = image;

        }

        public Integer getId() {
                return id;
        }

        public void setId(Integer id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getPrice() {
                return price;
        }

        public void setPrice(String price) {
                this.price = price;
        }

        public String getDescription() {
                return description;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getImage() {
                return image;
        }

        public void setImage(String image) {
                this.image = image;
        }



}
