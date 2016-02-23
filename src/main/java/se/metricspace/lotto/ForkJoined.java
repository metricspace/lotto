package se.metricspace.lotto;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class ForkJoined extends RecursiveTask<se.metricspace.lotto.Statistics> {
  private Competition itsCompetition = null;
  private CollectionGenerator itsGenerator = null;
  private int itsSize = 0;

  public ForkJoined(int theSize, Competition theCompetition, CollectionGenerator theGenerator) {
    itsCompetition = theCompetition;
    itsGenerator = theGenerator;
    itsSize = (theSize>0) ? theSize : 0;
  }

  @Override
  protected se.metricspace.lotto.Statistics compute() {
    se.metricspace.lotto.Statistics winner = null;
    if(itsSize < 2) {
      java.util.List<se.metricspace.lotto.Row> rows = itsGenerator.generateRows();
      winner = se.metricspace.lotto.Statistics.analyseRows(rows);
    } else {
      ForkJoined left =  null;
      ForkJoined right = null;
      if(itsSize % 2 == 1) {
        left = new ForkJoined(1, itsCompetition, itsGenerator);
        right = new ForkJoined(itsSize -1, itsCompetition, itsGenerator);
      } else {
        left = new ForkJoined(itsSize/2, itsCompetition, itsGenerator);
        right = new ForkJoined(itsSize/2, itsCompetition, itsGenerator);
      }
      left.fork();
      se.metricspace.lotto.Statistics rightAns = right.compute();
      se.metricspace.lotto.Statistics leftAns = left.join();
      winner = itsCompetition.getWinner(rightAns, leftAns);
    }
    return winner;
  }

  public static se.metricspace.lotto.Statistics execute(int theSize, Competition theCompetition, CollectionGenerator theGenerator) {
    return ForkJoinPool.commonPool().invoke(new ForkJoined(theSize, theCompetition, theGenerator));
  }
}
