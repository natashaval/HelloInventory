package hello.inven.helloinven.repository;

import hello.inven.helloinven.model.Asset;
import hello.inven.helloinven.model.AssetId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssetRepository extends JpaRepository<Asset, AssetId> {
}
