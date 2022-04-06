package ru.apashkevich.cityapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.apashkevich.cityapp.dto.CityResponseDto;
import ru.apashkevich.cityapp.model.City;
import ru.apashkevich.cityapp.service.CityService;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class CityRestController {

    private final CityService cityService;

    @PreAuthorize("hasRole('ROLE_ALLOW_VIEW')")
    @GetMapping("/cities")
    public ResponseEntity<CityResponseDto> getCities(@RequestParam Integer offset,
                                                     @RequestParam Integer limit,
                                                     @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(cityService.getCities(offset, limit, searchText));
    }

    @PreAuthorize("hasRole('ROLE_ALLOW_VIEW')")
    @GetMapping("/citiesV2")
    public ResponseEntity<CityResponseDto> getCitiesV2(@RequestParam Integer page,
                                                       @RequestParam Integer limit,
                                                       @RequestParam(required = false) String searchText) {
        return ResponseEntity.ok(cityService.getCitiesV2(page, limit, searchText));
    }

    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    @PatchMapping("/cities/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable Integer id, @RequestBody City city) {
        cityService.updateCity(id, city);
        return ResponseEntity.ok().build();
    }

}
