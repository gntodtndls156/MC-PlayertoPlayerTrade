package github.gntodtndls156.nextlife.betweentrade.init;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonWriter;

import java.io.*;


public class InitJsonFile {
    private String LANGFile;

    public InitJsonFile() {
        File file = new File("plugins\\PlayerToPlayerTrade\\config.json");
        JsonParser jsonParser = new JsonParser();
        try {
            JsonElement element =  jsonParser.parse(new FileReader(file));
            LANGFile = element.getAsJsonObject().get("lang").getAsString();
            System.out.println(LANGFile);
        } catch (FileNotFoundException exception) {
            // TODO
            System.out.println("InitJsonFile FileNot FoundException exception");
            if (!file.exists()) {
                System.out.println("file not found.");
                File file1 = new File("plugins\\PlayerToPlayerTrade");
                if (!file1.exists()) {
                    file1.mkdir();
                }
                try {
                    file.createNewFile();
                    JsonWriter jsonWriter = new JsonWriter(new FileWriter(file));
                    jsonWriter.beginObject();
                    jsonWriter.name("lang").value("ko_KR");
                    jsonWriter.endObject();
                    jsonWriter.close();
                } catch (IOException exception1) {
                    // TODO
                }
            }
        }
    }

    public String getLANGFile() {
        return LANGFile;
    }

    public void setLANGFile(String LANGFile) {
        this.LANGFile = LANGFile;
    }
}
