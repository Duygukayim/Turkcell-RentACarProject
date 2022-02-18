package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

public interface ColorService {

	List<ListColorDto> getAll();
	GetColorDto getById(int id) throws BusinessException;
	
	void create(CreateColorRequest createColorRequest) throws BusinessException;
	void delete(DeleteColorRequest deleteColorRequest);
	void update(UpdateColorRequest updateColorRequest);

}
