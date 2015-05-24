package de.frankbeeh.productbacklogtimeline.view;

import javafx.beans.NamedArg;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.sun.javafx.binding.DoubleConstant;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampData;
import de.frankbeeh.productbacklogtimeline.domain.State;

public class DoubleByStateValueFactory implements Callback<CellDataFeatures<ProductTimestampData, Double>, DoubleConstant> {
    private final State state;

    public DoubleByStateValueFactory(@NamedArg("state") String name) {
        state = State.valueOf(name);
    }

    @Override
    public DoubleConstant call(CellDataFeatures<ProductTimestampData, Double> cellDataFeatures) {
        final Double countByState = cellDataFeatures.getValue().getEstimateByState(state);
        return DoubleConstant.valueOf(countByState);
    }
}
