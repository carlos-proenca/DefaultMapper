package br.com.carlos.defaultmapper;

import java.util.List;

/**
 * The Default Mapper Type
 *  
 * This interface have all map operations
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public interface DefaultMapper {

	/**
	 * Map From To 
	 * 
	 * @param from The class to get the data
	 * @param toClass The class to store the data
	 * @return To class with all mapped data
	 */
	public <FROM,TO> TO map(FROM from, Class<TO> toClass);
	
	/**
	 * Map To List
	 * 
	 * @param fromList The list with all data
	 * 
	 * @param toClass The class type of return list 
	 * 
	 * @return The list with all data mapped 
	 */
	public <FROM,TO> List<TO> listMap(List<FROM> fromList, Class<TO> toClass);
}