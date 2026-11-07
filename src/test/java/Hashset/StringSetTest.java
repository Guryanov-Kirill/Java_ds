package test.java.Hashset;

import HashSet.StringList;
import HashSet.StringSet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class StringSetTest {
    private StringSet stringSet;
    private StringList mockList;

    @BeforeEach
    void setUp() {
        mockList = mock(StringList.class);
        stringSet = new StringSet(mockList);
    }

    @Test
    public void put_ShouldCallAddOnList_WhenElementIsNew() {
        when(mockList.contains("1")).thenReturn(false);
        stringSet.put("1");
        verify(mockList, times(1)).add("1");
        assertThat(stringSet.size()).isEqualTo(1);
    }

    @Test
    void put_ShouldNotCallAddOnList_WhenElementAlreadyExists() {
        when(mockList.contains("1")).thenReturn(true);
        stringSet.put("1");
        verify(mockList, never()).add("1");
        assertThat(stringSet.size()).isEqualTo(0);
    }

    @Test
    void contains_ShouldQueryList_AndReturnTrueIfListHasIt() {
        when(mockList.contains("1")).thenReturn(true);
        boolean result = stringSet.contains("1");
        assertThat(result).isTrue();
        verify(mockList, times(1)).contains("1");
    }

    @Test
    void remove_ShouldCallRemoveOnList_AndDecreaseSizeIfSuccessful() {
        when(mockList.contains("1")).thenReturn(false);
        stringSet.put("1");
        when(mockList.remove("1")).thenReturn(true);

        boolean isRemoved = stringSet.remove("1");

        assertThat(isRemoved).isTrue();
        assertThat(stringSet.size()).isEqualTo(0);
        verify(mockList, times(1)).remove("1");
    }
}
