
//	x = 0;
//	while(x < y)
//	{
//		y = f(x, y);
//		x = x + 1;
//	}

public class Example {
	private Graph graph;
	private TestRequirements tr;
	

	public Example() {
		graph = new Graph();
		tr = new TestRequirements();
}
	
	public void Test1() {
		Node n1 = new Node(1, "1", true, false);
		Node n2 = new Node(2, "2", false, false);
		Node n3 = new Node(3, "3", false, false);
		Node n4 = new Node(4, "4", false, true);
		
		graph.AddNode(n1);
		graph.AddNode(n2);
		graph.AddNode(n3);
		graph.AddNode(n4);
		
		graph.PrintNodes();
		
		Edge e12 = new Edge(1, 2);
		Edge e23 = new Edge(2, 3);
		Edge e24 = new Edge(2, 4);
		Edge e32 = new Edge(3, 2);
		
		graph.AddEdge(e12);
		graph.AddEdge(e23);
		graph.AddEdge(e24);
		graph.AddEdge(e32);
		
		graph.PrintEdges();
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
		
	}
	
	// Example2: Text p40
	public void Test2() {
		Node n0 = new Node(0, "0", true, false);
		Node n1 = new Node(1, "1", false, false);
		Node n2 = new Node(2, "2", false, false);
		Node n3 = new Node(3, "3", false, false);
		Node n4 = new Node(4, "4", false, false);
		Node n5 = new Node(5, "5", false, false);
		Node n6 = new Node(6, "6", false, true);
		
		graph.AddNode(n0);
		graph.AddNode(n1);
		graph.AddNode(n2);
		graph.AddNode(n3);
		graph.AddNode(n4);
		graph.AddNode(n5);
		graph.AddNode(n6);
		
		graph.PrintNodes();
		
		Edge e01 = new Edge(0, 1);
		Edge e12 = new Edge(1, 2);
		Edge e23 = new Edge(2, 3);
		Edge e31 = new Edge(3, 1);
		Edge e15 = new Edge(1, 5);
		Edge e56 = new Edge(5, 6);
		Edge e04 = new Edge(0, 4);
		Edge e44 = new Edge(4, 4);
		Edge e46 = new Edge(4, 6);
		
		graph.AddEdge(e01);
		graph.AddEdge(e12);
		graph.AddEdge(e23);
		graph.AddEdge(e31);
		graph.AddEdge(e15);
		graph.AddEdge(e56);
		graph.AddEdge(e04);
		graph.AddEdge(e44);
		graph.AddEdge(e46);
		
		graph.PrintEdges();
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
	}
	
	
	// Example3: Text p43 Exercise 5
	public void Test3() {
		Node n1 = new Node(1, "1", true, false);
		Node n2 = new Node(2, "2", false, false);
		Node n3 = new Node(3, "3", false, false);
		Node n4 = new Node(4, "4", false, false);
		Node n5 = new Node(5, "5", false, false);
		Node n6 = new Node(6, "6", false, false);
		Node n7 = new Node(7, "7", false, true);
		
		graph.AddNode(n1);
		graph.AddNode(n2);
		graph.AddNode(n3);
		graph.AddNode(n4);
		graph.AddNode(n5);
		graph.AddNode(n6);
		graph.AddNode(n7);
		
		graph.PrintNodes();
		
		Edge e12 = new Edge(1, 2);
		Edge e17 = new Edge(1, 7);
		Edge e23 = new Edge(2, 3);
		Edge e24 = new Edge(2, 4);
		Edge e32 = new Edge(3, 2);
		Edge e45 = new Edge(4, 5);
		Edge e46 = new Edge(4, 6);
		Edge e56 = new Edge(5, 6);
		Edge e61 = new Edge(6, 1);
		
		graph.AddEdge(e12);
		graph.AddEdge(e17);
		graph.AddEdge(e23);
		graph.AddEdge(e24);
		graph.AddEdge(e32);
		graph.AddEdge(e45);
		graph.AddEdge(e46);
		graph.AddEdge(e56);
		graph.AddEdge(e61);
		
		graph.PrintEdges();
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
	}
	
	// Example4: Text p42 Exercise 4
	public void Test4() {
		Node n1 = new Node(1, "1", true, false);
		Node n2 = new Node(2, "2", false, false);
		Node n3 = new Node(3, "3", false, false);
		Node n4 = new Node(4, "4", false, true);
		
		graph.AddNode(n1);
		graph.AddNode(n2);
		graph.AddNode(n3);
		graph.AddNode(n4);
		
		graph.PrintNodes();
		
		Edge e12 = new Edge(1, 2);
		Edge e23 = new Edge(2, 3);
		Edge e32 = new Edge(3, 2);
		Edge e24 = new Edge(2, 4);
		
		graph.AddEdge(e12);
		graph.AddEdge(e23);
		graph.AddEdge(e32);
		graph.AddEdge(e24);
		
		graph.PrintEdges();
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
	}
	
	// Example5: Text p43 Exercise 6 
	public void Test5() {
		Node n0 = new Node(0, "0", true, false);
		Node n1 = new Node(1, "1", true, false);
		Node n2 = new Node(2, "2", true, false);
		Node n3 = new Node(3, "3", false, false);
		Node n4 = new Node(4, "4", false, false);
		Node n5 = new Node(5, "5", false, false);
		Node n6 = new Node(6, "6", false, false);
		Node n7 = new Node(7, "7", false, true);
		Node n8 = new Node(8, "8", false, true);
		Node n9 = new Node(9, "9", false, true);
		
		
		graph.AddNode(n0);
		graph.AddNode(n1);
		graph.AddNode(n2);
		graph.AddNode(n3);
		graph.AddNode(n4);
		graph.AddNode(n5);
		graph.AddNode(n6);
		graph.AddNode(n7);
		graph.AddNode(n8);
		graph.AddNode(n9);
		
		
		graph.PrintNodes();
		
		
		Edge e03 = new Edge(0, 3);
		Edge e04 = new Edge(0, 4);
		Edge e14 = new Edge(1, 4);
		Edge e51 = new Edge(5, 1);
		Edge e25 = new Edge(2, 5);
		Edge e26 = new Edge(2, 6);
		Edge e37 = new Edge(3, 7);
		Edge e47 = new Edge(4, 7);
		Edge e48 = new Edge(4, 8);
		Edge e85 = new Edge(8, 5);
		Edge e59 = new Edge(5, 9);
		Edge e69 = new Edge(6, 9);
		
		
		graph.AddEdge(e03);
		graph.AddEdge(e04);
		graph.AddEdge(e14);
		graph.AddEdge(e51);
		graph.AddEdge(e25);
		graph.AddEdge(e26);
		graph.AddEdge(e37);
		graph.AddEdge(e47);
		graph.AddEdge(e48);
		graph.AddEdge(e85);
		graph.AddEdge(e59);
		graph.AddEdge(e69);
		
		graph.PrintEdges();
		
		tr.ReadGraph(graph);
		tr.PrintNodeCoverage();
		tr.PrintEdgeCoverage();
		tr.PrintEdgePairCoverage();
		tr.PrintPrimePathCoverage();
	}
}
