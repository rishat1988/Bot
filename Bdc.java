import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.sql.SQLException;


public class Bdc {
    private final String url = "jdbc:sqlite:C://Users//Гульназ//IdeaProjects//TelegramBotApi//BD.db.";
    private ConnectionSource source;
    private Dao<Userbase, String> accountDao;

    public Bdc() throws SQLException {
        source = new JdbcConnectionSource(url);
        accountDao = DaoManager.createDao(source, Userbase.class);

    }

    public void BeginSubscribe(Message message) throws SQLException {
        Userbase userbase = new Userbase ();
        accountDao = DaoManager.createDao(source, Userbase.class);
        userbase.setChatId(message.getChatId());
        userbase.setLat(message.getLocation().getLatitude());
        userbase.setLon(message.getLocation().getLongitude());
        userbase.setDate(message.getDate().toString());
        userbase.setSubscribe((byte) 0);
        accountDao.create(userbase);
    }
}
