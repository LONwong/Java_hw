import java.text.DecimalFormat;

/** This is a class to define Pet objects. Pets should be compared 
according to their owner's names, ignoring capitalization. Ties
should be broken based on the pet's name, ignoring capitalization.

Your job is to add the necessary data and methods to support the
P3main program, as well as the related classes in this system. Some
required methods are noted below with comments, but these are not the
only things you will need.
*/
public class Pet {

   /** Handy for formatting. */
   private static DecimalFormat money = new DecimalFormat("0.00");

   /* The access specifiers for these variables must not be changed! */

   private String name;
   private String owner;
   private double weight;


   /** Create a Pet object, initializing data members.
    *  @param pname the Pet's name
    *  @param oname the owner's name
    *  @param wt the weight of the pet
    */
   public Pet(String pname, String oname, double wt) {

   }

   @Override
   public String toString() {
      return this.name + " (owner " + this.owner + ") " + this.weight
          + " lbs, $" + money.format(this.avgCost()) + " avg cost/visit  ";
   }

   /** The Pet is visiting the vet, and will be charged accordingly.
    *  The base cost for a visit is $85.00, and $30/shot is added.
    *  @param shots the number of shots the pet is getting
    *  @return the entire cost for this particular visit
    */
   public double visit(int shots) {

   }

   /** Determine the average cost per visit for this pet.
    *  @return that cost, or 0 if no visits have occurred yet
    */
   public double avgCost() {

   }


}
