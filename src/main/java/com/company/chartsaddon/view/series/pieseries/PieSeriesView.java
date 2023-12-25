package com.company.chartsaddon.view.series.pieseries;


import com.company.chartsaddon.entity.CountryLitres;
import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.Label;
import io.jmix.chartsflowui.kit.component.model.series.PieSeries;
import io.jmix.chartsflowui.kit.component.model.shared.Orientation;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "PieSeries", layout = MainView.class)
@ViewController("PieSeriesView")
@ViewDescriptor("pie-series-view.xml")
public class PieSeriesView extends StandardView {

    @Autowired
    protected UiComponents uiComponents;
    @ViewComponent
    private CollectionContainer<CountryLitres> countryLitresDc;

    @Subscribe
    protected void onInit(InitEvent event) {
        Chart chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTitle(
                        new Title()
                                .withText("Liters of beer")
                                .withSubtext("By countries")
                )
                .withLegend(
                        new Legend()
                                .withLeft("")
                                .withTop("5%")
                                .withOrientation(Orientation.VERTICAL)
                )
                .withTooltip(
                        new Tooltip()
                                .withTrigger(Tooltip.Trigger.ITEM)
                )
                .withDataSet(createDataSet())
                .withSeries(createPieSeries());

        getContent().add(chart);
    }

    protected PieSeries createPieSeries() {
        return new PieSeries()
                .withName("Country")
                .withLabelLine(
                        new PieSeries.LabelLine()
                                .withShow(false)
                )
                .withEmphasis(
                        new PieSeries.Emphasis()
                                .withLabel(
                                        new Label()
                                                .withShow(true)
                                                .withFontSize(40)
                                                .withFontWeight("bold")
                                )
                );
    }

    protected DataSet createDataSet() {
        return new DataSet()
                .withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withDataProvider(new ContainerChartItems<>(countryLitresDc))
                                .withCategoryField("country")
                                .withValueField("litres")
                );
    }
}