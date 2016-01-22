package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class PersistSerialized {
  public static java.util.List<se.metricspace.lotto.Row> load(String filename) throws java.io.IOException,ClassNotFoundException {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    java.io.FileInputStream stream = null;
    java.io.ObjectInputStream reader = null;
    try {
      stream = new java.io.FileInputStream(filename);
      reader = new java.io.ObjectInputStream(stream);
      boolean cont = true;
      while(cont) {
        try {
          se.metricspace.lotto.Row row = (se.metricspace.lotto.Row)reader.readObject();
          if(null!=row) {
            rows.add(row);
          }
        } catch (java.io.EOFException exception) {
          cont=false;
        }
      }
    } finally {
      if(null!=reader) {
        try {
          reader.close();
        } catch (Throwable exception) {
        }
        reader = null;
      }
      if(null!=stream) {
        try {
          stream.close();
        } catch (Throwable exception) {
        }
        stream = null;
      }
    }
    return rows;
  }

  public static void save(String filename, java.util.List<se.metricspace.lotto.Row> rows) throws java.io.IOException {
    java.io.FileOutputStream stream = null;
    java.io.ObjectOutputStream objectstream = null;
    try {
      stream = new java.io.FileOutputStream(filename);
      objectstream = new java.io.ObjectOutputStream(stream);
      for(se.metricspace.lotto.Row row:rows) {
        objectstream.writeObject(row);
      }
    } finally {
      if(null!=objectstream) {
        try {
          objectstream.close();
        } catch(Throwable exception) {
        }
        objectstream = null;
      }
      if(null!=stream) {
        try {
          stream.close();
        } catch(Throwable exception) {
        }
        stream = null;
      }
    }
  }
  
}
