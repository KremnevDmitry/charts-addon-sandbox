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
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "RadarSeries", layout = MainView.class)
@ViewController("RadarSeriesView")
@ViewDescriptor("radar-series-view.xml")
public class RadarSeriesView extends StandardView {

    @ViewComponent
    protected Chart chart;

    @Subscribe
    protected void onInit(InitEvent event) {
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
    }
}