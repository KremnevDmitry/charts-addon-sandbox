package com.company.chartsaddon.view.blank;

import com.company.chartsaddon.entity.TransportCount;
import com.company.chartsaddon.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.chartsflowui.component.Chart;
import io.jmix.chartsflowui.data.ContainerChartItems;
import io.jmix.chartsflowui.data.item.EntityDataItem;
import io.jmix.chartsflowui.kit.component.model.DataSet;
import io.jmix.chartsflowui.kit.component.model.Title;
import io.jmix.chartsflowui.kit.component.model.Tooltip;
import io.jmix.chartsflowui.kit.component.model.axis.XAxis;
import io.jmix.chartsflowui.kit.component.model.axis.YAxis;
import io.jmix.chartsflowui.kit.component.model.legend.Legend;
import io.jmix.chartsflowui.kit.component.model.series.BarSeries;
import io.jmix.core.Metadata;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.splitlayout.JmixSplitLayout;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

@Route(value = "blank", layout = MainView.class)
@ViewController("BlankView")
@ViewDescriptor("blank-view.xml")
public class BlankView extends StandardView {

    @Autowired
    protected UiComponents uiComponents;
    @Autowired
    protected Metadata metadata;

    @ViewComponent
    protected CollectionContainer<TransportCount> transportDc;
    @ViewComponent
    protected Chart chart;
    @ViewComponent
    protected Timer timer;

    protected JmixButton startStopDataGen;

    protected Integer baseYear = 2013;
    protected Random random = new Random();
    protected boolean timerStarted;

    @Subscribe
    protected void onInit(InitEvent event) {
        JmixSplitLayout jmixSplitLayout = uiComponents.create(JmixSplitLayout.class);
        jmixSplitLayout.setWidthFull();
        jmixSplitLayout.setHeightFull();

        chart.setWidthFull();
        chart.setHeightFull();

        chart
                .withTitle(createTitle())
                .withLegend(new Legend())
                .withTooltip(new Tooltip())
                .withXAxis(new XAxis())
                .withYAxis(new YAxis())
                .withDataSet(createDataSet())
                .withSeries(
                        createBarSeries("Cars sells"),
                        createBarSeries("Motorcycles sells"),
                        createBarSeries("Bicycles sells")
                );

        jmixSplitLayout.addToPrimary(createPrimaryContent());
        jmixSplitLayout.addToSecondary(new Div());
        getContent().add(jmixSplitLayout);
    }

    protected Title createTitle() {
        return new Title("My First chart")
                .withSubtext("Go to google")
                .withSublink("https://google.com")
                .withRight("10%");
    }

    protected Component createPrimaryContent() {
        VerticalLayout verticalLayout = uiComponents.create(VerticalLayout.class);
        verticalLayout.setPadding(false);

        HorizontalLayout horizontalLayout = uiComponents.create(HorizontalLayout.class);
        horizontalLayout.setPadding(false);

        JmixButton addDataButton = uiComponents.create(JmixButton.class);
        addDataButton.setText("Add data");
        addDataButton.setIcon(VaadinIcon.PLUS.create());
        addDataButton.addClickListener(__ -> addData());

        JmixButton removeButton = uiComponents.create(JmixButton.class);
        removeButton.setText("Remove data");
        removeButton.setIcon(VaadinIcon.MINUS.create());
        removeButton.addClickListener(__ -> removeData());

        startStopDataGen = uiComponents.create(JmixButton.class);
        startStopDataGen.addClickListener(this::timerButtonClickListener);
        prepareTimerButtonToStart();

        horizontalLayout.add(removeButton, addDataButton, startStopDataGen);
        verticalLayout.add(horizontalLayout, chart);

        return verticalLayout;
    }

    protected void timerButtonClickListener(ClickEvent<Button> event) {
        if (timerStarted) {
            timer.stop();
            prepareTimerButtonToStart();
        } else {
            timer.start();
            prepareTimerButtonToStop();
        }

        timerStarted = !timerStarted;
    }

    protected void prepareTimerButtonToStart() {
        startStopDataGen.setText("Start");
        startStopDataGen.setIcon(VaadinIcon.PLAY.create());
        startStopDataGen.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        startStopDataGen.removeThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    protected void prepareTimerButtonToStop() {
        startStopDataGen.setText("Stop");
        startStopDataGen.setIcon(VaadinIcon.PAUSE.create());
        startStopDataGen.addThemeVariants(ButtonVariant.LUMO_ERROR);
    }

    @Subscribe("timer")
    protected void onTimerTick(Timer.TimerActionEvent event) {
        removeData();
        addData();
    }

    protected void addData() {
        TransportCount transportCount = metadata.create(TransportCount.class);

        transportCount.setYear(baseYear++);
        transportCount.setCars(random.nextInt(100, 1700));
        transportCount.setMotorcycles(random.nextInt(200, 700));
        transportCount.setBicycles(random.nextInt(5, 40));

        transportDc.getMutableItems().add(transportCount);
    }

    protected void removeData() {
        transportDc.getMutableItems().remove(0);
    }

    protected DataSet createDataSet() {
        return new DataSet()
                .withSource(
                        new DataSet.Source<EntityDataItem>()
                                .withDataProvider(new ContainerChartItems<>(transportDc))
                                .withCategoryField("year")
                                .withValueFields("cars", "motorcycles", "bicycles")
                );
    }

    protected BarSeries createBarSeries(String name) {
        return new BarSeries()
                .withName(name);
    }
}
