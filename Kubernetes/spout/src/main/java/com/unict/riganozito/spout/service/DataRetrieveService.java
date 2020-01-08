package com.unict.riganozito.spout.service;

import com.unict.riganozito.spout.kafka.KafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Service
public class DataRetrieveService {

    @Autowired
    KafkaProducer kafkaProducer;

    HashMap<String, Double> records;

    public HashMap<String, Double> convert(InputStream input){
        Scanner scanner = new Scanner(input);
        HashMap res = new HashMap<String, Double>();
        boolean condition = false;
        while(scanner.hasNextLine()){
            String line = scanner.nextLine().trim();
            if(line.equals("# TYPE gateway_requests_seconds summary")){
                condition = true;
            }
            else if(line.equals("# HELP gateway_requests_seconds_max")){
                condition = false;
            }
            if((condition) && !(line.equals("# TYPE gateway_requests_seconds summary"))){
                String[] s = line.split(" ");
                res.put(s[0], new Double(s[1]));
            }
        }
        return res;
    }

    public void sendNewRecords(HashMap<String, Double> recs){
        for (Map.Entry<String, Double> map: recs.entrySet()) {
            if(records.get(map.getKey())!= null){
                Double d = records.get(map.getKey());
                if(!d.equals(map.getValue())){
                    kafkaProducer.publish(map.getKey() + " " + map.getValue().toString());
                }
            }
            else{
                kafkaProducer.publish(map.getKey() + " " + map.getValue().toString());
            }
        }
    }

    @Scheduled(fixedRate = 10000)
    public void retrieve()  {
        ProcessBuilder builder = new ProcessBuilder("curl", "http://localhost:8080/actuator/prometheus");
        try {
            Process process = builder.start();
            InputStream inputStream = process.getInputStream();
            process.waitFor();
            HashMap<String, Double> recs = convert(inputStream);
            sendNewRecords(recs);
            records.putAll(recs);
        }
        catch (IOException|InterruptedException e){
            System.out.println("IOException occurred");
        }
    }
}
