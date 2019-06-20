import javax.naming.ConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;


class ParseFile {

    String cutFromFile(String file) throws ConfigurationException {
        return cutFile(file, null);
    }

    String cutFromFile(String file, int count) throws ConfigurationException {
        return cutFile(file, String.valueOf(count));
    }

    private String cutFile(String file, String count) throws ConfigurationException {

        if (file == null || file.isEmpty()) {
            throw new ConfigurationException("File is not specified");
        }
        //проверяем значение
        if (count == null) {
            count = "10";
        }

        List<List<String>> allLines = getAllLinesFromFile(file);

        List<List<String>> partOfLines = getRandomPart(allLines, count);

        overWriteTheFile(allLines, file);

        return writeToFile(partOfLines, getResultFilePath(file));
    }

    private List<List<String>> getAllLinesFromFile(String file) {
        List<List<String>> records = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(SeparatorEnum.TAB.getValue());
                records.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    private List<List<String>> getRandomPart(List<List<String>> allLines, String count) throws ConfigurationException {
        int countInt = Integer.parseInt(count);

        if (countInt < 0) {
            throw new ConfigurationException("Count is negative: " + count);
        }

        int maxIndexOfAvailableRow = allLines.size() - countInt + 1;

        if (maxIndexOfAvailableRow < 0) {
            throw new ConfigurationException("Count must be not more then size of file: " + count);
        }

        Random rn = new Random();
        int randomStartIndex = rn.nextInt(maxIndexOfAvailableRow);

        return allLines.subList(randomStartIndex, randomStartIndex + countInt);
    }

    private String getResultFilePath(String file) {
        return file.substring(0, file.lastIndexOf(".")) + "_res" + file.substring(file.lastIndexOf("."));
    }

    private String writeToFile(List<List<String>> allLines, String file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (List<String> strings : allLines) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.append(strings.get(i));
                    if (i < (strings.size() - 1))
                        writer.append(SeparatorEnum.TAB.getValue());
                }
                writer.append(SeparatorEnum.NEW_ROW.getValue());
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    private String overWriteTheFile(List<List<String>> allLines, String fileName) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (List<String> strings : allLines) {
                for (int i = 0; i < strings.size(); i++) {
                    writer.append(strings.get(i));
                    if (i < (strings.size() - 1))
                        writer.append(SeparatorEnum.TAB.getValue());
                }
                writer.append(SeparatorEnum.NEW_ROW.getValue());
            }
            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}
