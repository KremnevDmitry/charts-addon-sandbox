package com.company.chartsaddon.view.series.funnelseries;


import com.company.chartsaddon.entity.TitleValue;
import com.company.chartsaddon.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.FunnelSeries;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "FunnelSeries", layout = MainView.class)
@ViewController("FunnelSeriesView")
@ViewDescriptor("funnel-series-view.xml")
public class FunnelSeriesView extends StandardView {

    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private CollectionContainer<TitleValue> titleValueDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        Chart chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTooltip(
                        new Tooltip()
                                .withTrigger(Tooltip.Trigger.ITEM)
                )
                .withLegend(new Legend())
                .withDataSet(createDataSet())
                .withSeries(createSeries());

        getContent().add(chart);
    }

    protected DataSet createDataSet() {
        return new DataSet()
                .withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withDataProvider(new ContainerChartItems<>(titleValueDc))
                                .withCategoryField("title")
                                .withValueField("value")
                );
    }

    protected FunnelSeries createSeries() {
        return new FunnelSeries()
                .withName("Client activity");
    }
}