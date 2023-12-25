package com.company.chartsaddon.view.series.gaugeseries;


import com.company.chartsaddon.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.series.GaugeSeries;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "GaugeSeries", layout = MainView.class)
@ViewController("GaugeSeriesView")
@ViewDescriptor("gauge-series-view.xml")
public class GaugeSeriesView extends StandardView {

    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        Chart chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTooltip(
                        new Tooltip().withFormatter("{a} <br/>{b} : {c}%")
                )
                .withSeries(
                        new GaugeSeries()
                                .withName("Pressure")
                                .withProgress(
                                        new GaugeSeries.Progress()
                                                .withShow(true)
                                )
                                .withDetail(
                                        new GaugeSeries.Detail()
                                                .withValueAnimation(true)
                                                .withFormatter("{value}")
                                )
                )
                .withNativeJson(
                        """
                                {
                                "series": [
                                {
                                      "data": [
                                        {
                                          "value": 50,
                                          "name": 'SCORE'
                                        }
                                      ]
                                }
                                ]
                                }"""
                );

        getContent().add(chart);
    }
}