package sample.pair;

import se.metricspace.lotto.Statistics;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class Competition implements se.metricspace.lotto.Competition {
  @Override
  public Statistics getWinner(Statistics the1stStatistics, Statistics the2ndStatistics) {
    if(null==the1stStatistics) return the2ndStatistics;
    
    Statistics winner = the1stStatistics;
    if(the1stStatistics.getPairUniqueCount()>the2ndStatistics.getPairUniqueCount()) {
      winner = the1stStatistics;
    } else if (the1stStatistics.getPairUniqueCount()<the2ndStatistics.getPairUniqueCount()) {
      winner = the2ndStatistics;
    } else {
      if(the1stStatistics.getSimilarity()[2]<the2ndStatistics.getSimilarity()[2]) {
        winner = the1stStatistics;
      } else {
        winner = the2ndStatistics;
      }
    }
    return winner;
  }
}
