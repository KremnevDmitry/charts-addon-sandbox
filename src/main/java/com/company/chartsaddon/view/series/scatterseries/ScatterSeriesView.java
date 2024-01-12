package com.company.chartsaddon.view.series.scatterseries;


import com.company.chartsaddon.entity.Coordinate;
import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.series.AbstractSeries;
import io.jmix.chartsflowui.kit.component.model.series.EffectScatterSeries;
import io.jmix.chartsflowui.kit.component.model.series.ScatterSeries;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Route(value = "ScatterSeries", layout = MainView.class)
@ViewController("ScatterSeriesView")
@ViewDescriptor("scatter-series-view.xml")
public class ScatterSeriesView extends StandardView {

    protected static int ITEMS_COUNT = 100;

    @Autowired
    protected Metadata metadata;

    @ViewComponent
    protected Chart chart;

    protected Random random = new Random();

    protected AbstractSeries<?> series;

    @Subscribe
    protected void onInit(InitEvent event) {
        series = chart.getSeries("scatterSeries");
    }

    @Install(to = "coordinateDl", target = Target.DATA_LOADER)
    protected List<Coordinate> coordinateDlLoadDelegate(final LoadContext<Coordinate> loadContext) {
        List<Coordinate> coordinates = new ArrayList<>();

        double scale = Math.pow(10, 3);

        for (int i = 0; i < ITEMS_COUNT; ++i) {
            Coordinate coordinate = metadata.create(Coordinate.class);
            coordinate.setX(random.nextInt(15) + Math.ceil(random.nextDouble() * scale) / scale);
            coordinate.setY(random.nextInt(10) + Math.ceil(random.nextDouble() * scale) / scale);

            coordinates.add(coordinate);
        }

        coordinates.sort(Comparator.comparingDouble(Coordinate::getX));

        return coordinates;
    }

    @Subscribe("effect")
    public void onEffectComponentValueChange(final AbstractField.ComponentValueChangeEvent<JmixCheckbox, Boolean> event) {
        chart.removeSeries(series);

        if (event.getValue()) {
            series = new EffectScatterSeries()
                    .withSymbolSize(20);
        } else {
            series = new ScatterSeries()
                    .withSymbolSize(20);
        }

        chart.addSeries(series);
    }
}