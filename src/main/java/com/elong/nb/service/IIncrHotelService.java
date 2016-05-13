package com.elong.nb.service;

import java.util.Date;

import com.elong.nb.model.IncrHotel;

public interface IIncrHotelService {

	public IncrHotel getLastIncrHotel(Date lastTime);

}
