package boggle;

public class RegularState extends State
{
    public int point;

    public RegularState(BoggleStats boggleStats)
    {
        super(boggleStats);
        this.point = boggleStats.getScore();
    }

    @Override
    public int point()
    {
        return this.point;
    }
}
