package me.scratchone.dao;

import me.scratchone.domain.School;

import java.util.List;

public interface SchoolDao {

    List<School> findSchoolByType(String type);

    List<School> findAllSchools();

    School findSchoolByName(String name);

    School findSchoolBySid(int sid);
}
