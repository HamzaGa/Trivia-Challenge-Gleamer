import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game
{
  List<Player> players = new ArrayList<>();

  LinkedList popQuestions = new LinkedList();
  LinkedList scienceQuestions = new LinkedList();
  LinkedList sportsQuestions = new LinkedList();
  LinkedList rockQuestions = new LinkedList();

  int currentPlayer = 0;

  public Game()
  {
    for (int i = 0; i < 50; i++)
    {
      popQuestions.addLast("Pop Question " + i);
      scienceQuestions.addLast(("Science Question " + i));
      sportsQuestions.addLast(("Sports Question " + i));
      rockQuestions.addLast(createRockQuestion(i));
    }
  }

  public String createRockQuestion(int index)
  {
    return "Rock Question " + index;
  }

  public boolean isPlayable()
  {
    return players.size() >= 2;
  }

  public void add(String playerName)
  {
    players.add(new Player(playerName));
    System.out.println(playerName + " was added");
    System.out.println("They are " + players.size() + " total players");
  }

  public void roll(int roll)
  {
    System.out.println(currentPlayer() + " is the current player");
    System.out.println("They have rolled a " + roll);

    if (currentPlayer().isInPenaltyBox() && roll % 2 == 0)
    {
      System.out.println(currentPlayer() + " is not getting out of the penalty box");
      return;
    }

    currentPlayer().setMayGetOutOfPenaltyBox(true);
    currentPlayer().changePositionAndNotify(roll);
    askQuestion();
  }

  private void askQuestion()
  {
    System.out.println("The category is " + currentCategory());

    if (currentCategory() == "Pop")
      System.out.println(popQuestions.removeFirst());
    if (currentCategory() == "Science")
      System.out.println(scienceQuestions.removeFirst());
    if (currentCategory() == "Sports")
      System.out.println(sportsQuestions.removeFirst());
    if (currentCategory() == "Rock")
      System.out.println(rockQuestions.removeFirst());
  }


  private String currentCategory()
  {
    if (currentPlayer().getPosition() == 0)
      return "Pop";
    if (currentPlayer().getPosition() == 4)
      return "Pop";
    if (currentPlayer().getPosition() == 8)
      return "Pop";
    if (currentPlayer().getPosition() == 1)
      return "Science";
    if (currentPlayer().getPosition() == 5)
      return "Science";
    if (currentPlayer().getPosition() == 9)
      return "Science";
    if (currentPlayer().getPosition() == 2)
      return "Sports";
    if (currentPlayer().getPosition() == 6)
      return "Sports";
    if (currentPlayer().getPosition() == 10)
      return "Sports";
    return "Rock";
  }

  public void handleAnswerNature(boolean isCorrectAnswer)
  {
    if(!isCorrectAnswer){
      System.out.println("Question was incorrectly answered");
      currentPlayer().toPenaltyBox();
      System.out.println(currentPlayer() + " was sent to the penalty box");
      turnPass();
      return;
    }

    if (currentPlayer().isInPenaltyBox() && !currentPlayer().mayGetOutOfPenaltyBox())
    {
      turnPass();
      return;
    }

    System.out.println("Answer was correct!!!!");
    currentPlayer().rewardAndNotify();
    if (didPlayerWin())
    {
      System.out.println("******** Congratulations " + currentPlayer().getName() + " You won!!! ********");
      System.exit(0);
    }
    turnPass();
  }

  private Player currentPlayer()
  {
    return players.get(currentPlayer);
  }

  private void turnPass()
  {
    currentPlayer++;
    if (currentPlayer == players.size())
      currentPlayer = 0;
  }

  private boolean didPlayerWin()
  {
    return currentPlayer().getCoins() == 6;
  }
}
