import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.util.*;

public class AHP {

    public String headId = "";
    public List<PairwiseComparison> comparisons = new ArrayList<>();
    public List<String> alternatives = new ArrayList<>();

    public void addHeadId(){
        System.out.println("Podaj head ID");
        Scanner odczyt =  new Scanner(System.in);
        headId =  odczyt.nextLine();
    }

    public void addComparisons(){
        System.out.println("Uzupełnianie hierarchi AHP");
        boolean stop = false;
        String znak = "y";
        Scanner odczyt = new Scanner(System.in);
        while (!stop) {
            PairwiseComparison obj = new PairwiseComparison();
            System.out.println("HIERARCHIA: Jeśli chcesz nadal wprowadzać kliknij y, jesli nie kliknij n");
            znak = odczyt.nextLine();
            switch (znak) {
                case "y":
                    obj.addToPairwise();
                    comparisons.add(obj);
                    break;

                default:
                    stop = true;
                    break;
            }
        }
    }

    public void addAlternatives(){
        System.out.println("Uzupełnianie alternatyw AHP");
        boolean stop = false;
        String znak = "y";
        Scanner odczyt = new Scanner(System.in);
        while (!stop) {
            System.out.println("AHP-ALTERNATYWY: Jeśli chcesz nadal wprowadzać kliknij y, jesli nie kliknij n");
            znak = odczyt.nextLine();
            switch (znak) {
                case "y":
                    System.out.println("Wprowadź alternatywe");
                    alternatives.add(odczyt.nextLine());
                    break;

                default:
                    stop = true;
                    break;
            }
        }
    }

    public void addWeight(){
        for(PairwiseComparison comparison: comparisons){
            comparison.addWeightRatios();
        }
    }

    public void calculateWeightVector(){
        Scanner odczyt = new Scanner(System.in);
        System.out.println("Wybierz metodę obliczania wag.\n1 - Metoda średnich geometrycznych\n2 -  Metoda wektorów własnych");
        int wyb;
        wyb = odczyt.nextInt();
        switch (wyb) {
            case 1:
                for (PairwiseComparison comp : comparisons) {
                    comp.geometricMeanMethod();
                }
            case 2:
                for (PairwiseComparison comp : comparisons) {
                    comp.eigenvalueMethod();
                }
                break;

            default:
                break;
        }
    }

    public static void saveToJson() {
        if (Main.currentFile == null) {
            return;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (Writer writer = new FileWriter(Main.currentFile)) {
            gson.toJson(Main.AHP, AHP.class, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadFromJson() {
        try {
            JsonReader reader = new JsonReader(new FileReader(Main.currentFile));
            Main.AHP = new Gson().fromJson(reader, AHP.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

