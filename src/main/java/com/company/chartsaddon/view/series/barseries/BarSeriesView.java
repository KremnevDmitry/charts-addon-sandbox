package com.company.chartsaddon.view.series.barseries;


import com.company.chartsaddon.entity.TransportCount;
import com.company.chartsaddon.view.main.MainView;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.axis.AbstractAxis;
import io.jmix.chartsflowui.kit.component.model.axis.AxisType;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.axis.YAxis;
import io.jmix.chartsflowui.kit.component.model.datazoom.InsideDataZoom;
import io.jmix.chartsflowui.kit.component.model.datazoom.SliderDataZoom;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.AbstractSeries;
import io.jmix.chartsflowui.kit.component.model.series.BarSeries;
import io.jmix.chartsflowui.kit.component.model.series.LineSeries;
import io.jmix.chartsflowui.kit.component.model.shared.Orientation;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Route(value = "BarSeriesView", layout = MainView.class)
@ViewController("BarSeriesView")
@ViewDescriptor("bar-series-view.xml")
public class BarSeriesView extends StandardView {

    @ViewComponent
    protected CollectionContainer<TransportCount> transportDc;

    @Autowired
    protected UiComponents uiComponents;

    protected Chart chart;
    protected List<AbstractSeries<?>> series = new ArrayList<>();
    protected XAxis axis = new XAxis();

    @Subscribe
    protected void onInit(InitEvent event) {
        chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart.
                withXAxis(axis)
                .withYAxis(new YAxis())
                .withDataSet(createDataSet())
                .withSeries(
                        createBarSeries("Cars"),
                        createBarSeries("Motorcycles"),
                        createBarSeries("Bicycles")
                )
                .withTooltip(createTooltip())
                .withLegend(new Legend());

        getContent().add(chart);
    }

    protected Tooltip createTooltip() {
        return new Tooltip()
                .withTrigger(Tooltip.Trigger.AXIS);
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

    protected DataSet createDataSet() {
        return new DataSet()
                .withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withCategoryField("year")
                                .withValueField("cars")
                                .withValueField("motorcycles")
                                .withValueField("bicycles")
                                .withDataProvider(new ContainerChartItems<>(transportDc))
                );
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