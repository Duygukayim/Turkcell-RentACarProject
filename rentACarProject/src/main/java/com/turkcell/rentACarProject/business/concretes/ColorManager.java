package com.turkcell.rentACarProject.business.concretes;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentACarProject.business.abstracts.ColorService;
import com.turkcell.rentACarProject.business.dtos.get.GetColorDto;
import com.turkcell.rentACarProject.business.dtos.list.ListAdditionalServiceDto;
import com.turkcell.rentACarProject.business.dtos.list.ListColorDto;
import com.turkcell.rentACarProject.business.requests.color.CreateColorRequest;
import com.turkcell.rentACarProject.business.requests.color.DeleteColorRequest;
import com.turkcell.rentACarProject.business.requests.color.UpdateColorRequest;
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
		List<Color> result = colorDao.findAll();
		 if (result.isEmpty()) {
	            return new ErrorDataResult<List<ListColorDto>>("Color.NotListed");
	        }
		List<ListColorDto> response = result.stream()
				.map(color -> modelMapperService.forDto().map(color, ListColorDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<ListColorDto>>(response, "Success");
	}

	@Override
	public DataResult<GetColorDto> getById(int id) {
		Color color = colorDao.getById(id);
		if (checkIfColorId(color.getId())) {
			GetColorDto response = modelMapperService.forDto().map(color, GetColorDto.class);
			return new SuccessDataResult<GetColorDto>(response, "Success");
		}
		return new ErrorDataResult<GetColorDto>("Color.NotFounded , A color with this ID was not found!");
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		if (!checkIfColorName(color.getName())) {
			this.colorDao.save(color);
			return new SuccessResult("Color.Added : " + color.getName());
		}
		return new ErrorResult("Color.NotAdded : " + color.getName() + " , Color already exists!");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) {
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		if (checkIfColorId(color.getId())) {
			this.colorDao.delete(color);
			return new SuccessResult("Color.Deleted : " + color.getName());
		}
		return new ErrorResult("Color.NotDeleted : " + color.getName() + " , A color with this ID was not found!");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		if (checkIfColorId(color.getId())) {
			this.colorDao.save(color);
			return new SuccessResult("Color.Updated : " + color.getName());
		}
		return new ErrorResult("Color.NotUpdated : " + color.getName() + " , A color with this ID was not found!");
	}

	private boolean checkIfColorName(String colorName) {
		return Objects.nonNull(colorDao.getColorByName(colorName));
//		return new ErrorResult("Color already exists!");
	}

	private boolean checkIfColorId(int colorId) {
		return Objects.nonNull(colorDao.getColorById(colorId));
//		return new ErrorResult("A color with this ID was not found!");
	}
}
