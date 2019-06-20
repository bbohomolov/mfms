import org.junit.Assert;
import org.junit.Test;

import javax.naming.ConfigurationException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ParseFileTest {

    @Test
    public void cutFromFileTestPositive() throws URISyntaxException, ConfigurationException, IOException {
        ParseFile parseFile = new ParseFile();
        String fullPathToExistedFile = Paths.get(getClass().getClassLoader()
                .getResource("cutFromFileTestPositive.txt").toURI()).toFile().getAbsolutePath();
        String fullPathToCreatedFile = parseFile.cutFromFile(fullPathToExistedFile, 3);
        Assert.assertEquals("cutFromFileTestPositive_res.txt",
                fullPathToCreatedFile.substring(fullPathToCreatedFile.lastIndexOf("\\") + 1));
        Assert.assertEquals(3, countOfRowsInFile(fullPathToCreatedFile));
        Assert.assertEquals(13, countOfRowsInFile(fullPathToExistedFile));
    }

    @Test
    public void cutFromFileWOSpecifiedCountTestPositive() throws URISyntaxException, ConfigurationException, IOException {
        ParseFile parseFile = new ParseFile();
        String fullPathToExistedFile = Paths.get(getClass().getClassLoader()
                .getResource("cutFromFileWOSpecifiedCountTestPositive.txt").toURI()).toFile().getAbsolutePath();
        String fullPathToCreatedFile = parseFile.cutFromFile(fullPathToExistedFile);
        Assert.assertEquals("cutFromFileWOSpecifiedCountTestPositive_res.txt",
                fullPathToCreatedFile.substring(fullPathToCreatedFile.lastIndexOf("\\") + 1));
        Assert.assertEquals(10, countOfRowsInFile(fullPathToCreatedFile));
        Assert.assertEquals(6, countOfRowsInFile(fullPathToExistedFile));
    }

    @Test
    public void cutFromFileWithMaxAvailableCountTestPositive() throws URISyntaxException, ConfigurationException, IOException {
        ParseFile parseFile = new ParseFile();
        String fullPathToExistedFile = Paths.get(getClass().getClassLoader()
                .getResource("cutFromFileWithMaxAvailableCountTestPositive.txt").toURI()).toFile().getAbsolutePath();
        String fullPathToCreatedFile = parseFile.cutFromFile(fullPathToExistedFile, 16);
        Assert.assertEquals("cutFromFileWithMaxAvailableCountTestPositive_res.txt",
                fullPathToCreatedFile.substring(fullPathToCreatedFile.lastIndexOf("\\") + 1));
        Assert.assertEquals(16, countOfRowsInFile(fullPathToCreatedFile));
        Assert.assertEquals(0, countOfRowsInFile(fullPathToExistedFile));
    }

    @Test(expected = ConfigurationException.class)
    public void cutFromFileTestNegative() throws ConfigurationException {
        ParseFile parseFile = new ParseFile();
        parseFile.cutFromFile(null, 3);
    }

    @Test(expected = ConfigurationException.class)
    public void cutFromFileWOSpecifiedCountTestNegative() throws ConfigurationException {
        ParseFile parseFile = new ParseFile();
        parseFile.cutFromFile(null);
    }

    @Test(expected = ConfigurationException.class)
    public void cutFromFileTestWithBigCountNegative() throws ConfigurationException, URISyntaxException {
        ParseFile parseFile = new ParseFile();
        String fullPathToExistedFile = Paths.get(getClass().getClassLoader()
                .getResource("cutFromFileWithMaxAvailableCountTestPositive.txt").toURI()).toFile().getAbsolutePath();
        parseFile.cutFromFile(fullPathToExistedFile, 333);
    }

    private int countOfRowsInFile(String file) throws IOException {
        List<String> records = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            records.add(line);
        }
        return records.size();
    }
}
