package main;

import checker.Checker;
import common.Constants;
import database.Database;
import dataprocessing.InputData;
import dataprocessing.InputLoader;
import datawriting.OutputFormat;
import datawriting.Writer;
import observer.SantaClaus;

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

        // path-ul catre directorul out
        Path path = Paths.get(Constants.OUT_PATH);

        // daca nu exista path catre directorul out => ne creeaza fizic
        // (in calculator) director la path-ul respectiv
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        // instanta pentru directorul de out
        File outputDirectory = new File(Constants.OUT_PATH);


        for (File file : Objects.requireNonNull(outputDirectory.listFiles())) {
            if (!file.delete()) {
                System.out.println("error");
            }
        }

        // se itereaza prin lista de fisiere ale directorului de input
        for (File file : Objects.requireNonNull(inputDirectory.listFiles())) {
            String filepath = Constants.OUTPUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }
        // apelarea checker-ului
        Checker.calculateScore();
    }
    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {

        OutputFormat.getInstance().clearOutputFormat();

        InputLoader inputLoader = new InputLoader(filePath1);
        System.out.println(filePath1);
        InputData input = inputLoader.readData();

        // not require modification on fields
        Database database = Database.getInstance();
        database.clearDatabase();

        database.setNumberOfYears(input.getNumberOfYears());
        database.setAnnualChanges(input.getAnnualChanges());

        // require modification on fields
        SantaClaus santaClaus = SantaClaus.getInstance();
        santaClaus.clearSantaClaus();

        // the initial information that will be updated
        santaClaus.setSantaBudget(input.getSantaBudget());
        santaClaus.setChildren(input.getInitialData().getChildren());
        santaClaus.setSantaGiftList(input.getInitialData().getSantaGiftsList());

        OutputFormat outputFormat = OutputFormat.getInstance();
        Solver solver = new Solver(input.getNumberOfYears(), input.getAnnualChanges());
        solver.solve();

        Writer fileWriter = new Writer(filePath2);
        fileWriter.write(outputFormat);

        //TODO add here the entry point to your implementation
    }
}
