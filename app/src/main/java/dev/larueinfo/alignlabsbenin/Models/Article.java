package dev.larueinfo.alignlabsbenin.Models;

/**
 * @author Seth-Pharès Gnavo
 */
public class Article {
    private String articleTitle;
    private String articleDescription;
    private String graphicDescription;
    private String rawHtmlContent;
    private String authorName;
    private String sourceName;
    private long issueTime;

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }

    public String getGraphicDescription() {
        return graphicDescription;
    }

    public void setGraphicDescription(String graphicDescription) {
        this.graphicDescription = graphicDescription;
    }

    public String getRawHtmlContent() {
        return rawHtmlContent;
    }

    public void setRawHtmlContent(String rawHtmlContent) {
        this.rawHtmlContent = rawHtmlContent;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public long getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(long issueTime) {
        this.issueTime = issueTime;
    }
}