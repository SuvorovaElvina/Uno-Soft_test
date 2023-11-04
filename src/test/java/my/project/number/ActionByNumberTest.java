package my.project.number;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ActionByNumberTest {
    private final ActionByNumber action = new ActionByNumber();

    @Test
    void findGroups() {
        List<List<String>> groups = action.findGroups(List.of("\"79091456279\";\"79815345729\";\"79451948366\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));

        List<List<String>> actual = List.of(List.of("\"79091456279\";\"79815345729\";\"79451948366\""),
                List.of("\"79189701474\";\"79455321178\";\"79986590996\""));

        assertEquals(groups, actual, "не группирует.");
    }

    @Test
    void findGroupsByDuplicate() {
        List<List<String>> groups = action.findGroups(List.of("\"79189701474\";\"79455321178\";\"79986590996\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));

        List<List<String>> actual = List.of(List.of("\"79189701474\";\"79455321178\";\"79986590996\""));

        assertEquals(groups, actual, "не убирает дублирование в группе.");
    }

    @Test
    void findGroupsByValid() {
        List<List<String>> groups = action.findGroups(List.of("\"79189701474\"\"79455321178\";\"79986590996\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));

        List<List<String>> actual = List.of(List.of("\"79189701474\";\"79455321178\";\"79986590996\""));

        assertEquals(groups, actual, "записывает строку формата \"2\"\"2\" в группу.");
    }

    @Test
    void findGroupsByColumns() {
        List<List<String>> groups = action.findGroups(List.of("\"79091456279\";\"79455321178\";\"79451948366\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));

        List<List<String>> actual = List.of(List.of("\"79091456279\";\"79455321178\";\"79451948366\"",
                "\"79189701474\";\"79455321178\";\"79986590996\""));

        assertEquals(groups, actual, "не группирует по столбцам.");
    }

    @Test
    void findGroupsByColumnsFromEmpty() {
        List<List<String>> groups = action.findGroups(List.of("\"79091456279\";\"79815345729\";\"\"",
                "\"79189701474\";\"79455321178\";\"\""));

        List<List<String>> actual = List.of(List.of("\"79091456279\";\"79815345729\";\"\""),
                List.of("\"79189701474\";\"79455321178\";\"\""));

        assertEquals(groups, actual, "группирует по столбцам формата \"\".");
    }
}