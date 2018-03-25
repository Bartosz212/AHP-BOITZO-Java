import java.io.File;

public class Main {
    public static File currentFile;
    public static AHP AHP = new AHP();

    public static void main(String[] args) {
        currentFile = new File("AHP-wynik");
        AHP.loadFromJson();
        AHP.calculateWeightVector();
        AHP.saveToJson();
    }

    public static void addJsonHierarchy(){
        AHP.addHeadId();
        AHP.addComparisons();
        AHP.addAlternatives();
    }

}