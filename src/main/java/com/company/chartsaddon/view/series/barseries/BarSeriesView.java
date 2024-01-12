package com.company.chartsaddon.view.series.barseries;


import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.series.AbstractSeries;
import io.jmix.chartsflowui.kit.component.model.series.BarSeries;
import io.jmix.chartsflowui.kit.component.model.series.LineSeries;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "BarSeriesView", layout = MainView.class)
@ViewController("BarSeriesView")
@ViewDescriptor("bar-series-view.xml")
public class BarSeriesView extends StandardView {

    @ViewComponent
    protected Chart chart;

    protected List<AbstractSeries<?>> series = new ArrayList<>();
    protected XAxis axis = new XAxis();

    @Subscribe
    protected void onInit(InitEvent event) {
        chart
                .withXAxis(axis)
                .withSeries(
                        createBarSeries("Cars"),
                        createBarSeries("Motorcycles"),
                        createBarSeries("Bicycles")
                );
    }

    protected BarSeries createBarSeries(String name) {
        BarSeries series = new BarSeries()
                .withName(name)
                .withStack("stack");
        this.series.add(series);
        return series;
    }

    protected LineSeries createLineSeries(String name) {
        LineSeries series = new LineSeries()
                .withName(name)
                .withStack("stack")
                .withAreaStyle(new LineSeries.AreaStyle());

        this.series.add(series);
        return series;
    }

    @Subscribe(id = "areaStyle", subject = "clickListener")
    public void onAreaStyleClick(final ClickEvent<JmixButton> event) {
        if (series.stream().anyMatch(series -> series instanceof BarSeries)) {
            axis.setBoundaryGap(false);

            series.forEach(chart::removeSeries);
            series.clear();

            chart.addSeries(createLineSeries("Cars"));
            chart.addSeries(createLineSeries("Motorcycles"));
            chart.addSeries(createLineSeries("Bicycles"));
        } else {
            axis.setBoundaryGap(true);

            series.forEach(chart::removeSeries);
            series.clear();

            chart.addSeries(createBarSeries("Cars"));
            chart.addSeries(createBarSeries("Motorcycles"));
            chart.addSeries(createBarSeries("Bicycles"));
        }
    }
}