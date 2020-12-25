package com.example.smartnotes;

public class Note {
    private String Title;
    private String Content;
    private String Date;
    public Note(){}
    public Note(String Title,String Content,String Date){
        this.Title=Title;
        this.Content=Content;
        this.Date=Date;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
