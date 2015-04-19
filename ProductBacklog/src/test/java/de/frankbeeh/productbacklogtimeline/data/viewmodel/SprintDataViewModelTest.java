package de.frankbeeh.productbacklogtimeline.data.viewmodel;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import de.frankbeeh.productbacklogtimeline.data.SprintData;

public class SprintDataViewModelTest {

    private SprintDataViewModel sprintDataViewModel;

    @Test
    public void fromEntity() {
        sprintDataViewModel = new SprintDataViewModel(new SprintData("name", new Date(0), new Date(0), 1.0, 2.0, 3.0, 4.0));

        assertEquals("name", sprintDataViewModel.nameProperty().get());
        assertEquals("01.01.1970", sprintDataViewModel.startDateProperty().get());
        assertEquals("01.01.1970", sprintDataViewModel.endDateProperty().get());
        assertEquals("1.0", sprintDataViewModel.capacityForecastProperty().get());
        assertEquals("2.0", sprintDataViewModel.effortForecastProperty().get());
        assertEquals("3.0", sprintDataViewModel.capacityDoneProperty().get());
        assertEquals("4.0", sprintDataViewModel.effortDoneProperty().get());
    }

    @Test
    public void fromEmptyEntity() {
        sprintDataViewModel = new SprintDataViewModel(null);

        assertEquals("", sprintDataViewModel.nameProperty().get());
        assertEquals("", sprintDataViewModel.startDateProperty().get());
        assertEquals("", sprintDataViewModel.endDateProperty().get());
        assertEquals("", sprintDataViewModel.capacityForecastProperty().get());
        assertEquals("", sprintDataViewModel.effortForecastProperty().get());
        assertEquals("", sprintDataViewModel.capacityDoneProperty().get());
        assertEquals("", sprintDataViewModel.effortDoneProperty().get());
    }

    @Ignore("See FIXME")
    @Test
    public void toEntity() throws Exception {
        sprintDataViewModel = new SprintDataViewModel(new SprintData());

        sprintDataViewModel.nameProperty().set("name");
        assertEquals("name", sprintDataViewModel.entityProperty().get().nameProperty().get());

        // FIXME: Date binding should allow empty values..., empty mode
        assertEquals("", sprintDataViewModel.entityProperty().get().startDateProperty().get());
        assertEquals("", sprintDataViewModel.entityProperty().get().endDateProperty().get());

        assertEquals("", sprintDataViewModel.entityProperty().get().capacityForecastProperty().get());
        assertEquals("", sprintDataViewModel.entityProperty().get().effortForecastProperty().get());
        assertEquals("", sprintDataViewModel.entityProperty().get().capacityDoneProperty().get());
        assertEquals("", sprintDataViewModel.entityProperty().get().effortDoneProperty().get());
    }

}
