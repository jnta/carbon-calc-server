package br.com.actionlabs.carboncalc.repository;

import br.com.actionlabs.carboncalc.model.CarbonCalculator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarbonCalculatorRepository extends MongoRepository<CarbonCalculator, String> {
}
