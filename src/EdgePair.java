
public class EdgePair {
	private int start;		// (A, B, C): A=start, B=middle, C=end
	private int middle;
    private int end;
   
    public EdgePair(int _st, int _mi, int _e){
        start = _st;
        middle = _mi;
        end = _e; 
    }
    
    public EdgePair(Edge _e1, Edge _e2) {
    	if(_e1.GetEnd() != _e2.GetStart()) {	// (A, B), (B, C): Then we can generate an edge-pair
    		System.out.println("[EdgePair] Cannot generate an edge pair with this combination");
    		return;
    	}
    	start = _e1.GetStart();
    	middle = _e1.GetEnd();
    	end = _e2.GetEnd();
    }
    
    public int GetStart() {
    	return start;
    }
    
    public int GetMiddle() {
    	return middle;
    }
    
    public int GetEnd() {
    	return end;
    }
    
	public String toString() {
		return "EdgePair(" + start + "," + middle + "," + end + ")";
	}
	
	public boolean isSameEdgePair(EdgePair _ep) {
		return (start == _ep.GetStart() &&
				middle == _ep.GetMiddle() &&
				end == _ep.GetEnd());
	}
}
