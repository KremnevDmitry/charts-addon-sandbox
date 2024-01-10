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
}
