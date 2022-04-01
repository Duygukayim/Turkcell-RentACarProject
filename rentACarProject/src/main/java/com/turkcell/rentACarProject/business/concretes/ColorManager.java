package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.Result;
import com.turkcell.rentACarProject.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACarProject.core.utilities.results.SuccessResult;
import com.turkcell.rentACarProject.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACarProject.entities.concretes.Color;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<GetColorDto>> getAll() {
		
		List<Color> result = colorDao.findAll();
		List<GetColorDto> response = result.stream().map(color -> modelMapperService.forDto().map(color, GetColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<GetColorDto>>(response, Messages.COLORLIST);
	}

	@Override
	public DataResult<GetColorDto> getById(long id) {
		
		checkIfColorIdExists(id);
		
		Color color = colorDao.getById(id);
		GetColorDto response = modelMapperService.forDto().map(color, GetColorDto.class);
		
		return new SuccessDataResult<GetColorDto>(response, Messages.COLORFOUND);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest)  {
		
		checkIfColorNameExists(createColorRequest.getName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		this.colorDao.save(color);
		
		return new SuccessResult(Messages.COLORADD);
	}

	@Override
	public Result delete(long id) {
		
		checkIfColorIdExists(id);
		this.colorDao.deleteById(id);
	
		return new SuccessResult(Messages.COLORDELETE);
	}

	@Override
	public Result update(long id, UpdateColorRequest updateColorRequest) {
		
		checkIfColorIdExists(id);
		checkIfColorNameExists(updateColorRequest.getName());
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		color.setId(id);
		this.colorDao.save(color);
		
		return new SuccessResult(Messages.COLORUPDATE);

	}

	private boolean checkIfColorNameExists(String colorName) {
		
		Color color = this.colorDao.findByName(colorName);
		if (color == null) {
			return true;
		}
		throw new BusinessException(Messages.COLORNAMEERROR);
	}

	private void checkIfColorIdExists(long id) {
		
		if(!this.colorDao.existsById(id)) {
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
	}
	
}
