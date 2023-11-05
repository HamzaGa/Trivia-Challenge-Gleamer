import java.util.ArrayList;
import java.util.List;

public class Game
{
  private List<Player> players;
  private QuestionProvider questionProvider;

  int currentPlayer = 0;

  public Game()
  {
    players = new ArrayList<>();
    questionProvider = new QuestionProvider();
  }

  public boolean isPlayable()
  {
    return players.size() >= 2;
  }

  public void addPlayer(String playerName)
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
    System.out.println("The category is " + questionProvider.getCategoryByPlayerPosition(currentPlayer().getPosition()));
    System.out.println(questionProvider.getQuestionByPlayerPosition(currentPlayer().getPosition()));
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
