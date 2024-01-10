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
    protected Chart chart;

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe(id = "applySmooth", subject = "clickListener")
    public void onApplySmoothClick(final ClickEvent<JmixButton> event) {
        LineSeries line = chart.getSeries("line");
        line.setSmooth(line.getSmooth() != null && line.getSmooth() > 0.49 ? 0.0 : 0.5);
    }
}