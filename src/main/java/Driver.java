import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    Double shag;

    public Driver(Integer variant, Double h){
        shag = h;
        List<Double> ft = new ArrayList<>(), pt = new ArrayList<>();
        //variant 13 13	R(5∙10-8)	R(7∙10-8)	N(0.15,19)	Exp(0.001)	TN(1000, 110)


        Distributions ds = new Distributions();
        Double lambda = 5 * Math.pow(10, -8);
        Double m = 0.15;
        Double sigma = 19.;

        for(Double t = 0.; t <= 3000; t += shag){
            Double [] params = new Double[] {m,sigma, t};
            ft.add(ds.normal(params).get(Keys.FT.getValue()));
            pt.add(ds.normal(params).get(Keys.PT.getValue()));
        }
    }
    public static List<List<String>> parseInputFileMacOS(Integer variantNo, String srcFile){
        List<List<List<String>>> cleanCSV = new ArrayList<>();

        try(BufferedReader variants = new BufferedReader(new FileReader(srcFile))) {
            String line;
            while ( (line = variants.readLine()) != null){
                List <String> variant = Arrays.asList(line.replaceAll(" ","").split(";"));
                List<List<String>> tasks = new ArrayList<>();

                for (int i = 1; i < variant.size(); ++i ) {
                    String s = variant.get(i);
                    String[] parsed = s.trim().replaceAll("[)]", "").split("[( | ,]");

                    if(s.contains("∙")){
                        String[] xed = s.split("[∙|-]");
                        Double index = Double.parseDouble(xed[2].replaceAll("[)]", ""));
                        Double base = Double.parseDouble(xed[1]);
                        String [] stR = xed[0].split("[(]");
                        Double val = Double.parseDouble(stR[1]);
                        Double value = val * Math.pow(base, - index);
                        parsed = new String[] {stR[0], value.toString()};
                    }
                    List<String> fin = new ArrayList<>();

                    tasks.add(Arrays.asList(parsed));

                }
                cleanCSV.add(tasks);
            }

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return cleanCSV.get(variantNo);
        }catch (IndexOutOfBoundsException e){
            return cleanCSV.get(0);
        }

    }
}
