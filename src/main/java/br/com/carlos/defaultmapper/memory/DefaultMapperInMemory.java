package br.com.carlos.defaultmapper.memory;

import java.util.ArrayList;
import java.util.List;

import br.com.carlos.defaultmapper.DefaultMapper;
import br.com.carlos.defaultmapper.util.FieldTransferUtils;

/**
 * The Default Mapper In Memory Type
 * 
 * @author Carlos Proença (carlos_proenca@live.com)
 *
 */
public final class DefaultMapperInMemory implements DefaultMapper{
	
	
	public final <FROM,TO> TO map(FROM from, Class<TO> toClass) {

		if(from == null){ 
			throw new IllegalArgumentException("The from class can not be null");
		}

		TO to = getInstance(toClass);
		if(to != null){
			FieldTransferUtils.transferMappedFields(from, to);
			FieldTransferUtils.transferEmbeddedMappedFields(from, to);
			FieldTransferUtils.transferMappedFromFields(from, to);
			FieldTransferUtils.transferEmbeddedMappedFromFields(from, to);
		}
		return to;
	}


	public <FROM,TO> List<TO> listMap(List<FROM> fromList, Class<TO> toClass) {
		List<TO> result = new ArrayList<TO>();
		for (FROM from : fromList) {
			result.add(map(from, toClass));
		}
		return result;
	}

	private <TO> TO getInstance(Class<TO> toClass) {
		TO to = null;
		try {
			to = toClass.newInstance();
		} catch (Exception e) {
			//nothing to do here
		}
		return to;
	}
}