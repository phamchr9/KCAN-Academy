package boggle;

public class BonusState extends State
{
    public int point;

    public BonusState(BoggleStats boggleStats)
    {
        super(boggleStats);
        this.point = boggleStats.getScore();
    }

    @Override
    public int point()
    {
        return this.point*2;
    }
}
