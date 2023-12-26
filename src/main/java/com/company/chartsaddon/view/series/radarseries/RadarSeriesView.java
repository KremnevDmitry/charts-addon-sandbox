package com.company.chartsaddon.view.series.radarseries;

import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.axis.Radar;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.RadarSeries;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "RadarSeries", layout = MainView.class)
@ViewController("RadarSeriesView")
@ViewDescriptor("radar-series-view.xml")
public class RadarSeriesView extends StandardView {

    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        Chart chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart.setTitle(
                new Title()
                        .withText("Basic Radar")
        );

        chart.setLegend(
                new Legend()
        );

        chart.setTooltip(
                new Tooltip()
        );

        chart.setRadar(
                new Radar()
                        .withIndicators(
                                new Radar.Indicator()
                                        .withName("Russia")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("Ireland")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("Germany")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("Australia")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("Austria")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("UK")
                                        .withMax(300),
                                new Radar.Indicator()
                                        .withName("Belgium")
                                        .withMax(300)
                        )
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<MapDataItem>()
                                        .withDataProvider(
                                                new ListChartItems<>(
                                                        new MapDataItem(Map.of(
                                                                "Russia", 256.9,
                                                                "Ireland", 131.1,
                                                                "Germany", 115.8,
                                                                "Australia", 109.9,
                                                                "Austria", 108.3,
                                                                "UK", 65.0,
                                                                "Belgium", 40.0
                                                        ))
                                                )
                                        )
                                        .withValueFields(
                                                "Russia", "Ireland", "Germany", "Australia", "Austria", "UK", "Belgium"
                                        )
                        )
        );

        chart.addSeries(
                new RadarSeries()
                        .withName("Litres of beer per year")
        );

        getContent().add(chart);
    }
}