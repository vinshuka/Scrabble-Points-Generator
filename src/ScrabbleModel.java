import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.util.ArrayList;
import java.util.HashMap;

public class ScrabbleModel
{
    //instance variables
     private int score = 0;
     private String error = "";
     public ArrayList<String> wordList = new ArrayList<>();

     //process runs all necessary methods to validate, calculate score, remove letters from the bag, add the word to history, and check for game over
     public void process(String word, HashMap<Character, Integer> bag, HashMap<Character, Integer> points)
     {
        try
        {
            HashMap<Character, Integer> wordMap = changeToHashMap(word);
            validate(word, wordMap, bag);
            setScore(calculateScore(wordMap, points));
            removeFromBag(wordMap, bag);
            wordList.add(word);
            checkGameOver(bag);
        }
        catch(Exception e)
        {
            error = e.getMessage();
        }
     }

     //takes the submitted word and puts it into a hashmap, Key : Letter , Value : number of that letter in the word
    //catches if the user tries to submit a blank word as this method cannot work with a blank string
    private HashMap<Character, Integer> changeToHashMap(String word) throws Exception
    {
        if(word.length() == 0)
        {
            throw new Exception("Word cannot be blank");
        }
        setError("");
        word = word.toUpperCase();
        HashMap<Character, Integer> wordMap = new HashMap<>();
        for(int i = 0; i < word.length(); i++)
        {
            if(!wordMap.containsKey(word.charAt(i)))
            {
                wordMap.put(word.charAt(i), 1);
            }
            else
            {
               int value = wordMap.get(word.charAt(i));
               wordMap.put(word.charAt(i), value + 1);
            }
        }
        return wordMap;
    }

    //validate runs all of the check helper methods
    private void validate(String word,HashMap<Character, Integer> wordMap, HashMap<Character, Integer> bag) throws Exception
    {
        checkDuplicate(word);
        checkLength(word);
        checkVowels(wordMap);
        checkInBag(wordMap, bag);
    }

    //check length compares the word to the min and max character limits and gives a specific error based on which on it fails
    private void checkLength(String word) throws Exception
    {
        if(word.length() < 2)
        {
            throw new Exception("Word is too short");
        }
        else if(word.length() > 8)
        {
            throw new Exception("Word is too long");
        }
        //here the error text is reset if the word passes
        setError("");
    }

    //check in bag makes sure the user has enough letter in the bag to submit the word, gives an error if they don't
    private void checkInBag(HashMap<Character, Integer> wordMap, HashMap<Character, Integer> bag) throws Exception
    {
        //for each key in our word hashmap
        for (HashMap.Entry<Character, Integer> entry : wordMap.entrySet())
        {
            //we check if the number of the same letter in the word exceeds the number left in the bag
            if(bag.get(entry.getKey()) < entry.getValue())
            {
                throw new Exception("There are not enough letters in the bag");
            }
        }
        setError("");
    }

    //check vowels makes sure the word contains a vowel
    private void checkVowels(HashMap<Character, Integer> wordMap) throws Exception
    {

        char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};
        for(char c : vowels)
        {
            if(wordMap.containsKey(c))
            {
                return;
            }
        }
        throw new Exception("Word must contain a vowel");
    }

    //check duplicate makes sure the user isn't trying to submit the same word again
    private void checkDuplicate(String word) throws Exception
    {
        if(wordList.contains(word))
        {
            throw new Exception("Word has already been submitted");
        }
    }

    //calculate score gets the right point value from the points map, multiples it by the number of the
    //same letter in the word and adds it to the score
    private int calculateScore(HashMap<Character, Integer> wordMap, HashMap<Character, Integer> points)
    {
        int calculatedScore = 0;
        for (HashMap.Entry<Character, Integer> entry : wordMap.entrySet())
        {
            calculatedScore += points.get(entry.getKey()) * entry.getValue();
        }
        return calculatedScore;
    }

    //set score lets us add the value to the existing score
    private void setScore(int value)
    {
        score += value;
    }

    //clears the score for the reset method
    public void clearScore()
    {
        score = 0;
    }

    //getter for score
    public int getScore()
    {
        return score;
    }

    //setter for error
    private void setError(String err)
    {
        this.error = err;
    }

    //getter for error
    public String getError()
    {
        return error;
    }

    //addToWordHistory creates a string to put into our previous word text area, prevents the trailing ','
    public String addToWordHistory()
    {
        String words =  "";
        for(int i = 0; i < wordList.size(); i++)
        {
            if(i+1 < wordList.size())
            {
                words += wordList.get(i) + ", ";
            }
            else
            {
                words += wordList.get(i);
            }
        }
        return words;
    }

    //removeFromBag, removes the letters in the submitted word from the bag
    private void removeFromBag(HashMap<Character, Integer> wordMap, HashMap<Character, Integer> bag)
    {
        for (HashMap.Entry<Character, Integer> entry : wordMap.entrySet())
        {
            int value = bag.get(entry.getKey());
            bag.put(entry.getKey(), value - entry.getValue());
        }
    }

    //setButton sets the state of our letter buttons and updates the letter count labels, this is a helper method
    private void setLabelAndButton(HashMap<Character, Integer> bag, Button btn, Label lb, char letter)
    {
        lb.setText(String.valueOf(bag.get(letter)));
        if(bag.get(letter) == 0)
        {
            btn.setDisable(true);
        }
        else
        {
            btn.setDisable(false);
        }
    }

    //setLetters sets the state of all buttons and labels at the same time
    public void setLetters(HashMap<Character, Integer> bag, HashMap<Character, Button> button, HashMap<Character, Label> label)
    {
        for (HashMap.Entry<Character, Integer> entry : bag.entrySet())
        {
           setLabelAndButton(bag, button.get(entry.getKey()), label.get(entry.getKey()), entry.getKey());
        }
    }

    //checkVowelsRemaining is a helper method for checkGameOver
    private void checkVowelsRemaining(HashMap<Character, Integer> bag) throws Exception
    {
        char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};
        int vowelTotal = 0;
        //adds up the total number of vowels, if 0 there are no more vowels left
        for (char c : vowels)
        {
            vowelTotal += bag.get(c);
        }
        if(vowelTotal == 0)
        {
            throw new Exception("GAME OVER");
        }
    }
    //checkLettersRemaining is a helper method for checkGameOver
    private void checkLettersRemaining(HashMap<Character, Integer> bag) throws Exception
    {
        int letterTotal = 0;
        //adds up all the letters in the back, if <= 1 there are not enough letters left
        for (HashMap.Entry<Character, Integer> entry : bag.entrySet())
        {
            letterTotal += entry.getValue();
        }
        if(letterTotal <= 1)
        {
            throw new Exception("GAME OVER");
        }
    }

    //checkGameOver runs both game over condition methods
    public void checkGameOver(HashMap<Character, Integer> bag) throws Exception
    {
        checkVowelsRemaining(bag);
        checkLettersRemaining(bag);
    }

    //clearWordList clears the word history for the reset method
    public void clearWordList()
    {
        wordList.clear();
    }

}
