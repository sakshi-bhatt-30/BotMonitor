package msl.rpamonitoring.application.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import msl.rpamonitoring.application.Entity.Organization;

@Repository
public interface OrganizationRepository extends JpaRepository<Organization,Long> {

}
