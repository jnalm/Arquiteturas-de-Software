
public class StringC extends ABS {
	
	boolean less(Object a, Object b) {
		String as = (String) a;
		String bs = (String) b;
		if((as.length() - bs.length() >= 0))
			return false;
		else
			return true;
	}
	
}
