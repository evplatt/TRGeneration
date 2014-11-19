import java.io.File;
import java.util.*;

public class Graph {
	private List<Node> nodes;		// Node list
	private List<Edge> edges;		// Edge list
	private List<String> lines;
	
	private boolean printDebug;
	
	public Graph() {
		
		lines = new ArrayList<String>();
		nodes = new ArrayList<Node>();
		edges = new ArrayList<Edge>();
	
		printDebug = false;
	}
	
	public void setDebug(boolean d){ printDebug = d; }
	
	// Add a line of source code
	public void AddSrcLine(String line){
		lines.add(line);		
	}
	
	// Add a node to the graph
	public void AddNode(Node _node) {
		
		nodes.add(_node);
		
	}
	
	// Add an edge to the graph
	public void AddEdge(Edge _edge) {
		edges.add(_edge);
	}
	
	// Get the first entry node
	public Node GetEntryNode() {
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			//System.out.println("GetEntryNode = " + node);
			if(node.isEntry() == true) {
				return node;
			}
		}
		return null;
	}
	
	// Get all the entry node list
	public List<Node> GetEntryNodeList() {
		List<Node> node_list = new LinkedList<Node>();
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			//System.out.println("GetEntryNode = " + node);
			if(node.isEntry() == true) {
				node_list.add(node);
			}
		}
		return node_list;
	}
	
	// Get the first exit node
	public Node GetExitNode() {
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			//System.out.println(node);
			if(node.isExit() == true) {
				return node;
			}
		}
		return null;
	}
	
	// Get all the exit node list
	public List<Node> GetExitNodeList() {
		List<Node> node_list = new LinkedList<Node>();
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			//System.out.println(node);
			if(node.isExit() == true) {
				node_list.add(node);
			}
		}
		return node_list;
	}
	
	// Get edge list that start from Node "_node"
	public List<Edge> GetEdgeStartFrom(Node _node) {
		List<Edge> chosenEdges = new LinkedList<Edge>();
		Iterator<Edge> iterator = edges.iterator();
		Edge edge;
		while(iterator.hasNext()) {
			edge = iterator.next();
			if(edge.GetStart() == _node.GetNodeNumber()) {
				chosenEdges.add(edge);
				//System.out.println("Node num = " + _node.GetNodeNumber() + ", Edge = " + edge);
			}
		}
		return chosenEdges;
	}
	
	// Get the node with a specified node number
	public Node GetNode(int _node_num) {
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			//System.out.println(node);
			if(node.GetNodeNumber() == _node_num) {
				return node;
			}
		}
		return null;
	}
	
	public void PrintNodes() {
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			System.out.println(node);
		}
	}
	
	public void PrintEdges() {
		Iterator<Edge> iterator = edges.iterator();
		Edge edge;
		while(iterator.hasNext()) {
			edge = iterator.next();
			System.out.println(edge);
		}
	}
	
	public void build(){
		
		cleanup();
		addDummyNodes();
		getNodes();
		numberNodes();
		combineNodes();
		fixNumbering();
		
	}
	
	public void writePng(String path){
		
		String strDOT = generateDOT();
		
		if (printDebug) System.out.println("\n***** Generated DOT Code:\n\n"+strDOT+"\n\n");
				
		File out = new File(path);
		
		GraphViz gv = new GraphViz();
		gv.writeGraphToFile(gv.getGraph(strDOT, "png"), out);
		
	}
	
	//CURRENT FORMAT CONSTRAINTS:
	//  Must use surrounding braces for all loops and conditionals
	//  do,for,while loop supported / do-while loops not supported
	
	private int cleanup(){
		
		//trim all lines (remove indents and other leading/trailing whitespace)
		for (int i=0; i<lines.size(); i++){
			lines.set(i, lines.get(i).trim());
		}
		
		//remove blank lines
		lines.removeAll(Collections.singleton(""));
		
		//eliminate comments
		for (int i=0; i<lines.size(); i++){
			int idx = lines.get(i).indexOf("//"); 
			if ( idx >= 0){
				lines.set(i, lines.get(i).substring(0,idx)); 
			}
		}
		
		//move opening braces on their own line to the previous line
		for (int i=lines.size()-1; i>=0; i--){
			if (lines.get(i).equals("{")){
				lines.set(i-1, lines.get(i-1) + "{");
				lines.remove(i);
			}			
		}
		
		//move any code after an opening brace to the next line
		for (int i=0; i<lines.size(); i++){
			int idx = lines.get(i).indexOf("{");
			if (idx > -1 && idx < lines.get(i).length()-1 && lines.get(i).length() > idx-1){ //this means there is text after the {
				lines.add(i+1,lines.get(i).substring(idx+1)); //insert the text right of the { as the next line
				lines.set(i,lines.get(i).substring(0,idx+1)); //remove the text right of the { on the current line
			}
		}
				
		//move closing braces NOT starting a line to the next line
		for (int i=0; i<lines.size(); i++){
			int idx = lines.get(i).indexOf("}"); 
			if (idx > 1){ //this means the } is not starting a line
				lines.add(i+1,lines.get(i).substring(idx)); //insert the text starting with the } as the next line
				lines.set(i,lines.get(i).substring(0,idx)); //remove the text starting with the } on the current line
			}
		}
		
		//move any code after a closing brace to the next line
		for (int i=0; i<lines.size(); i++){
			int idx = lines.get(i).indexOf("}"); 
			if (idx > -1 && lines.get(i).length() > 1){ //this means there is text after the {
				lines.add(i+1,lines.get(i).substring(1)); //insert the text right of the { as the next line
				lines.set(i,lines.get(i).substring(0,1)); //remove the text right of the { on the current line
			}
		}
		
		// At this point, all opening braces end a line and all closing braces are on their own line;
				
		//Separate lines with containing semicolons except at the end
		for (int i=0; i<lines.size(); i++){
			List<String> spl = new ArrayList<String>(Arrays.asList(lines.get(i).split(";")));
						
			if (spl.size() > 1){
								
				boolean lastsc = false;
				if (lines.get(i).matches("^.*;$")) lastsc = true;
				lines.set(i,spl.get(0)+";");
				for (int j=1; j<spl.size(); j++){
					if (j<spl.size()-1) lines.add(i+j,spl.get(j)+";");
					else lines.add(i+j,spl.get(j)+(lastsc?";":""));
				}
			}
		}
		
		//Combine any multi-line statements
		int i=0;
		while (i<lines.size()){
			while (!lines.get(i).contains(";") && !lines.get(i).contains("{") && !lines.get(i).contains("}")){
				lines.set(i, lines.get(i) + lines.get(i+1));
				lines.remove(i+1);
			}
			i++;
		}
		
		//turn for loops into while loops
		for (i=0; i<lines.size(); i++){
			if (lines.get(i).matches("^for.+$")){
				
				//find the closing
				int j=i+3;
				int closeline =-1;
				int depth=0;
				while (j<lines.size() && closeline==-1){
					if (lines.get(j).contains("{")) depth++;
					if (lines.get(j).contains("}")){
						if (depth==0) closeline = j;
						else depth--;
					}
					j++;
				}
				if (closeline==-1){
					System.err.println("Braces are not balanced");
					System.exit(2);
				}
				
				int idx = lines.get(i).indexOf("(");
				lines.add(i, "%forcenode%" + lines.get(i).substring(idx+1)); //move the initialization before the loop
				i++; //adjust for insertion
				idx = lines.get(i+2).indexOf(")");
				lines.add(closeline+1, "%forcenode%" + lines.get(i+2).substring(0, idx) + ";"); //move the iterator to just before the close
				lines.remove(i+2);
				lines.set(i, "while ("+lines.get(i+1).substring(0, lines.get(i+1).length()-1).trim()+"){");
				lines.remove(i+1);			
			}
			
		}
		
		//separate case statements with next line
		for (i=0; i<lines.size(); i++){
			if (lines.get(i).matches("[case|default].*:.*")){
				int idx = lines.get(i).indexOf(":");
				if (idx < lines.get(i).length()-1){
					lines.add(i+1, lines.get(i).substring(idx+1));
					lines.set(i, lines.get(i).substring(0, idx+1));
				}
			}
			
		}
		
		//again, trim all lines (remove indents and other leading/trailing whitespace)
		for (i=0; i<lines.size(); i++){
			lines.set(i, lines.get(i).trim());
		}
		
		return Defs.success;
	}
	
	private void addDummyNodes(){
		
		for (int i=0; i<lines.size(); i++){
		
			String line = lines.get(i);
			
			if (line.matches("}")){
				
				//find the opening
				int j=i-1;
				int openline=-1;
				int depth=0;
				while (j>=0 & openline==-1){
					if (lines.get(j).contains("}")) depth++;
					if (lines.get(j).contains("{")){
						if (depth==0) openline = j;
						else depth--;
					}
					j--;
				}
				if (j<-1){
					System.err.println("Braces are not balanced");
					System.exit(2);
				}
				
				if (lines.get(openline).toLowerCase().matches("^(for|while|do).*")){
					
					if (lines.get(i-1).equals("}")){
						lines.add(i, "dummy_node;");
						i--; //adjust i due to insertion
					}
					
				}
			}
		}
	}
	
	private void getNodes(){
		
		
		if (printDebug){
			String outlines="\n***** Processed Source Code:\n\n";
			for (int i=0; i<lines.size(); i++) outlines += i+": "+lines.get(i)+"\n";
			System.out.printf("%s\n", outlines);
		}
		
		int conditionalStartLine=0;
		List<Integer> edgeStartLines = new ArrayList<Integer>();
		
		for (int i=0; i<lines.size(); i++){
			
			String line = lines.get(i);
			
			//if we find a close brace, need to figure out where to go from here
			if (line.matches("}")){
				
				//find the opening
				int j=i-1;
				int openline=-1;
				int depth=0;
				while (j>=0 & openline==-1){
					if (lines.get(j).contains("}")) depth++;
					if (lines.get(j).contains("{")){
						if (depth==0) openline = j;
						else depth--;
					}
					j--;
				}
				if (openline == -1){
					System.err.println("Braces are not balanced");
					System.exit(2);
				}
				
				//for loops, add an edge back to the start
				if (lines.get(openline).toLowerCase().matches("^(for|while|do).*")){
					if (lines.get(openline).toLowerCase().matches("^(for|while).*")){
						addEdge(getPrevLine(i),openline);	
						addEdge(openline,getNextLine(i));
					}
					else{ //do
						addEdge(getPrevLine(openline), getNextLine(openline)); //entry edge that skips the "do" statement
						addEdge(getPrevLine(i),getNextLine(openline)); //looping edge
						addEdge(getPrevLine(i),getNextLine(i)); //loop exit edge
					}
				}
				
				//for conditionals, we won't add edges until after the block.  Then link all the close braces to the end of the block
				else if (lines.get(openline).toLowerCase().matches("^(if|else if).*")){
					if (lines.get(openline).toLowerCase().matches("^if.*")) conditionalStartLine = openline;
					addEdge(conditionalStartLine,openline+1);
					//if we're not done with the conditional block, save the start of this edge until we find the end of the block
					if (lines.size() > i+1 && lines.get(i+1).toLowerCase().matches("^else.*")){
						
						edgeStartLines.add(getPrevLine(i));						
					}
					else{
						for (Integer start: edgeStartLines){
							addEdge(start, i+1);
						}
						edgeStartLines.clear();
						
						addEdge(getPrevLine(i),getNextLine(i));
						addEdge(openline,getNextLine(i));
					}
				}
				else if (lines.get(openline).toLowerCase().substring(0,4).equals("else")){
					if (edgeStartLines.size() == 0){
						System.err.println("Else without If");
						System.exit(2);
					}
					edgeStartLines.add(i-1);
					addEdge(conditionalStartLine,openline+1);
					for (Integer start: edgeStartLines){
						addEdge(start, i+1);
					}
					edgeStartLines.clear();
				}
				else if (lines.get(openline).toLowerCase().matches("^switch.*")){

					//add edges to cases
					for (int k=openline; k<i; k++){ //iterate through the case statement
						if (lines.get(k).matches("^(case|default).*")){
							if (lines.get(k).matches(":$")) addEdge(openline,k);
							else addEdge(openline,getNextLine(k));  //didnt't split lines at : so could be the next line
						}
						if (lines.get(k).matches("^break;")) addEdge(k,getNextLine(i));
					}
				}
			}
			
			else{
				//we'll add a node and an edge unless these are not executable lines
				if (!lines.get(i).toLowerCase().matches("^(do|else|case|default).*")){
					addNode(line,i);
					if (i>0 && !lines.get(getPrevLine(i)).toLowerCase().matches("^(do|else|case|default).*") && !lines.get(i-1).equals("}")){
						addEdge(getPrevLine(i), i);
					}
					
				}
			}
						
		}
		
		// remove entry edges
		for (int i=0;i<edges.size();i++)
			if (edges.get(i).GetStart() < 0) edges.remove(i);

		// remove any duplicates.  this is very naughty but our list is relatively small
		for (int i=0; i<edges.size();i++){
			for (int j=i+1; j<edges.size(); j++){
				if (edges.get(j).GetStart() == edges.get(i).GetStart() && edges.get(j).GetEnd() == edges.get(i).GetEnd()) edges.remove(j);
			}
		}
		
		//fix any returns before the last line
		for (int i=0; i<nodes.size(); i++){
			
			if (nodes.get(i).GetSrcLine().contains("return")){
				
				//mark node as an exit node
				Node n = nodes.get(i);
				n.SetExit(true);
				nodes.set(i,n);
				
				//remove any lines coming from that node
				for (int j=0; j<edges.size(); j++){
					if (edges.get(j).GetStart() == n.GetSrcLineIdx()) edges.remove(j);
				}
				
			}
			
		}
		
		if (printDebug){
			System.out.print("\n***** Edges:\n   - numbers correspond to processed source code line numbers (above)\n   - basic block nodes not yet combined\n\n");
			for (Edge e: edges) System.out.println("("+e.GetStart()+","+e.GetEnd()+")");
		}
		
				
	}
	
	private void combineNodes(){
	

		//add entry edge temporarily to prevent combination of loop nodes
		addEdge(-1,0);
	
		//add dummy end nodes if needed
		for (Edge e: edges){
		boolean foundEnd=false;
			for (Node n: nodes){
				if (e.GetEnd() == n.GetNodeNumber()) foundEnd=true;				
			}
			if (!foundEnd){
				Node n = new Node();
				n.SetSrcLine("");
				n.SetNodeNumber(e.GetEnd());
				nodes.add(n);
			}
		}
		
		//figure out how many edges each node has (to and from the node)
		for (int i=0; i<nodes.size(); i++){
			for (Edge e: edges){
				if (e.GetStart() == nodes.get(i).GetNodeNumber()) nodes.get(i).IncEdgesFrom();
				if (e.GetEnd() == nodes.get(i).GetNodeNumber()) nodes.get(i).IncEdgesTo();
			}
		}
		
		// for any pair of consecutive nodes that have only 1 edge between, combine them
		
		for (int i=0; i<nodes.size(); i++){
			
			// if there's more than one edge (or no edges) leaving this node, we can't combine
			if (nodes.get(i).GetEdgesFrom() != 1 || nodes.get(i).GetSrcLine().contains("%forcenode%")) continue;
			
			// find the edge leaving this node
			int midEdge = 0;
			while (midEdge < edges.size() && edges.get(midEdge).GetStart() != nodes.get(i).GetNodeNumber()) midEdge++;
			int nextNode = 0;
			while (nodes.get(nextNode).GetNodeNumber() != edges.get(midEdge).GetEnd()) nextNode++;
			
			// if there's more than one edge entering the next node, we can't combine
			if (nodes.get(nextNode).GetEdgesTo() > 1 || nodes.get(nextNode).GetSrcLine().contains("%forcenode%")) continue;
			
			// if it's a self-loop we can't combine
                        if (nextNode == i) continue;	

			// If we got here we can combine the nodes
						
			//copy the sourceline (we'll delete nextNode)
			nodes.get(i).SetSrcLine(nodes.get(i).GetSrcLine()+"\n"+nodes.get(nextNode).GetSrcLine());
				
			// get all the edges leaving the next node
			List<Integer> outEdges = new ArrayList<Integer>();
			for (int j=0; j<edges.size(); j++){
				if (edges.get(j).GetStart() == nodes.get(nextNode).GetNodeNumber()) outEdges.add(j);
			}
			nodes.get(i).ClearEdgesFrom();
			if (outEdges.size() > 0){ //if false, this is the last node
				// relink the outbound edges to start at the first node
				for (int idx: outEdges){
					edges.set(idx, new Edge(nodes.get(i).GetNodeNumber(), edges.get(idx).GetEnd()));
					nodes.get(i).IncEdgesFrom();
				}
			}
			
			// remove old middle edge and second node
			edges.remove(midEdge);
			nodes.remove(nextNode);
 
			//keep the current node as start until we can't combine any more
			i--;
			
		}

		//delete the temporary entry edge
		edges.remove(edges.size()-1);
		
	}

	private void numberNodes(){
		
		//save the oldedges and clear edges
		List<Edge> oldedges = new ArrayList<Edge>();
		for (Edge e: edges) oldedges.add(new Edge(e.GetStart(), e.GetEnd()));
		edges.clear();
		
		//number the nodes and add edges with new numbers
		
		//First assign node numbers
		for (int i=0; i<nodes.size(); i++){
			Node n = nodes.get(i);
			n.SetNodeNumber(i);
			nodes.set(i, n);
		}
		
		//add edges using node_numbers instead of source line index
		for (int i=0; i<oldedges.size(); i++){
			int newStart=0;
			int newEnd=0;
			
			while (newStart < nodes.size() && nodes.get(newStart).GetSrcLineIdx() != oldedges.get(i).GetStart()) newStart++;
			while (newEnd < nodes.size() && nodes.get(newEnd).GetSrcLineIdx() != oldedges.get(i).GetEnd()) newEnd++;
			
			addEdge(newStart,newEnd);
		}
		
	}

	private void fixNumbering(){
		
		//Renumber the nodes, and the edges accordingly
		for (int i=0; i<nodes.size(); i++){
			Node n = nodes.get(i);
			for (int j=0; j<edges.size(); j++){
				if (edges.get(j).GetStart() == n.GetNodeNumber()) edges.set(j, new Edge(i, edges.get(j).GetEnd()));
				if (edges.get(j).GetEnd() == n.GetNodeNumber()) edges.set(j, new Edge(edges.get(j).GetStart(), i));
			}
			n.SetNodeNumber(i);
			nodes.set(i, n);
		}
	
		nodes.get(0).SetEntry(true);
	
		// mark entry and exits
		for (int i=0;i<nodes.size(); i++){
			
			boolean exit = true;
			
			for (Edge e: edges){
				if (e.GetStart() == nodes.get(i).GetNodeNumber()) exit = false;
			}
			
			if (!nodes.get(i).isExit()) nodes.get(i).SetExit(exit); //make sure override stays for return nodes
			
		}
		
	}
	
	
	private String generateDOT(){
		
		String strDOT = "digraph cfg{\n";
		
		for (Node n: nodes){
			
			String line = "";
			
			//attributes
			if (n.isEntry()){
				line += "\tstart [style=invis];\n\tstart -> "+n.GetNodeNumber()+";\n"; // invisible entry node required to draw the entry arrow
				
			}
			if (n.isExit()){
				line += "\t"+n.GetNodeNumber()+" [penwidth=4];\n"; // make the exit node bold
			}
			
			if (n.GetSrcLine().contains("%forcelabel%")){
			//	line += "\t"+n.node_number+" [xlabel=\"" + removeTags(n.srcline).trim() + "\",labelloc=\"c\"]"; // label the node if forced
			}
			
			if (line.length() > 0) strDOT += line;
			
		}
		
		for (Edge e: edges){
			strDOT += "\t"+e.GetStart()+" -> "+e.GetEnd();
			
			// attributes
			
			strDOT += ";\n";
		}
		
		strDOT += "}";
		
		return strDOT;		
		
	}
	
	private void addNode(String line, int lineidx){
		
		Node node = new Node(0,line,false,false);
		node.SetSrcLineIdx(lineidx);
		nodes.add(node);
	
	}
	private void addEdge(int startidx, int endidx){
		
		edges.add(new Edge(startidx,endidx));
		
	}

	private int getPrevLine(int start){
		
		int prevEdge=start-1;
		
		while (prevEdge > -1 && lines.get(prevEdge).equals("}")) prevEdge--;
		
		return prevEdge;
		
	}
	
	private int getNextLine(int start){
		
		int nextEdge=start+1;
		
		while (nextEdge < lines.size() && lines.get(nextEdge).equals("}")) nextEdge++;
		
		return nextEdge;
	
	}
	
	private String removeTags(String line){
		
		line = line.replace("%forcenode%", "");
		line = line.replace("%forcelabel%", "");
		
		return line;
		
	}
		
}
