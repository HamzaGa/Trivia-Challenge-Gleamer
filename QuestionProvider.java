import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuestionProvider
{

  private LinkedList<String> popQuestions = new LinkedList<>();
  private LinkedList<String> scienceQuestions = new LinkedList<>();
  private LinkedList<String> sportsQuestions = new LinkedList<>();
  private LinkedList<String> rockQuestions = new LinkedList<>();
  private List<LinkedList<String>> questions = new ArrayList<>();
  private List<String> catogories = new ArrayList<>();

  public QuestionProvider()
  {
    questions.add(popQuestions);
    questions.add(scienceQuestions);
    questions.add(sportsQuestions);
    questions.add(rockQuestions);
    catogories.add("Pop");
    catogories.add("Science");
    catogories.add("Sports");
    catogories.add("Rock");
    initQuestions();
  }

  public void initQuestions()
  {
    for (int i = 0; i < 50; i++)
    {
      popQuestions.addLast("Pop Question " + i);
      scienceQuestions.addLast(("Science Question " + i));
      sportsQuestions.addLast(("Sports Question " + i));
      rockQuestions.addLast(("Rock Question " + i));
    }
  }

  public String getCategoryByPlayerPosition(Integer playerPosition)
  {
    return catogories.get(playerPosition % 4);
  }

  public String getQuestionByPlayerPosition(Integer playerPosition)
  {
    return questions.get(playerPosition % 4).removeFirst();
  }


}
