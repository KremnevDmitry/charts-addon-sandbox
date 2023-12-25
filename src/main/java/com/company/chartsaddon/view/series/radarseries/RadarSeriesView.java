package com.company.chartsaddon.view.series.radarseries;


import com.company.chartsaddon.entity.CountryLitres;
import com.company.chartsaddon.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.axis.Radar;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.RadarSeries;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.data.grid.ContainerDataGridItems;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "RadarSeries", layout = MainView.class)
@ViewController("RadarSeriesView")
@ViewDescriptor("radar-series-view.xml")
public class RadarSeriesView extends StandardView {

    @Autowired
    private UiComponents uiComponents;
    @ViewComponent
    private CollectionContainer<CountryLitres> countryLitresDc;

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

        chart.setRadar(
                new Radar()
        );

        chart.setDataSet(
                new DataSet()
                        .withSource(
                                new DataSet.Source<>()
                                        .withDataProvider(new ContainerChartItems(countryLitresDc))
                                        .withCategoryField("country")
                                        .withValueField("litres")
                        )
        );

        chart.addSeries(
                new RadarSeries()
        );

        getContent().add(chart);
    }
}