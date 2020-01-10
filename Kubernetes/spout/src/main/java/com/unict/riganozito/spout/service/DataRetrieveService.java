package com.unict.riganozito.spout.service;

import com.unict.riganozito.spout.entity.Record;
import com.unict.riganozito.spout.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

@Service
public class DataRetrieveService {

    @Autowired
    KafkaProducer kafkaProducer;

    ArrayList<Record> records;

    public ArrayList<Record> convert(InputStream input) {
        Scanner scanner = new Scanner(input);
        ArrayList res = new ArrayList<Record>();
        boolean condition = false;
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.equals("# TYPE gateway_requests_seconds summary")) {
                condition = true;
            } else if (line.equals("# HELP gateway_requests_seconds_max")) {
                condition = false;
            }
            if ((condition) && !(line.equals("# TYPE gateway_requests_seconds summary"))) {
                String[] s = line.split(" ");
                String line1 = scanner.nextLine().trim();
                String[] v = line1.split(" ");
                res.add(new Record(s[0], Double.parseDouble(s[1]), Double.parseDouble(v[1])));
            }
        }
        return res;
    }

    public void sendNewRecords(ArrayList<Record> recs) {
        if(records == null || records.isEmpty()){
            recs.forEach((n) -> {
                String message = n.getRequest() + " #" + n.getNumber().toString() + " @" + n.getTime().toString();
                System.out.println(message);
                //kafkaProducer.publish(message);
            });
        }
        else{
            recs.forEach((n) -> {
                records.forEach((m) -> {
                    if (n.getRequest().equals(m.getRequest())) {
                        if (!(n.getNumber().equals(m.getNumber()))) {
                            Double number = n.getNumber() - m.getNumber();
                            Double time = n.getTime() - m.getTime();
                            String message = n.getRequest() + " #" + number.toString() + " @" + time.toString();
                            System.out.println(message);
                            //kafkaProducer.publish(message);
                        }
                    }
                });
            });
        }
    }

    @Scheduled(fixedRate = 10000)
    public void retrieve() {
        ProcessBuilder builder = new ProcessBuilder("curl", "http://localhost:8080/actuator/prometheus");
        try {
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            process.waitFor();
            ArrayList<Record> recs = convert(inputStream);
            sendNewRecords(recs);
            if(records == null || records.isEmpty()){
                System.out.println("Lista vuota");
            }
            else{
                records.clear();
            }
            records = new ArrayList<Record>(recs);
        } catch (IOException | InterruptedException e) {
            System.out.println("IOException occurred");
        }
    }
}
