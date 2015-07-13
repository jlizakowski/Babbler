/************************************************************************
  			Session.java - Copyright (c) Jeremy 2004

**************************************************************************/

package com.lizakowski.tools.math;


import java.util.*;
import java.io.*;
import java.lang.Math;
import java.security.*;
import com.lizakowski.tools.Bug.*;

public class RandomTools {

	Random gen;

	public RandomTools() throws Bug{
		//if sessid is null, this is a new session, so create a new empty session

                this.gen=new Random();
	}

	public static String bytesToHexString(byte [] b) {

		StringBuffer ret = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			ret.append(Integer.toHexString((b[i] & 0xFF)| 0x100).toUpperCase().substring(1,3));
		}
		return ret.toString();
	}
	
	public static String makeHexString(int size) throws Bug{
                String r= new String("");

//return makeAwesomeString();
		if (size<1) throw new Bug("HexString must have positive length");

                for (int i=0;i<size;i++) {
			//r=r.concat(Integer.toHexString(gen.nextInt(16)));
			//use math.random, cuz if you instantiate the random class multiple times within a millisecond, the seed is the same, and you get repeats!!
			//int a=(Math.floor(Math.random()*16)).intValue();			
			int a=(int)(Math.floor(Math.random()*16));			
			r=r.concat(Integer.toHexString( a ));
                }
		return r;

	}

	public static String makeArtistString(int size) {
                String r= new String("");

		String [] artists= new String[] {
			
			"Bacon","Canaletto","Cezanne","Constable","Degas","VanDyck","VanEyck","Freud","Gauguin","Greco",
			"Guillaumin","Hockney","Kitaj","DaVinci","Lorrain","Manet","Monet","Matisse",
			"Raphael","Rembrandt","Renoir","Rodin","Rossetti","Rubens","Seurat","Sisley",
			"Turner","VanGogh","Velazquez","Waterhouse","Whistler","Wright","Albert","Arbus",
			"Adams","Bellocq","Stieglitz","Brady","Bisson","Barth","Abbott","Abell",
			"Bacon","Canaletto","Cezanne","Constable","Degas","VanDyck","VanEyck","Freud","Gauguin","Greco",
			"Guillaumin","Hockney","Kitaj","DaVinci","Lorrain","Manet","Monet","Matisse",
			"Raphael","Rembrandt","Renoir","Rodin","Rossetti","Rubens","Seurat","Sisley",
			"Turner","VanGogh","Velazquez","Waterhouse","Whistler","Wright","Albert","Arbus",
			"Adams","Bellocq","Stieglitz","Brady","Bisson","Barth","Abbott","Abell",
			"Atget","Avery","Avedon","Baltz","Beaton","Bentley","Billingham","Creed",
			"Blossfeldt","Boltanski","Bourdin","Brandt","Brassai","Brouws","Burkett","Callahan",
			"Calle","Cameron","Capa","Carroll","Carter","Clark","Coburn","Connor",
			"Cushman","Curtis","Daguerre","DiCorcia","Dijkstra","Dupain","Doisneau","Eggleston",
			"Eisenstaedt","Elsken","Erwitt","Evans","Ewald","Fenton","Fitch","Fontcuberta",
			"Friedlander","Friedman","Garnet","Gatewood","Gedney","Gibson","Goldin","Goldsworthy",
			"Gowin","Gursky","Hido","Hine","Horn","Hurrell","Ishimoto","Kane",
			"Karsh","Kenna","Kennerly","Kern","Kertesz","Kinsey","Kirkland","Klein",
			"LaChapelle","Lange","Lee","Leibovitz","Leonard","Levitt","Liebling","Mann",
			"Mapplethorpe","Michals","Miller","Misrach","Morimura","Muniz","Muybridge","Mydans",
			"Newton","Niepce","Newton","Olaf","Owens","Parks","Parr","Penn",
			"Peress","Porter","Riis","Ritts","Rowell","Salgado","Runyon",
			"Saudek","Serrano","Seymour","Shahn","Sheeler","Sherman","Simmons","Siskind",
			"Smith","Steichen","Stieglitz","Strand","Sturges","Tenneson","Tunick","Uelsmann",
			"Wagner","Wall","Watkins","Weber","Weegee","Weems","Weston","Winogrand",
			"Modiglian","Mondrian","Millet","Moreau","Munch","Pissaro","Pollock"};

		int a;

                for (int i=0;i<size;i++) {
			a=(int)(Math.floor(Math.random()*artists.length));			
			r=r.concat(artists[a]);
                }

		return r;
	}

	public static String makeAwesomeString() {
                String r= new String("");

		String [][] words= new String[][] { {"Bob","Joe","Doug","Rob","Tom","Gary","Julie","Stacy","Rob","Alice","Jen","Kate","PonchoTheClown","Napoleon","Caesar","Brian"},{"Is","Has","Tries","Wears","Keeps","Drives","Wants","Lost","Was","Needs","Craves","Works","Makes","Loses","Hoists","Buys","Desires","Pushes","Lifts","Drops"},{"TheChaCha","ADigeridoo","Anyone","TheUrge","AnyHope","Hamburgers","Cars","ASpaceship","TheWar","TheBattle","BigNuggets","AFlag","ToBeKing","Whoopie","Pants","APiano","ThePhrase","AHaricut","PeanutButter","Beer"}  };

		//25
		String [] SingularPeopleNouns= new String[] {
			"Bob","Joe","Doug","Rob","Tom","Gary","Julie","Stacy","Rob","Alice",
			"Jen","Kate","PonchoTheClown","Napoleon","Caesar","Brian","Frankie","Vince",
			"Biff","George","Lucy","Octavio","He","She","BenedictArnold"};

		//35
		String [] SingularVerbs= new String[] {
			"Is","Has","Tries","Wears","Keeps","Drives","Wants","Lost","Was","Needs",
			"Craves","Works","Makes","Loses","Hoists","Buys","Desires","Pushes","Lifts",
			"Drops","Seeks","Stuffs","Grabs","Bends","Twists","Locates","Sails","Rides",
			"Forgets","Whips","Hawks","Sells","Promotes","Advocates","DancesLike",
			"SmellsLike","MovesLike","ActsLikeTheyHave","Enjoys","WantsToBe","CravesToHave"};

		//43
		String [] DirectObjects= new String[] {  //either nonspcific quantities "Snad" "water", or nouns with an article, gerunds, infitives, preprositons, or other nounlike DOs or PNs
			"TheChaCha","ADigeridoo","Anyone","TheUrge","AnyHope","Hamburgers","Cars","ASpaceship",
			"TheWar","TheBattle","BigNuggets","AFlag","ToBeKing","Whoopie","Pants","APiano",
			"ThePhrase","AHaricut","PeanutButter","Beer","BeerNuts","Pizza","CrazyWheels",
			"TheWill","AMakeover","Change","ALittleFriend","APuppetMaster","AnAirplane","Dinner",
			"ABouncyBall","Noodles","ABurrito","TheBusiness","AnAppearance","TheDawn","TheNight",
			"TheFaith","Sand","Water","Snow","Marbles","Oil","AMoment","AMop","AStickyWicket","Chocolate"} ;

		//31
		String [] Prepositions= new String[] {
			"Aboard","About","Above","AccordingTo","Across","After","Around","Beyond","Besides","Between","Despite",
			"During","Except","For","From","In","Inside","InSpiteOf","InAdditionTo","Between","To","Through",
			"Upon","By","Among","FromUnder","OutOf","OnBehalfOf","Of","PriorToBeing","Toward","Within",
			"Like","InsteadOf","InFrontOf"};


		int a;

		a=(int)(Math.floor(Math.random()*SingularPeopleNouns.length));			
		r=r.concat(SingularPeopleNouns[a]);
		a=(int)(Math.floor(Math.random()*SingularVerbs.length));			
		r=r.concat(SingularVerbs[a]);
		a=(int)(Math.floor(Math.random()*DirectObjects.length));			
		r=r.concat(DirectObjects[a]);
		a=(int)(Math.floor(Math.random()*Prepositions.length));			
		r=r.concat(Prepositions[a]);
		if (Math.random()>.5) {  //we can use either names or DOs
			a=(int)(Math.floor(Math.random()*SingularPeopleNouns.length));			
			r=r.concat(SingularPeopleNouns[a]);
		}
		else {
			a=(int)(Math.floor(Math.random()*DirectObjects.length));			
			r=r.concat(DirectObjects[a]);
		}
		

/*                for (int i=0;i<3;i++) {
			a=(int)(Math.floor(Math.random()*16));			
			r=r.concat(words[i][a]);
                }
*/
		return r;
	}


	public static int gaussianRandom(int max) {
		//provides a bell/triangle distribution from 0 to max

		return (  (
			(int)(Math.floor(Math.random()*max)) +
			(int)(Math.floor(Math.random()*max)) +
			(int)(Math.floor(Math.random()*max)) +
			(int)(Math.floor(Math.random()*max)) 
			)/4 );
	

	}
	public static int lessGaussianRandom(int max) {
		//provides a bell/triangle distribution from 0 to max

		return (  (
			(int)(Math.floor(Math.random()*max)) +
			(int)(Math.floor(Math.random()*max)) 
			)/2 );
	
	}
	public static int straightRandom(int max) {
		//provides a bell/triangle distribution from 0 to max

		return (  
			(int)(Math.floor(Math.random()*max)) 
			 );
	
	}
	public static int StraightRandom(int max) {  //deprecated due to lousy capitalization
		//provides a bell/triangle distribution from 0 to max

		return (  
			(int)(Math.floor(Math.random()*max)) 
			 );
	
	}
	public static long StraightRandom(long max) {  //deprecated due to lousy capitalization
		//provides a bell/triangle distribution from 0 to max

		return (  
			(long)(Math.floor(Math.random()*max)) 
			 );
	
	}
	public static double straightRandom(double max) {  // lousy inconsistent capitalization
		//provides a bell/triangle distribution from 0 to max

		return (  
			(double)(Math.floor(Math.random()*max)) 
			 );
	
	}


}
