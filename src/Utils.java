import org.telegram.telegrambots.meta.api.objects.Update;

public class Utils {
    public static Long getChatId(Update update) {
        // Для текстовых сообщений
        if (update.hasMessage() && update.getMessage().hasText()) {
            return update.getMessage().getChatId();
        }

        // Для callback-запросов (нажатия на кнопки)
        if (update.hasCallbackQuery()) {
            return update.getCallbackQuery().getMessage().getChatId();
        }

        return null;
    }
}
