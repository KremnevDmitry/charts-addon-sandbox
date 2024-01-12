package com.company.chartsaddon.view.series.pieseries;


import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "PieSeries", layout = MainView.class)
@ViewController("PieSeriesView")
@ViewDescriptor("pie-series-view.xml")
public class PieSeriesView extends StandardView {
}