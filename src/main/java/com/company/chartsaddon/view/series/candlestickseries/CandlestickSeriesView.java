package com.company.chartsaddon.view.series.candlestickseries;


import com.company.chartsaddon.bean.StockDataGenerator;
import com.company.chartsaddon.entity.StockData;
import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Grid;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.axis.*;
import io.jmix.chartsflowui.kit.component.model.datazoom.InsideDataZoom;
import io.jmix.chartsflowui.kit.component.model.datazoom.SliderDataZoom;
import io.jmix.chartsflowui.kit.component.model.series.*;
import io.jmix.chartsflowui.kit.component.model.shared.Color;
import io.jmix.chartsflowui.kit.component.model.shared.LineStyle;
import io.jmix.chartsflowui.kit.component.model.toolbox.DataZoomFeature;
import io.jmix.chartsflowui.kit.component.model.toolbox.Toolbox;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "CandlestickSeries", layout = MainView.class)
@ViewController("CandlestickSeriesView")
@ViewDescriptor("candlestick-series-view.xml")
public class CandlestickSeriesView extends StandardView {

    @ViewComponent
    protected CollectionContainer<StockData> stockDataDc;
    @ViewComponent
    protected Chart chart;

    @Autowired
    protected StockDataGenerator stockDataGenerator;


    @Subscribe
    protected void onInit(InitEvent event) {
        stockDataGenerator.generateData(stockDataDc);

        // TODO: 18.12.2023 visual map https://echarts.apache.org/examples/en/editor.html?c=candlestick-large
    }
}