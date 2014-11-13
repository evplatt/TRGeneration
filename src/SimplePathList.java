import java.util.Collections;
import java.util.Comparator;
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
	
//	public String toString() {
//		SimplePath sp;
//		String res = "";
//		for(int i = 0; i < spl.size(); i++) {
//			sp = spl.get(i);
//			res += sp + " ";
//		}
//		return res;
//	}
	
	public String toString() {
		// Sort SimplePath list
		Collections.sort(spl, new Comparator<SimplePath>() {
			public int compare(SimplePath _s1, SimplePath _s2) {
				if(_s1.len() == _s2.len()) {		// If lengths are same
					int len = _s1.len();
					for(int i = 0; i < len; i++) {	// Compare node one by one
						if(_s1.GetNode(i).GetNodeNumber() != _s2.GetNode(i).GetNodeNumber()) {
							return(_s1.GetNode(i).GetNodeNumber() - _s2.GetNode(i).GetNodeNumber());
						}
					}
					return 0;
				}
				else								// If lengths are different, compare them
					return _s1.len() - _s2.len();
			}
		});
		
		SimplePath sp;
		String res = "";
		for(int i = 0; i < spl.size(); i++) {
			sp = spl.get(i);
			res += sp + " ";
		}
		return res;
	}
	
	
	
}
