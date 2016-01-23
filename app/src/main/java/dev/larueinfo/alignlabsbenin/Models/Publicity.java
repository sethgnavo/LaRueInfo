package dev.larueinfo.alignlabsbenin.Models;

/**
 * @author Seth-Pharès Gnavo (sethgnavo)
 */
public class Publicity {
    //image, contenu, date, nom de l'entreprise
    private String adPictureUrl;
    private String adTitle;
    private String adAuthorName;
    private long adTime;

    public Publicity() {
    }

    public Publicity(String adPictureUrl, String adTitle, String adAuthorName, long adTime) {
        this.adPictureUrl = adPictureUrl;
        this.adTitle = adTitle;
        this.adAuthorName = adAuthorName;
        this.adTime = adTime;
    }

    public String getAdPictureUrl() {
        return adPictureUrl;
    }

    public void setAdPictureUrl(String adPictureUrl) {
        this.adPictureUrl = adPictureUrl;
    }

    public String getAdTitle() {
        return adTitle;
    }

    public void setAdTitle(String adTitle) {
        this.adTitle = adTitle;
    }

    public String getAdAuthorName() {
        return adAuthorName;
    }

    public void setAdAuthorName(String adAuthorName) {
        this.adAuthorName = adAuthorName;
    }

    public long getAdTime() {
        return adTime;
    }

    public void setAdTime(long adTime) {
        this.adTime = adTime;
    }
}