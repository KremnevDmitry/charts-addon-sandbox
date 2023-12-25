package com.company.chartsaddon.bean;

import com.company.chartsaddon.entity.StockData;
import io.jmix.core.Metadata;
import io.jmix.core.TimeSource;
import io.jmix.flowui.model.CollectionContainer;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Component
public class StockDataGenerator {

    protected static final int DAYS_COUNT = 100;

    protected Metadata metadata;
    protected TimeSource timeSource;

    public StockDataGenerator(TimeSource timeSource, Metadata metadata) {
        this.timeSource = timeSource;
        this.metadata = metadata;
    }

    protected final Random random = new Random();

    public void generateData(CollectionContainer<StockData> stockChartDc) {
        List<StockData> items = new ArrayList<>();
        Date startDate = DateUtils.addDays(timeSource.currentTimestamp(), -DAYS_COUNT);
        for (int i = 0; i < DAYS_COUNT; i++) {
            items.add(generateStockData(DateUtils.addDays(startDate, i), i));
        }
        stockChartDc.setItems(items);
    }

    protected StockData generateStockData(Date date, int i) {
        Long open = Math.round(random.nextDouble() * 30 + 100);
        Long close = open + Math.round(random.nextDouble() * 15 - random.nextDouble() * 10);

        Long low = (open < close ? open : close) - Math.round(random.nextDouble() * 5);
        Long high = (open < close ? close : open) + Math.round(random.nextDouble() * 5);

        Long volume = Math.round(random.nextDouble() * (1000 + i)) + 100 + i;
        Long value = Math.round(random.nextDouble() * 30 + 100);

        return stockData(date, value, volume, open, close, high, low);
    }

    protected StockData stockData(Date date, Long value, Long volume,
                                  Long open, Long close, Long high, Long low) {
        StockData dateValueVolume = metadata.create(StockData.class);
        dateValueVolume.setDate(date);
        dateValueVolume.setValue(value);
        dateValueVolume.setVolume(volume);
        dateValueVolume.setOpen(open);
        dateValueVolume.setClose(close);
        dateValueVolume.setHigh(high);
        dateValueVolume.setLow(low);
        return dateValueVolume;
    }
}
