package lavatoryreservation.toilet.controller;

import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.service.ToiletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/toilet/")
public class ToiletController {
    private final ToiletService toiletService;

    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    @PostMapping
    public ResponseEntity<Long> addToilet(AddToiletDto addToiletDto) {
        Long id = toiletService.addToilet(addToiletDto);
        return ResponseEntity.ok(id);
    }
}
