package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.FindColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.CreateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;

public interface ColorService {

	List<ListColorDto> listAll();

	void create(CreateColorRequest createColorRequest) throws BusinessException;

	FindColorDto findById(int colorId) throws BusinessException;
}
