package pe.greenminds.ecomind_backend.ranking.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.greenminds.ecomind_backend.ranking.application.internal.services.FamilyReportService;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.resources.FamilyReportResource;
import pe.greenminds.ecomind_backend.ranking.interfaces.rest.transform.FamilyReportResourceFromReportAssembler;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/family-reports", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Family Reports", description = "Weekly Family Progress Report Endpoints")
public class FamilyReportsController {

    private final FamilyReportService familyReportService;

    public FamilyReportsController(FamilyReportService familyReportService) {
        this.familyReportService = familyReportService;
    }

    @GetMapping("/{familyId}")
    public ResponseEntity<FamilyReportResource> getWeeklyReport(@PathVariable Long familyId) {
        var report = familyReportService.generateWeeklyReport(familyId);
        return ResponseEntity.ok(FamilyReportResourceFromReportAssembler.toResourceFromReport(report));
    }
}
