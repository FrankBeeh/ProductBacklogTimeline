package de.frankbeeh.productbacklogtimeline.view;

import static org.junit.Assert.*;

import org.junit.Test;

public class TableViewContentTest {

    @Test
    public void test() {
        assertEquals("{\n{\"ID 1\",\"Description 1\"},\n{\"ID 2\",\"Description\\n2\"}\n}", new TableViewContent(new String[][] { { "ID 1", "Description 1" }, { "ID 2", "Description\n2" } }).toString());
    }
}
