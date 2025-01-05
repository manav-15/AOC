import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.lang3.time.StopWatch;


@Slf4j
public class Day1 {
    final List<Integer> firstColumn;
    final List<Integer> secondColumn;

    public Day1() {
        this.firstColumn = new ArrayList<>();
        this.secondColumn = new ArrayList<>();
        init();
    }

    private void init() {

        StopWatch stopWatch = StopWatch.createStarted();
        try {
            // Get file from resources folder
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("day1input.txt");

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by whitespace
                String[] numbers = line.trim().split("\\s+");

                if (numbers.length == 2) {
                    firstColumn.add(Integer.parseInt(numbers[0]));
                    secondColumn.add(Integer.parseInt(numbers[1]));
                }
            }

            reader.close();
            log.info("reading files finished in {}", stopWatch.formatTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Long getAnswerPart1() {
        final StopWatch stopWatch = StopWatch.createStarted();

        // sort the lists
        Collections.sort(firstColumn);
        Collections.sort(secondColumn);

        Long answer = 0L;

        for (int i = 0; i< firstColumn.size(); i++) {
            Integer firstInteger = firstColumn.get(i);
            Integer secondInteger = secondColumn.get(i);

            if (firstInteger < secondInteger) {
                answer += secondInteger - firstInteger;
            } else {
                answer += firstInteger - secondInteger;
            }
        }

        log.info("Answer: {}", answer);
        log.info("Finished in time: {}", stopWatch.formatTime());

        return answer;

    }

    public Long getAnswerPart2() {
        StopWatch stopWatch = StopWatch.createStarted();
        // convert right/second list to hashmap
        HashMap<Integer, Integer> rightListCountMap = new HashMap<>();
        secondColumn.forEach(k -> {
            rightListCountMap.computeIfPresent(k, (key,v) -> v+1);
            rightListCountMap.computeIfAbsent(k , key -> 1);
        });

        Long answer = 0L;
        for (Integer firstInteger: firstColumn) {
            answer += firstInteger*rightListCountMap.getOrDefault(firstInteger, 0);
        }

        log.info("part 2 answer: {}", answer);
        log.info("Finished part 2 in {}", stopWatch.formatTime());
        return answer;
    }
}
