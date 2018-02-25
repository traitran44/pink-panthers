package com.pinkpanthers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private FileReader file;

    public Parser(String filename) {
        try {
            this.file = new FileReader(filename);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public List<Shelter> createShelters() {
        CSVParser reader = null;
        try {
            reader = new CSVParser(file, CSVFormat.DEFAULT.withHeader());
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        List<Shelter> shelters = new ArrayList<>();
        Db db = new Db("pinkpanther", "PinkPantherReturns!", "pinkpanther");
        for (CSVRecord record: reader) {
            Shelter shelter = db.createShelter(
                    record.get("Shelter Name"),
                    record.get("Capacity"),
                    record.get("Special Notes"),
                    Double.valueOf(record.get("Latitude")),
                    Double.valueOf(record.get("Longitude")),
                    record.get("Phone Number"),
                    record.get("Restrictions"),
                    record.get("Address")
            );
            shelters.add(shelter);
        }
        return shelters;
    }
}
