package com.hmq.universe.controller.impl;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
	public List<Model> serach(HttpServletRequest request, Integer pageIndex, Integer pageSize, String orderBy, String order) {
		Map<String, Object> filter = getParams(request);
		List<Model> modelList = null;
		if (pageIndex != null) {
			modelList = this.service.findByFilter(filter, pageIndex, pageSize, orderBy, order);
		} else if (orderBy != null) {
			modelList = this.service.findByFilter(filter, orderBy, order);
		} else {
			modelList = this.service.findByFilter(filter);
		}
		return modelList;
	}
	
	@GetMapping("/count")
	public long count(HttpServletRequest request) {
		Map<String, Object> filter = getParams(request);
		return this.service.countByFilter(filter);
	}
	
	@GetMapping("/serachWithPage")
	public Page<Model> serachWithPage(HttpServletRequest request, Integer pageIndex, Integer pageSize, String orderBy, String order) {
		Map<String, Object> filter = getParams(request);
		Page<Model> modelPage = this.service.findByFilterWithPage(filter, pageIndex, pageSize, orderBy, order);
		return modelPage;
	}
	
	private Set<String> pageKeySet=new HashSet<String>();
	{
		pageKeySet.add("pageIndex");
		pageKeySet.add("pageSize");
		pageKeySet.add("orderBy");
		pageKeySet.add("order");
	}

	public Map<String, Object> getParams(HttpServletRequest request) {
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();
		String key = "";
		String value = "";
		while (entries.hasNext()) {
			Entry<String, String[]> entry = entries.next();
			key = (String) entry.getKey();
			if(pageKeySet.contains(key)){
				continue;
			}
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
			returnMap.put(key, value);
		}
		return returnMap;
	}

}
