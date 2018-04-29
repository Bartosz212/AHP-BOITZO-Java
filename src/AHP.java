import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;
import org.apache.commons.lang.SerializationUtils;

import java.io.*;
import java.util.*;

public class AHP {

    public String headId = "";
    public List<PairwiseComparison> comparisons = new ArrayList<>();
    public List<String> alternatives = new ArrayList<>();

    public void calculateAHPResult(){
        int i;
        double[] result = new double[alternatives.size()];
        List<PairwiseComparison> comparisonsCopy = new ArrayList<>();
        for(int j = 0; j<comparisons.size(); j++){
            comparisonsCopy.add(j, (PairwiseComparison) SerializationUtils.clone(comparisons.get(j)));
        }
        for (PairwiseComparison obj: comparisonsCopy) {
            i = 0;
            if(obj.alternatives.equals(alternatives)){
                for (int j = 0; j<result.length; j++){
                    result[j] = result[j] + obj.weightVector[j];
                }
            }else {
                for (String name : obj.alternatives) {
                    findByNameID(name, comparisonsCopy).refactor(obj.weightVector[i]);
                    i++;
                }
            }
        }
        for (int j = 0; j<result.length; j++) {
            System.out.println(alternatives.get(j)+ "  " + result[j]);
        }
    }

    public PairwiseComparison findByNameID(String nameID, List<PairwiseComparison> comparisonsList){
        for (PairwiseComparison temp: comparisonsList) {
            if (temp.id.equals(nameID)){
                return temp;
            }
        }
        return null;
    }

    public void addHeadId(){
        System.out.println("Podaj head ID");
        Scanner read = new Scanner(System.in);
        headId = read.nextLine();
    }

    public void addComparisons(){
        System.out.println("Uzupełnianie hierarchi AHP");
        boolean stop = false;
        String character = "y";
        Scanner read = new Scanner(System.in);
        while (!stop) {
            PairwiseComparison obj = new PairwiseComparison();
            System.out.println("HIERARCHIA: Jeśli chcesz nadal wprowadzać kliknij y, jesli nie kliknij n");
            character = read.nextLine();
            switch (character) {
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

    public void addMainAHPAlternatives(){
        String AHPalternatives = new String();
        System.out.println("WPROWADZANIE ALTERNATYW AHP\nWprowadź wszystkie alternatywy oddzielając je przecinkiem np. alt1,alt2,alt3");
        Scanner read = new Scanner(System.in);
        AHPalternatives = read.nextLine();
        alternatives = new ArrayList<String>(Arrays.asList(AHPalternatives.split(",")));
    }

    public void addWeight(){
        for(PairwiseComparison comparison: comparisons){
            comparison.addWeightRatios();
        }
    }

    public void calculateWeightVector(){
        Scanner read = new Scanner(System.in);
        System.out.println("Wybierz metodę obliczania wag.\n1 - Metoda średnich geometrycznych\n2 - Metoda wektorów własnych");
        int choose;
        choose = read.nextInt();
        switch (choose) {
            case 1:
                for (PairwiseComparison comp : comparisons) {
                    comp.geometricMeanMethod();
                }
                break;

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

