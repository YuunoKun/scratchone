package me.scratchone.service.impl;

import me.scratchone.dao.SchoolDao;
import me.scratchone.dao.impl.SchoolDaoImpl;
import me.scratchone.domain.School;
import me.scratchone.service.SchoolService;

import java.util.List;

public class SchoolServiceImpl implements SchoolService {

    private SchoolDao schoolDao = new SchoolDaoImpl();

    @Override
    public List<School> queryForAllSchools() { return schoolDao.findAllSchools(); }

    @Override
    public List<School> queryForSchools(String type) { return schoolDao.findSchoolByType(type); }

    @Override
    public School queryForSchool(String name) { return schoolDao.findSchoolByName(name); }

    @Override
    public School queryForSchool(int sid) { return schoolDao.findSchoolBySid(sid); }
}
