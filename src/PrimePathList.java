import java.util.*;


public class PrimePathList extends SimplePathList {
	
	private List<SimplePath> ppl_candidates;
	
	public PrimePathList() {
		super();
		ppl_candidates = new LinkedList<SimplePath>();
	}
	
	// Store only the ones with asterisk('*') or exclamation('!')
	public void ChoosePPLCandidates(SimplePathPool _pool) {
		Iterator<SimplePath> iterator = _pool.iterator();
		SimplePath sp;
		while(iterator.hasNext()) {
			sp = iterator.next();
			if(sp.isAsterisk() || sp.isExclamation()) {
				ppl_candidates.add(sp);
			}
		}
		spl = ppl_candidates;
	}
	
	// Remove sub path
	public void RemoveSubPath() {
		List<SimplePath> spl_filtered = new LinkedList<SimplePath>();
		SimplePath short_sp, long_sp;
		boolean isSubPath = false;
		for(int i = 0; i < spl.size(); i++) {
			short_sp = spl.get(i);
			for(int j = i+1; j < spl.size(); j++) {
				long_sp = spl.get(j);
				//System.out.println("Short SP:" + short_sp + ", Long SP:" + long_sp);
				if(short_sp.isSubPathOf(long_sp)) {		// If any of sub paths is found, then do not add to the list
					//System.out.println("Sub Path Found!");
					isSubPath = true;
					break;
				}
			}
			if(!isSubPath) {		// If there is no sub path found, then add it to the final Prime Path List
				//System.out.println("Added Short SP: " + short_sp);
				spl_filtered.add(short_sp);
			}
			isSubPath = false;
		}
		spl = spl_filtered;
	}
}
