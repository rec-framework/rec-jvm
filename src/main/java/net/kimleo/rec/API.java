package net.kimleo.rec;

import net.kimleo.rec.accessor.AccessorFactory;
import net.kimleo.rec.record.RecCollection;
import net.kimleo.rec.record.RecType;
import net.kimleo.rec.record.parser.ParseConfig;
import net.kimleo.rec.record.Record;
import net.kimleo.rec.record.parser.SimpleParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {
    private static final Map<ParseConfig, SimpleParser> parsers = new HashMap<>();

    public static Record rec(String input) {
        return rec(input, new ParseConfig(',', '"'));
    }

    public static Record rec(String input, ParseConfig config) {
        if (!parsers.containsKey(config)) {
            parsers.put(config, new SimpleParser(config));
        }
        return parsers.get(config).parse(input);
    }

    public static AccessorFactory accessor(String ...fields) {
        return new AccessorFactory(fields);
    }

    public static AccessorFactory accessor(Record record) {
        return new AccessorFactory(record);
    }

    public static RecCollection collect(List<Record> records, RecType type) {
        return new RecCollection(records, type);
    }
}
