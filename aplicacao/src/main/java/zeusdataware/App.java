package zeusdataware;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Importar CSV");

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Selecionar Arquivo CSV");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("Arquivos CSV", "*.csv")
                );
                File selectedFile = fileChooser.showOpenDialog(primaryStage);
                if (selectedFile != null) {
                    System.out.println("Arquivo selecionado: " + selectedFile.getAbsolutePath());
                    imprimirConteudoCSV(selectedFile);
                }
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Importar CSV");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void imprimirConteudoCSV(File arquivo) {
        try (Reader reader = new FileReader(arquivo);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withDelimiter(';').withFirstRecordAsHeader())) {
    
            // Imprime os cabeçalhos das colunas
            System.out.println("---------------------------------------------------------------------------------------");
            for (String header : csvParser.getHeaderNames()) {
                System.out.printf("%-20s", header);
            }
            System.out.println("\n---------------------------------------------------------------------------------------");
    
            // Itera sobre cada registro no arquivo CSV
            for (CSVRecord csvRecord : csvParser) {
                // Imprime o conteúdo de cada coluna para o registro atual
                for (String header : csvParser.getHeaderNames()) {
                    System.out.printf("%-20s", csvRecord.get(header));
                }
                System.out.println();
            }
            System.out.println("---------------------------------------------------------------------------------------");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo CSV: " + e.getMessage());
        }
    }
    

    public static void main(String[] args) {
        launch(args);
    }
}
