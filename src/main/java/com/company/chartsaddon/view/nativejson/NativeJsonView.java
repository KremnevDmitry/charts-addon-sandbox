package com.company.chartsaddon.view.nativejson;

import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "nativejson", layout = MainView.class)
@ViewController("NativeJsonView")
@ViewDescriptor("native-json-view.xml")
public class NativeJsonView extends StandardView {

    @Autowired
    protected UiComponents uiComponents;

    @Subscribe
    protected void onInit(InitEvent event) {
        initChart();
    }

    protected void initChart() {
        Chart chart = uiComponents.create(Chart.class);
        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTitle(new Title("Title"))
                .withNativeJson("""
                        {"angleAxis": {
                            "max": 2,
                            "startAngle": 30,
                            "splitLine": {
                              "show": false
                            }
                          },
                          "radiusAxis": {
                            "type": 'category',
                            "data": ['v', 'w', 'x', 'y', 'z'],
                            "z": 10
                          },
                          "polar": {},
                          "series": [
                            {
                              "type": 'bar',
                              "data": [4, 3, 2, 1, 0],
                              "coordinateSystem": 'polar',
                              "name": 'Without Round Cap',
                              "itemStyle": {
                                "borderColor": 'red',
                                "opacity": 0.8,
                                "borderWidth": 1
                              }
                            },
                            {
                              "type": 'bar',
                              "data": [4, 3, 2, 1, 0],
                              "coordinateSystem": 'polar',
                              "name": 'With Round Cap',
                              "roundCap": true,
                              "itemStyle": {
                                "borderColor": 'green',
                                "opacity": 0.8,
                                "borderWidth": 1
                              }
                            }
                          ],
                          "legend": {
                            "show": true,
                            "data": ['Without Round Cap', 'With Round Cap']
                          },
                          "title": {
                            "subtext": 'subtext'
                          }
                          }""");

        getContent().add(chart);
    }
}
