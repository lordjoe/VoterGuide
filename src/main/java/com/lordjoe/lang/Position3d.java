package com.lordjoe.lang;


/**
 * immutable class representing a position
 * Position in cm used by robots - note there
 * is a constructor witn a single argument for shuttles
 */
public class Position3d implements IState {

    public static final Position3d ZERO = new Position3d(0,0,0);



    public final double X;
    public final double Y;
    public final double Z;

    /**
     * build a 3d position
     * @param xpos
     * @param ypos
     * @param zpos
     */
    public Position3d(double xpos,double ypos,double zpos)
    {
        X = xpos;
        Y = ypos;
        Z = zpos;
    }

    /**
     * build a 3d position
     * @param inp string x,y,x
     */
    public Position3d(String inp)
    {
        String[] items = inp.split(",");
        if(items.length <  3)
            throw new IllegalArgumentException("nees x,y,z not " + inp);
        X = Double.parseDouble(items[0].trim());
        Y = Double.parseDouble(items[1].trim());
        Z = Double.parseDouble(items[2].trim());
    }

    /**
     * conveinence constructor fo ra 1 dimensional position
     * @param xpos
     */
    public Position3d(double xpos)
    {
        X = xpos;
        Y = 0;
        Z = 0;

    }

    /**
     * make a new position adding X
     * @param x
     * @return
     */
    public Position3d add(double x) {
        return new Position3d(X + x,Y,Z);
    }

    /**
     * make a new position adding x,y,z
     * @param x
     * @param y
     * @param z
     * @return
     */
    public Position3d add(double x,double y,double z) {
        return new Position3d(X + x,Y + y ,Z + z);
    }

    /**
      * make a new position as the sum of p and this
      * @param p
      * @return
      */
     public Position3d max(Position3d p) {
         return new Position3d(Math.max(X , p.X),Math.max(Y , p.Y),Math.max(Z , p.Z));
     }
    /**
      * make a new position as the sum of p and this
      * @param p
      * @return
      */
     public Position3d min(Position3d p) {
         return new Position3d(Math.min(X , p.X),Math.min(Y , p.Y),Math.min(Z , p.Z));
     }
     /**
      * make a new position as the sum of p and this
      * @param p
      * @return
      */
     public Position3d add(Position3d p) {
         return new Position3d(X + p.X,Y + p.Y,Z + p.Z);
     }
    /**
      * make a new position as the difference of p and this
      * @param p
      * @return
      */
     public Position3d subtract(Position3d p) {
         return new Position3d(X - p.X,Y - p.Y,Z - p.Z);
     }
    /**
       * make a new position as the difference of p and this
       * @param p
       * @return
       */
      public Position3d divide(double p) {
          return new Position3d(X/p,Y/p,Z/p);
      }

        public Position3d divide(Position3d p) {
            return new Position3d(X/p.X, Y/p.Y, Z/p.Z);
        }

    /**
       * make a new position as the difference of p and this
       * @param p
       * @return
       */
      public Position3d orient(ArrayOrientation orientation) {
          switch(orientation) {
              case XNormal:
                     return this;
              case YNormal:
                     return new Position3d(Y,X,Z);
              case YReversed:
                     return new Position3d(Y,X,Z);
              case XReversed:
                  return this;
              default:
                  throw new IllegalStateException("never get here");
          }
      }

    public String toString() {
        return "" + X + "," +Y + "," +Z;

    }

    /**
        * make a new position as the difference of p and this
        * @param p
        * @return
        */
       public Position3d multiply(double p) {
           return new Position3d(X * p,Y * p,Z * p);
       }
    /**
        * make a new position as the difference of p and this
        * @param p  non-null set of multipliers
        * @return dot product
        */
       public Position3d multiply(Position3d p) {
           return new Position3d(X * p.X,Y * p.Y,Z * p.Z);
       }

    public boolean isZero() {
        return X == 0 && Y == 0 && Z == 0;
    }

    public boolean isKnown() {
        return true;
    }

    public boolean isApplicable() {
        return true;
    }

    public String getStateDescription() {
        return StringOps.formatUni(X) + "," + StringOps.formatUni(Y) + StringOps.formatUni(Z);
    }

    public String getDescription() {
        return "Position " + getStateDescription();
     }


    public int hashCode() {
        return (int)(10000 * X) ^ (int)(10000 * Z) ^ (int)(10000 * Z);
    }

    public boolean equals(Object test) {
        if(!(test instanceof Position3d))
             return false;
         Position3d realTest = (Position3d)test;
         return realTest.X == X && realTest.Y == Y && realTest.Z == Z;
    }

    public boolean equivalent(Object test) {
        if(!(test instanceof Position3d))
            return false;
        Position3d realTest = (Position3d)test;
        return realTest.equals(test);
    }


}
