package de.frankbeeh.productbacklogtimeline.view;

import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import com.sun.javafx.binding.IntegerConstant;

import de.frankbeeh.productbacklogtimeline.domain.ProductTimestampData;

public class TotalCountValueFactory implements Callback<CellDataFeatures<ProductTimestampData, Integer>, IntegerConstant> {
    @Override
    public IntegerConstant call(CellDataFeatures<ProductTimestampData, Integer> cellDataFeatures) {
        final Integer countByState = cellDataFeatures.getValue().getTotalCount();
        return IntegerConstant.valueOf(countByState);
    }
}
