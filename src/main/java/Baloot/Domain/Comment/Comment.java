package Baloot.Domain.Comment;

public class Comment {

    private String userEmail;
    private String username;
    private Integer commodityId;
    private String text;
    private String date;

    private Integer likeCount;
    private Integer dislikeCount;

    public Comment(
            String userEmail_,
            String username_,
            Integer commodityId_,
            String text_,
            String date_){
        this.userEmail = userEmail_;
        this.username = username_;
        this.commodityId = commodityId_;
        this.text = text_;
        this.date = date_;
        this.likeCount = 0;
        this.dislikeCount = 0;
    }

    public String getUsername() {
        return username;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount() {
        this.likeCount++;
    }

    public Integer getDislikeCount() {
        return dislikeCount;
    }

    public void setDislikeCount() {
        this.dislikeCount++;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public Integer getCommodityId() {
        return commodityId;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }
}
