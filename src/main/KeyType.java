package main;
/*****************************************************************************************
 * @file  KeyType.java
 *
 * @author   John Miller
 * 
 * @see http://leepoint.net/notes-java/data/expressions/22compareobjects.html
 */

import java.io.Serializable;

import static java.lang.System.out;

/*****************************************************************************************
 * The KeyType class provides a key type for handling both non-composite and composite keys.
 * A key is a minimal set of attributes that can be used to uniquely identify a tuple.
 */
public class KeyType
       implements Comparable <KeyType>, Serializable
{
    /** Array holding the attribute values for a particular key
     */
    private final Comparable [] key;

    /*************************************************************************************
     * Construct an instance of KeyType from a Comparable array.  
     * @param _key  the primary key
     */
    public KeyType (Comparable [] _key)
    {
         key = _key;
    } // constructor

    /*************************************************************************************
     * Construct an instance of KeyType from a Comparable variable argument list.
     * @param key0  the first key
     * * @param keys  the all keys
     */
    public KeyType (Comparable key0, Comparable ... keys)
    {
         key = new Comparable [keys.length + 1];
         key [0] = key0;
         for (int i = 1; i < key.length; i++) key [i] = keys [i-1];
    } // constructor

    /*************************************************************************************
     * Compare two keys (negative => less than, zero => equals, positive => greater than).
     * @param k  the other key (to compare with this)
     * @return  resultant integer that's negative, zero or positive
     */
    @SuppressWarnings("unchecked")
    public int compareTo (KeyType k)
    {
        for (int i = 0; i < key.length; i++) {
            if (key [i].compareTo (k.key [i]) < 0) return -1;
            if (key [i].compareTo (k.key [i]) > 0) return 1;
        } // for
        return 0;
    } // compareTo

    /*************************************************************************************
     * Determine whether two keys are equal (equals must agree with compareTo).
     * @param k  the other key (to compare with this)
     * @return  true if equal, false otherwise
     */
    public boolean equals (Object k)
    {
        return compareTo ((KeyType) k) == 0;
    } // equals

    /*************************************************************************************
     * Compute a hash code for this object (equal objects should produce the same hash code).
     * @return  an integer hash code value
     */
    public int hashCode ()
    {
        var sum = 0;
        for (int i = 0; i < key.length; i++) sum = 7 * sum + key [i].hashCode ();
        return sum;
    } // hashCode

    /*************************************************************************************
     * Convert the key to a string.
     * @return  the string representation of the key
     */
    public String toString ()
    {
        var s = "Key (";
        for (int i = 0; i < key.length; i++) s += " " + key [i];
        return s + (" )");
    } // toString

    /*************************************************************************************
     * The main method is used for testing purposes only.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        var key1 = new KeyType (new Comparable [] { "Star_Wars_2", 1980 });
        var key2 = new KeyType (new Comparable [] { "Rocky", 1985 } );
        var key3 = new KeyType (new Comparable [] { "Star_Wars_2", 1980 });

        out.println ();
        out.println ("Test the KeyClass");
        out.println ();
        out.println ("key1 = " + key1);
        out.println ("key2 = " + key2);
        out.println ("key3 = " + key3);
        out.println ();
        out.println ("key1 < key2: "  + (key1.compareTo (key2) < 0));
        out.println ("key1 == key2: " + (key1.compareTo (key2) == 0));
        out.println ("key1 > key2: "  + (key1.compareTo (key2) > 0));
        out.println ();
        out.println ("key2 < key1: "  + (key2.compareTo (key1) < 0));
        out.println ("key2 == key1: " + (key2.compareTo (key1) == 0));
        out.println ("key2 > key1: "  + (key2.compareTo (key1) > 0));
        out.println ();
        out.println ("key1 < key3: "  + (key1.compareTo (key3) < 0));
        out.println ("key1 == key3: " + (key1.compareTo (key3) == 0));
        out.println ("key1 > key3: "  + (key1.compareTo (key3) > 0));
        out.println ();
        out.println ("key1.equals (key2): " + key1.equals (key2));
        out.println ("key1.equals (key3): " + key1.equals (key3));
        out.println ("key1.hashCode () == key2.hashCode (): " + (key1.hashCode () == key2.hashCode ()));
        out.println ("key1.hashCode () == key3.hashCode (): " + (key1.hashCode () == key3.hashCode ()));
    } // main

} // KeyType class
