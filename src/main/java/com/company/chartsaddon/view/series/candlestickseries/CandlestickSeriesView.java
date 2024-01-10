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

    @Autowired
    protected StockDataGenerator stockDataGenerator;
    @Autowired
    protected UiComponents uiComponents;

    protected Chart chart;

    @Subscribe
    protected void onInit(InitEvent event) {
        stockDataGenerator.generateData(stockDataDc);

        chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        initTools();
        initGrid();
        initAxes();
        initDataZoom();

        // TODO: 18.12.2023 visual map https://echarts.apache.org/examples/en/editor.html?c=candlestick-large

        initSeries();

        chart.setDataSet(
                new DataSet().withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withCategoryField("date")
                                .withValueFields(
                                        "open",
                                        "close",
                                        "low",
                                        "high",
                                        "volume"
                                )
                                .withDataProvider(new ContainerChartItems<>(stockDataDc))
                )
        );

        getContent().add(chart);
    }

    protected void initTools() {
        // TODO: 18.12.2023 fix tooltip
        chart.setTitle(
                new Title()
                        .withText("Data Amount: " + 2000)
        );

        chart.setTooltip(
                new Tooltip()
                        .withTrigger(Tooltip.Trigger.AXIS)
                        .withAxisPointer(
                                new Tooltip.AxisPointer()
                                        .withType(Tooltip.AxisPointer.IndicatorType.LINE)
                        )
        );

        chart.setToolbox(
                new Toolbox()
                        .withFeatures(
                                new DataZoomFeature()
                        )
        );
    }

    protected void initGrid() {
        chart.addGrid(
                new Grid()
                        .withLeft("10%")
                        .withRight("10%")
                        .withBottom("200")
        );

        chart.addGrid(
                new Grid()
                        .withLeft("10%")
                        .withRight("10%")
                        .withHeight("80")
                        .withBottom("80")
        );
    }

    protected void initAxes() {
        chart.addXAxis(
                new XAxis()
                        .withBoundaryGap(false)
                        .withAxisLine(
                                new AxisLine()
                                        .withOnZero(false)
                        )
                        .withSplitLine(
                                new SplitLine()
                                        .withShow(false)
                        )
                        .withMin("dataMin")
                        .withMax("dataMax")
        );
        chart.addXAxis(
                new XAxis()
                        .withScale(true)
                        .withGridIndex(1)
                        .withBoundaryGap(false)
                        .withAxisLine(
                                new AxisLine()
                                        .withOnZero(false)
                        )
                        .withAxisTick(
                                new AxisTick()
                                        .withShow(false)
                        )
                        .withSplitLine(
                                new SplitLine()
                                        .withShow(false)
                        )
                        .withAxisLabel(
                                new AxisLabel()
                                        .withShow(false)
                        )
                        .withMin("dataMin")
                        .withMax("dataMax")
        );

        chart.addYAxis(
                new YAxis()
                        .withScale(true)
                        .withSplitArea(
                                new SplitArea()
                                        .withShow(true)
                        )
        );

        chart.addYAxis(
                new YAxis()
                        .withScale(true)
                        .withGridIndex(1)
                        .withSplitNumber(2)
                        .withAxisLabel(
                                new AxisLabel()
                                        .withShow(false)
                        )
                        .withAxisLine(
                                new AxisLine()
                                        .withShow(false)
                        )
                        .withAxisTick(
                                new AxisTick()
                                        .withShow(false)
                        )
                        .withSplitLine(
                                new SplitLine()
                                        .withShow(false)
                        )
        );
    }

    protected void initDataZoom() {
        chart.addDataZoom(
                new InsideDataZoom()
                        .withXAxisIndexes(0, 1)
                        .withStart(10D)
                        .withEnd(100D)
        );

        chart.addDataZoom(
                new SliderDataZoom()
                        .withShow(true)
                        .withXAxisIndexes(0, 1)
                        .withBottom("10")
                        .withStart(10D)
                        .withEnd(100D)
        );
    }

    protected void initSeries() {
        chart.withSeries(
                new CandlestickSeries()
                        .withEncode(
                                new Encode()
                                        .withX("date")
                                        .withY("open", "close", "low", "high")
                        )
                        .withTooltip(
                                new AbstractSeries.Tooltip()
                                        .withFormatter("Series name: {a}, Category name: {b}, DataValue: {c}, None: {d}")
                        ),
                new LineSeries()
                        .withName("High")
                        .withEncode(new Encode().withX("date").withY("high"))
                        .withSmooth(0.5)
                        .withLineStyle(
                                new LineStyle().withOpacity(0.5)
                        ),
                new BarSeries()
                        .withName("Volume")
                        .withXAxisIndex(1)
                        .withYAxisIndex(1)
                        .withItemStyle(
                                new BarSeries.ItemStyleWithDecal()
                                        .withColor(Color.valueOf("#7fbe9e"))
                        )
                        .withLarge(true)
                        .withEncode(
                                new Encode()
                                        .withX("date")
                                        .withY("volume")
                        )
        );
    }
}