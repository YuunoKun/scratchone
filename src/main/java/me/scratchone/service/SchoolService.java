package me.scratchone.service;

import me.scratchone.domain.School;

import java.util.List;

public interface SchoolService {

    List<School> queryForAllSchools();

    List<School> queryForSchools(String type);

    School queryForSchool(String name);

    School queryForSchool(int sid);
}
