package com.prologiccreations.payrollapplication.service.config;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import com.prologiccreations.payrollapplication.dao.config.HolidayRepository;
import com.prologiccreations.payrollapplication.dto.Response;
import com.prologiccreations.payrollapplication.model.config.Holidays;
import com.prologiccreations.payrollapplication.service.super_classes.config.HolidayService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class HolidayServiceImpl implements HolidayService {

    private final HolidayRepository holidayRepository;

    @Override
    public Holidays saveHoliday(Holidays holiday) {
        return holidayRepository.save(holiday);
    }

    @Override
    public List<Holidays> getAllHolidays() {
        return holidayRepository.findAll();
    }

    @Override
    public Holidays getHolidayById(Long id) {
        Optional<Holidays> holiday = holidayRepository.findById(id);
        return holiday.orElseThrow(() -> new RuntimeException("Holiday not found with id: " + id));
    }

    @Override
    public Holidays updateHoliday(Long id, Holidays holidayDetails) {
        Holidays holiday = getHolidayById(id);
        holiday.setYear(holidayDetails.getYear());
        holiday.setMonth(holidayDetails.getMonth());
        holiday.setDates(holidayDetails.getDates());
        return holidayRepository.save(holiday);
    }

    @Override
    public void deleteHoliday(Long id) {
        Holidays holiday = getHolidayById(id);
        holidayRepository.delete(holiday);
    }

    @Override
    public List<Holidays> findHolidaysByYearAndMonth(int year, int month) {
        return holidayRepository.findByYearAndMonth(year, month);
    }

    @Override
    @NonNull
    public Response storeData(@NonNull Holidays data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'storeData'");
    }

    @Override
    @NonNull
    public Response<Page<Holidays>> getAll(@NonNull Pageable pageable) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAll'");
    }

    @Override
    @NonNull
    public Response<Holidays> getById(@NonNull Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    @NonNull
    public Response delete(@NonNull Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    @Nullable
    public String validate(@NonNull Holidays data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'validate'");
    }

    @Override
    @Nullable
    public String checkDuplicate(@NonNull Holidays data) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkDuplicate'");
    }
}
