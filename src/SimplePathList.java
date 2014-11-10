import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class SimplePathList {
	protected List<SimplePath> spl;
	
	public SimplePathList() {
		spl = new LinkedList<SimplePath>();
	}
	
	public SimplePathList(List<Node> _nodes) {
		spl = new LinkedList<SimplePath>();
		AddInitialNodes(_nodes);
	}
	
	// Add simple paths with given node list
	public void AddInitialNodes(List<Node> _nodeList) {
		SimplePath sp;
		for(int i = 0; i < _nodeList.size(); i++) {
			Node node = _nodeList.get(i);
			sp = new SimplePath(node);
			spl.add(sp);
		}
	}
	
	public Iterator<SimplePath> iterator() {
		return spl.iterator();
	}
	
	public void add(SimplePath _sp) {
		spl.add(_sp);
	}
	
	public int size() {
		return spl.size();
	}
	
	public String toString() {
		SimplePath sp;
		String res = "";
		for(int i = 0; i < spl.size(); i++) {
			sp = spl.get(i);
			res += sp + " ";
		}
		return res;
	}
}
