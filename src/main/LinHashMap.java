package main;

/************************************************************************************
 * @file LinHashMap.java
 *
 * @author  John Miller
 */

import java.io.*;
import java.lang.reflect.Array;
import static java.lang.System.out;
import java.util.*;

/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm.
 * A hash table is created that is an array of buckets.
 */
public class LinHashMap <K, V>
       extends AbstractMap <K, V>
       implements Serializable, Cloneable, Map <K, V>
{
    /** The number of slots (for key-value pairs) per bucket.
     */
    private static final int SLOTS = 4;

    /** The class for type K.
     */
    private final Class <K> classK;

    /** The class for type V.
     */
    private final Class <V> classV;

    /********************************************************************************
     * This inner class defines buckets that are stored in the hash table.
     */
    private double bf= 0.7;// Load factor
    
    
    private int N=0; // Number of keys in the hash table

    /** Number of keys in hasTable.
     */
    private class Bucket
    {
        int    nKeys;
        K []   key;
        V []   value;
        Bucket next;

        @SuppressWarnings("unchecked")
        Bucket (Bucket n)
        {
            nKeys = 0;
            key   = (K []) Array.newInstance (classK, SLOTS);
            value = (V []) Array.newInstance (classV, SLOTS);
            next  = n;
        } // constructor
    } // Bucket inner class

    /** The list of buckets making up the hash table.
     */
    private final List <Bucket> hTable;

    /** The modulus for low resolution hashing
     */
    private int mod1;

    /** The modulus for high resolution hashing
     */
    private int mod2;

    /** Counter for the number buckets accessed (for performance testing).
     */
    private int count = 0;

    /** The index of the next bucket to split.
     */
    private int split = 0;

    /********************************************************************************
     * Construct a hash table that uses Linear Hashing.
     * @param classK    the class for keys (K)
     * @param classV    the class for keys (V)
     * @param initSize  the initial number of home buckets (a power of 2, e.g., 4)
     */
    public LinHashMap (Class <K> _classK, Class <V> _classV)    // , int initSize)
    {
        classK = _classK;
        classV = _classV;
        hTable = new ArrayList <> ();
        mod1   = 4;                        // initSize;
        mod2   = 2 * mod1;
        for(int i=0;i<mod1;i++)
        {
            
                Bucket b= new Bucket(null);
                hTable.add(b);// Making a empty home bucket as the mod1 function is initially taken as 4 so making that size of home bucket
        }
            
    } // constructor

    /********************************************************************************
     * Return a set containing all the entries as pairs of keys and values.
     * @return  the set view of the map
     */
    public Set <Map.Entry <K, V>> entrySet ()
    {
        var enSet = new HashSet <Map.Entry <K, V>> ();

        //  T O   B E   I M P L E M E N T E D
        for(int i = 0; i < hTable.size(); i++)
        {
            Bucket temp = hTable.get(i);
            for(int j=0; j<temp.nKeys; j++)
            {
                enSet.add(new AbstractMap.SimpleEntry<>(temp.key[j], temp.value[j]));
            }
        }
       
            
        return enSet;
    } // entrySet

    /********************************************************************************
     * Given the key, look up the value in the hash table.
     * @param key  the key used for look up
     * @return  the value associated with the key
     */
    public V get (Object key)
    {
        var i = h (key);

        //  T O   B E   I M P L E M E N T E D
        if(i<split)
            i=h2(key);
        Bucket temp = hTable.get(i);
        if(temp.nKeys==0)
            return null;
        while(temp!=null)
        {
            count++;
            for(int m=0; m<temp.nKeys; m++){
                if(key.equals(temp.key[m]))
                    return temp.value[m];
            }
            temp = temp.next; 
        }

        return null;
    } // get

    public int useH2() {
        
        mod1=mod2;
        mod2=2*mod1;
        return 0;
    }
    /********************************************************************************
     * Put the key-value pair in the hash table.
     * @param key    the key to insert
     * @param value  the value to insert
     * @return  null (not the previous value)
     */
    public V put (K key, V value)
    {
        //System.out.println(hTable);
        var i = h (key);
        //out.println ("LinearHashMap.put: key = " + key + ", h() = " + i + ", value = " + value);

            //  T O   B E   I M P L E M E N T E D
        
        
            // if there is a place in the home bucket
            if(hTable.get(i).nKeys<SLOTS) {
            //add the key into bucket at i
            hTable.get(i).key[hTable.get(i).nKeys]=key;
            hTable.get(i).value[hTable.get(i).nKeys]=value;
            hTable.get(i).nKeys++;  
            //System.out.println("Added new record at " +i+"\nchecking blocking factor...");
            loadFactor_check();
            }
            
            else {
                
                //check if already overflow bucket present
                
                //create a overflow bucket
                //System.out.println("Creating an overflow bucket..");
                Bucket tempBucket=new Bucket(null);
                tempBucket.key[0]=key;
                tempBucket.value[0]=value;
                tempBucket.nKeys++;
                
                Bucket tempBucket2=hTable.get(i);
            //  System.out.println("i is "+i);
                
                while(tempBucket2.next!=null)//check if overflow bucket already present
                {
                    tempBucket2=tempBucket2.next;
                }
                tempBucket2.next=tempBucket;
             loadFactor_check();
                
            }
            
            
              if(N==(2*SLOTS)) // To check if the home bucket is exhausted
             {
                split(); 
                }
  
            loadFactor_check();
            
         
       
            
           


        return null;
    } 
            
            
     void split() {
        
        Bucket splitBucket = hTable.get(split); // Split Bucket is the bucket which is pointing to the split pointer which is to be taken into consideration for redistribution and the new bucket added
        
    
        ArrayList<K> temp_key = new ArrayList<K>();
        ArrayList<V> temp_value = new ArrayList<V>(); // Storing those keys and values in a temporary storage
        for(;splitBucket!=null; splitBucket = splitBucket.next)
        {   
            for (int j = 0; j < splitBucket.nKeys; j++) // 
            {
                temp_key.add(splitBucket.key[j]);
                temp_value.add(splitBucket.value[j]);
            }
            
        }
        
        
        
        hTable.set(split, new Bucket(null));
    
        
        Bucket new_Bucket=new Bucket(null);
        hTable.add(new_Bucket); // create the new bucket and adding it to the htable
        
        if (split == mod1) {
            split = 0;
            mod1= mod1*2;
            mod2= mod1*2;       }
        
        else {
            split++;
        }
        
        for (int j = 0; j < temp_key.size(); j++)
        {
            put(temp_key.get(j), temp_value.get(j));
            
        }
        
      }
     

    /********************************************************************************
     * Return the size (SLOTS * number of home buckets) of the hash table. 
     * @return  the size of the hash table
     */
    public int size ()
    {
        return SLOTS * (mod1 + split);
    } // size

    /********************************************************************************
     * Print the hash table.
     */
    private void print ()
    {
        out.println ("Hash Table (Linear Hashing)");
        out.println ("-------------------------------------------");

        //  T O   B E   I M P L E M E N T E D
        //System.out.println(hTable);
        //hTable.pr
        int count=0;
        for(Bucket i:hTable) {
            int nkey=0;
        
            
            System.out.println("\n\n----------Bucket- "+    count+++"----------------"+"\n");
            while(nkey<i.key.length) {
                
                System.out.print("| KEY "+i.key[nkey]+" VALUE "+ i.value[nkey]+" |");
                
                 nkey++;
            }
            Bucket temp=new Bucket(null);
            temp=i.next;
            
            while(temp!=null) {
                System.out.print("->");
                nkey=0;
                while(nkey<temp.nKeys) {
                    System.out.print("| KEY "+temp.key[nkey]+" VALUE "+ temp.value[nkey]+"|");
                     nkey++;
                }
            
                temp=temp.next;
            }
            System.out.println("\n--------------------------\n");
        }

        out.println ("-------------------------------------------");

        //out.println ("-------------------------------------------");
    } // print

    /********************************************************************************
     * Hash the key using the low resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h (Object key)
    {
        return Math.abs(key.hashCode () % mod1);
    } // h

    /********************************************************************************
     * Hash the key using the high resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h2 (Object key)
    {
        return Math.abs(key.hashCode () % mod2);
    } // h2

    /********************************************************************************
     * The main method used for testing.
     * @param  the command-line arguments (args [0] gives number of keys to insert)
     */
    
    public void loadFactor_check()
    {   
       Float CurrentLoad =  ((float)N/(float)size());
        if(CurrentLoad>=bf)
         split();
    }//CheckLoad
    public static void main (String [] args)
    {

        var totalKeys = 100;
        var RANDOMLY  = false;
        

        LinHashMap <Integer, Integer> ht = new LinHashMap <> (Integer.class, Integer.class);
       
        if (args.length == 1) totalKeys = Integer.valueOf (args [0]);

        if (RANDOMLY) {
            var rng = new Random ();
            for (int i = 1; i <= totalKeys; i++) ht.put (rng.nextInt (2 * totalKeys), i * i);
        } else {
            for (int i = 1; i <= totalKeys; i++) ht.put (i, i * i);
        } // if

        ht.print ();
        for (int i = 0; i <= totalKeys; i++) {
            out.println ("key = " + i + " value = " + ht.get (i));
        } // for
        out.println ("-------------------------------------------");
        out.println ("Average number of buckets accessed = " + ht.count / (double) totalKeys);
        //System.out.println(hTable);
    } // main

} // LinHashMap class


