package Baloot.Domain.Comment;

public class CommentInit {

    private String userEmail;
    private Integer commodityId;
    private String text;
    private String date;

    public CommentInit(
        String userEmail_,
        Integer commodityId_,
        String text_,
        String date_){
         this.userEmail = userEmail_;
         this.commodityId = commodityId_;
         this.text = text_;
         this.date = date_;
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
