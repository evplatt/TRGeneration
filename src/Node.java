
public class Node {
	private int node_number;	// Node number
	private String srcline;  	// the source code making up the node
	private boolean isEntry; 	// true if this is the entry node
	private boolean isExit;  	// true if this is an exit node
	
	private int srcLineIdx;
	private int edgesFrom;
	private int edgesTo;
	
	public Node(){}
	
	public Node(int _node_number, String _srcline, boolean _isEntry, boolean _isExit) {
		node_number = _node_number;
		srcline = _srcline;
		isEntry = _isEntry;
		isExit = _isExit;
	}
	
	public void SetSrcLineIdx(int idx){	srcLineIdx=idx;	}
	public int GetSrcLineIdx(){	return srcLineIdx; }
	
	public int GetEdgesFrom(){ return edgesFrom; }
	public void IncEdgesFrom(){	edgesFrom++; }
	
	public int GetEdgesTo(){ return edgesTo;	}
	public void IncEdgesTo(){ edgesTo++; }
	
	public int GetNodeNumber() { return node_number; }
	public void SetNodeNumber(int i){ node_number = i; }
	
	public String GetSrcLine() { return srcline; }
	public void SetSrcLine(String line) { srcline = line; }
	
	public boolean isEntry() { return isEntry;	}
	public void SetEntry(){	isEntry = true; }
	
	public boolean isExit() { return isExit; }
	public void SetExit(){ isExit = true; }
	
	public String toString() {
		return "Node" + node_number;
	}
	
	public boolean isSameNode(Node _node) {
		return node_number == _node.GetNodeNumber();
	}
}
