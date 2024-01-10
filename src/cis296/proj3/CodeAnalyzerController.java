/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cis296.proj3;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ashleyyyyayyyy
 */
public class CodeAnalyzerController implements Initializable {
    @FXML
    private Button btn;

    @FXML
    private ImageView imgView;

    @FXML
    private ListView<String> listView;

    private String[] keywords = {"abstract", "assert", "boolean", "break", "byte", "case", "catch", "char", "class", "const",
            "const", "continue", "default", "do", "double", "else", "enum", "extends", "final", "finally",
            "float", "for", "goto", "if", "implements", "import", "instanceof", "int", "interface", "long",
            "long", "native", "new", "package", "private", "protected", "public", "return", "short", "static",
            "strictfp", "super", "switch", "synchronized", "this", "throw", "throws", "transient", "try",
            "try", "void", "volatile", "while"};

    private HashMap <String, Integer> keywordCount;


    @FXML
    void btnClick(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Java File", "*.java"));
        File f = fileChooser.showOpenDialog(null);
        List<String> fList = new ArrayList<String>();
        // Read from the selected file
        if (f != null) {
            Scanner reader = new Scanner(f);
            while (reader.hasNextLine()) {
                String text = reader.nextLine();
                text = text.replaceAll("[^\\w\\s]", " "); // get rid of all special characters
                String[] words = text.split(" ");
                fList.addAll(Arrays.asList(words));
            }
            // System.out.println(fList);
            updateListView(fList);
        }

    }


    public void updateListView(List<String> list) {
        // Update HashMap
        for (String word: list) {
            // System.out.println(word);
            if (keywordCount.containsKey(word))
                keywordCount.replace(word, keywordCount.get(word)+1);
        }
        // System.out.println(keywordCount);
        // Update ListView
        Iterator i = keywordCount.entrySet().iterator();
        while(i.hasNext()) {
            Map.Entry word = (Map.Entry)i.next();
            String frequency;
            if ((int)word.getValue() == 0) {
                frequency = " ";
            }
            else {
                frequency = String.valueOf(word.getValue());
            }
            listView.getItems().add(String.format("%-20s %150s ", word.getKey(), frequency));
            // System.out.printf("%s %40s%n", word.getKey(), frequency);
        }
    }

    public void initialize(URL location, ResourceBundle resource) {
        keywordCount = new HashMap<String, Integer>();
        for (String keyword: keywords) {
            keywordCount.put(keyword, 0);
        }
        listView.getItems().addAll(keywords);

    }
}
