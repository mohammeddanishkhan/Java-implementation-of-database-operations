package main;
/*****************************************************************************************
 * @file  MovieDB.java
 *
 * @author   John Miller
 */

import static java.lang.System.out;

/*****************************************************************************************
 * The MovieDB class makes a Movie Database.  It serves as a template for making other
 * databases.  See "Database Systems: The Complete Book", second edition, page 26 for more
 * information on the Movie Database schema.
 */
class MovieDB
{
    /*************************************************************************************
     * Main method for creating, populating and querying a Movie Database.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        out.println ();


        var movie = new Table ("movie", "title year length genre studioName producerNo",
                                        "String Integer Integer String String Integer", "title year");

        var cinema = new Table ("cinema", "title year length genre studioName producerNo",
                                          "String Integer Integer String String Integer", "title year");

        var movieStar = new Table ("movieStar", "name address gender birthdate",
                                                "String String Character String", "name");

        var starsIn = new Table ("starsIn", "movieTitle movieYear starName",
                                            "String Integer String", "movieTitle movieYear starName");

        var movieExec = new Table ("movieExec", "certNo name address fee",
                                                "Integer String String Integer", "certNo");

        var studio = new Table ("studio", "name address presNo",
                                          "String String Integer", "name");

        var film0 = new Comparable [] { "Star_Wars", 1977, 124, "sciFi", "Fox", 12345 };
        var film1 = new Comparable [] { "Star_Wars_2", 1980, 124, "sciFi", "Fox", 12345 };
        var film2 = new Comparable [] { "Rocky", 1985, 200, "action", "Universal", 12125 };
        var film3 = new Comparable [] { "Rambo", 1978, 100, "action", "Universal", 32355 };

        out.println ();
        movie.insert (film0);
        movie.insert (film1);
        movie.insert (film2);
        movie.insert (film3);
        movie.print ();

        var film4 = new Comparable [] { "Galaxy_Quest", 1999, 104, "comedy", "DreamWorks", 67890 };
        out.println ();
        cinema.insert (film2);
        cinema.insert (film3);
        cinema.insert (film4);
        cinema.print ();

        var star0 = new Comparable [] { "Carrie_Fisher", "Hollywood", 'F', "9/9/99" };
        var star1 = new Comparable [] { "Mark_Hamill", "Brentwood", 'M', "8/8/88" };
        var star2 = new Comparable [] { "Harrison_Ford", "Beverly_Hills", 'M', "7/7/77" };
        out.println ();
        movieStar.insert (star0);
        movieStar.insert (star1);
        movieStar.insert (star2);
        movieStar.print ();

        var cast0 = new Comparable [] { "Star_Wars", 1977, "Carrie_Fisher" };
        out.println ();
        starsIn.insert (cast0);
        starsIn.print ();

        var exec0 = new Comparable [] { 9999, "S_Spielberg", "Hollywood", 10000};
        out.println ();
        movieExec.insert (exec0);
        movieExec.print ();

        var studio0 = new Comparable [] { "Fox", "Los_Angeles", 7777 };
        var studio1 = new Comparable [] { "Universal", "Universal_City", 8888 };
        var studio2 = new Comparable [] { "DreamWorks", "Universal_City", 9999 };
        out.println ();
        studio.insert (studio0);
        studio.insert (studio1);
        studio.insert (studio2);
        studio.print ();

        //movie.save ();
       // cinema.save ();
        //movieStar.save ();
      //  starsIn.save ();
       // movieExec.save ();
        //studio.save ();

        movieStar.printIndex ();

        //--------------------- project: title year

        out.println ();
        var t_project = movie.project ("title year");
        if(t_project == null)
        	System.out.println(" The table cannot be printed as an attribute that is not existing is passed");
        else
        	t_project.print();


        //--------------------- select: equals, &&

        out.println ();
     
        String column3="title"; // name of the attribute_one on which condition is to be check  
        String column2="year"; // name of the attribute_two on which condition is to be check
        
        
        //this check if the attributes mentioned are present in the table or not.
        if(cinema.col(column3)==-1 || movie.col(column2)==-1) {
        	System.out.println("-----Error in select_eqauls------------");
        	System.out.println("Please enter valid attribute name. The given attribute doesn't exists" ); 
        }
        
        else {
        
        	var t_select = movie.select (t -> (Integer)t[movie.col(column2)] > 1970 &&
                    (Integer)t[movie.col(column2)] < (1980));
        	System.out.println("STAAAAAAAAAAAAAAAAAAARRRRRRRRRTTTTTTTTTT");
        	t_select.print ();
        	System.out.println();
        	
        	var i_select=movie.indexedSelect(t -> (Integer)t[movie.col(column2)] > 1970 &&
                                            (Integer)t[movie.col(column2)] < (1980));
        	System.out.println("HEEEEEEEEERRRRRRRRRRREEEEEEEEEEEE");
        	i_select.print();
        	System.out.println("EEEEEEEEEEENNNNNNNNNNNDDDDDDDDDDDd");
        	
        }
        //--------------------- select: <

        out.println ();

        String column="year"; // name of the attribute_one on which condition is to be check  
      //this check if the attributes mentioned are present in the table or not.
        if(movie.col(column)==-1) {
        	System.out.println("-----Error in select_comp------------");
        	System.out.println("Please eneter valid attribute name. The given attribute doesn't exists" ); 
        }
     
        var t_select2 = movie.select (t -> (Integer) t[movie.col(column)] < 1980);
        t_select2.print ();

        //--------------------- indexed select: key

        out.println ();
        var t_iselect = movieStar.select (new KeyType ("Harrison_Ford"));
        t_iselect.print ();
        out.println("b4 this");

        //--------------------- union: movie UNION cinema

        out.println ();
        var t_union = movie.union (cinema);
        if(t_union == null)
        	System.out.println(" ");
        else
        t_union.print ();

        //--------------------- minus: movie MINUS cinema

        out.println ();
        var t_minus = movie.minus (cinema);
        t_minus.print ();

        //--------------------- equi-join: movie JOIN studio ON studioName = name

        out.println ();
        var t_join = movie.join ("studioName", "name", studio);
        t_join.print ();
        
        out.print("performing i_join");
        var i_join = movie.i_join("title", "title", cinema);
        if(i_join!=null) {
        	i_join.print ();	
        }
        out.print("end performing i_join");
        
        out.print("performing h_join");
        var h_join = movie.h_join("studioName", "name", studio);
        h_join.print ();
        out.print("end performing h_join");

        //--------------------- natural join: movie JOIN studio

        out.println ();
        var t_join2 = movie.join (cinema);
        t_join2.print ();

    } // main

} // MovieDB class