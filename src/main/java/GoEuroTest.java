import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;

/**
 * Created by berkaybuharali on 28/08/15.
 *
 * jar  -uvfe  GoEuroTest.jar GoEuroTest
 */
public class GoEuroTest {

    public static void main(String [] args)
    {
        String path = args[0];
        String content = null;
        File file = new File("output.csv");
        FileWriter fw1 = null;

        try {
            fw1 = new FileWriter(file.getAbsoluteFile(),false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedWriter bw1 = new BufferedWriter(fw1);

        try {
            content = DownloadURL("http://api.goeuro.com/api/v2/position/suggest/en/" + path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONArray json = new JSONArray(content);

        if(json.length() > 0){
            try {
                bw1.write("sep=,\n");
                bw1.write("id,name,type,latitude,longitude\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(int i = 0 ; i < json.length() ; i++){
            JSONObject geo = (JSONObject) json.getJSONObject(i).get("geo_position");
            try {
                bw1.write(json.getJSONObject(i).get("_id").toString() + "," +  json.getJSONObject(i).get("name").toString() + "," +  json.getJSONObject(i).get("type").toString() + "," + geo.get("latitude").toString() + "," + geo.get("longitude").toString() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        try {
            bw1.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static String DownloadURL(String url) throws Exception {
        BufferedReader reader = null;
        try {
            URL url2 = new URL(url);
            reader = new BufferedReader(new InputStreamReader(url2.openStream()));
            StringBuffer buffer = new StringBuffer();
            int read;
            char[] chars = new char[1024];
            while ((read = reader.read(chars)) != -1)
                buffer.append(chars, 0, read);

            return buffer.toString();
        } finally {
            if (reader != null)
                reader.close();
        }
    }
}


