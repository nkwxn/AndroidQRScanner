package com.example.qrscanner;

public class QRContent {
    private String id;
    private String contents;

    public QRContent(){}
    public QRContent(String id, String datas){
        this.id = id;
        this.contents = datas;
    }

    public QRContent(String datas){
        this.contents = datas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
