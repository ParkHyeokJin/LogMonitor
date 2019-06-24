import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;

public class FileKeyWordCheckerTest {
    private FileKeyWordChecker fileReader;
    private static final Logger log = LogManager.getLogger(FileKeyWordCheckerTest.class.getName());
    private String targetFile = System.getProperty("user.dir") + "//" + "log//" + "testLog.log";

    @Before
    public void init() throws IOException{
        createTestLogFile();
    }

    @Test
    public void fileExistsTest() throws FileNotFoundException {
        log.trace("file Exists Test");
        assertTrue(getFile(targetFile).exists());
    }

    @Test
    public void logMonitorTest() throws IOException {
        log.trace("File KeyWord Check");
        fileReader = new FileKeyWordChecker(getFile(targetFile));
        putCheckKeyWord("Exception", "ERROR");
        new Thread(fileReader).run();
    }

    private void putCheckKeyWord(String key1, String key2) {
        fileReader.putKeyWord(key1);
        fileReader.putKeyWord(key2);
    }

    private File getFile(String targetFile) throws FileNotFoundException {
        File file = new File(targetFile);
        if (!file.exists()) {
            throw new FileNotFoundException(targetFile);
        }
        return file;
    }

    private void createTestLogFile() throws IOException {
        String testMessage = "test message\n" +
                "test message2\n" +
                "test Exception\n" +
                "Exception Test3\n";

        File file = new File(targetFile);
        if(!file.exists()){
            try(OutputStream outputStream = new FileOutputStream(file)) {
                outputStream.write(testMessage.getBytes());
            }
        }
    }
}
