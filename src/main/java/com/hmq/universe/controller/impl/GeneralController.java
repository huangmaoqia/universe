package com.hmq.universe.controller.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hmq.universe.model.po.CommonModel;
import com.hmq.universe.service.IGeneralService;

public class GeneralController<Model extends CommonModel<ID>, ID extends Serializable, Service extends IGeneralService<Model, ID>> {
	@Autowired
	private Service service;

	protected Service getService() {
		return this.service;
	}

	@GetMapping("/get/{id}")
	public Model getById(@PathVariable ID id) {
		Model model = this.service.getById(id);
		return model;
	}

	@PostMapping("/delete/{id}")
	public void deleteById(ID id) {
		this.service.deleteById(id);
	}

	@PostMapping("/saveOne")
	public ID saveOne(@RequestBody Model entity) {
		return this.service.saveOne(entity).getId();
	}

	@PostMapping("/saveAll")
	public int saveAll(@RequestBody List<Model> entities) {
		return this.service.saveAll(entities).size();
	}

	@GetMapping("/serach")
	public List<Model> serach(HttpServletRequest request, Integer page, Integer pageSize, String sort, String dsc) {
		Map<String, Object> filter = getParams(request);
		List<Model> modelList = null;
		if (page != null) {
			modelList = this.service.findByFilter(filter, page, pageSize, sort, dsc);
		} else if (sort != null) {
			modelList = this.service.findByFilter(filter, sort, dsc);
		} else {
			modelList = this.service.findByFilter(filter);
		}

		return modelList;
	}

	public Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			Entry<String, String[]> entry = entries.next();
			name = (String) entry.getKey();
			//pageIndex,pageSize,orderBy,order
			Object valueObj = entry.getValue();
			if (null == valueObj) {
				value = "";
			} else if (valueObj instanceof String[]) {
				String[] values = (String[]) valueObj;
				for (int i = 0; i < values.length; i++) {
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}

}
