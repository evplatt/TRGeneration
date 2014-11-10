import java.util.*;

public class SimplePathPool extends SimplePathList {
	
	public void AddExtendedNodes(SimplePath _sp) {
		spl.add(_sp);
	}
	
	// Check whether any of current simple paths is a sub path of the given simple path 
	public boolean isSubPathOf(SimplePath _sp) {
		Iterator<SimplePath> iterator = spl.iterator();
		SimplePath sp;
		while(iterator.hasNext()) {
			sp = iterator.next();
			if(_sp.isSubPathOf(sp)) {
				return true;
			}
		}
		return false;
	}
	
	// Check whether this simple path includes the given simple path
	public boolean isExist(SimplePath _sp) {
		Iterator<SimplePath> iterator = spl.iterator();
		SimplePath sp;
		while(iterator.hasNext()) {
			sp = iterator.next();
			if(sp.isSame(_sp)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		SimplePath sp;
		String res = "";
		for(int i = 0; i < spl.size(); i++) {
			sp = spl.get(i);
			if(sp.isAsterisk()) {
				res += sp + "* ";
			}
			else if(sp.isExclamation()) {
				res += sp + "! ";
			}
			else {
				res += sp + " ";
			}
		}
		return res;
	}
}
