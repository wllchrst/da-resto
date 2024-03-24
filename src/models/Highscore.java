package models;
import java.io.*;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class Highscore {
    private String fileName = "src/highscore.txt";
    Vector<String> restaurantNames;
    Vector<Integer> scores;

    private Highscore(){
        restaurantNames = new Vector<>();
        scores = new Vector<>();
        readFile();
    }

    public void endGameWrite(String name, int score){
        File file = new File(fileName);
        try {
            FileWriter writer = new FileWriter(file, true);
            writer.write(String.format("%s#%d\n", name, score));
            writer.close();
        } catch (IOException e) {}
    }

    public void sort(){
        int n = scores.size();

        for (int i = 0; i < n-1; i++)  {
            // Find the minimum element in unsorted array
            int max = i;
            for (int j = i+1; j < n; j++)
                if (scores.get(j) > scores.get(max))
                    max = j;

            Collections.swap(scores, max, i);
            Collections.swap(restaurantNames, max, i);
        }
    }

    public void showHighscore(){
        sort();
        System.out.printf("%-10s %-20s %-10s\n", "rank", "Restaurant's Name", "Score");
        for(int i = 0; i < scores.size(); i++){
            System.out.printf("%-10d %-20s %-10d\n", i + 1, restaurantNames.get(i), scores.get(i));
        }
    }

    public void showHighscoreAfterGame(String name, int score){
        sort();
        int index = scores.size();
        if(score > scores.get(index - 1)) showHighscore(name, score);
        else showHighscore();
    }

    public void showHighscore(String name, int score){
        scores.add(score);
        restaurantNames.add(name);

        sort();
        for(int i = 0; i < scores.size(); i++){
            if(restaurantNames.get(i).equals(name)) {
                System.out.printf(">>> %-10d %-20s %-10d <<<\n", i + 1, restaurantNames.get(i), scores.get(i));
            }
            System.out.printf("%-10d %-20s %-10d\n", i + 1, restaurantNames.get(i), scores.get(i));
        }

    }

    public void readFile(){
        File file = new File(fileName);
        try {
            Scanner reader = new Scanner(file);
            String line, name;
            int score;
            while(reader.hasNextLine()){
                line = reader.nextLine();
                name = line.split("#")[0];
                score = Integer.parseInt(line.split("#")[1]);

                restaurantNames.add(name);
                scores.add(score);
            }
        } catch (FileNotFoundException e) {}
    }

    public static Highscore highscore = null;

    public static Highscore getHighscore (){
        if(highscore == null) {
            synchronized (Highscore.class) {
                if(highscore == null) {
                    highscore = new Highscore();
                }
            }
        }
        return highscore;
    }

    public Vector<String> getRestaurantNames() {
        return restaurantNames;
    }

    public void setRestaurantNames(Vector<String> restaurantNames) {
        this.restaurantNames = restaurantNames;
    }

    public Vector<Integer> getScores() {
        return scores;
    }

    public void setScores(Vector<Integer> scores) {
        this.scores = scores;
    }
}
