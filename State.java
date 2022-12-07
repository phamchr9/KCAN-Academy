package boggle;

public abstract class State
{
    protected BoggleStats boggleStats;

    public State(BoggleStats boggleStats){
        this.boggleStats = boggleStats;

    }
    public abstract int point();
}
