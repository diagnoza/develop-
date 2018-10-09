import java.io.*;
import java.util.*;

		class ColEdge
			{
			int u;
			int v;
			}
		
public class ReadGraph
		{
		
		public final static boolean DEBUG = true;
		
		public final static String COMMENT = "//";
		
		public static void main( String args[] )
			{
			if( args.length < 1 )
				{
				System.out.println("Error! No filename specified.");
				System.exit(0);
				}

				
			String inputfile = args[0];
			
			boolean seen[] = null;
			
			//! n is the number of vertices in the graph
			int n = -1;
			
			//! m is the number of edges in the graph
			int m = -1;
			
			//! e will contain the edges of the graph
			ColEdge e[] = null;
			
			try 	{ 
			    	FileReader fr = new FileReader(inputfile);
			        BufferedReader br = new BufferedReader(fr);

			        String record = new String();
					
					//! THe first few lines of the file are allowed to be comments, staring with a // symbol.
					//! These comments are only allowed at the top of the file.
					
					//! -----------------------------------------
			        while ((record = br.readLine()) != null)
						{
						if( record.startsWith("//") ) continue;
						break; // Saw a line that did not start with a comment -- time to start reading the data in!
						}
	
					if( record.startsWith("VERTICES = ") )
						{
						n = Integer.parseInt( record.substring(11) );					
						if(DEBUG) System.out.println(COMMENT + " Number of vertices = "+n);
						}

					seen = new boolean[n+1];	
						
					record = br.readLine();
					
					if( record.startsWith("EDGES = ") )
						{
						m = Integer.parseInt( record.substring(8) );					
						if(DEBUG) System.out.println(COMMENT + " Expected number of edges = "+m);
						}

					e = new ColEdge[m];	
												
					for( int d=0; d<m; d++)
						{
						if(DEBUG) System.out.println(COMMENT + " Reading edge "+(d+1));
						record = br.readLine();
						String data[] = record.split(" ");
						if( data.length != 2 )
								{
								System.out.println("Error! Malformed edge line: "+record);
								System.exit(0);
								}
						e[d] = new ColEdge();
						
						e[d].u = Integer.parseInt(data[0]);
						e[d].v = Integer.parseInt(data[1]);

						seen[ e[d].u ] = true;
						seen[ e[d].v ] = true;
						
						if(DEBUG) System.out.println(COMMENT + " Edge: "+ e[d].u +" "+e[d].v);
				
						}
									
					String surplus = br.readLine();
					if( surplus != null )
						{
						if( surplus.length() >= 2 ) if(DEBUG) System.out.println(COMMENT + " Warning: there appeared to be data in your file after the last edge: '"+surplus+"'");						
						}
					
					}
			catch (IOException ex)
				{ 
		        // catch possible io errors from readLine()
			    System.out.println("Error! Problem reading file "+inputfile);
				System.exit(0);
				}

			for( int x=1; x<=n; x++ )
				{
				if( seen[x] == false )
					{
					if(DEBUG) System.out.println(COMMENT + " Warning: vertex "+x+" didn't appear in any edge : it will be considered a disconnected vertex on its own.");
					}
				}

			//! At this point e[0] will be the first edge, with e[0].u referring to one endpoint and e[0].v to the other
			//! e[1] will be the second edge...
			//! (and so on)
			//! e[m-1] will be the last edge
			//! 
			//! there will be n vertices in the graph, numbered 1 to n

			//! INSERT YOUR CODE HERE!
			
		     hasnoNodes(n);
			hasNoEdge(m);
			if (oddCycle(n,e,m)) {
				System.out.println("This is an odd cycle.");
			} else{
				System.out.println("This is not an odd cycle.");
			}
			//EXCEPTIONS GO HERE


}
			
		public static boolean hasnoNodes(int n)//n = vertices
	{	//if there is no vertices then chromatic number is 0 and graph does not exits.
		
		
		if(n == 0)
		{         
			
			//System.out.println("this graph doesnt exist");
			return true;
		}
		//System.out.println("this graph  exist");

		return false;
	}
	
	
	public static boolean hasNoEdge(int m )
	{
		//m == number of edges
		//if no edges then chromatic number is zero
		if(m==0)
		{
			return true;
		}
	
	return false;
	}
	
	 
	/**
	 a cycle is a path with the same first and last vertex.
	 and length of the cycle equal to number of edges that contains
	 and a cycle is odd if it contains an odd number of edges
	 and chromatic number of oddCycle graph is 3.
	*/
	
	public static boolean oddCycle(int n, ColEdge e[],int m) {
		//n == no.of vertices ,, m == number of edges
		boolean cyclic = true;
		
		//if number of vertices is even return false;
		if(n%2==0) return false;
		
		if(m!=n)return false;
		
		 for(int i = 1;i<=n;i++)
		 {	
		 	 int count =0;
		 	 for(int j = 0;j<e.length;j++)
		 	 {	
		 	 	 
		 	 	 if(e[j].u==i||e[j].v==i)
		 	 	 {
		 	 	 	 count++;
		 	 	 }
		 	 }
		 	
		 	 if(count!=2)
		 	 {
		 	 	 cyclic = false;
		 	 }
		 }
		 return cyclic;
	}
		
}			