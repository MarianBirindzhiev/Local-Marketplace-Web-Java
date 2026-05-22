package bg.sofia.uni.fmi.localmarketplace.service;

import bg.sofia.uni.fmi.localmarketplace.repository.ReviewRepository;
import bg.sofia.uni.fmi.localmarketplace.service.contract.ReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
}
