
import org.json.JSONArray;
import org.json.JSONObject;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    // 713141997cf1bf39e9dcbf03a53d804a
    public static String getWeather(Message message, Model model) throws IOException {
        URL url;
        if (message.hasLocation()) {

            url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + message.getLocation().getLatitude() +
                    "&lon=" + message.getLocation().getLongitude() + "&appid=713141997cf1bf39e9dcbf03a53d804a&units=metric&");
        } else {
            url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message +
                    "&appid=713141997cf1bf39e9dcbf03a53d804a&units=metric&");
        }
        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));


        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));


        model.setHumidity(main.getDouble("humidity"));
        JSONArray getArray = object.getJSONArray("weather");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));

        }

        return "City" + model.getName() + "\n" + "Temperature" + model.getTemp() +
                "c" + "\n" + "Humidity" + model.getHumidity() + "%" + "\n" +
                "Mai :" + "\n" + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/10d.png" + model.getIcon() + ".png";

    }


}



