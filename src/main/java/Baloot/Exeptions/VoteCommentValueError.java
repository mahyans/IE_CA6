package Baloot.Exeptions;

public class VoteCommentValueError extends Exception{
    public VoteCommentValueError() {
        super("Insufficient Voting Please Vote Between [1, -1, 0]"
                + "Forbidden!"
        );
    }
}
