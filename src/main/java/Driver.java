import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
    static List<List<String>> parseInputFileMacOS(Integer variantNo, String srcFile){
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

        }catch (IOException e) {
            e.printStackTrace();
        }

        try {
            return cleanCSV.get(variantNo);
        }catch (IndexOutOfBoundsException e){
            return cleanCSV.get(0);
        }

    }
}
