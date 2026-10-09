package test.java.Hashset;

import HashSet.StringList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class StringListTest {
    private StringList list;

    @BeforeEach
    void setUp() {
        list = new StringList();
    }

    @Test
    void contains_ShouldReturnFalse_WhenListIsEmpty() {
        boolean result = list.contains("1");
        assertThat(result).isFalse();
    }

    @Test
    void contains_ShouldReturnTrue_WhenElementAdded() {
        list.add("1");
        boolean result = list.contains("1");
        assertThat(result).isTrue();
    }

    @Test
    void remove_ShouldReturnTrueAndRemoveElement_WhenElementExists() {
        list.add("1");
        boolean isRemoved = list.remove("1");
        assertThat(isRemoved).isTrue();
        assertThat(list.contains("1")).isFalse();
    }

    @Test
    void remove_ShouldReturnFalse_WhenElementDoesNotExist() {
        list.add("1");
        boolean isRemoved = list.remove("2");
        assertThat(isRemoved).isFalse();
        assertThat(list.contains("1")).isTrue();
    }

    @Test
    void clear_ShouldEmptyTheList_WhenElementWasPresent() {
        list.add("1");
        list.clear();
        assertThat(list.contains("1")).isFalse();
    }
}
