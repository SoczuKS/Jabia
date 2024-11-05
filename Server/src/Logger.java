import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private final BufferedWriter writer;
    DateTimeFormatter dateTimeFormatter;

    public Logger() throws IOException {
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss.SSSS");
        File file = new File("logs.txt");
        writer = new BufferedWriter(new FileWriter(file));
    }

    public void write(String msg) throws IOException {
        ZonedDateTime dateTime = ZonedDateTime.now();
        String formattedMsg = "[" + dateTime.format(dateTimeFormatter) + "] " + msg;
        writer.write(formattedMsg);
        writer.newLine();
        writer.flush();
        System.out.println(formattedMsg);
    }
}
