import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Timestamp;


@DatabaseTable
public class Userbase {

    @DatabaseField(id = true)
    private long id;
    @DatabaseField
    private String name;
    @DatabaseField
    private double lon;
    @DatabaseField
    private double lat;
    @DatabaseField
    private String DateTime;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setDateTime(String dateTime) {
        DateTime = dateTime;
    }

    public Userbase() {


    }
}


    

