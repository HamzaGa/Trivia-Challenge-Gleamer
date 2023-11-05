public class Player
{
  private String name;
  private Integer position;
  private Integer coins;
  private Boolean isInPenaltyBox;
  private Boolean mayGetOutOfPenaltyBox;

  public Player(String name)
  {
    this.name = name;
    this.position = 0;
    this.coins = 0;
    this.isInPenaltyBox = false;
    this.mayGetOutOfPenaltyBox = false;
  }

  public String getName()
  {
    return name;
  }

  public Integer getPosition()
  {
    return position;
  }

  public void changePosition(Integer step)
  {
    this.position += step;
    if (this.position > 11)
      this.position = this.position - 12;
  }

  public void changePositionAndNotify(Integer step)
  {
    changePosition(step);
    System.out.println(this.name + "'s new location is "+ this.position);
  }

  public Integer getCoins()
  {
    return coins;
  }

  public void reward()
  {
    this.coins++;
  }

  public void rewardAndNotify()
  {
    reward();
    System.out.println(name + " now has " + coins + " Gold Coins.");
  }

  public Boolean isInPenaltyBox()
  {
    return isInPenaltyBox;
  }

  public void toPenaltyBox()
  {
    isInPenaltyBox = true;
    mayGetOutOfPenaltyBox = false;
  }

  public void setMayGetOutOfPenaltyBox(boolean mayGetOutOfPenaltyBox)
  {
    this.mayGetOutOfPenaltyBox = mayGetOutOfPenaltyBox;
  }

  public boolean mayGetOutOfPenaltyBox()
  {
    return this.mayGetOutOfPenaltyBox;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
