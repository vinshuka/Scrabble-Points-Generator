import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.HashMap;

public class ScrabbleController
{
    @FXML
    Label noA, noB, noC, noD, noE, noF, noG, noH, noI, noJ, noK, noL, noM, noN, noO, noP, noQ, noR, noS, noT, noU, noV, noW, noX, noY, noZ, error, pointValue;
    @FXML
    TextField textWord;
    @FXML
    TextArea prevWord;
    @FXML
    Button btnA, btnB, btnC, btnD, btnE, btnF, btnG, btnH, btnI, btnJ, btnK, btnL, btnM, btnN, btnO, btnP, btnQ, btnR, btnS, btnT, btnU, btnV, btnW, btnX, btnY, btnZ;

    private ScrabbleModel model = new ScrabbleModel();
    private HashMap<Character, Integer> inBag = new HashMap<>();
    final HashMap<Character, Integer> points = new HashMap<>();
    private HashMap<Character, Button> buttons = new HashMap<>();
    private HashMap<Character, Label> labels = new HashMap<>();

    @FXML
    private void btnSubmit(ActionEvent event)
    {
        /*
        btnSubmit grabs the submitted word, processes it, displays any given error,
        sets the current score, adds the word to the previously submitted words,
        disables any letter button if needed, then clears the word textfield
         */
        String word = textWord.getText();
        model.process(word, inBag, points);
        error.setText(model.getError());
        pointValue.setText(String.valueOf(model.getScore()));
        prevWord.setText(model.addToWordHistory());
        model.setLetters(inBag, buttons, labels);
        textWord.clear();
    }

    @FXML
    private void typeButton(ActionEvent event)
    {
        //typeButton allows the user to type the word with the gui controls,
        //it does this by grabbing the button text and concatenating it with the current text in the text field
        String letter = ((Button)event.getSource()).getText();
        textWord.setText(textWord.getText() + letter);
    }

    @FXML
    private void reset(ActionEvent event)
    {
        /*
        reset returns the app to its original state, resets the letters in the bag,
        re-enables buttons, resets the point total, error text, the word text field,
        and the previous words, then clears the old score from memory
         */
        initialize();
        model.setLetters(inBag, buttons, labels);
        pointValue.setText("0");
        error.setText("");
        textWord.clear();
        model.clearWordList();
        prevWord.clear();
        model.clearScore();
    }

    @FXML
    private void initialize()
    {
        //initial set up of the letters in the bag
        inBag.put('A', 9);
        inBag.put('B', 2);
        inBag.put('C', 2);
        inBag.put('D', 4);
        inBag.put('E', 12);
        inBag.put('F', 2);
        inBag.put('G', 3);
        inBag.put('H', 2);
        inBag.put('I', 8);
        inBag.put('J', 1);
        inBag.put('K', 1);
        inBag.put('L', 4);
        inBag.put('M', 2);
        inBag.put('N', 6);
        inBag.put('O', 8);
        inBag.put('P', 2);
        inBag.put('Q', 1);
        inBag.put('R', 6);
        inBag.put('S', 4);
        inBag.put('T', 6);
        inBag.put('U', 4);
        inBag.put('V', 2);
        inBag.put('W', 2);
        inBag.put('X', 1);
        inBag.put('Y', 2);
        inBag.put('Z', 1);

        //initialize the point values
        points.put('A', 1);
        points.put('B', 3);
        points.put('C', 3);
        points.put('D', 2);
        points.put('E', 1);
        points.put('F', 4);
        points.put('G', 2);
        points.put('H', 4);
        points.put('I', 1);
        points.put('J', 8);
        points.put('K', 5);
        points.put('L', 1);
        points.put('M', 3);
        points.put('N', 1);
        points.put('O', 1);
        points.put('P', 3);
        points.put('Q', 10);
        points.put('R', 1);
        points.put('S', 1);
        points.put('T', 1);
        points.put('U', 1);
        points.put('V', 4);
        points.put('W', 4);
        points.put('X', 8);
        points.put('Y', 4);
        points.put('Z', 10);

        //initialize the button map
        buttons.put('A', btnA);
        buttons.put('B', btnB);
        buttons.put('C', btnC);
        buttons.put('D', btnD);
        buttons.put('E', btnE);
        buttons.put('F', btnF);
        buttons.put('G', btnG);
        buttons.put('H', btnH);
        buttons.put('I', btnI);
        buttons.put('J', btnJ);
        buttons.put('K', btnK);
        buttons.put('L', btnL);
        buttons.put('M', btnM);
        buttons.put('N', btnN);
        buttons.put('O', btnO);
        buttons.put('P', btnP);
        buttons.put('Q', btnQ);
        buttons.put('R', btnR);
        buttons.put('S', btnS);
        buttons.put('T', btnT);
        buttons.put('U', btnU);
        buttons.put('V', btnV);
        buttons.put('W', btnW);
        buttons.put('X', btnX);
        buttons.put('Y', btnY);
        buttons.put('Z', btnZ);

        //initialize label map
        labels.put('A', noA);
        labels.put('B', noB);
        labels.put('C', noC);
        labels.put('D', noD);
        labels.put('E', noE);
        labels.put('F', noF);
        labels.put('G', noG);
        labels.put('H', noH);
        labels.put('I', noI);
        labels.put('J', noJ);
        labels.put('K', noK);
        labels.put('L', noL);
        labels.put('M', noM);
        labels.put('N', noN);
        labels.put('O', noO);
        labels.put('P', noP);
        labels.put('Q', noQ);
        labels.put('R', noR);
        labels.put('S', noS);
        labels.put('T', noT);
        labels.put('U', noU);
        labels.put('V', noV);
        labels.put('W', noW);
        labels.put('X', noX);
        labels.put('Y', noY);
        labels.put('Z', noZ);
    }

}
