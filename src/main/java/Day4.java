import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class Day4 {

    private List<List<Character>> array2D = new ArrayList<>();

    public Day4 () {
        init();
    }


    private void init() {
        StopWatch stopWatch = StopWatch.createStarted();
        try (InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream("day4input.txt")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found in resources!");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            // Read file line by line
            while ((line = reader.readLine()) != null) {
                array2D.add(line.chars().mapToObj(ch -> (char) ch).collect(Collectors.toList()));
            }
            log.info("reading files finished in {}", stopWatch.formatTime());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Integer getAnswerPart1() {
        // for each X, check in all 8 directions
        int rows = array2D.size();
        int cols = array2D.get(0).size();
        int answer = 0;

        for (int i=0; i< rows; i++) {
            for (int j = 0; j< cols; j++) {
                if (array2D.get(i).get(j).equals('X')) {
                    // check in all 8 dirs
                    if (checkEastSide(i,j,rows,cols)) answer++;
                    if (checkWestSide(i,j,rows,cols)) answer++;
                    if (checkNorthSide(i,j,rows,cols)) answer++;
                    if (checkSouthSide(i,j,rows,cols)) answer++;
                    if (checkNorthEastDiagonal(i,j,rows,cols)) answer++;
                    if (checkSouthEastDiagonal(i,j,rows,cols)) answer++;
                    if (checkSouthWestDiagonal(i,j,rows,cols)) answer++;
                    if (checkNorthWestDiagonal(i,j,rows,cols)) answer++;

                }
            }
        }
        log.info("Day 4 part 1 answer: {}", answer);
        return answer;

    }

    public Integer getAnswerPart2() {
        // for each X, check in all 8 directions
        int rows = array2D.size();
        int cols = array2D.get(0).size();
        int answer = 0;

        for (int i=0; i< rows; i++) {
            for (int j = 0; j< cols; j++) {
                if (array2D.get(i).get(j).equals('A')) {
                    // check in all 2 dirs
                    if (checkRightDiagonal(i,j,rows,cols) && checkLeftDiagonal(i,j,rows,cols)) answer++;
                }
            }
        }
        log.info("Day 4 part 2 answer: {}", answer);
        return answer;

    }

    private boolean checkRightDiagonal(int i, int j, int rows, int cols) {
        if (j + 1>= cols || i < 1) return false;
        if (j < 1 || i + 1 >=rows) return false;

        return (array2D.get(i-1).get(j+1) == 'M' && array2D.get(i+1).get(j-1) == 'S') || (array2D.get(i-1).get(j+1) == 'S' && array2D.get(i+1).get(j-1) == 'M');

    }

    private boolean checkLeftDiagonal(int i, int j, int rows, int cols) {
        if (j < 1 || i < 1) return false;
        if (j + 1 >= cols || i + 1 >=rows) return false;

        return (array2D.get(i-1).get(j-1) == 'M' && array2D.get(i+1).get(j+1) == 'S') || (array2D.get(i-1).get(j-1) == 'S' && array2D.get(i+1).get(j+1) == 'M');

    }

    private boolean checkEastSide(int i, int j, int rows, int cols) {
        if (j + 3 >= cols) return false;

        List<Character> row = array2D.get(i);
        return row.get(j+1) == 'M'
                && row.get(j+2) == 'A'
                && row.get(j+3) == 'S';
    }

    private boolean checkWestSide(int i, int j, int rows, int cols) {
        if (j < 3) return false;

        List<Character> row = array2D.get(i);
        return row.get(j-1) == 'M'
                && row.get(j-2) == 'A'
                && row.get(j-3) == 'S';
    }

    private boolean checkNorthSide(int i, int j, int rows, int cols) {
        if (i < 3) return false;

        return array2D.get(i-1).get(j) == 'M'
                && array2D.get(i-2).get(j) == 'A'
                && array2D.get(i-3).get(j) == 'S';
    }

    private boolean checkSouthSide(int i, int j, int rows, int cols) {
        if (i + 3 >= rows) return false;

        return array2D.get(i+1).get(j) == 'M'
                && array2D.get(i+2).get(j) == 'A'
                && array2D.get(i+3).get(j) == 'S';
    }

    private boolean checkSouthEastDiagonal(int i, int j, int rows, int cols) {
        if (i + 3 >= rows || j + 3 >= cols) return false;

        return array2D.get(i+1).get(j+1) == 'M'
                && array2D.get(i+2).get(j+2) == 'A'
                && array2D.get(i+3).get(j+3) == 'S';
    }

    private boolean checkSouthWestDiagonal(int i, int j, int rows, int cols) {
        if (i + 3 >= rows || j - 3 < 0) return false;

        return array2D.get(i+1).get(j-1) == 'M'
                && array2D.get(i+2).get(j-2) == 'A'
                && array2D.get(i+3).get(j-3) == 'S';
    }

    private boolean checkNorthEastDiagonal(int i, int j, int rows, int cols) {
        if (i - 3 < 0 || j + 3 >= cols) return false;

        return array2D.get(i-1).get(j+1) == 'M'
                && array2D.get(i-2).get(j+2) == 'A'
                && array2D.get(i-3).get(j+3) == 'S';
    }

    private boolean checkNorthWestDiagonal(int i, int j, int rows, int cols) {
        if (i - 3 < 0 || j - 3 < 0) return false;

        return array2D.get(i-1).get(j-1) == 'M'
                && array2D.get(i-2).get(j-2) == 'A'
                && array2D.get(i-3).get(j-3) == 'S';
    }


}
