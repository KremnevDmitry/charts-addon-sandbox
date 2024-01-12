package com.company.chartsaddon.view.series.funnelseries;


import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "FunnelSeries", layout = MainView.class)
@ViewController("FunnelSeriesView")
@ViewDescriptor("funnel-series-view.xml")
public class FunnelSeriesView extends StandardView {
}