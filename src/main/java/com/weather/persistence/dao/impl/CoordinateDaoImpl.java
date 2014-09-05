package com.weather.persistence.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.weather.persistence.dao.CoordinateDao;
import com.weather.persistence.model.Coordinate;
@Repository
@Transactional
public class CoordinateDaoImpl extends GenericDaoImpl<Coordinate> implements CoordinateDao{

}
