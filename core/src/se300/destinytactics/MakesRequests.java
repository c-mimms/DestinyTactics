package se300.destinytactics;

import com.badlogic.gdx.utils.OrderedMap;

/**
 * Interface to implement HTTP requests from any object.
 * @author mimmsc
 *
 */
public interface MakesRequests {

	//void http(OrderedMap<String, String> orderedMap);

	void http(OrderedMap<String, Object> map);
	
}
