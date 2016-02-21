package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// Spela inte för mer än du har råd att förlora!
public class Statistics {
  public static void showPairStats(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    java.util.Map<Pair,Integer> pairCount = new java.util.HashMap<>(595);
    for(int i = 0; i < 34 ; i++) {
      for(int j = i+1; j < 35; j++) {
        pairCount.put(new Pair(i,j), 0);
      }
    }
    for(Row row:rows) {
      int [] items = row.getItems();
      for(int i = 0; i < 6; i++) {
        for(int j = 1+i; j < 7; j++) {
          Pair pair = new Pair(items[i],items[j]);
          Integer count = 1+pairCount.getOrDefault(pair, 0);
          pairCount.replace(pair, count);
        }
      }
    }
    int max = -1;
    int min = 999999;
    double average = 0.0;
    for(int i = 0; i < 34 ; i++) {
      for(int j = i+1; j < 35; j++) {
        Integer count = pairCount.getOrDefault(new Pair(i,j), 0);
        if(count>max) {
          max = count;
        }
        if(count<min) {
          min = count;
        }
        average += count;
      }
    }
    average /= 595.0;
    theWriter.println("Pairs");
    theWriter.println("Average: "+average);
    theWriter.println("Min-Max: ["+min+","+max+"]");
  }

  public static void showSimilarity(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    int similarity[] = {0, 0, 0, 0, 0, 0, 0, 0};
    int max = -1;
    int min = 9;
    double average = 0.0;
    for(int outer = 0; outer < rows.size()-1; outer++) {
      for(int inner = 1+outer; inner < rows.size(); inner++) {
        int sim = rows.get(inner).getSimilarity(rows.get(outer));
        similarity[sim]++;
        average += sim;
        if(sim>max) {
          max = sim;
        }
        if(sim<min) {
          min = sim;
        }
      }
    }
    average /= (rows.size()*(rows.size()-1)/2);
    theWriter.println("Similarity");
    theWriter.println("Average: "+average);
    theWriter.println("Min-Max: ["+min+","+max+"]");
    theWriter.println("Distribution: ["+similarity[0]+","+similarity[1]+","+similarity[2]+","+similarity[3]+","+similarity[4]+","+similarity[5]+","+similarity[6]+","+similarity[7]+"]");
  }

  public static void showStatistics(java.util.List<Row> rows, java.io.PrintStream theWriter) {
    int count[] = {
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0, 
      0, 0, 0, 0, 0, 0, 0};
    rows.stream().map((Row row) -> row.getItems()).forEach((int[] items) -> {
      for(int item:items) {
        count[item]++;
      }
    });
    int max = -1;
    int min = 99999999;
    double average = rows.size() / 5.0; // rows.size() * 7 / 35
    double variance = 0.0;
    for(int index=0; index<count.length; index++) {
      if(count[index]>max) {
        max = count[index];
      }
      if(count[index]<min) {
        min = count[index];
      }
      variance += (count[index]-average) * (count[index]-average);
    }
    variance /= count.length;
    
    theWriter.println("Distribution");
    theWriter.println("Average: "+average);
    theWriter.println("Stddev: "+Math.sqrt(variance));
    theWriter.println("Min-Max: ["+min+","+max+"]");
  }
}
