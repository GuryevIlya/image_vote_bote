import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import java.util.ArrayList;
import java.util.List;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;

public class MyBot extends TelegramLongPollingBot {

    private static final String BOT_TOKEN = "5048010637:AAHgzYsTADTV2LA8Ip4TZUvw7FALTu3GdZs";
    private static final String BOT_USERNAME = "YOUR_BOT_USERNAME";
    private static String lastCommand = "";
    private static final List tags = new ArrayList<String>();
    private static String state = "";
    private static Vote newVote;

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    @Override
    public void onRegister() {
        try {
            execute(Menu.mainMenu());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage message = new SendMessage();
        long chatId = Utils.getChatId(update);
        message.setChatId(String.valueOf(chatId));
        if(Menu.commands.keySet().contains(update.getMessage().getText())){
            String messageText = update.getMessage().getText();
            switch (messageText) {
                case "/test_first":
                    message.setText("тест");
                    state = "expect_new_vote_title";
                    newVote = new Vote();
                    break;
                case "/create":
                    state = "expect_new_vote_title";
                    newVote = new Vote();
                    message.setText("Введите название голосования");
                    break;
                case "/add_images":
                    message.setText("Добавить картинки за которые вы хотите проголосовать");
                    break;
                case "/publish_vote":
                    sendChatSelectionMessage(chatId);
                    break;
                case "/test":
                    //  String callbackData = update.getCallbackQuery().getData();
                    // Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                    // Удаляем сообщение с инструкциями
                    sendEmptyVoteMessage(chatId);
                    // showChatSelection(chatId, messageId);
                    // message.setText("Тест");
                    // message.setReplyMarkup(Menu.ratingsMenu());
                    break;
                case "/random":
                    message.setText("Текст случайного анекдота");
                    message.setReplyMarkup(Menu.ratingsMenu());
                    break;
                case "/by_tags":
                    message.setText("Введите тему анекдота");
                    message.setReplyMarkup(Menu.tags());
                    break;
                case "/search":
                    message.setText("Введите текст анекдота(как в гугле)");
                    break;
                case "/help":
                    message.setText("List of commands:\n/start - Start the bot\n/help - Get help\n/commands - Show list of commands");
                    break;
                default:
                    message.setText("Unknown command");
            }
        }else if(state.equals("expect_new_vote_title")){
            newVote.title = update.getMessage().getText();
            state = "expect_new_vote_images";
            message.setText("Добавьте картинки");
        }else if(state.equals("expect_new_vote_images")){
            state = "expect_new_vote_images_or_click_finish";
            message.setText("Добавьте еще картинки или нажмите за завершить");
        }else if(state.equals("expect_new_vote_images_or_click_finish")){
            state = "expect_new_vote_images_or_click_finish";
            message.setText("Добавьте еще картинки или нажмите за завершить");
        }else {
            if (update.hasCallbackQuery()) {
                System.out.println("ОБНАРУЖЕН CALLBACK!");
                handleCallbackQuery(update);
            } else if (update.hasMessage() && update.getMessage().hasText()) {
                String messageText = update.getMessage().getText();
                lastCommand = messageText;
                int messageId = update.getMessage().getMessageId();

                message.setChatId(String.valueOf(chatId));

                switch (messageText) {
                    case "/test_first":
                        message.setText("тест");
                        state = "expect_new_vote_title";
                        newVote = new Vote();
                        break;
                    case "/create":
                        state = "expect_new_vote_title";
                        newVote = new Vote();
                        message.setText("Введите название голосования");
                        break;
                    case "/add_images":
                        message.setText("Добавить картинки за которые вы хотите проголосовать");
                        break;
                    case "/publish_vote":
                        sendChatSelectionMessage(chatId);
                        break;
                    case "/test":
                        //  String callbackData = update.getCallbackQuery().getData();
                        // Integer messageId = update.getCallbackQuery().getMessage().getMessageId();
                        // Удаляем сообщение с инструкциями
                        sendEmptyVoteMessage(chatId);
                        // showChatSelection(chatId, messageId);
                        // message.setText("Тест");
                        // message.setReplyMarkup(Menu.ratingsMenu());
                        break;
                    case "/random":
                        message.setText("Текст случайного анекдота");
                        message.setReplyMarkup(Menu.ratingsMenu());
                        break;
                    case "/by_tags":
                        message.setText("Введите тему анекдота");
                        message.setReplyMarkup(Menu.tags());
                        break;
                    case "/search":
                        message.setText("Введите текст анекдота(как в гугле)");
                        break;
                    case "/help":
                        message.setText("List of commands:\n/start - Start the bot\n/help - Get help\n/commands - Show list of commands");
                        break;
                    default:
                        message.setText("Unknown command");
                }

                /*try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }*/
            }else if(update.hasCallbackQuery()){
                String callData = update.getCallbackQuery().getData();
                if(callData.equals("next")){
                    message.setChatId(String.valueOf(chatId));
                    message.setText("Текст случайного анекдота");
                    message.setReplyMarkup(Menu.ratingsMenu());
                }
            }
        }

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Отправляем плашку с "empty vote"
    private void sendEmptyVoteMessage(Long chatId) {
        try {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("empti vote")
                    .replyMarkup(createPublishButton())
                    .build();

            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Создаем кнопку "publish vote"
    private InlineKeyboardMarkup createPublishButton() {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("📤 publish vote")
                .callbackData("publish_vote")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }

    private void handleCallbackQuery(Update update) {
        String callbackData = update.getCallbackQuery().getData();
        Long chatId = update.getCallbackQuery().getMessage().getChatId();
        String callbackId = update.getCallbackQuery().getId();

        System.out.println("Обрабатываю callback: " + callbackData);

        // Обязательно отвечаем на callback-запрос
        answerCallbackQuery(callbackId, "Обрабатываю...");

        if (callbackData.equals("publish_vote")) {
            System.out.println("Нажата кнопка publish_vote!");

            // Отправляем новое сообщение с инструкциями
            sendChatSelectionMessage(chatId);
        }
    }

    // Метод для ответа на callback-запрос
    private void answerCallbackQuery(String callbackId, String text) {
        try {
            System.out.println("Отвечаю на callback: " + callbackId);

            AnswerCallbackQuery answer = AnswerCallbackQuery.builder()
                    .callbackQueryId(callbackId)
                    .text(text)
                    .showAlert(false)
                    .build();

            execute(answer);
            System.out.println("Ответ на callback отправлен");
        } catch (TelegramApiException e) {
            System.err.println("Ошибка ответа на callback:");
            e.printStackTrace();
        }
    }

    // Отправляем инструкции по выбору чата
    private void sendChatSelectionMessage(Long chatId) {
        try {
            SendMessage message = SendMessage.builder()
                    .chatId(chatId.toString())
                    .text("✅ **Готово! Вот как отправить голосование:**\n\n" +
                            "1. **Перешлите это сообщение** в любой чат\n" +
                            "2. Или используйте **кнопку ниже** для inline-режима\n\n" +
                            "*Текст для отправки:*\n" +
                            "`empti vote`")
                    .parseMode("Markdown")
                    .replyMarkup(createInlineButton())
                    .build();

            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    // Кнопка для inline-режима
    private InlineKeyboardMarkup createInlineButton() {
        InlineKeyboardButton button = InlineKeyboardButton.builder()
                .text("🚀 Отправить в другой чат")
                .switchInlineQuery("empti vote")
                .build();

        List<InlineKeyboardButton> row = new ArrayList<>();
        row.add(button);

        List<List<InlineKeyboardButton>> keyboard = new ArrayList<>();
        keyboard.add(row);

        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }


}