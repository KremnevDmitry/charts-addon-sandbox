package com.company.chartsaddon.view.incrementallist;

import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.item.MapDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.axis.YAxis;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.BarSeries;
import io.jmix.chartsflowui.kit.component.model.series.Encode;
import io.jmix.chartsflowui.kit.data.chart.ListChartItems;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

@Route(value = "incrementallist", layout = MainView.class)
@ViewController("IncrementalListView")
@ViewDescriptor("incremental-list-view.xml")
public class IncrementalListView extends StandardView {

    @Autowired
    protected UiComponents uiComponents;

    // data
    protected ListChartItems<MapDataItem> dataItemListChartItems;

    @Subscribe
    protected void onInit(InitEvent event) {
        initButton();
        initChart();
    }

    protected void initButton() {
        JmixButton button = uiComponents.create(JmixButton.class);
        button.setText("Calculator");
        button.setIcon(VaadinIcon.CALC.create());
        button.addClickListener(this::onButtonClick);
        getContent().add(button);
    }

    protected void onButtonClick(ClickEvent<Button> event) {
        // add test
        dataItemListChartItems.addItem(
                new MapDataItem(Map.of("year", 2018, "cars", 150, "motorcycles", 700))
        );

        // remove test
        dataItemListChartItems.removeItem(dataItemListChartItems.getItems().get(0));

        // update test
        MapDataItem dataItem = dataItemListChartItems.getItems().get(1);
        dataItem.add("motorcycles", 2);
        dataItemListChartItems.updateItem(dataItem);
    }

    protected void initChart() {
        Chart chart = uiComponents.create(Chart.class);
        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTitle(new Title("Incremental list data update"))
                .withLegend(new Legend())
                .withXAxis(new XAxis())
                .withYAxis(new YAxis())
                .withDataSet(createDataSet())
                .withSeries(new BarSeries()
                                .withEncode(
                                        new Encode()
                                                .withY("motorcycle")
                                ),
                        new BarSeries()
                                .withEncode(
                                        new Encode()
                                                .withY("motorcycle")
                                )
                );

        getContent().add(chart);
    }

    protected DataSet createDataSet() {
        DataSet dataSet = new DataSet();

        DataSet.Source<MapDataItem> listDataSource = new DataSet.Source<>();
        dataItemListChartItems = new ListChartItems<>();
        dataItemListChartItems.addItems(
                new MapDataItem(Map.of("year", 2015, "cars", 10, "motorcycles", 50)),
                new MapDataItem(Map.of("year", 2016, "cars", 15, "motorcycles", 102)),
                new MapDataItem(Map.of("year", 2017, "cars", 1, "motorcycles", 70))
        );

        listDataSource.setCategoryField("year");
        listDataSource.addValueFields("cars", "motorcycles");
        listDataSource.setDataProvider(dataItemListChartItems);

        dataSet.setSource(listDataSource);
        return dataSet;
    }
}
