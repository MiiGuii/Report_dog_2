package com.example.lucian.myapplication;

/**
 * Created by lucian on 26-07-18.
 */

public class Reporte {
    private String image;
    private String descc;

    public Reporte(String image,String descc){
        this.image = image;
        this.descc = descc;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescc() {
        return descc;
    }

    public void setDescc(String descc) {
        this.descc = descc;
    }
}
