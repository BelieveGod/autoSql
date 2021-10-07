package com.example.demo.sql.other;

import java.time.Duration;
import java.time.Instant;

/**
 * @author LTJ
 * @date 2021/10/7
 */
public class CodeTimer {
    private Instant start;
    private Instant end;

    public CodeTimer() {
        start = Instant.now();
        end=start;
    }

    public void record(){
        start=end;
        end = Instant.now();
    }

    public String print(String prefix){
        Duration between = Duration.between(start, end);
        long millis = between.toMillis();
        return String.format("%s耗时%d ms", prefix, millis);
    }
}
