import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day3 {

    private String text;

    public Day3() {
        init();
    }

    private void init() {
        StopWatch stopWatch = StopWatch.createStarted();
        try (InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("day3input.txt")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String text = "";

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                text = text.concat(line);
            }
            this.text = text;
            log.info("reading files finished in {}", stopWatch.formatTime());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Long getAnswer() {
        StopWatch stopWatch = StopWatch.createStarted();
        // The regex pattern
        String regex = "mul\\(([1-9]\\d{0,2}),([1-9]\\d{0,2})\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        Long answer = 0L;

        // Find and print all valid matches
        while (matcher.find()) {
            String fullMatch = matcher.group(0);  // Complete match
            String num1 = matcher.group(1);       // First number
            String num2 = matcher.group(2);
            answer += Long.parseLong(num1)*Long.parseLong(num2);
        }
        log.info("Day3 answer: {}", answer);
        log.info("Completed in {}", stopWatch.formatTime());
        return answer;
    }

    public Long getAnswerPart2() {
        StopWatch stopWatch = StopWatch.createStarted();
        // The regex pattern
        String regex = "mul\\(([1-9]\\d{0,2}),([1-9]\\d{0,2})\\)|do\\(\\)|don't\\(\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        Long answer = 0L;
        boolean isDo = true;

        // Find and print all valid matches
        while (matcher.find()) {
            String fullMatch = matcher.group(0);  // Complete match
            if (fullMatch.startsWith("mul") && isDo) {
                String num1 = matcher.group(1);       // First number
                String num2 = matcher.group(2);
                answer += Long.parseLong(num1)*Long.parseLong(num2);
            } else isDo = fullMatch.equals("do()");

        }
        log.info("Day3 part 2 answer: {}", answer);
        log.info("Completed in {}", stopWatch.formatTime());
        return answer;
    }


}
