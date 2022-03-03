package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
import com.turkcell.rentACarProject.core.exceptions.BusinessException;
import com.turkcell.rentACarProject.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACarProject.core.utilities.results.DataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACarProject.core.utilities.results.ErrorResult;
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
		List<Color> result = this.colorDao.findAll();
		List<ListColorDto> response = result.stream()
				.map(color -> this.modelMapperService.forDto().map(color, ListColorDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(response, "Success");
	}

	@Override
	public DataResult<GetColorDto> getById(int id) {
		Color color = this.colorDao.getColorById(id);
		if (color != null) {
			GetColorDto response = this.modelMapperService.forDto().map(color, GetColorDto.class);
			return new SuccessDataResult<GetColorDto>(response, "Success");
		}
		return new ErrorDataResult<GetColorDto>("Color.NotFounded");
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if (checkIfColorName(color.getName()).isSuccess()) {
			this.colorDao.save(color);
			return new SuccessResult("Color.Added : " + color.getName());
		}
		return new ErrorResult("Color.NotAdded : " + color.getName());

	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		if (checkIfColorId(color.getId()).isSuccess()) {
			this.colorDao.delete(color);
			return new SuccessResult("Color.Deleted : " + color.getName());
		}
		return new ErrorResult("Color.NotDeleted : " + color.getName());
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		if (!checkIfColorId(color.getId()).isSuccess()) {
			return new ErrorResult("Color.NotUpdated : " + color.getName());
		}
		if (!checkIfColorName(color.getName()).isSuccess()) {
			return new ErrorResult("Color.NotUpdated : " + color.getName());
		}
		this.colorDao.save(color);
		return new SuccessResult("Color.Updated : " + color.getName());
	}

	private Result checkIfColorName(String colorName) throws BusinessException {
		if (this.colorDao.getColorByName(colorName) == null) {
			return new SuccessResult();
		}
		return new ErrorResult("Color already exists!");
	}

	private Result checkIfColorId(int colorId) throws BusinessException {
		if (this.colorDao.getColorById(colorId) != null) {
			return new SuccessResult();
		}
		return new ErrorResult("A color with this id was not found!");
	}
}
