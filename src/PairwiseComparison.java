import java.io.Serializable;
import java.util.*;
import org.apache.commons.math3.linear.Array2DRowRealMatrix;
import org.apache.commons.math3.linear.EigenDecomposition;
import org.apache.commons.math3.linear.RealVector;

public class PairwiseComparison implements Serializable{

    public String id;
    public List<String> parents;
    public List<String> alternatives;

    private double[][] weightRatios;
    public double[] weightVector;

    public void eigenvalueMethod() {
        weightVector = new double[alternatives.size()];
        Array2DRowRealMatrix matrix = new Array2DRowRealMatrix(weightRatios);
        EigenDecomposition eigen = new EigenDecomposition(matrix);
        int eigenIndex = 0;
        for (int k = 0; k < eigen.getRealEigenvalues().length; k++) {
            eigenIndex = (eigen.getRealEigenvalue(k) > eigen.getRealEigenvalue(eigenIndex)) ? k : eigenIndex;
        }

        double sum = 0.0;
        RealVector vector = eigen.getEigenvector(eigenIndex);
        for (double d : vector.toArray()) {
            sum += d;
        }
        for (int k = 0; k < vector.getDimension(); k++) {
            weightVector[k] = vector.getEntry(k) / sum;
        }
    }

    public void geometricMeanMethod(){
        weightVector = new double[alternatives.size()];
        double result;
        double sum = 0;
        for(int i=0; i<alternatives.size(); i++){
            result = 1;
            for(int j=0; j<alternatives.size(); j++){
                result = result * weightRatios[i][j];
            }
            sum = sum + Math.pow(result, 1.0/alternatives.size());
            weightVector[i] = Math.pow(result, 1.0/alternatives.size());
        }
        for(int i=0; i<alternatives.size(); i++){
            weightVector[i] = weightVector[i]/sum;
        }
    }

    public void addWeightRatios(){
        weightRatios = new double[alternatives.size()][alternatives.size()];
        System.out.println("Uzupełnianie macierzy porownań dla "+ id);
        int i = 0;
        int j = 0;
        int swap = 0;
        Scanner read = new Scanner(System.in);
        for(String alter1: alternatives){
            for(String alter2: alternatives){
                if(alter1.equals(alter2)){
                    weightRatios[i][j] = 1;
                } else if (weightRatios[i][j] == 0){
                    System.out.println("Co bardziej wolisz. Jeśli "+alter1+" kliknij 1, jeśli "+alter2+ " kliknij 2, jeśli tak samo to kliknij 3.");
                    swap = read.nextInt();
                    switch (swap){
                        case 1:
                            System.out.println("O ile bardziej wolisz "+alter1+" niż "+alter2);
                            weightRatios[i][j] = read.nextDouble();
                            weightRatios[j][i] = 1/(weightRatios[i][j]);
                            break;
                        case 2:
                            System.out.println("O ile bardziej wolisz "+alter2+" niż "+alter1);
                            weightRatios[j][i] = read.nextDouble();
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

    public void refactor(double factor){
        for (int i = 0; i<weightVector.length; i++){
            weightVector[i] = weightVector[i]*factor;
        }
    }

    public void addId(){
        System.out.println("Podaj id");
        Scanner read =  new Scanner(System.in);
        id = read.nextLine();
    }

    public void addParents() {
        System.out.println("Wprowadzanie rodzica");
        parents = new ArrayList<>();
        String character = "y";
        Scanner read = new Scanner(System.in);
        System.out.println("RODZIC: Jeśli chcesz podać rodzica kliknij y, jesli nie kliknij n");
        character = read.nextLine();
        switch (character) {
            case "y":
                System.out.println("Podaj rodzica");
                parents.add(read.nextLine());
                break;

            default:
                break;
        }
    }

    public void addAlternatives() {
        String alternativesInString;
        System.out.println("WPROWADZANIE ALTERNATYW\nWprowadź wszystkie alternatywy oddzielając je przecinkiem np. alt1,alt2,alt3");
        Scanner read = new Scanner(System.in);
        alternativesInString = read.nextLine();
        alternatives = new ArrayList<String>(Arrays.asList(alternativesInString.split(",")));
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
