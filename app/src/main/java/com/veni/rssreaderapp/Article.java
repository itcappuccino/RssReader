package com.veni.rssreaderapp;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Root(name = "item", strict = false)
public class Article  {

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;



    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Element(name = "pubDate", required = false)
    private String publicationDate;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Element(name = "encoded", required = false)
    private String content;

    public Date getPubDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy hh:mm:ss Z", Locale.UK);
        Date date = new Date();
        try {
            date = dateFormat.parse(publicationDate);
        } catch (Exception e) {

        }
        return date;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Element(name = "description")
    private String description;

    public String getPictureUrl(){
        Pattern p = Pattern.compile("<img [^>]*src=[\"']([^\"^']*)",
                Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(description);

        String pictureUrl;
        if (m.find()) {
             pictureUrl = m.group(1);
             return pictureUrl;
        }else if(content!=null){
            p = Pattern.compile("<img class[^>]*src=[\"']([^\"^']*)",
                    Pattern.CASE_INSENSITIVE);
            m = p.matcher(content);
            if (m.find()) {
                pictureUrl = m.group(1);
                return pictureUrl;
            }
        }
        return null;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
