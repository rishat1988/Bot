import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Location;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.lang.ClassNotFoundException;

class Main {
    private static String PROXY_HOST = "";////////////
    private static Integer PROXY_PORT = ;///////////
    private static String PROXY_USER = "";////////
    private static String PROXY_PASSWORD = "";/////////



    public static void main(String[] args)  {
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
            }
        });

        ApiContextInitializer.init(); //инициализируем апи

        DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);/////

        botOptions.setProxyHost(PROXY_HOST);////////
        botOptions.setProxyPort(PROXY_PORT);////////

        botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS5);//////
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();// создаем объект апи
        Bot bot =  new Bot (botOptions);
        try {
            telegramBotsApi.registerBot(bot);

        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }


    }


    public static class Bot extends TelegramLongPollingBot {
        public Bot (DefaultBotOptions options){super(options);}

        public void sendMsg(Message message, String text) {
            SendMessage sendMessage = new SendMessage();
            sendMessage.enableMarkdown(true);
            sendMessage.setChatId(message.getChatId().toString());
            sendMessage.setReplyToMessageId(message.getMessageId());
            sendMessage.setText(text);
            try {
                setButtons(sendMessage);
                execute(sendMessage);

            } catch (TelegramApiException e) {
                e.printStackTrace();

            }

        }


        public void onUpdateReceived(Update update) {
            Model model = new Model();
            Message message = update.getMessage();
            SendMessage sendMessage = new SendMessage();
            if (message.hasLocation()) {
                Location location = message.getLocation();


                try {
                    sendMsg(message, Weather.getWeather(message, model));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (message != null && message.hasText()) {
                switch (message.getText()) {
                    case "/help":
                        try {
                            sendMessage.setChatId(update.getMessage().getChatId().toString());
                            execute(sendMessage.setText( "используй кнопки для получения инфорации"));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/setting":
                        try {
                            sendMessage.setChatId(update.getMessage().getChatId().toString());
                            execute(sendMessage.setText( "Вышли локацию для получени погоды, либо укажи свой город"));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/subscribe":
                        try {
//                            BD bd = new BD();

                            sendMessage.setChatId(update.getMessage().getChatId().toString());
                            execute(sendMessage.setText( "ежедневная подписка оформлена"));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/unscribe":
                        try {
                            sendMessage.setChatId(update.getMessage().getChatId().toString());
                            execute(sendMessage.setText( "Вы отписались"));

                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        try {
                            sendMsg(message, Weather.getWeather(message, model));
                        } catch (IOException e) {
                            sendMsg(message, "город не найден!");
                            break;
                        }
                }
            }
        }

        public void setButtons(SendMessage sendMessage) {      //install buttons
            ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
            sendMessage.setReplyMarkup(replyKeyboardMarkup);
            replyKeyboardMarkup.setSelective(true);
            replyKeyboardMarkup.setResizeKeyboard(true);
            replyKeyboardMarkup.setOneTimeKeyboard(false);

            List<KeyboardRow> keyboardRowList = new ArrayList<>();
            KeyboardRow keyboardFirstRow = new KeyboardRow();
            keyboardFirstRow.add(new KeyboardButton("/help"));
            keyboardFirstRow.add(new KeyboardButton("/setting"));
            KeyboardRow keyboardSecondRow = new KeyboardRow();////////////////
            keyboardSecondRow.add(new KeyboardButton("/subscribe"));/////////////
            keyboardSecondRow.add(new KeyboardButton("/unscribe"));/////////////////

            keyboardRowList.add(keyboardFirstRow);
            replyKeyboardMarkup.setKeyboard(keyboardRowList);
            keyboardRowList.add(keyboardSecondRow);///////////////
            replyKeyboardMarkup.setKeyboard(keyboardRowList);///////////////
        }


        public String getBotUsername() { // для возвращение имени бота при регистрации
            return "WeatherRishatBot";
        }

        public String getBotToken() {  //токен фазер
            return "699550544:AAGerPslJNSES5yfPsq8quRzp5_r0xbvhlw";
        }


    }
}
