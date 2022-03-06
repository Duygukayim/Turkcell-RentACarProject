package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

@Service
public interface ColorService {

	DataResult<List<ListColorDto>> getAll();

	DataResult<GetColorDto> getById(int id) throws BusinessException;

	Result add(CreateColorRequest createColorRequest) throws BusinessException;

	Result delete(DeleteColorRequest deleteColorRequest)throws BusinessException;

	Result update(UpdateColorRequest updateColorRequest)throws BusinessException;

}
