package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.constants.Messages;
import com.turkcell.rentACarProject.business.dtos.get.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.list.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
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
	public DataResult<List<ListColorDto>> getAll() {
		
		List<Color> result = colorDao.findAll();
		List<ListColorDto> response = result.stream().map(color -> modelMapperService.forDto().map(color, ListColorDto.class)).collect(Collectors.toList());
		
		return new SuccessDataResult<List<ListColorDto>>(response, Messages.COLORLIST);
	}

	@Override
	public DataResult<GetColorDto> getById(int id) throws BusinessException {
		
		Color color = colorDao.getById(id);
		checkIfColorIdExists(color.getId());
		GetColorDto response = modelMapperService.forDto().map(color, GetColorDto.class);
		
		return new SuccessDataResult<GetColorDto>(response, Messages.COLORFOUND);
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		checkIfColorNameExists(color.getName());
		this.colorDao.save(color);
		
		return new SuccessResult(Messages.COLORADD);
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		checkIfColorIdExists(color.getId());
		this.colorDao.delete(color);
		
		return new SuccessResult(Messages.COLORDELETE);
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		checkIfColorIdExists(color.getId());
		checkIfColorNameExists(color.getName());
		this.colorDao.save(color);
		
		return new SuccessResult(Messages.COLORUPDATE);

	}

	private boolean checkIfColorNameExists(String colorName) throws BusinessException {
		
		Color color = this.colorDao.getColorByName(colorName);
		if (color == null) {
			return true;
		}
		throw new BusinessException(Messages.COLORNAMEERROR);
	}

	private void checkIfColorIdExists(int colorId) throws BusinessException {
		
		if(!this.colorDao.existsById(colorId)) {
			throw new BusinessException(Messages.COLORNOTFOUND);
		}
	}
	
}
