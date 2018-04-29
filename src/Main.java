import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static File currentFile;
    public static AHP AHP = new AHP();

    public static void main(String[] args) {
        currentFile = new File("test4alternatywy.json");
        boolean stop = false;
        int choose;
        Scanner read = new Scanner(System.in);
        if(!currentFile.exists()){
            try {
                currentFile.createNewFile();
                Main.addJsonHierarchy();
                AHP.saveToJson();
                Main.AHPResult();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            if(currentFile.length() == 0){
                Main.addJsonHierarchy();
                AHP.saveToJson();
                Main.AHPResult();
            }else {
                AHP.loadFromJson();
                while(!stop) {
                    System.out.println("\nCo chcesz zrobić?\n1 - Dodanie kryteriów\n2 - Dodanie alternatyw AHP\n3 - Dodanie wag\n4 - Przeliczenie AHP\n5 - Wyjscie");
                    choose = read.nextInt();
                    switch (choose) {
                        case 1:
                            AHP.addComparisons();
                            AHP.saveToJson();
                            break;
                        case 2:
                            AHP.addMainAHPAlternatives();
                            AHP.saveToJson();
                            break;
                        case 3:
                            AHP.addWeight();
                            AHP.saveToJson();
                            break;
                        case 4:
                            Main.AHPResult();
                            break;
                        default:
                            stop = true;
                            break;
                    }
                }
            }
        }
        AHP.saveToJson();
    }

    public static void addJsonHierarchy(){
        AHP.addHeadId();
        AHP.addComparisons();
        AHP.addMainAHPAlternatives();
        AHP.addWeight();
    }

    public static void AHPResult(){
        AHP.calculateWeightVector();
        AHP.calculateAHPResult();
    }

}