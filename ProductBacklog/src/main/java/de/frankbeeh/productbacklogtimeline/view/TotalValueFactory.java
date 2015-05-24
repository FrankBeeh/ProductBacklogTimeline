package de.frankbeeh.productbacklogtimeline.view;

import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.sun.javafx.binding.DoubleConstant;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampData;

public class TotalValueFactory implements Callback<CellDataFeatures<ProductTimestampData, Double>, DoubleConstant> {
    @Override
    public DoubleConstant call(CellDataFeatures<ProductTimestampData, Double> cellDataFeatures) {
        final Double valueByState = cellDataFeatures.getValue().getTotalEstimate();
        return DoubleConstant.valueOf(valueByState);
    }
}
