package main;

import checker.Checker;
import common.Constants;
import inputfiles.InputData;
import inputfiles.InputLoader;
import outputfiles.OutputFormat;
import outputfiles.Writer;
import observer.SantaClaus;
import observer.Solver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * Class used to run the code
 */
public final class Main {

    private Main() {
        ///constructor for checkstyle
    }
    /**
     * This method is used to call the checker which calculates the score
     * @param args
     *          the arguments used to call the main method
     */
    public static void main(final String[] args) throws IOException {
        File inputDirectory = new File(Constants.INPUT_PATH);

        // the path to the result directory
        Path path = Paths.get(Constants.RESULT_PATH);

        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);
        for (File file : Objects.requireNonNull(outputDirectory.listFiles())) {
            if (!file.delete()) {
                System.out.println("error");
            }
        }

        // iterate through the list of files of the input directory to apply the required action
        for (File file : Objects.requireNonNull(inputDirectory.listFiles())) {
            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }
        // checker call
        Checker.calculateScore();
    }
    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        // clear the output format database for every test
        OutputFormat.getInstance().clear();

        // load the input data from every test file and read it
        InputLoader inputLoader = new InputLoader(filePath1);
        InputData input = inputLoader.readData();

        // database used for keeping santa specific info that will be updated every round
        SantaClaus santaClaus = SantaClaus.getInstance();
        santaClaus.clearSantaClaus();

        // set the initial information used in Round0
        santaClaus.setSantaBudget(input);
        santaClaus.setChildren(input);
        santaClaus.setSantaGiftList(input);

        // database used for writing the output format
        OutputFormat outputFormat = OutputFormat.getInstance();

        // responsible class for solving the rounds
        Solver solver = new Solver();
        solver.setNumberOfYears(input);
        solver.setAnnualChanges(input);
        solver.solve();

        Writer fileWriter = new Writer(filePath2);
        fileWriter.write();
    }
}
