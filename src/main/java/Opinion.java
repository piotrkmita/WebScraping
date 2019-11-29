import java.time.LocalDate;

public class Opinion {


    private String header;
    private String opinionContent;
    private String nick;
    private String stars;
    private LocalDate opinionDate;
    private int votesFor;
    private int votesAgainst;

    public Opinion(String header, String opinionContent, String nick, LocalDate opinionDate, int votesFor, int votesAgainst) {
        this.header = header;
        this.opinionContent = opinionContent;
        this.nick = nick;
        this.opinionDate = opinionDate;
        this.votesFor = votesFor;
        this.votesAgainst = votesAgainst;
    }

    public String getHeader() {
        return header;
    }

    public String getContent() {
        return opinionContent;
    }

    public String getNick() {
        return nick;
    }

    public String getStars() {
        return stars;
    }

    public LocalDate getDate() {
        return opinionDate;
    }

    public int getVotesFor() {
        return votesFor;
    }

    public int getVotesAgainst() {
        return votesAgainst;
    }

    @Override
    public String toString() {
        return "Opinion{" +
                "header='" + header + '\'' +
                ", content='" + opinionContent + '\'' +
                ", nick='" + nick + '\'' +
                ", stars='" + stars + '\'' +
                ", date=" + opinionDate +
                ", votesFor=" + votesFor +
                ", votesAgainst=" + votesAgainst +
                '}';
    }


}
