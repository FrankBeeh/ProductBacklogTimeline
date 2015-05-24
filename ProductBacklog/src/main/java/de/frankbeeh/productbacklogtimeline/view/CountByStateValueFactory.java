package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.NamedArg;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.sun.javafx.binding.IntegerConstant;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampData;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class CountByStateValueFactory implements Callback<CellDataFeatures<ProductTimestampData, Integer>, IntegerConstant> {
    private final State state;

    public CountByStateValueFactory(@NamedArg("state") String name) {
        state = State.valueOf(name);
    }

    @Override
    public IntegerConstant call(CellDataFeatures<ProductTimestampData, Integer> cellDataFeatures) {
        final Integer countByState = cellDataFeatures.getValue().getCountByState(state);
        return IntegerConstant.valueOf(countByState);
    }
}
