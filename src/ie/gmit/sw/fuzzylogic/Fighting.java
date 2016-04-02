package ie.gmit.sw.fuzzylogic;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

public class Fighting {
	
	public static double fight(int waeponStrength, int enemyStrength){
		// Load from 'FCL' file
        String fileName = "fcl/fighting.fcl";
        FIS fis = FIS.load(fileName,true);

        // Error while loading?
        if( fis == null ) { 
            System.err.println("Can't load file: '" + fileName + "'");
        }
        
        FunctionBlock functionBlock = fis.getFunctionBlock("fight");
        
        // Show 
        //JFuzzyChart.get().chart(functionBlock);

        // Set inputs
        fis.setVariable("weapon", waeponStrength);
        fis.setVariable("opponent", enemyStrength);

        // Evaluate
        fis.evaluate();

        // Show output variable's chart
        Variable damage = functionBlock.getVariable("damage");
        //System.out.println(damage.getDefaultValue());
        //System.out.println("Value: " + damage.getValue());
        //JFuzzyChart.get().chart(damage, damage.getDefuzzifier(), true);
       
        // Print ruleSet
        //System.out.println(fis);
        
        return damage.getValue();
	}
	
	public static void main(String[] args) {
		double damage = Fighting.fight(3, 5);
		System.out.println(damage);
	}
}
