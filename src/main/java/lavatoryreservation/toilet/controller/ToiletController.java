package lavatoryreservation.toilet.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lavatoryreservation.toilet.domain.Toilet;
import lavatoryreservation.toilet.dto.AddToiletDto;
import lavatoryreservation.toilet.dto.ToiletDto;
import lavatoryreservation.toilet.service.ToiletService;

@RestController
@RequestMapping("/api/toilet/")
@Tag(name = "화장실 관리", description = "화장실 등록 및 조회 관련 API")
public class ToiletController {
    private final ToiletService toiletService;

    public ToiletController(ToiletService toiletService) {
        this.toiletService = toiletService;
    }

    @Operation(summary = "화장실 등록", description = "새로운 화장실을 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "화장실 등록 성공",
                    content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 데이터"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 화장실실 ID")
    })
    @PostMapping
    public ResponseEntity<Long> addToilet(
            @Parameter(description = "화장실 등록 정보", required = true)
            @RequestBody AddToiletDto addToiletDto) {
        Long id = toiletService.addToilet(addToiletDto);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "화장실 목록 조회", description = "등록된 모든 화장실 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "화장실 목록 조회 성공",
                    content = @Content(schema = @Schema(implementation = ToiletDto.class))),
            @ApiResponse(responseCode = "500", description = "서버 내부 오류")
    })
    @GetMapping
    public ResponseEntity<List<ToiletDto>> getToilets(){
        List<Toilet> toilets = toiletService.getToilets();
        List<ToiletDto> toiletDtos = toilets.stream()
                .map(toilet -> new ToiletDto(toilet.getDescription(),toilet.getLavatory().getDescription(),toilet.getLavatory().getSex().getDescription()))
                .toList();
        return ResponseEntity.ok(toiletDtos);
    }
}
