package com.company.chartsaddon.view.series.lineseries;


import com.company.chartsaddon.entity.DateValue;
import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.axis.AxisType;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.axis.YAxis;
import io.jmix.chartsflowui.kit.component.model.datazoom.InsideDataZoom;
import io.jmix.chartsflowui.kit.component.model.datazoom.SliderDataZoom;
import io.jmix.chartsflowui.kit.component.model.series.LineSeries;
import io.jmix.chartsflowui.kit.component.model.shared.HasSymbols;
import io.jmix.chartsflowui.kit.component.model.shared.Orientation;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.JmixNumberField;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "LineSeriesView", layout = MainView.class)
@ViewController("LineSeriesView")
@ViewDescriptor("line-series-view.xml")
public class LineSeriesView extends StandardView {

    @ViewComponent
    protected CollectionContainer<DateValue> dateValueDc;
    @ViewComponent
    private JmixNumberField smoothField;

    @Autowired
    protected UiComponents uiComponents;

    protected Chart chart;
    protected LineSeries series;

    @Subscribe
    protected void onInit(InitEvent event) {
        chart = uiComponents.create(Chart.class);

        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withXAxis(new XAxis())
                .withYAxis(new YAxis())
                .withDataSet(createDataSet())
                .withSeries(createLineSeries())
                .withTooltip(createTooltip())
                .withDataZoom(
                        new SliderDataZoom()
                                .withOrientation(Orientation.HORIZONTAL)
                )
                .withDataZoom(new SliderDataZoom()
                        .withOrientation(Orientation.VERTICAL)
                        .withLeft("7%"))
                .withDataZoom(new InsideDataZoom());

        getContent().add(chart);
    }

    protected Tooltip createTooltip() {
        return new Tooltip()
                .withTrigger(Tooltip.Trigger.AXIS);
    }

    protected LineSeries createLineSeries() {
        series = new LineSeries()
                .withSymbol(HasSymbols.SymbolType.NONE);
        return series;
    }

    protected DataSet createDataSet() {
        return new DataSet()
                .withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withCategoryField("date")
                                .withValueField("value")
                                .withDataProvider(new ContainerChartItems<>(dateValueDc))
                );
    }

    @Subscribe(id = "applySmooth", subject = "clickListener")
    public void onApplySmoothClick(final ClickEvent<JmixButton> event) {
        Double value = smoothField.getValue();
        if (0 <= value && value <= 1.0) {
            series.setSmooth(value);
        }
    }
}