import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

  /**
   * @return true if there is more than one player in the game; false otherwise.
   */
  public boolean isPlayable()
  {
    return players.size() >= 2;
  }

  /**
   * Creates a new player and adds it to the game.
   * @param playerName player's name to add
   */
  public void addPlayer(String playerName)
  {
    players.add(new Player(playerName));
    System.out.println(playerName + " was added");
    System.out.println("They are " + players.size() + " total players");
  }

  /**
   * Rolls a dice (1-6)
   * If the player is in penalty box and gets an even number, he remains suspended
   * Otherwise, he gets flagged as mayGetOutOfPenaltyBox, his position gets changed according to the roll result and he gets asked a question
   */
  public void roll()
  {
    int roll = ThreadLocalRandom.current().nextInt(1, 7);
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

  /**
   * Prints the question category corresponding to the current player's position
   * Prints a question from that category
   */
  private void askQuestion()
  {
    System.out.println("The category is " + questionProvider.getCategoryByPlayerPosition(currentPlayer().getPosition()));
    System.out.println(questionProvider.getQuestionByPlayerPosition(currentPlayer().getPosition()));
  }

  /**
   * If the player provides a wrong answer, he goes to the penalty box, the turn passes.
   * If the player provides a correct answer, is already in the penalty box, and is not marked as mayGetOutOfPenaltyBox, he remains suspended and the turn passes.
   * Otherwise, the player gets rewarded. We end the game if he is a winner. The turn passes otherwise.
   * @param isCorrectAnswer a boolean input to indicate whether the player has correctly answered the question or not
   */
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
    if (didPlayerWin(currentPlayer()))
    {
      System.out.println("******** Congratulations " + currentPlayer().getName() + " You won!!! ********");
      System.exit(0);
    }
    turnPass();
  }

  /**
   * @return Player the player that has the turn.
   */
  private Player currentPlayer()
  {
    return players.get(currentPlayer);
  }

  /**
   * Changes the current player
   */
  private void turnPass()
  {
    currentPlayer++;
    if (currentPlayer == players.size())
      currentPlayer = 0;
  }

  /**
   * @param candidate the player that is a candidate for win check
   * @return true if the candidate is a winner, false otherwise.
   */
  private boolean didPlayerWin(Player candidate)
  {
    return candidate.getCoins() == 6;
  }
}
