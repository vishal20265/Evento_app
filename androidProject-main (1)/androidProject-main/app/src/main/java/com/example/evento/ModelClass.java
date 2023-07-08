package com.example.evento;

public class ModelClass {
    private int imgView1;
    private String txtView1;
    private String txtView2;

    ModelClass(int imgView1, String txtView1,String txtView2){
        this.imgView1=imgView1;
        this.txtView1=txtView1;
        this.txtView2=txtView2;

    }

    public int getImgView1() {
        return imgView1;
    }

    public String getTxtView1() {
        return txtView1;
    }

    public String getTxtView2() {
        return txtView2;
    }

}
