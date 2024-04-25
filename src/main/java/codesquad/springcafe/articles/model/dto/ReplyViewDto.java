package codesquad.springcafe.articles.model.dto;

import codesquad.springcafe.articles.model.Reply;

import java.time.LocalDate;

public class ReplyViewDto {
    private final Reply reply;
    private final boolean editRight;
    private long articleId;

    public ReplyViewDto(Reply reply, boolean editRight) {
        this.reply = reply;
        this.editRight = editRight;
    }

    public boolean hasEditRight() {
        return editRight;
    }


    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public long getArticleId() {
        return articleId;
    }

    public long getReplyId() {
        return reply.getReplyId();
    }

    public String getUserId() {
        return reply.getUserId();
    }

    public String getComment() {
        return reply.getComment();
    }

    public LocalDate getCreationDate() {
        return reply.getCreationDate();
    }

    public boolean getEditRight() {
        return editRight;
    }

}
