import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

public class FileKeyWordChecker implements Runnable{
    private final File file;
    private Long checkTime = 1000L;
    private ArrayList<String> keyWords;
    private long filePos = 0L;

    private Logger logger = LogManager.getLogger(FileKeyWordChecker.class.getName());

    public FileKeyWordChecker(File file) {
        keyWords = new ArrayList<>();
        this.file = file;
    }

    @Override
    public void run() {
        long lastCheckTime = 0L;
        while(true){
            if(isCheckTime(lastCheckTime)){
                try {
                    keyWordChecker();
                    lastCheckTime = System.currentTimeMillis();
                    logger.trace("Check Ok..........");
                } catch (IOException e) {
                    logger.error("Logger Exception : {}", e);
                }
            }
        }
    }

    private void keyWordChecker() throws IOException {
        String strLine;
        try(RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r")) {
            randomAccessFile.seek(filePos);
            while ((strLine = randomAccessFile.readLine()) != null)
                for (String keyWord : keyWords)
                    if (strLine.contains(keyWord))
                        doSomeThingMethod(strLine);

            filePos = randomAccessFile.getFilePointer();
        }
    }

    private boolean isCheckTime(long lastCheckTime) {
        return (System.currentTimeMillis() - lastCheckTime) > checkTime;
    }

    private void doSomeThingMethod(String strLine) {
        logger.trace("find Exception Log : {}", strLine);
    }

    public void putKeyWord(String keyWord){
        keyWords.add(keyWord);
    }

    public void setCheckTime(Long time){
        this.checkTime = time;
    }
}