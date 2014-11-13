import java.util.*;


public class TestRequirements {
	private Graph graph = null; 
	
	public TestRequirements() {
		graph = null;
	}
	
	public TestRequirements(Graph _graph) {
		graph = _graph;
	}
	
	public void ReadGraph(Graph _graph) {
		graph = _graph;
	}
	
//	public void PrintNodeCoverage() {
//		if(graph == null) {
//			System.out.println("Graph is null");
//			return;
//		}
//		
//		System.out.print("TR for Node Coverage: ");
//		List<Node> traveledNodes = new LinkedList<Node>();
//		Node node = graph.GetEntryNode();
//		TravelNode(traveledNodes, node);
//		PrintTraveledNodes(traveledNodes);
//		System.out.println();
//	}
	
	public void PrintNodeCoverage() {
		if(graph == null) {
			System.out.println("Graph is null");
			return;
		}
		
		System.out.print("TR for Node Coverage: ");
		List<Node> traveledNodes = new LinkedList<Node>();
		List<Node> entryNodeList = graph.GetEntryNodeList();
		TravelNodeList(traveledNodes, entryNodeList);
		PrintTraveledNodes(traveledNodes);
		System.out.println();
	}
	
	private void TravelNode(List<Node> _traveledNodes, Node _node) {
		if(_node == null) {
			System.out.println("ERROR: Node is null");
			return;
		}
		Iterator<Node> iterator = _traveledNodes.iterator();
		Node n;
		while(iterator.hasNext()) {
			n = iterator.next();
			if(n.isSameNode(_node)) {
				return;
			}
		}
		_traveledNodes.add(_node);
		//System.out.print(_node.GetNodeNumber() + " ");
		
		List<Edge> edgesList = graph.GetEdgeStartFrom(_node);
		Node node = null;
		Edge edge = null;
		int end = -1;
		for(int i = 0; i < edgesList.size(); i++) {
			edge = edgesList.get(i);
			//System.out.println("[PrintNode] Edge = " + edge);
			end = edge.GetEnd();
			//System.out.println("[PrintNode] Edge end = " + end);
			node = graph.GetNode(end);
			TravelNode(_traveledNodes, node);
		}
	}
	
	private void TravelNodeList(List<Node> _traveledNodes, List<Node> _node_list) {
		if(_node_list == null) {
			System.out.println("ERROR: Node is null");
			return;
		}
		
		for(int i = 0; i < _node_list.size(); i++) {
			TravelNode(_traveledNodes, _node_list.get(i));
		}
	}
	
	private void PrintTraveledNodes(List<Node> _traveledNodes) {
		
		// Sort node list
		Collections.sort(_traveledNodes, new Comparator<Node>() {
			public int compare(Node _n1, Node _n2) {
				return (_n1.GetNodeNumber() - _n2.GetNodeNumber());
			}
		});
		
		Iterator<Node> iterator = _traveledNodes.iterator();
		Node node;
		while(iterator.hasNext()) {
			node = iterator.next();
			System.out.print(node.GetNodeNumber() + " ");
		}
	}
	
	public void PrintEdgeCoverage() {
		if(graph == null) {
			System.out.println("Graph is null");
			return;
		}
		
		System.out.print("TR for Edge Coverage: ");
		
		List<Edge> traveledEdges = new LinkedList<Edge>();
		Node node = graph.GetEntryNode();
		TravelEdges(traveledEdges, node);			
		PrintTraveledEdges(traveledEdges);		// Display sorted edge list
		System.out.println();
	}
	
	private void TravelEdges(List<Edge> _traveledEdges, Node _node) {
		if(_node == null) {
			System.out.println("Node is null");
			return;
		}
		List<Edge> edgesList = graph.GetEdgeStartFrom(_node);
		Edge edge1 = null, edge2 = null;
		boolean skip = false;
		for(int i = 0; i < edgesList.size(); i++) {
			skip = false;
			edge1 = edgesList.get(i);
			//System.out.println("[PrintEdge] Edge = " + edge);
			for(int j = 0; j < _traveledEdges.size(); j++) {
				edge2 = _traveledEdges.get(j);
				if(edge1.isSameEdge(edge2)) {
					skip = true;
					break;
				}
			}
			if(skip == false) {
				int end = edge1.GetEnd();
				Node node = graph.GetNode(end);
				_traveledEdges.add(edge1);
				//System.out.print("[" + edge1.GetStart() + "," + edge1.GetEnd() + "] ");
				
				TravelEdges(_traveledEdges, node);
			}
		}		
	}
	
	private void PrintTraveledEdges(List<Edge> _traveledEdges) {
		
		// Sort edge list
		Collections.sort(_traveledEdges, new Comparator<Edge>() {
			public int compare(Edge _e1, Edge _e2) {
				if(_e1.GetStart() == _e2.GetStart()) {		// (a1,b1) (a2,b2) are compared
															// If a1=a2, then we compare b1 and b2
					return (_e1.GetEnd() - _e2.GetEnd());
				}
				else										// Otherwise we just compare a1 and a2
					return (_e1.GetStart() - _e2.GetStart());
			}
		});
		
		Iterator<Edge> iterator = _traveledEdges.iterator();
		Edge edge;
		while(iterator.hasNext()) {
			edge = iterator.next();
			System.out.print("[" + edge.GetStart() + "," + edge.GetEnd() + "] ");
		}
	}

	public void PrintEdgePairCoverage() {
		if(graph == null) {
			System.out.println("Graph is null");
			return;
		}
		
		System.out.print("TR for Edge-Pair Coverage: ");
		List<EdgePair> traveledEPs = new LinkedList<EdgePair>();
		Node node = graph.GetEntryNode();
		TravelEdgePairs(traveledEPs, node);
		PrintTraveledEPs(traveledEPs);
		System.out.println();
	}
	
	private void TravelEdgePairs(List<EdgePair> _traveledEPs, Node _node) {
		if(_node == null) {
			System.out.println("Node is null");
			return;
		}
		List<Edge> edgesList1 = graph.GetEdgeStartFrom(_node);
		Edge edge1 = null, edge2 = null;
		Node node1 = null, node2 = null;
		boolean skip = false;
		EdgePair ep1 = null, ep2 = null;
		for(int i = 0; i < edgesList1.size(); i++) {
			skip = false;
			edge1 = edgesList1.get(i);
			//System.out.println("Edge1 = " + edge1);
			node1 = graph.GetNode(edge1.GetEnd());
			List<Edge> edgesList2 = graph.GetEdgeStartFrom(node1);
			for(int j = 0; j < edgesList2.size(); j++) {
				edge2 = edgesList2.get(j);
				//System.out.println("Edge2 = " + edge2);
				ep1 = new EdgePair(edge1, edge2);
				
				for(int k = 0; k < _traveledEPs.size(); k++) {
					ep2 = _traveledEPs.get(k);
					if(ep1.isSameEdgePair(ep2)) {
						skip = true;
						break;
					}
				}
				if(skip == false) {
					_traveledEPs.add(ep1);
					//System.out.print("[" + ep1.GetStart() + "," + ep1.GetMiddle() + "," + ep1.GetEnd() + "] ");
					
					TravelEdgePairs(_traveledEPs, node1);
				}
			}	
		}		
	}
	
	private void PrintTraveledEPs(List<EdgePair> _traveledEPs) {
		
		// Sort edge list
		Collections.sort(_traveledEPs, new Comparator<EdgePair>() {
			public int compare(EdgePair _ep1, EdgePair _ep2) {
				// When (a1,b1,c1), (a2,b2,c2) are compared
				if(_ep1.GetStart() == _ep2.GetStart()) {		// If a1=a2, then we compare b1 and b2
					if(_ep1.GetMiddle() == _ep2.GetMiddle()) {	// If a1=a2 & b1=b2, then we compare c1 and c2
						return (_ep1.GetEnd() - _ep2.GetEnd());	
					}
					return(_ep1.GetMiddle() - _ep2.GetMiddle());// If a1=a2 & b1!=b2, then we just compare b1 and b2
				}
				else										// Otherwise we just compare a1 and a2
					return (_ep1.GetStart() - _ep2.GetStart());
			}
		});
		
		Iterator<EdgePair> iterator = _traveledEPs.iterator();
		EdgePair ep;
		while(iterator.hasNext()) {
			ep = iterator.next();
			System.out.print("[" + ep.GetStart() + "," + ep.GetMiddle() + "," + ep.GetEnd() + "] ");
		}
	}

	public void PrintPrimePathCoverage() {
		if(graph == null) {
			System.out.println("Graph is null");
			return;
		}
		
		System.out.print("TR for Prime Path Coverage: ");
		
		List<Node> traveledNodes = new LinkedList<Node>();
		Node node = graph.GetEntryNode();
		TravelNode(traveledNodes, node);
		SimplePathList spl = new SimplePathList(traveledNodes);
		SimplePathPool pool = new SimplePathPool();
		GenerateSimplePath(pool, spl);
		//System.out.println("Final Simple Path : " + pool);
		PrimePathList ppl = new PrimePathList();
		ppl.ChoosePPLCandidates(pool);
		//System.out.println("1st Prime Path TR: " + ppl);
		ppl.RemoveSubPath();
		//System.out.println("Final Prime Path TR: ");
		System.out.println(ppl);
	}
	
	private void GenerateSimplePath(SimplePathPool _pool, SimplePathList _spl) {
		if(_pool == null) {
			System.out.println("SimplePathPool is null");
			return;
		}
		
		if(_spl == null) {
			System.out.println("SimplePathList is null");
			return;
		}
		
		if(_spl.size() == 0) {
			//System.out.println("SimplePathList is empty");
			return;
		}
		//System.out.println(_pool);
		
		Iterator<SimplePath> iterator = _spl.iterator();
		SimplePath sp;
		SimplePathList spl2 = new SimplePathList();
		
		while(iterator.hasNext()) {
			sp = iterator.next();
			if(!_pool.isExist(sp)) {
				//System.out.println("Added SimplePath: " + sp);
				_pool.add(sp);
			}
			if(sp.isExclamation() || sp.isAsterisk()) {
				//System.out.println("!(end node): " + sp.isExclamation() + ", *(cycle): " + sp.isAsterisk());
				continue;	
			}
			
			Node lastNode = sp.GetLastNode();
			List<Edge> edgesList = graph.GetEdgeStartFrom(lastNode);
			Node node = null;
			Edge edge = null;
			int end = -1;
			for(int i = 0; i < edgesList.size(); i++) {
				edge = edgesList.get(i);
				//System.out.println("Edges: " + edge);
				end = edge.GetEnd();
				//System.out.println("end: " + end);
				node = graph.GetNode(end);
				//System.out.println("Node: " + node);
				//System.out.println("SP: " + sp);
				//System.out.println("isSP(node): " + sp.isSP(node));
			
				if(sp.isSP(node)) {			// When the list is a simple path after adding 'node'
					SimplePath sp2 = sp.ExtendSP(node);
					//System.out.println("Extended: " + sp2);
					spl2.add(sp2);	
				}
			}	
		}
		//System.out.println("SimplePath List : " + spl2);
		GenerateSimplePath(_pool, spl2);
	}
}
