import java.time.LocalDate;

public class Opinion {


    private String header;
    private String opinionContent;
    private String nick;
    private double stars;
    private LocalDate opinionDate;
    private int votesFor;
    private int votesAgainst;

    public Opinion(String header, String opinionContent, String nick, double stars, LocalDate opinionDate, int votesFor, int votesAgainst) {
        this.header = header;
        this.opinionContent = opinionContent;
        this.nick = nick;
        this.stars = stars;
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

    public double getStars() {
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
        return "\n Opinion: " +
                "\n header: " + header +
                "\n content: " + opinionContent +
                "\n nick: " + nick +
                "\n stars: " + stars +
                "\n stars: " + stars +
                "\n date: " + opinionDate +
                "\n votesFo: " + votesFor +
                "\n votesAgainst: " + votesAgainst;
    }


}
