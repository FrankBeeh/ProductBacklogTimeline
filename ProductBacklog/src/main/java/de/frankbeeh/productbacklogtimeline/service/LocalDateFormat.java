package de.frankbeeh.productbacklogtimeline.service;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.LocalDate;

@SuppressWarnings("serial")
public class LocalDateFormat extends Format {

    @Override
    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        final StringBuffer stringBuffer = new StringBuffer();
        final LocalDate value = (LocalDate) obj;
        if (value == null) {
            return stringBuffer.append("");
        } else {
            return stringBuffer.append(FormatUtility.formatLocalDate(value));
        }
    }

    @Override
    public Object parseObject(String source, ParsePosition pos) {
        // TODO: Implement this, once needed
        return null;
    }

}
