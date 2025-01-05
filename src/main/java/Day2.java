import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day2 {
    private final List<List<Integer>> reportsPerLevel;
    public Day2() {
        reportsPerLevel = new ArrayList<>();
        init();

    }

    private void init() {
        StopWatch stopWatch = StopWatch.createStarted();
        try {
            // Get file from resources folder
            InputStream inputStream = this.getClass().getClassLoader()
                    .getResourceAsStream("day2input.txt");

            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                // Split the line by whitespace
                String[] numbers = line.trim().split("\\s+");
                List<Integer> levels = new ArrayList<>();
                for (String number: numbers) {
                    levels.add(Integer.parseInt(number));
                }
                reportsPerLevel.add(levels);
            }

            reader.close();
            log.info("reading files finished in {}", stopWatch.formatTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getAnswer() {

        Integer answer = 0;


        for (List<Integer> levels: reportsPerLevel) {

            boolean isSafe = true;
            if (levels.size() <=1) {
                answer++;
                continue;
            }
            boolean isDecreasing = (levels.get(0) - levels.get(1) > 0);

            for (int i = 1; i < levels.size(); i++) {
                if (isDecreasing != (levels.get(i-1) - levels.get(i) > 0)) {
                    isSafe = false;
                    break;
                }
                int diff = Math.abs(levels.get(i-1) - levels.get(i));
                if (diff < 1 || diff > 3) {
                    isSafe = false;
                    break;
                }
            }

            if (isSafe) answer++;

        }
        log.info("Day 2 answer: {}", answer);

        return answer;

    }


    public Integer getAnswerPart2() {

        Integer answer = 0;


        for (List<Integer> originalLevels: reportsPerLevel) {
            if (checkIfALevelIsSafe(originalLevels)) {
                answer++;
                continue;
            }

            for(int i =0; i< originalLevels.size(); i++) {
                List<Integer> newList = new ArrayList<>(originalLevels);
                newList.remove(i);
                if (checkIfALevelIsSafe(newList)) {
                    answer++;
                    break;
                }
            }
        }
        log.info("Day 2 part 2 answer: {}", answer);

        return answer;

    }




    private boolean checkIfALevelIsSafe(List<Integer> level) {
        boolean isSafe = true;
        if (level.size() <=1) {
            return true;
        }
        boolean isDecreasing = (level.get(0) - level.get(1) > 0);

        for (int i = 1; i < level.size(); i++) {
            if (isDecreasing != (level.get(i-1) - level.get(i) > 0)) {
                isSafe = false;
                break;
            }
            int diff = Math.abs(level.get(i-1) - level.get(i));
            if (diff < 1 || diff > 3) {
                isSafe = false;
                break;
            }
        }

        return isSafe;
    }



}
