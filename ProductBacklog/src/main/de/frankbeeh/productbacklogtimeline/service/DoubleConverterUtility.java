package de.frankbeeh.productbacklogtimeline.service;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

@SuppressWarnings("serial")
public class DoubleConverterUtility extends Format {

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        final StringBuffer stringBuffer = new StringBuffer();
        final Double value = (Double) obj;
        if (value == null) {
            return stringBuffer.append("");
        } else {
            return stringBuffer.append(value);
        }
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        // TODO: Implement this, once needed
        return null;
    }

}
