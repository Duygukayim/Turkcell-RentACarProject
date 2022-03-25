package com.turkcell.rentACarProject.business.abstracts;

import java.util.List;

import com.turkcell.rentACarProject.business.dtos.get.GetColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;

public interface ColorService {

	DataResult<List<GetColorDto>> getAll();

	DataResult<GetColorDto> getById(long id);

	Result add(CreateColorRequest createRequest);

	Result delete(long id);

	Result update(long id, UpdateColorRequest updateRequest);

}
