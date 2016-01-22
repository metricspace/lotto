package se.metricspace.lotto;

// Lotto är ett slumpspel och alla siffror är lika sannolika by design
// och därmed är de 6724520 olika raderna lika sannolika. 
// Spela inte för mer än du har råd att förlora!
public class PersistJSon {
  public static java.util.List<se.metricspace.lotto.Row> load(String filename) throws java.io.IOException {
    java.util.List<se.metricspace.lotto.Row> rows = new java.util.ArrayList<>();
    java.io.FileInputStream stream = null;
    javax.json.JsonReader reader = null;
    try {
      stream = new java.io.FileInputStream(filename);
      reader = javax.json.Json.createReader(stream);
      javax.json.JsonArray array = reader.readArray();
      for(javax.json.JsonValue row:array) {
        if(row.getValueType()==javax.json.JsonValue.ValueType.ARRAY) {
          javax.json.JsonArray jsonrad=(javax.json.JsonArray)row;
          int[] items=new int[jsonrad.size()];
          for(int i=0;i<items.length;i++) {
            items[i]=jsonrad.getInt(i);
          }
          rows.add(new se.metricspace.lotto.Row(items));
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
    javax.json.JsonArrayBuilder arrayBuilder = javax.json.Json.createArrayBuilder();
    for(se.metricspace.lotto.Row row:rows) {
      javax.json.JsonArrayBuilder rowBuilder = javax.json.Json.createArrayBuilder();
      int[] items = row.getItems();
      for(int i=0;i<items.length;i++) {
        rowBuilder.add(items[i]);
      }
      arrayBuilder.add(rowBuilder);
    }
    java.io.FileOutputStream stream = null;
    javax.json.JsonWriter jsonWriter = null;
    try {
      stream = new java.io.FileOutputStream(filename);
      jsonWriter = javax.json.Json.createWriter(stream);
      jsonWriter.writeArray(arrayBuilder.build());
    } finally {
      if(null!=jsonWriter) {
        try {
          jsonWriter.close();
        } catch(Throwable exception) {
        }
        jsonWriter = null;
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
