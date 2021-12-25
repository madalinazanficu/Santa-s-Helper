package inputfiles;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public final class InputLoader {
    /**
     * The path to the input file
     */
    private final String inputPath;

    public InputLoader(final String inputPath) {
        this.inputPath = inputPath;
    }

    /**
     * Method used to read the data from JSON file
     * Map the fields of the InputData class to JSON file fields
     * @return the input data
     * @throws IOException
     */
    public InputData readData() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(new File(inputPath), InputData.class);
    }

}
