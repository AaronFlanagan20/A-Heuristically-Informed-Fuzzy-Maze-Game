package ie.gmit.sw.fuzzylogic;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 * Fighting.java is the class that handles the fuzzy logic. It takes in the strength of the players weapon and enemy and fires the fighting.fcl
 * file in the fcl folder to determine how much damage will be dealt.
 * 
 * The JFuzzyLogic API is used for this section. It makes use of it's fuzzy constraint language to execute rules based on strength and determine the damage
 * This is done in the fcl/fighting.fcl file within this project
 * 
 * @author Aaron
 */
public class Fighting {
	
	public static double fight(int weaponStrength, int enemyStrength){
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
        fis.setVariable("weapon", weaponStrength);
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
