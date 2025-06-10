package lavatoryreservation.toilet.service;

import lavatoryreservation.exception.ToiletException;
import lavatoryreservation.lavatory.service.LavatoryService;
import lavatoryreservation.reservation.domain.Toilet;
import lavatoryreservation.toilet.repository.ToiletRepository;
import org.springframework.stereotype.Service;

@Service
public class ToiletService {

    private final ToiletRepository toiletRepository;
    private final LavatoryService lavatoryService;

    public ToiletService(ToiletRepository toiletRepository, LavatoryService lavatoryService) {
        this.toiletRepository = toiletRepository;
        this.lavatoryService = lavatoryService;
    }

    public void addToilet(Toilet toilet) {
        validateDuplicateToilet(toilet);
        lavatoryService.validateExistLavatory(toilet.getLavatory());
        toiletRepository.save(toilet);
    }

    private void validateDuplicateToilet(Toilet toilet) {
        if (toiletRepository.existsByDescriptionAndLavatory_Id(toilet.getDescription(), toilet.getLavatory().getId())) {
            throw new ToiletException("중복된 설명을 가진 화장실입니다");
        }
    }
}
