import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class TRGeneration {

	private static Graph graph;
	private static TestRequirements tr;
	
	public static void main(String[] args) {
		
		//ex.Test1();
		//ex.Test2();
		//ex.Test3();
		//ex.Test4();
		//ex.Test5();			// Need to work on
	
		tr = new TestRequirements();
		graph = new Graph();
		
		if (args.length < 1){
			System.err.println("You must supply an input file");
			System.exit(1);
		}

		readSource(args[0]);
		
		//graph.setDebug(true);
		graph.build();
		graph.writePng(args.length>1?args[1]:"out.png");
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
		
	}

	private static void readSource(String path){
		
		FileInputStream fstream = null;
		
		try{
			fstream = new FileInputStream(path);
		}
		catch (IOException e){
			System.err.println("Unable opening file "+path+".\n"+e.getMessage());
			System.exit(1);
		}
		
		Scanner s = new Scanner(fstream);
		while (s.hasNextLine()){
			graph.AddSrcLine(s.nextLine());
		}
		s.close();
		try{
			fstream.close();
		}
		catch (IOException e){
			System.err.println("Error closing file "+path+".\n"+e.getMessage());
		}
		
	}
	
}
