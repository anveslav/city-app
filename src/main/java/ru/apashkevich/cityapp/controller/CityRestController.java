package ru.apashkevich.cityapp.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
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

    @PreAuthorize("hasRole('ROLE_ALLOW_EDIT')")
    @PatchMapping("/cities/{id}")
    public ResponseEntity<Void> updateCity(@PathVariable Integer id, @RequestBody City city) {
        cityService.updateCity(id, city);
        return ResponseEntity.ok().build();
    }
}
