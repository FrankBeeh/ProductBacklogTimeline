package de.frankbeeh.productbacklogtimeline.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class TableViewContent {
    private List<List<String>> rows = new ArrayList<List<String>>();
    private List<String> presentRow;

    public TableViewContent(){
    }
    
    public TableViewContent(String[][] rows) {
        for (String[] row : rows) {
            addRow(row);
        }
    }

    public void addRow() {
        presentRow = new ArrayList<String>();
        rows.add(presentRow);
    }

    public void addRow(String... cellContents) {
        presentRow = Arrays.asList(cellContents);
        rows.add(presentRow);
    }

    public void addCellContent(String cellContent) {
        if (presentRow != null) {
            presentRow.add(cellContent);
        }
    }

    public void stopAdding() {
        removeAllEmptyRows();
        presentRow = null;
    }

    public int getRowCount() {
        return rows.size();
    }

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        for (List<String> row : rows) {
            stringBuilder.append(row).append("\n");
        }
        return stringBuilder.toString();
    }

    private void removeAllEmptyRows() {
        rows.removeIf(new Predicate<List<String>>() {
            @Override
            public boolean test(List<String> row) {
                for (String content : row) {
                    if (!content.isEmpty()) {
                        return false;
                    }
                }
                return true;
            }
        });
    }
}
