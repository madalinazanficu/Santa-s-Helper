package datawriting;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;

public final class Writer {
    private final String outputPath;

    public Writer(final String outputPath) {
        this.outputPath = outputPath;
    }

    /**
     * @param outputFormat
     * @throws IOException
     */
    // Scrierea in format JSON a datelor
    public void write(final OutputFormat outputFormat) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        ObjectWriter objectWriter = objectMapper.writer(new DefaultPrettyPrinter());
        objectWriter.writeValue(new File(outputPath), OutputFormat.getInstance().getFinalResult());
    }
}
