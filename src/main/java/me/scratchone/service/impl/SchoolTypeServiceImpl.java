package me.scratchone.service.impl;

import me.scratchone.dao.SchoolTypeDao;
import me.scratchone.dao.impl.SchoolTypeDaoImpl;
import me.scratchone.domain.SchoolType;
import me.scratchone.service.SchoolTypeService;

import java.util.List;

public class SchoolTypeServiceImpl implements SchoolTypeService {

    private SchoolTypeDao schoolTypeDao = new SchoolTypeDaoImpl();

    @Override
    public List<SchoolType> getAllSchoolTypes() { return schoolTypeDao.getAll(); }
}
