import java.util.*;
public class PairwiseComparison {

    public String id;
    public List<String> parents;
    public List<String> alternatives;

    private double[][] weightRatios;
    private double[] weightVector;

    public void addWeightRatios(){
        weightRatios = new double[alternatives.size()][alternatives.size()];
        System.out.println("Uzupełnianie macierzy porownań dla "+ id);
        int i = 0;
        int j = 0;
        int swap = 0;
        Scanner odczyt = new Scanner(System.in);
        for(String alter1: alternatives){
            for(String alter2: alternatives){
                if(alter1.equals(alter2)){
                    weightRatios[i][j] = 1;
                } else if (weightRatios[i][j] == 0){
                    System.out.println("Co bardziej wolisz. Jeśli "+alter1+" kliknij 1, jeśli "+alter2+ " kliknij 2, jeśli tak samo to kliknij 3.");
                    swap = odczyt.nextInt();
                    switch (swap){
                        case 1:
                            System.out.println("O ile bardziej wolisz "+alter1+" niż "+alter2);
                            weightRatios[i][j] = odczyt.nextDouble();
                            weightRatios[j][i] = 1/(weightRatios[i][j]);
                            break;
                        case 2:
                            System.out.println("O ile bardziej wolisz "+alter2+" niż "+alter1);
                            weightRatios[j][i] = odczyt.nextDouble();
                            weightRatios[i][j] = 1/(weightRatios[j][i]);
                            break;
                        case 3:
                            weightRatios[i][j] = 1;
                            weightRatios[j][i] = 1;
                            break;
                        default:
                            break;
                    }
                }
                j++;
            }
            i++;
            j = 0;
        }
    }

    public void addId(){
        System.out.println("Podaj id");
        Scanner odczyt =  new Scanner(System.in);
        id = odczyt.nextLine();
    }

    public void addParents() {
        System.out.println("Wprowadzanie rodziców");
        parents = new ArrayList<>();
        boolean stop = false;
        String znak = "y";
        Scanner odczyt = new Scanner(System.in);
        while (!stop) {
            System.out.println("RODZICE: Jeśli chcesz nadal wprowadzać kliknij y, jesli nie kliknij n");
            znak = odczyt.nextLine();
            switch (znak) {
                case "y":
                    System.out.println("Podaj rodzica");
                    parents.add(odczyt.nextLine());
                    break;

                default:
                    stop = true;
                    break;
            }
        }
    }
    public void addAlternatives() {
        System.out.println("Wprowadzanie alternatyw");
        alternatives = new ArrayList<>();
        boolean stop = false;
        String znak = "y";
        Scanner odczyt = new Scanner(System.in);
        while (!stop) {
            System.out.println("ALTERNATYWY: Jeśli chcesz nadal wprowadzać kliknij y, jesli nie kliknij n");
            znak = odczyt.nextLine();
            switch (znak) {
                case "y":
                    System.out.println("Podaj alternatywe");
                    alternatives.add(odczyt.nextLine());
                    break;

                default:
                    stop = true;
                    break;
            }
        }
    }

    public void addToPairwise(){
        addId();
        addParents();
        addAlternatives();
    }
    public static PairwiseComparison get(String id){
        for( PairwiseComparison comparison : Main.AHP.comparisons ){
            if( comparison.id.equals(id)){
                return comparison;
            }
        }
        return null;
    }
}
