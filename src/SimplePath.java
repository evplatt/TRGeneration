import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class SimplePath {
	private List<Node> nodes;				// node list
	private int len;						// node length
	private boolean exclamation = false; 	// exclamation: Cannot be extended
	private boolean asterisk = false;		// asterisk: Path cannot go further
	
	public SimplePath() {
		nodes = new LinkedList<Node>();
		len = 0;
		exclamation = false;
		asterisk = false;
	}
	
	public SimplePath(Node _node) {
		nodes = new LinkedList<Node>();
		nodes.add(_node);
		len = 1;
		if(_node.isExit()) {				// if the given node is an exit node, then we need to add "exclamation"
											// to show that it cannot be extended
			exclamation = true;
		}
		asterisk = false;
	}
	
	public boolean isExclamation() {
		return exclamation;
	}
	
	public boolean isAsterisk() {
		return asterisk;
	}
	
	public boolean isSame(SimplePath _sp) {
		if(len != _sp.len())
			return false;
		List<Node> nodes2 = _sp.GetNodes();
		for(int i = 0; i < _sp.len(); i++) {
			if(nodes.get(i) != nodes2.get(i)) {
				return false;
			}
		}
		return true;
	}
	
	public SimplePath ExtendSP(Node _node) {
		SimplePath sp = copy();
		sp.AddNode(_node);
		return sp;
	}
	
	// Add a node at the end of the simple path
	private void AddNode(Node _node) {
		nodes.add(_node);
		inc();
		if(_node.isExit()) {
			exclamation = true;
		}
		if((len != 1) && (nodes.get(0).GetNodeNumber() == _node.GetNodeNumber())) {
			// The first node is the same as the last (it is already a cycle)
			//System.out.println("Node[0]: " + nodes.get(0).GetNodeNumber() + 
			//					", Node[" + (len-1) + "]: " + _node.GetNodeNumber());
			asterisk = true;
		}
	}
	
	
	// After adding a new node '_node', check whether the current simple path will be still a simple path 
	public boolean isSP(Node _node) {
		//System.out.println("len=" + len);
		if(len == 0 || len == 1) {		// If List<Node> is empty, then adding one more node will always create a simple path
										// If List<Node> is size 1, then adding one more node will always create a simple path
										// Example (3,3) => Simple Path, (3,4) => Simple Path 
			return true;				// Simple Path
		}
		
		// When the first node is the same as the last node (Still a simple path)
		Node firstNode = nodes.get(0);
		if(firstNode.isSameNode(_node)) {
			return true;				// Simple Path
		}
		
		// Case: len >= 2
		Iterator<Node> iterator = nodes.iterator();
		Node includedNode;
		
		while(iterator.hasNext()) {
			includedNode = iterator.next();
			if(_node.isSameNode(includedNode)) {
				exclamation = true;		// Exclamation: Cannot be extended
				return false; 			// NOT Simple Path
			}
		}
		return true;					// Simple Path
	}
	
	// Get all the node list
	public List<Node> GetNodes() {
		return nodes;
	}
	
	// Get a node located at the given index
	public Node GetNode(int index) {
		return nodes.get(index);
	}
	
	// Get the first node in the simple path
	public Node GetFirstNode() {
		if(len == 0) { 
			return null;
		}
		return nodes.get(0);
	}
	
	// Get the last node in the simple path
	public Node GetLastNode() {
		if(len == 0) {
			return null;
		}
		return nodes.get(len - 1);
	}
	
	// Create a new simple path and copy all the contents to that new simple path
	private SimplePath copy() {
		if(nodes == null) {
			return null;
		}
		SimplePath res = new SimplePath();
		for(int i = 0; i < nodes.size(); i++) {
			res.AddNode(nodes.get(i));
		}
//		for(int i = 0; i < nodes.size(); i++) {
//			System.out.println("nodes1: " + nodes.get(i) + ", nodes2: " + res.GetNode(i));
//		}
		return res;
	}
	
	
	// Check whether this simple path is a subpath of '_sp'
	public boolean isSubPathOf(SimplePath _long_sp) {
		if(len > _long_sp.len()) {
			return false;
		}
		Node firstNode = GetFirstNode();
		Node short_node; 		// Candidate to be checked whether this path should be included or not
		Node long_node;			// 
		boolean isSubPath = false;
		for(int i = 0; i < (_long_sp.len() - (len - 1)); i++) {
			long_node = _long_sp.GetNode(i);
			if(firstNode == long_node) {			// Compare the first node number
				isSubPath = true;					// If there's no mismatch found, then it matches (sub path)
				for(int j = 1; j < len; j++) {		// Let's compare from the second node number since the first one is alreay
													// compared
					short_node = nodes.get(j);
					long_node = _long_sp.GetNode(i+j);
					if(short_node.GetNodeNumber() != long_node.GetNodeNumber()) {
						isSubPath = false;
						break;
					}
				}
				
			}
		}
		return isSubPath;
	}
	
	// Get the length of the current simple path
	public int len() {
		return len;
	}
	
	// Increase one in size
	private void inc() {
		len++;
	}
	
	public String toString() {
		Iterator<Node> iterator = nodes.iterator();
		Node node;
		String res = "[";
		while(iterator.hasNext()) {
			node = iterator.next();
			res += node.GetNodeNumber();
			if(iterator.hasNext())
				res += ",";
		}
		res += "]";
		return res;
	}
}
