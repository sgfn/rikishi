package com.rikishi.rikishi.loader.csv;

import com.opencsv.CSVReader;
import com.rikishi.rikishi.model.Sex;
import com.rikishi.rikishi.model.User;
import com.rikishi.rikishi.provider.ConfigProvider;

import java.io.Closeable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

class CSVUserIterator implements Iterator<User>, Closeable {
    private final static int FIELD_COUNT = 9;

    private final CSVReader reader;
    private final Iterator<String[]> csvIt;

    private final ConfigProvider configProvider;

    public CSVUserIterator(CSVReader reader, ConfigProvider configProvider) {
        this.reader = reader;
        csvIt = reader.iterator();
        this.configProvider = configProvider;
    }

    @Override
    public boolean hasNext() {
        return csvIt.hasNext();
    }

    @Override
    public User next() {
        var data = csvIt.next();

        if (data == null)
            return null;

        if (data.length != FIELD_COUNT) {
            throw new RuntimeException(String.format(
                "Unexpected data length:\n\texpected:\t%s\n\tactual:\t\t%s\n\tdata:\t\t'%s'",
                FIELD_COUNT, data.length, Arrays.toString(data)
            ));
        }

        return new User(
            Long.parseLong(data[0]),
            data[1],
            data[2],
            Integer.parseInt(data[3]),
            Double.parseDouble(data[4]),
            configProvider.getWeightClassById(Long.parseLong(data[5])).orElseThrow(),
            Sex.fromString(data[6]).orElseThrow(),
            data[7],
            data[8]
        );
    }

    @Override
    public void close() throws IOException {
        reader.close();
    }
}
