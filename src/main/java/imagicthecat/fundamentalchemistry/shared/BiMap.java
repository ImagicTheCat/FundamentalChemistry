package imagicthecat.fundamentalchemistry.shared;

import java.util.HashMap;
import java.util.Map;

/*
 * Simple bi-directional map
 */
public class BiMap <T,U> {
	public Map<T,U> forward;
	public Map<U,T> inverse;
	
	public BiMap()
	{
		forward = new HashMap<T,U>();
		inverse = new HashMap<U,T>();
	}
	
	public U get(T key)
	{
		return forward.get(key);
	}
	
	public T invget(U key)
	{
		return inverse.get(key);
	}
	
	public void put(T key, U val)
	{
		forward.put(key, val);
		inverse.put(val, key);
	}
	
	public void rm(T key)
	{
		U val = forward.remove(key);
		if(val != null)
			inverse.remove(val);
	}
	
	public void invrm(U key)
	{
		T val = inverse.remove(key);
		if(val != null)
			forward.remove(val);
	}
}
