import org.apache.commons.math3.special.Erf;
import org.apache.commons.math3.special.Gamma;

import java.util.HashMap;
import java.util.List;

public class Distributions {
    private static  HashMap<String, Double> msExp(Double lambda){
        Double m = 1/lambda;
        Double sigma = 1/lambda;
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }
    private static  HashMap<String, Double> msUniform(Double a, Double b){
        Double sigma = (b - a) / (2 * Math.sqrt(3));
        Double m = (a + b) / 2;
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }
    private static  HashMap<String, Double> msGamma(Double alpha, Double beta){

        Double sigma = Math.sqrt(alpha * beta);

        Double m = alpha * beta;

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }
    private static  HashMap<String, Double> msTruncatedNormal(Double m0, Double sigma0){

        Double c = 1 /(0.5 + Erf.erf(m0/sigma0));

        Double k = (c/Math.sqrt(2 * Math.PI)) * Math.exp(-1 * m0 * m0 / (2 *sigma0 * sigma0) );
        Double sigma = sigma0 * Math.sqrt(1 + k * (m0/sigma0) - k*k);

        Double m = m0 + k *sigma0;

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }

    private static  HashMap<String, Double> msRayleigh(double lambda){
        Double m = Math.sqrt(Math.PI/(4*lambda));
        Double sigma = Math.sqrt((4 - Math.PI)/4*lambda);
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }
    private static  HashMap<String, Double> msWeibull(Double alpha, Double beta){
        Double sigma = beta * Math.sqrt( Gamma.gamma(1 + 2/alpha) - Math.pow(Gamma.gamma(1 + 1/alpha),2.0));

        Double m = beta * Gamma.gamma(1 + 1/alpha);

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m);
        x.put(Keys.SIGMA.getValue(), sigma);
        return x;
    }
    private static  HashMap<String, Double> msNormal(Double m0, Double sigma0){
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(), m0);
        x.put(Keys.SIGMA.getValue(), sigma0);
        return x;
    }




    private static  HashMap<String, Double> exp(Double[] args){
        Double lambda = args[0], t = args[1];

        Double ft = lambda * Math.exp(-1*lambda*t);
        Double pt = Math.exp(-1 * lambda * t);
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),ft);
        x.put(Keys.PT.getValue(), pt);

        return x;
    }
    private static  HashMap<String, Double> uniform(Double args[]){
        Double a = args[0], b = args[1], t = args[2];

        double ft = 0.0;
        double pt = 0.;
        if(a <= t && t <= b){
            ft = 1 / (b - a);
            pt = (b-t)/(b-a);
        }
        if(t < a)
            pt = 1.;
        if(t > b)
            pt = 0.;

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),ft);
        x.put(Keys.PT.getValue(), pt);

        return x;
    }
    private static  HashMap<String,Double> gamma(Double[] args){
        Double alpha = args[0], beta = args[1], t = args[2];

        Double ft = (Math.pow(t, alpha - 1)/ (Math.pow(beta, alpha) * Gamma.gamma(alpha))) * Math.exp(-t/beta);
        Double pt = 1.0 - Gamma.regularizedGammaP(alpha, t/beta);

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),ft);
        x.put(Keys.PT.getValue(), pt);

        return x;
    }

    private static  HashMap<String, Double> truncatedNormal(Double[] args){
        Double m = args[0], sigma = args[1], t = args[2];

        Double c = 1 /(0.5 + Erf.erf(m/sigma));
        HashMap<String,Double> normalDis = normal(new Double[] {m,sigma,t});
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(), c/normalDis.get(Keys.FT.getValue()));
        x.put(Keys.PT.getValue(), c * normalDis.get(Keys.PT.getValue()));
        return x;
    }

    private static  HashMap<String, Double> rayleigh(Double[] args){
        Double lambda = args[0], t = args[1];

        Double ft = 2*lambda*t*Math.exp(-1*lambda*Math.pow(t,2.0));
        Double pt = Math.exp(-1 * lambda * Math.pow(t,2.0));
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),ft);
        x.put(Keys.PT.getValue(), pt);
        return x;
    }
    private static  HashMap<String, Double> weibull(Double[] args){
        Double alpha =  args[0], beta = args[1], t = args[2];

        Double ft, pt;
        pt = Math.exp(-(Math.pow(t/beta, alpha)));
        ft = ((alpha * Math.pow(t, alpha - 1))/Math.pow(beta, alpha))* pt;

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(), ft);
        x.put(Keys.PT.getValue(), pt);
        return x;
    }
    private static  HashMap<String, Double> normal(Double[] args){
        Double m = args[0]
                ,sigma = args[1]
                ,t = args[2];

        Double ft = (1.0/(sigma * Math.sqrt(2 * Math.PI))) * Math.exp(-1 * Math.pow(t - m,2.0)/(2 * Math.pow(sigma, 2.0)));
        Double pt = 0.5 - Erf.erf((t - m)/sigma);
        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),ft);
        x.put(Keys.PT.getValue(), pt);
        return x;
    }
    private static Double [] getArgs(List<String> task, Double t){
        Double[] args = new Double[task.size()];
        int i = 1;
        //System.out.println(task);
        for(; i < task.size(); ++ i){
            args[i -1] = Double.parseDouble(task.get(i));
        }
        args[i - 1] = t;
        return args;
    }
    private static Double [] getArgs(List<String> task){
        Double[] args = new Double[task.size()];
        int i = 1;
        for(; i < task.size(); ++ i){
            args[i -1] = Double.parseDouble(task.get(i));
        }
        return args;
    }
    static HashMap<String, Double> getFtPt(List<String> task, Double t){

        if (task.get(0).equals(Keys.EXPONENTIAL.getValue()))
                return exp(getArgs(task, t));
        if (task.get(0).equals(Keys.RAYLEIGH.getValue()))
            return rayleigh(getArgs(task, t));
        if (task.get(0).equals(Keys.GAMMA.getValue()))
            return gamma(getArgs(task, t));
        if (task.get(0).equals(Keys.WEIBULL.getValue()))
            return weibull(getArgs(task, t));
        if (task.get(0).equals(Keys.TRUNCATED_NORMAL.getValue()))
            return truncatedNormal(getArgs(task, t));
        if (task.get(0).equals(Keys.NORMAL.getValue()))
            return normal(getArgs(task, t));
        if (task.get(0).equals(Keys.U.getValue()))
            return uniform(getArgs(task, t));

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.FT.getValue(),0.0);
        x.put(Keys.PT.getValue(), 0.0);
        return x;
    }
    static HashMap<String, Double> getMSigma(List<String> task){
        Double [] args = getArgs(task);
        if (task.get(0).equals(Keys.EXPONENTIAL.getValue()))
            return msExp(args[0]);
        if (task.get(0).equals(Keys.RAYLEIGH.getValue()))
            return msRayleigh(args[0]);
        if (task.get(0).equals(Keys.GAMMA.getValue()))
            return msGamma(args[0], args[1]);
        if (task.get(0).equals(Keys.WEIBULL.getValue()))
            return msWeibull(args[0], args[1]);
        if (task.get(0).equals(Keys.TRUNCATED_NORMAL.getValue()))
            return msTruncatedNormal(args[0], args[1]);
        if (task.get(0).equals(Keys.NORMAL.getValue()))
            return msNormal(args[0], args[1]);
        if (task.get(0).equals(Keys.U.getValue()))
            return msUniform(args[0], args[1]);

        HashMap<String, Double> x = new HashMap<>();
        x.put(Keys.M.getValue(),0.0);
        x.put(Keys.SIGMA.getValue(), 0.0);
        return x;
    }
}
