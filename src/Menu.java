import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Menu {
    public static final Map<String, String> commands = new LinkedHashMap<String, String>() {{
        put("/test", "Тест");
        put("/create", "Создать голосования");
        put("/random_compare", "Сравнить случайных");
        put("/my_votes", "Мои голосования");
        put("/help", "Справка");
    }};

    public static SetMyCommands mainMenu() {
        List<BotCommand> botCommands = commands.entrySet().stream()
                .map(entry -> new BotCommand(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        return SetMyCommands.builder()
                .commands(botCommands)
                .build();
    }

    public static ReplyKeyboardMarkup tags(){
        KeyboardRow row1 = new KeyboardRow();
        row1.add("Про работу");
        row1.add("Про интернет");

        KeyboardRow row2 = new KeyboardRow();
        row2.add("Про негров");
        row2.add("Политика");

        KeyboardRow row3 = new KeyboardRow();
        row3.add("Про евреев");
        row3.add("Про блондинок");

        KeyboardRow row4 = new KeyboardRow();
        row4.add("Про Петьку И Василия Ивановича");
        row4.add("Про тещу");

        return
                ReplyKeyboardMarkup
                        .builder()
                        .selective(true)
                        .oneTimeKeyboard(true)
                        .keyboardRow(row1)
                        .keyboardRow(row2)
                        .keyboardRow(row3)
                        .keyboardRow(row4)
                        .selective(true)
                        .isPersistent(true)
                        .build();

    }


    public static InlineKeyboardMarkup ratingsMenu() {
        InlineKeyboardMarkup.InlineKeyboardMarkupBuilder inlineKeyboardMarkupBuilder = InlineKeyboardMarkup.builder();

        List<InlineKeyboardButton> row1Buttons = new ArrayList<InlineKeyboardButton>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton("Очень смешно");
        inlineKeyboardButton.setCallbackData("1");
        row1Buttons.add(inlineKeyboardButton);
        inlineKeyboardButton = new InlineKeyboardButton("Смешно");
        inlineKeyboardButton.setCallbackData("2");
        row1Buttons.add(inlineKeyboardButton);

        List<InlineKeyboardButton> row2Buttons = new ArrayList<InlineKeyboardButton>();
        inlineKeyboardButton = new InlineKeyboardButton("Не смешно");
        inlineKeyboardButton.setCallbackData("3");
        row2Buttons.add(inlineKeyboardButton);
        inlineKeyboardButton = new InlineKeyboardButton("Совсем не смешно");
        inlineKeyboardButton.setCallbackData("4");
        row2Buttons.add(inlineKeyboardButton);

        List<InlineKeyboardButton> row3Buttons = new ArrayList<InlineKeyboardButton>();
        inlineKeyboardButton = new InlineKeyboardButton("Следующий");
        inlineKeyboardButton.setCallbackData("next");
        row3Buttons.add(inlineKeyboardButton);


        return inlineKeyboardMarkupBuilder
                .keyboardRow(row1Buttons)
                .keyboardRow(row2Buttons)
                .keyboardRow(row3Buttons)
                .build();
    }
}
